package ru.iete.android_app_development_with_parse_and_android_studio_ide;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;


public class HomepageActivity extends ListActivity {

    private List<ParseObject> statusList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        Parse.initialize(this, "DGoaIKDFN78cJfvr2Z8DBG2Q5QQ3YAKmzziTRNaq", "FNqzOhdvkqb8Bega1UJb4E4OWVgI9uSsMCMSSLd6");

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Status");
            parseQuery.orderByDescending("createdAt");
            parseQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> list, ParseException e) {
                    if (e == null) {
                        statusList = list;

                        StatusAdapter adapter = new StatusAdapter(getListView().getContext(), list);
                        setListAdapter(adapter);

                    }
                }
            });

        } else {
            Intent takeUserLogin = new Intent(HomepageActivity.this, LoginActivity.class);
            startActivity(takeUserLogin);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_homepage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.updateStatus:
                Intent intent = new Intent(HomepageActivity.this, UpdateActivity.class);
                startActivity(intent);
                break;
            case R.id.logoutUser:
                ParseUser.logOut();
                Intent takeUserLogin = new Intent(HomepageActivity.this, LoginActivity.class);
                startActivity(takeUserLogin);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        ParseObject statusObject = statusList.get(position);
        String statusId = statusObject.getObjectId();

        Intent seeStatusDetail = new Intent(HomepageActivity.this, DetailActivity.class);
        seeStatusDetail.putExtra(DetailActivity.EXTRA_STATUS_ID, statusId);
        startActivity(seeStatusDetail);
    }
}
