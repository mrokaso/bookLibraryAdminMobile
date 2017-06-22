package admin.mobile.rsi.adminmobile.utils.http;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import admin.mobile.rsi.adminmobile.exceptions.HttpClientException;


public class HttpRequest {

    HttpMethod method;
    String uri;
    Object body;
    Map<String, String> headers;

    HttpRequest() {}

    public HttpResponse send() {
        try {
            HttpURLConnection connection = makeConnection();
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return new HttpResponse(responseCode, getResponse(connection));
            } else {
                return new HttpResponse(responseCode, getErrorResponse(connection));
            }
        } catch (Exception e) {
            throw new HttpClientException(e);
        }
    }

    private HttpURLConnection makeConnection() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(this.uri).openConnection();
        connection.setRequestMethod(this.method.name());
        connection.setConnectTimeout(5000);
        connection.setRequestProperty("Content-Type", "application/json");

        for (Entry<String, String> header : this.headers.entrySet()) {
            connection.setRequestProperty(header.getKey(), header.getValue());
        }

        if (this.body != null && (this.method == HttpMethod.PUT || this.method == HttpMethod.POST)) {
            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            Gson gson = new GsonBuilder().create();
            os.write(gson.toJson(this.body).getBytes());
            os.flush();
            os.close();
        }

        return connection;
    }

    private String getResponse(HttpURLConnection connection) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    private String getErrorResponse(HttpURLConnection connection) throws IOException {
        String errorResponse = "";
        byte[] buf = new byte[1024];
        while (true) {
            int n = connection.getErrorStream().read(buf);
            if (n > 0) {
                errorResponse += new String(buf).substring(0, n);
            } else {
                break;
            }
        }
        return errorResponse;
    }


    @Override
    public String toString() {
        return "{method: " + method.name() +
                ",uri: " + this.uri +
                ",body: " + body +
                ",headers: " + headers + "}";
    }
}

