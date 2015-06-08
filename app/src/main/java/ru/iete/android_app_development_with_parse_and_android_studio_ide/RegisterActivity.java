package ru.iete.android_app_development_with_parse_and_android_studio_ide;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class RegisterActivity extends Activity {

    private EditText userLogin;
    private EditText userPassword;
    private EditText userEmail;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userLogin = (EditText) findViewById(R.id.loginEditText);
        userPassword = (EditText) findViewById(R.id.passwordEditText);
        userEmail = (EditText) findViewById(R.id.emailEditText);
        registerButton = (Button) findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String login = userLogin.getText().toString().trim();
                String password = userPassword.getText().toString().trim();
                String email = userEmail.getText().toString().trim();

                ParseUser user = new ParseUser();
                user.setUsername(login);
                user.setPassword(password);
                user.setEmail(email);

                user.signUpInBackground(new SignUpCallback() {

                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(RegisterActivity.this,
                                    R.string.register_toast, Toast.LENGTH_LONG).show();
                            Intent takeUserHomepage = new Intent(RegisterActivity.this, HomepageActivity.class);
                            startActivity(takeUserHomepage);
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
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
        });
    }
}
