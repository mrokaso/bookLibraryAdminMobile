package admin.mobile.rsi.adminmobile.utils.async;


import admin.mobile.rsi.adminmobile.utils.http.HttpResponse;

public interface AsyncRequestListener {
    void done(HttpResponse response);
}
