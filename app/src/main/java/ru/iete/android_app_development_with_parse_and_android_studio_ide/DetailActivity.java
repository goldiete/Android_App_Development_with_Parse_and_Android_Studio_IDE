package ru.iete.android_app_development_with_parse_and_android_studio_ide;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Date;


public class DetailActivity extends Activity {

    public static final String EXTRA_STATUS_ID = "ru.iete.android_app_development_with_parse_and_android_studio_ide.status_id";

    String statusId;

    private TextView creatorName;
    private TextView createDate;
    private TextView statusBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        statusId = getIntent().getStringExtra(EXTRA_STATUS_ID);

        creatorName = (TextView) findViewById(R.id.creatorName);
        createDate = (TextView) findViewById(R.id.timeCreated);
        statusBody = (TextView) findViewById(R.id.statusBody);

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Status");
        query.getInBackground(statusId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (e == null) {
                    String creatorNameData = parseObject.getString("username");
                    Date createdAt = parseObject.getCreatedAt();
                    String statusBodyData = parseObject.getString("body");

                    creatorName.setText(creatorNameData);
                    createDate.setText(createdAt.toString());
                    statusBody.setText(statusBodyData);

                }            }
        });
    }
}
