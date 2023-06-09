package com.gini.config;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
/**
 * https://codingnconcepts.com/spring-boot/customize-jackson-json-mapper/
 * */

@Configuration
public class ObjectMapperConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder.serializationInclusion(JsonInclude.Include.NON_NULL)
                .featuresToEnable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
                .featuresToEnable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
                .failOnUnknownProperties(false)
                .featuresToDisable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .modules(new JavaTimeModule()
                        .addSerializer(OffsetDateTime.class,
                                new OffsetDateTimeSerializer(
                                        OffsetDateTimeSerializer.INSTANCE,
                                        false,
                                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
                                        JsonFormat.Shape.STRING))
                );
    }
}
