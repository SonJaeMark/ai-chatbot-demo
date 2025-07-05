package com.github.sonjaemark.ai_chatbot_demo.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("") // Required!
public class MainView extends VerticalLayout {
    public MainView() {
        add(new H1("Welcome to AI Chatbot Demo"));
        add(new Button("Say Hello", e -> e.getSource().setText("Hello again!")));
    }
}

