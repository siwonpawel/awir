package com.github.siwonpawel.awir.integration;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.http.dsl.Http;

import com.github.siwonpawel.awir.domain.User;
import com.github.siwonpawel.awir.services.UserService;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;

@Slf4j
@Configuration
@EnableIntegration
@RequiredArgsConstructor
public class IntegrationConfiguration
{

    private static final Logger log = LoggerFactory.getLogger(IntegrationConfiguration.class);
    private final UserService userService;

    @Bean
    IntegrationFlow getFlow()
    {
        return IntegrationFlow
                .from(
                        Http.inboundGateway("/api/2/users")
                                .requestMapping(m -> m.methods(HttpMethod.GET))
                                .requestPayloadType(String.class))
                .handle(List.class, (payload, headers) -> {
                    log.info("GET flow activated");
                    return userService.findAll();
                })
                .get();
    }

    @Bean
    IntegrationFlow postFlow()
    {
        return IntegrationFlow
                .from(
                        Http.inboundGateway("/api/2/users")
                                .requestMapping(m -> m.methods(HttpMethod.POST)
                                        .consumes(MediaType.APPLICATION_JSON_VALUE))
                                .requestPayloadType(User.class))
                .handle(User.class, (payload, headers) -> userService.save(payload, null))
                .get();
    }

    @Bean
    IntegrationFlow putFlow()
    {
        return IntegrationFlow
                .from(
                        Http.inboundGateway("/api/2/users")
                                .requestMapping(m -> m.methods(HttpMethod.PUT)
                                        .consumes(MediaType.APPLICATION_JSON_VALUE))
                                .requestPayloadType(User.class))
                .handle(User.class, (payload, headers) -> userService.save(payload, null))
                .get();
    }

    @Bean
    IntegrationFlow deleteFlow()
    {
        return IntegrationFlow
                .from(
                        Http.inboundGateway("/api/2/users/{id}")
                                .requestMapping(m -> m.methods(HttpMethod.DELETE))
                                .payloadExpression("#pathVariables.id")
                                .requestPayloadType(Long.class))
                .handle(Long.class, (payload, headers) -> {
                    userService.deleteById(payload);
                    return ResponseEntity.ok().build();
                })
                .get();
    }
}
