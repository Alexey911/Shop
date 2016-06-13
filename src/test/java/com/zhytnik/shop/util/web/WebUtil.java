package com.zhytnik.shop.util.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.Charset;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * @author Alexey Zhytnik
 * @since 13.06.2016
 */
public class WebUtil {

    public static final MediaType APPLICATION_JSON_UTF8;

    static {
        APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
                MediaType.APPLICATION_JSON.getSubtype(),
                Charset.forName("UTF-8")
        );
    }

    private WebUtil() {
    }

    public static byte[] convertToJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(NON_NULL);
        try {
            return mapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultMatcher content(String value) {
        return MockMvcResultMatchers.content().string(value);
    }

    public static ResultMatcher content(Long value) {
        return content(Long.toString(value));
    }
}
