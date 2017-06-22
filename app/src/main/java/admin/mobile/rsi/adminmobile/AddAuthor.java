package admin.mobile.rsi.adminmobile;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import admin.mobile.rsi.adminmobile.api.ApiConnector;
import admin.mobile.rsi.adminmobile.api.models.AuthorRequest;
import admin.mobile.rsi.adminmobile.business.dialogs.Dialogs;
import admin.mobile.rsi.adminmobile.utils.async.AsyncRequestListener;
import admin.mobile.rsi.adminmobile.utils.http.HttpResponse;

public class AddAuthor extends AppCompatActivity {

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_author);
        setTitle(getString(R.string.app_name) + " / " + getString(R.string.dodaj_autora));

        final EditText nameField = (EditText) findViewById(R.id.addAuthorNameText);
        final EditText surnameField = (EditText) findViewById(R.id.addAuthorSurnameText);
        final EditText dateField = (EditText) findViewById(R.id.addAuthorDateText);

        final Button addButton = (Button) findViewById(R.id.addAuthorButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog al = Dialogs.showWaitDialog(context); al.show();

                AuthorRequest authorRequest = new AuthorRequest();
                authorRequest.setName(nameField.getText().toString());
                authorRequest.setSurname(surnameField.getText().toString());
                String date = dateField.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                long dd = 0;
                try {
                    dd = sdf.parse(date).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                authorRequest.setBirthDate(dd);

                ApiConnector.addAuthor(authorRequest, new AsyncRequestListener() {
                    @Override
                    public void done(HttpResponse response) {
                        if (response != null && response.getStatusCode() == 200) {
                            Dialogs.showInfoDialog(context, "Sukces", "Autor został pomyślnie dodany");
                        } else {
                            Dialogs.showInfoDialog(context, "Błąd " + response.getStatusCode(), "Autor nie został dodany");
                        }
                        al.dismiss();
                    }
                });

            }


        });

    }
}
