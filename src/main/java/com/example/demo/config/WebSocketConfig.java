package com.example.demo.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import com.example.demo.service.MyWebSocketHandler;
/*
 * This class is used to configure WebSocket support in your Spring application. 
 * 
 * Typically, you'll use the '@Configuration' annotation to create a configuration class that 
 * implements 'WebSocketConfigurer' or extends 'AbstractWebSocketMessageBrokerConfigurer' to define your WebSocket endpoints.
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new MyWebSocketHandler(), "/ws").setAllowedOrigins("*");
    }
}
