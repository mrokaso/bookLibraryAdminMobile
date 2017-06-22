package admin.mobile.rsi.adminmobile.exceptions;

public class HttpClientException extends RuntimeException {
    private static final long serialVersionUID = 6212059497246133316L;

    public HttpClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpClientException(String message) {
        super(message);
    }

    public HttpClientException(Throwable cause) {
        super(cause);
    }

    public HttpClientException() {
        super();
    }
}

