package admin.mobile.rsi.adminmobile.utils.http;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import admin.mobile.rsi.adminmobile.exceptions.HttpClientException;

public class HttpResponse {
    private int statusCode;
    private String body;

    HttpResponse(
            int code,
            String body) {
        this.statusCode = code;
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getBody() {
        return body;
    }

    public <T> T getBody(TypeReference<?> valueTypeRef) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(body, valueTypeRef);
        } catch (Exception e) {
            throw new HttpClientException(e);
        }
    }

    public <T> T getBody(Class<T> valueType) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(body, valueType);
        } catch (Exception e) {
            throw new HttpClientException(e);
        }
    }
}

