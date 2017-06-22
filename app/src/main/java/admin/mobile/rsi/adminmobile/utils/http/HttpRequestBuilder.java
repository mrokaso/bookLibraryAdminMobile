package admin.mobile.rsi.adminmobile.utils.http;

import java.util.HashMap;

public class HttpRequestBuilder {

    private HttpRequest request;

    public HttpRequestBuilder() {
        request = new HttpRequest();
        request.headers = new HashMap<>();
    }

    public HttpRequestBuilder withMethod(HttpMethod method) {
        request.method = method;
        return this;
    }

    public HttpRequestBuilder withUri(String uri) {
        request.uri = uri;
        return this;
    }

    public HttpRequestBuilder withBody(Object body) {
        request.body = body;
        return this;
    }

    public HttpRequestBuilder withHeader(String key, String value) {
        request.headers.put(key, value);
        return this;
    }

    public HttpRequest build() {
        return request;
    }

}

