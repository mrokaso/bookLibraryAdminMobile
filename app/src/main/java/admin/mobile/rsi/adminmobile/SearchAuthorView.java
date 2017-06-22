package admin.mobile.rsi.adminmobile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import admin.mobile.rsi.adminmobile.api.ApiConnector;
import admin.mobile.rsi.adminmobile.api.models.Author;
import admin.mobile.rsi.adminmobile.api.models.SearchAuthorCriteria;
import admin.mobile.rsi.adminmobile.business.dialogs.Dialogs;
import admin.mobile.rsi.adminmobile.models.Page;
import admin.mobile.rsi.adminmobile.utils.async.AsyncRequestListener;
import admin.mobile.rsi.adminmobile.utils.http.HttpResponse;

public class SearchAuthorView extends AppCompatActivity {

    private Context context = this;
    private List<Author> listOfAuthors;
    private AuthorSearchAdapter authorSearchAdapter;
    private AlertDialog al;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_author_view);
        setTitle(getString(R.string.app_name) + " / Wyniki wyszukiwania");

        al = Dialogs.showWaitDialog(context); al.show();

        String name = (String) getIntent().getExtras().get("name");
        String dateOf = (String) getIntent().getExtras().get("dateOf");
        String dateTo = (String) getIntent().getExtras().get("dateTo");

        SearchAuthorCriteria searchAuthorCriteria = new SearchAuthorCriteria();
        if(!name.isEmpty())
            searchAuthorCriteria.setNameLike(name);
        if(!dateOf.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try { searchAuthorCriteria.setBirthdateFrom(sdf.parse(dateOf)); }
            catch (ParseException e) { System.out.println("Blad konwersja daty - Szukanie autora");}
        }
        if(!dateTo.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try { searchAuthorCriteria.setBirthdateFrom(sdf.parse(dateTo)); }
            catch (ParseException e) { System.out.println("Blad konwersja daty - Szukanie autora");}
        }

        ApiConnector.searchAuthor(searchAuthorCriteria, new AsyncRequestListener() {
            @Override
            public void done(HttpResponse response) {
                al.dismiss();
                if (response != null && response.getStatusCode() == 200) {
                    Page<Author> authors = response.getBody(new TypeReference<Page<Author>>() {});
                    listOfAuthors = authors.getContent();
                    ListView listView = (ListView) findViewById(R.id.showAuthorsList);
                    authorSearchAdapter = new AuthorSearchAdapter();
                    listView.setAdapter(authorSearchAdapter);
                } else {
                    Dialogs.showInfoDialog(context, "Błąd " + response.getStatusCode(), "Błąd podczas wyszukiwania");
                }
            }
        });
    }

    class AuthorSearchAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return listOfAuthors != null ? listOfAuthors.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            view = getLayoutInflater().inflate(R.layout.search_author_row, null);
            TextView name = (TextView) view.findViewById(R.id.nameAuthorRow);
            TextView surname = (TextView) view.findViewById(R.id.surnameAuthorRow);
            TextView date = (TextView) view.findViewById(R.id.dateAuthorRow);
            Button removeUserButton = (Button) view.findViewById(R.id.removeAuthorRow);

            if(listOfAuthors != null && listOfAuthors.size() > 0)
            {
                name.setText(listOfAuthors.get(position).getName());
                surname.setText(listOfAuthors.get(position).getSurname());

                if(listOfAuthors.get(position).getBirthDate() != null)
                {
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    date.setText(df.format(listOfAuthors.get(position).getBirthDate()));
                }
            }

            removeUserButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    al.show();
                    ApiConnector.removeAuthor(listOfAuthors.get(position).getId().toString(), new AsyncRequestListener() {
                        @Override
                        public void done(HttpResponse response) {
                            al.dismiss();
                            if (response != null && response.getStatusCode() == 200) {
                                Dialogs.showInfoDialog(context,
                                        "Usunięto autora",
                                        "Autor " + listOfAuthors.get(position).getName() + " " + listOfAuthors.get(position).getSurname() + " został usunięty");
                                listOfAuthors.remove(position);
                                if(authorSearchAdapter != null)
                                    authorSearchAdapter.notifyDataSetChanged();
                            } else {
                                Dialogs.showInfoDialog(context, "Błąd " + response.getStatusCode(), "Błąd podczas usuwania");
                            }
                        }
                    });
                }
            });

            return view;
        }
    }
}
