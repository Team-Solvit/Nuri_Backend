package nuri.nuri_server.global.config;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.global.exception.stomp.handler.StompAuthenticationExceptionHandler;
import nuri.nuri_server.global.websocket.interceptor.StompConnectionInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final StompConnectionInterceptor stompConnectionInterceptor;
    private final ThreadPoolTaskScheduler messageBrokerTaskScheduler;
    private final StompAuthenticationExceptionHandler stompAuthenticationExceptionHandler;

    @Value("${front-url}")
    private String frontUrl;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        registry.setApplicationDestinationPrefixes("/chat");
        registry.enableSimpleBroker("/chat", "/user")
                .setHeartbeatValue(new long[]{10000, 10000})
                .setTaskScheduler(messageBrokerTaskScheduler);
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/websocket")
                .setAllowedOriginPatterns(frontUrl)
                .withSockJS();
        registry.setErrorHandler(stompAuthenticationExceptionHandler);
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompConnectionInterceptor);
    }
}