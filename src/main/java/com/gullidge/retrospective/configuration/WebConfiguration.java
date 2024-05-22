package com.gullidge.retrospective.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.core.env.ConfigurableEnvironment;

import jakarta.annotation.PostConstruct;

@Configuration
public class WebConfiguration {

    @Autowired
    private ConfigurableEnvironment environment;

    @PostConstruct
    public void addCustomConverters() {
        ConfigurableConversionService conversionService = environment.getConversionService();
        conversionService.addConverter(new FeedbackTypeConverter());
    }    
    
}
