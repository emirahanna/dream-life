package edu.psu.ist.hcdd340.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "LOG_IN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button button = findViewById(R.id.button_login);
        button.setOnClickListener(this);
        TextView signUpLink = findViewById(R.id.register_text_link);
        signUpLink.setOnClickListener(this);
        Log.v(TAG, "on create entered");

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Log.v(TAG, "on click entered");
        if (id == R.id.button_login) {
            EditText userNameEditText = findViewById(R.id.editTextEmail);
            String userName = userNameEditText.getText().toString();

            EditText passwordEditText = findViewById(R.id.editTextPassword);
            String password = passwordEditText.getText().toString();
            Log.v(TAG, "Email: " + userName + " and Password: " + password);

            UserManager userManager = new UserManager(this);
            Button btn = findViewById(R.id.button_login);

            if (userManager.validateLogin(userName, password)) {
                Snackbar.make(btn, "Login successful!", Snackbar.LENGTH_SHORT).show();
                SharedPreferences currentState = getSharedPreferences(MainActivity.CURRENT_STATE, MODE_PRIVATE);
                currentState.edit().putBoolean(UserManager.CURRENT_STATE_KEY, true).apply();
                currentState.edit().putString(UserManager.CURRENT_USER_KEY,userName).apply();
                // Proceed to the user's dashboard or app home screen
                createIntentAndStartActivity(this, MainActivity.class);
            } else {
                AlertDialog.Builder d = new AlertDialog.Builder(this);
                d.setTitle(R.string.login_error_title);
                d.setMessage(R.string.login_error_message);
                d.setPositiveButton(android.R.string.ok, null);
                d.show();
            }
        } else if (id == R.id.register_text_link) {
            createIntentAndStartActivity(this, RegisterActivity.class);
        }

    }

    private boolean createIntentAndStartActivity(Context context, Class cls) {
        Intent intent = new Intent(context, cls);
        startActivity(intent);
        return true;
    }
}