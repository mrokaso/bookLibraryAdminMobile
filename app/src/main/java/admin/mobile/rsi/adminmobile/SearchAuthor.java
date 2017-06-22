package admin.mobile.rsi.adminmobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import admin.mobile.rsi.adminmobile.api.ApiConnector;
import admin.mobile.rsi.adminmobile.api.models.Author;
import admin.mobile.rsi.adminmobile.api.models.SearchAuthorCriteria;
import admin.mobile.rsi.adminmobile.business.dialogs.Dialogs;
import admin.mobile.rsi.adminmobile.utils.async.AsyncRequestListener;
import admin.mobile.rsi.adminmobile.utils.http.HttpResponse;

public class SearchAuthor extends AppCompatActivity {

    private Context context = this;
    private SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_author);
        setTitle(getString(R.string.app_name) + " / " + getString(R.string.szukaj_autora));

        prefs = getSharedPreferences("myPreferencesFileName", MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();
        final EditText nameField = (EditText) findViewById(R.id.searchAuthorName);
        final EditText dateOfField = (EditText) findViewById(R.id.searchAuthorDateOf);
        final EditText dateToField = (EditText) findViewById(R.id.searchAuthorDateTo);

        final Button searchButton = (Button) findViewById(R.id.searchAuthorButton);

        if (prefs.contains("nameAuthor")) {
            nameField.setText(prefs.getString("nameAuthor", ""));
        }

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchAuthorView.class);
                intent.putExtra("name", nameField.getText().toString());
                intent.putExtra("dateOf", dateOfField.getText().toString());
                intent.putExtra("dateTo", dateToField.getText().toString());

                editor.putString("nameAuthor",nameField.getText().toString());
                editor.commit();

                startActivity(intent);
            }
        });
    }
}
