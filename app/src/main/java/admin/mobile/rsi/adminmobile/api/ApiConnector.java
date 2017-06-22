package admin.mobile.rsi.adminmobile.api;


import android.content.Context;
import android.os.AsyncTask;

import java.util.Timer;
import java.util.TimerTask;

import admin.mobile.rsi.adminmobile.api.models.AuthorRequest;
import admin.mobile.rsi.adminmobile.api.models.BookRequest;
import admin.mobile.rsi.adminmobile.api.models.SearchAuthorCriteria;
import admin.mobile.rsi.adminmobile.business.dialogs.Dialogs;
import admin.mobile.rsi.adminmobile.utils.async.AsyncRequestListener;
import admin.mobile.rsi.adminmobile.utils.async.AsyncRequestSender;
import admin.mobile.rsi.adminmobile.utils.http.HttpMethod;
import admin.mobile.rsi.adminmobile.utils.http.HttpRequest;
import admin.mobile.rsi.adminmobile.utils.http.HttpRequestBuilder;
import admin.mobile.rsi.adminmobile.utils.http.HttpResponse;

public class ApiConnector {

    private static String AUTHORIZATION_HEADER = "Authorization";
    private static String API_LOCATION = "https://timebender-crud.herokuapp.com";
    private static String authToken = "";
    private static long refreshDelay = 300000; // 5 minutes
    private static Timer timer;

    private static synchronized String getAuthToken() {
        return authToken;
    }

    private static synchronized void setAuthToken(String newAuthToken) {
        authToken = newAuthToken;
    }

    public static void getToken() {
        new AsyncRequestSender(new AsyncRequestListener() {
            @Override
            public void done(HttpResponse response) {
                if(response != null && response.getStatusCode() == 200) {
                    setAuthToken(new String(response.getBody()));
                    if(timer != null) timer.cancel();
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            refreshToken();
                        }
                    }, refreshDelay, refreshDelay);
                }
            }
        }).execute(new HttpRequestBuilder()
                .withMethod(HttpMethod.GET)
                .withUri(API_LOCATION + "/login/1")
                .build());
    }

    private static void refreshToken() {
        new AsyncRequestSender(new AsyncRequestListener() {
            @Override
            public void done(HttpResponse response) {
                if(response != null && response.getStatusCode() == 200) {
                    setAuthToken(new String(response.getBody()));
                }
            }
        }).execute(new HttpRequestBuilder()
                .withMethod(HttpMethod.GET)
                .withUri(API_LOCATION + "/login/1")
                .build());
    }


    public static void addAuthor(final AuthorRequest authorRequest, final AsyncRequestListener listener) {
        new AsyncRequestSender(listener).execute(new HttpRequestBuilder()
                .withMethod(HttpMethod.POST)
                .withHeader(AUTHORIZATION_HEADER, getAuthToken())
                .withBody(authorRequest)
                .withUri(API_LOCATION + "/author")
                .build());
    }

    public static void addBook(final BookRequest bookRequest, final AsyncRequestListener listener) {
        new AsyncRequestSender(listener).execute(new HttpRequestBuilder()
                .withMethod(HttpMethod.POST)
                .withHeader(AUTHORIZATION_HEADER, getAuthToken())
                .withBody(bookRequest)
                .withUri(API_LOCATION + "/book")
                .build());
    }

    public static void searchAuthor(final SearchAuthorCriteria searchAuthorCriteria, final AsyncRequestListener listener) {
        new AsyncRequestSender(listener).execute(new HttpRequestBuilder()
                .withMethod(HttpMethod.POST)
                .withHeader(AUTHORIZATION_HEADER, getAuthToken())
                .withBody(searchAuthorCriteria)
                .withUri(API_LOCATION + "/author/search")
                .build());
    }

    public static void removeAuthor(final String id, final AsyncRequestListener listener) {
        new AsyncRequestSender(listener).execute(new HttpRequestBuilder()
                .withMethod(HttpMethod.DELETE)
                .withHeader(AUTHORIZATION_HEADER, getAuthToken())
                .withUri(API_LOCATION + "/author/" + id)
                .build());
    }


}
