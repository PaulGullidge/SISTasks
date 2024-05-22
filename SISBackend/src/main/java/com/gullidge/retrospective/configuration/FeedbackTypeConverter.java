package com.gullidge.retrospective.configuration;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.gullidge.retrospective.model.FeedbackType;

@Component
public class FeedbackTypeConverter implements Converter<String, FeedbackType> {

    @Override
    public FeedbackType convert(String source) {
    	System.out.println("converting " + source);
        return FeedbackType.valueOf(source.toUpperCase());
    }
	
}
