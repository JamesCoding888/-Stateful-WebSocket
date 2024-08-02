package com.example.demo.service;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.CloseStatus;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/*
	In Spring Framework, WebSocket support is primarily provided through the `org.springframework.web.socket` package. This package includes several key classes and interfaces for WebSocket communication. Here’s a general overview of the structure:

	1. **WebSocketConfig**: This class is used to configure WebSocket support in your Spring application. Typically, you'll use the `@Configuration` annotation to create a configuration class that implements `WebSocketConfigurer` or extends `AbstractWebSocketMessageBrokerConfigurer` to define your WebSocket endpoints.
	
	    @Configuration // Indicates that this class contains configuration settings
	    public class WebSocketConfig implements WebSocketConfigurer {
	        // Configuration for WebSocket endpoints goes here
	    }
	
	2. **WebSocketHandler**: This interface is used to handle WebSocket messages. You implement this interface to define how to process incoming WebSocket messages and to send messages to the client.
	
	    public interface MyWebSocketHandler extends WebSocketHandler {
	        // Methods for handling messages
	    }
	
	3. **TextWebSocketHandler**: This abstract class is a convenient implementation of `WebSocketHandler` that only handles text messages. You can extend this class to handle text-based WebSocket communication.
	
	    public class MyTextWebSocketHandler extends TextWebSocketHandler {
	        @Override
	        public void handleMessage(WebSocketSession session, TextMessage message) throws Exception {
	            // Handle text messages here
	        }
	    }
	
	4. **WebSocketSession**: Represents a WebSocket session, providing methods to interact with the client, such as sending and receiving messages.
	
	    public void handleSession(WebSocketSession session) {
	        // Interact with WebSocket session
	    }
	
	5. **WebSocketHandlerAdapter**: An adapter that integrates WebSocket handlers with the Spring WebSocket infrastructure. It is typically used internally by Spring's WebSocket support.
	
	    @Configuration
	    public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
	        @Override
	        public void configureWebSocketHandlers(WebSocketHandlerRegistry registry) {
	            registry.addHandler(myWebSocketHandler(), "/ws").setAllowedOrigins("*");
	        }
	    }
	
	6. **WebSocketStompClient**: A client implementation for STOMP (Simple Text Oriented Messaging Protocol) over WebSocket. It’s part of Spring's support for messaging protocols over WebSocket.
	
	    public class MyStompClient {
	        private WebSocketStompClient stompClient;
	
	        public MyStompClient() {
	            stompClient = new WebSocketStompClient(new WebSocketClient());
	            // Configure STOMP client
	        }
	    }
	
	7. **SockJS**: While not directly part of `org.springframework.web.socket`, Spring also supports SockJS, a library that provides a fallback mechanism for WebSocket communication. It’s often used in combination with Spring’s WebSocket support to provide better compatibility with older browsers.
*/
public class MyWebSocketHandler extends TextWebSocketHandler {
	
	// List to keep track of all connected sessions
    private static final List<WebSocketSession> sessions = new ArrayList<>();
    
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Connection established: " + session.getId());
        // Add all connected sessions
        sessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
    	System.out.println("Received message from " + session.getId() + ": " + message.getPayload());    	    	   
        //session.sendMessage(new TextMessage("Server received: " + message.getPayload()));            
    	// Broadcast message to all connected clients
        for (WebSocketSession s : sessions) {
            if (s.isOpen()) {
                s.sendMessage(new TextMessage("Message from " + session.getId() + ": " + message.getPayload()));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    	System.out.println("Connection closed: " + session.getId() + ", Status: " + status);
        // Remove all connected sessions
        sessions.remove(session);
    }
}
