package com.justincranford.springsecurity.webauthn.demo;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.webauthn.jackson.WebauthnJackson2Module;

import static org.springframework.security.web.authentication.ui.DefaultResourcesFilter.webauthn;

@Configuration
@EnableWebSecurity
public class WebauthnConfig {
    @Bean
    public ObjectMapper objectMapper(final Jackson2ObjectMapperBuilder builder) {
        return new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .registerModule(new Jdk8Module())
            .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
            .enable(JsonParser.Feature.INCLUDE_SOURCE_IN_LOCATION)
            .configure(SerializationFeature.INDENT_OUTPUT, true)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false)
            .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
            .configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true)
            .configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true)
            .configure(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY, true)
            .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true)
            .configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false)
            .configure(DeserializationFeature.FAIL_ON_TRAILING_TOKENS, false)
            .configure(DeserializationFeature.FAIL_ON_UNEXPECTED_VIEW_PROPERTIES, true)
            .configure(DeserializationFeature.ACCEPT_FLOAT_AS_INT, false)
            .registerModules(SecurityJackson2Modules.getModules(WebauthnConfig.class.getClassLoader()))
            .registerModule(new WebauthnJackson2Module())
            .activateDefaultTyping(
                LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.PROPERTY
            );
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .formLogin(Customizer.withDefaults())
            .httpBasic(Customizer.withDefaults())
            .authorizeHttpRequests(requests -> requests
                .requestMatchers("/public/**", "/login", "/login/**", "/error").permitAll()
                .requestMatchers("/home").authenticated()
                .anyRequest().authenticated()
            )
            .webAuthn((webAuthn) -> webAuthn
                                        .rpName("WebAuthn Demo Relying Party")
                                        .rpId("localhost")
                                        .allowedOrigins("https://localhost:8443")
            );
        return http.build();
    }
}
