package com.github.sonjaemark.ai_chatbot_demo.config;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import org.springframework.stereotype.Component;

@Component
@PWA(name = "AI Chatbot", shortName = "Chatbot")
public class AppShell implements AppShellConfigurator {
}
