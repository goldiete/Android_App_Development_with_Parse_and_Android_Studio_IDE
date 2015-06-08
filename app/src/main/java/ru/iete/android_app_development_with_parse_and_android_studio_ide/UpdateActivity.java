package ru.iete.android_app_development_with_parse_and_android_studio_ide;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class UpdateActivity extends Activity {

    private EditText updateText;
    private Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        updateText = (EditText) findViewById(R.id.updateText);
        updateButton = (Button) findViewById(R.id.updateButton);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ParseUser.getCurrentUser().getUsername();

                String status = updateText.getText().toString();

                if (status.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this);
                    builder.setMessage(R.string.oops).setTitle(R.string.oops_title).
                            setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else {

                    ParseObject statusObject = new ParseObject("Status");
                    statusObject.put("body", status);
                    statusObject.put("username", username);
                    statusObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(UpdateActivity.this,
                                        R.string.update_toast, Toast.LENGTH_LONG).show();
                                Intent takeUserHomepage = new Intent(UpdateActivity.this, HomepageActivity.class);
                                startActivity(takeUserHomepage);

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this);
                                builder.setMessage(e.getMessage()).setTitle(R.string.sorry_title).
                                        setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                AlertDialog dialog = builder.create();
                                dialog.show();

                            }
                        }
                    });
                }
            }
        });
    }
}


