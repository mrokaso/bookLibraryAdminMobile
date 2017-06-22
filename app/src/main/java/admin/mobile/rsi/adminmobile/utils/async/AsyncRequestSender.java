package admin.mobile.rsi.adminmobile.utils.async;

import android.os.AsyncTask;

import admin.mobile.rsi.adminmobile.utils.http.HttpRequest;
import admin.mobile.rsi.adminmobile.utils.http.HttpResponse;

public class AsyncRequestSender extends AsyncTask<HttpRequest, Void, HttpResponse> {

    private AsyncRequestListener observer;

    public AsyncRequestSender(AsyncRequestListener observer) {
        this.observer = observer;
    }

    protected HttpResponse doInBackground(HttpRequest... requests) {
        try {
            return requests[0].send();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void onPostExecute(HttpResponse result) {
        observer.done(result);
    }
}
