package com.github.sonjaemark.ai_chatbot_demo.views;

import com.github.sonjaemark.ai_chatbot_demo.service.ContextAndInstructionService;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Route("/chat")
public class ChatBoxView extends Div {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ContextAndInstructionService contextAndInstructionService;

    @SuppressWarnings("unchecked")
    public ChatBoxView(ContextAndInstructionService contextAndInstructionService) {
        this.contextAndInstructionService = contextAndInstructionService;
        String sessionId = getSessionId();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        

        Dialog chatDialog = new Dialog();
        chatDialog.setModal(false);
        chatDialog.setDraggable(true);
        chatDialog.setResizable(true);
        chatDialog.setWidth("300px");
        chatDialog.setHeight("400px");

        VerticalLayout chatLayout = new VerticalLayout();
        chatLayout.setPadding(true);
        chatLayout.setSpacing(true);

        Div chatHistory = new Div();
        chatHistory.getStyle()
                .set("overflow-y", "auto")
                .set("max-height", "280px")
                .set("white-space", "pre-wrap");

        chatHistory.getElement().setProperty("innerHTML",
                "<p><strong style='color:green;'>Gemini:</strong> 👋 Hello! How can I help you?</p>");

        TextField userPrompt = new TextField();
        userPrompt.setPlaceholder("Type your message...");
        userPrompt.setWidthFull();

        Button sendButton = new Button("Send", click -> {
            String prompt = userPrompt.getValue();
            ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                "https://ai-chatbot-0mmy.onrender.com/gemini/chat?prompt={prompt}&sessionId={sessionId}",
                HttpMethod.POST,
                entity,
                (Class<Map<String, Object>>)(Class<?>)Map.class, // <== Use Map to parse the JSON
                prompt,
                sessionId
            );

            Map<String, Object> responseBody = responseEntity.getBody();

            String geminiAnswer = (String) responseBody.get("response"); 

            chatHistory.getElement().setProperty("innerHTML",
                    chatHistory.getElement().getProperty("innerHTML") +
                            "<p><strong style='color:blue;'>You:</strong> " + escapeHtml(prompt) + "</p>" +
                            "<p><strong style='color:green;'>AI Assistant:</strong> " + escapeHtml(geminiAnswer) + "</p>"
            );

            userPrompt.clear();
        });

        chatLayout.add(chatHistory, userPrompt, sendButton);
        chatDialog.add(chatLayout);

        Button floatingChatButton = new Button(new Icon(VaadinIcon.COMMENT));
        floatingChatButton.getStyle()
                .set("position", "fixed")
                .set("bottom", "20px")
                .set("right", "20px")
                .set("border-radius", "50%")
                .set("box-shadow", "0 2px 10px rgba(0,0,0,0.3)");

        floatingChatButton.addClickListener(e -> {
            if (chatDialog.isOpened()) {
                chatDialog.close();
            } else {
                chatDialog.open();
            }
        });

        add(floatingChatButton, chatDialog);
    }

    private String escapeHtml(String text) {
        return text == null ? "" : text
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }

    @SuppressWarnings("unchecked")
    private String getSessionId() {
        String context = contextAndInstructionService.getContext();
        String instruction = contextAndInstructionService.getInstruction();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(context, headers);

        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
            "https://ai-chatbot-0mmy.onrender.com/gemini/upload-context?instruction={instruction}",
            HttpMethod.POST,
            entity,
            (Class<Map<String, Object>>)(Class<?>)Map.class, // <== Use Map to parse the JSON
            instruction
        );

        Map<String, Object> responseBody = responseEntity.getBody();
        String sessionId = (String) responseBody.get("sessionId"); 

        return sessionId;
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        UI.getCurrent().getPage().executeJs("document.body.style.overflow='hidden';");
    }
}
