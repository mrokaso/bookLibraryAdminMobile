package admin.mobile.rsi.adminmobile;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import admin.mobile.rsi.adminmobile.api.ApiConnector;
import admin.mobile.rsi.adminmobile.api.models.BookRequest;
import admin.mobile.rsi.adminmobile.business.dialogs.Dialogs;
import admin.mobile.rsi.adminmobile.utils.async.AsyncRequestListener;
import admin.mobile.rsi.adminmobile.utils.http.HttpResponse;

public class AddBook extends AppCompatActivity {

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        setTitle(getString(R.string.app_name) + " / " + getString(R.string.dodaj_ksiazke));

        final EditText titleField = (EditText) findViewById(R.id.addBookTitle);
        final EditText placeField = (EditText) findViewById(R.id.addBookPlace);
        final EditText yearField = (EditText) findViewById(R.id.addBookYear);
        final EditText publisherField = (EditText) findViewById(R.id.addBookPublisher);
        final EditText pagesField = (EditText) findViewById(R.id.addBookPages);
        final EditText isbnField = (EditText) findViewById(R.id.addBookIsbn);
        final EditText imageField = (EditText) findViewById(R.id.addBookImage);
        final EditText weightField = (EditText) findViewById(R.id.addBookWeight);
        final EditText descField = (EditText) findViewById(R.id.addBookDesc);
        final EditText amountField = (EditText) findViewById(R.id.addBookAmount);

        final Button addButton = (Button) findViewById(R.id.addBookButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog al = Dialogs.showWaitDialog(context); al.show();

                BookRequest bookRequest = new BookRequest();
                if(!amountField.getText().toString().isEmpty())
                    bookRequest.setCount(Integer.parseInt(amountField.getText().toString()));
                if(!descField.getText().toString().isEmpty())
                    bookRequest.setDescription(descField.getText().toString());
                if(!weightField.getText().toString().isEmpty())
                    bookRequest.setWeight(weightField.getText().toString());
                if(!imageField.getText().toString().isEmpty())
                    bookRequest.setImage(imageField.getText().toString());
                if(!isbnField.getText().toString().isEmpty())
                    bookRequest.setIsbn(isbnField.getText().toString());
                if(!pagesField.getText().toString().isEmpty())
                    bookRequest.setPages(Integer.parseInt(pagesField.getText().toString()));
                if(!publisherField.getText().toString().isEmpty())
                    bookRequest.setPublisher(publisherField.getText().toString());
                if(!yearField.getText().toString().isEmpty())
                    bookRequest.setPublicationYear(Integer.parseInt(yearField.getText().toString()));
                if(!placeField.getText().toString().isEmpty())
                    bookRequest.setPublicationPlace(placeField.getText().toString());
                if(!titleField.getText().toString().isEmpty())
                    bookRequest.setTitle(titleField.getText().toString());

                ApiConnector.addBook(bookRequest, new AsyncRequestListener() {
                    @Override
                    public void done(HttpResponse response) {
                        if (response != null && response.getStatusCode() == 200) {
                            Dialogs.showInfoDialog(context, "Sukces", "Książka została pomyślnie dodana");
                        } else {
                            Dialogs.showInfoDialog(context, "Błąd " + response.getStatusCode(), "Książka nie została dodana");
                        }
                        al.dismiss();
                    }
                });

            }
        });
    }
}
