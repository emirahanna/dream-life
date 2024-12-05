package edu.psu.ist.hcdd340.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "LOG_IN";

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button button = findViewById(R.id.button_login);
        button.setOnClickListener(this);

        sharedPreferences = getSharedPreferences(RegisterActivity.SHARED_PREF_NAME, MODE_PRIVATE);
    }

    @Override
    public void onClick(View view) {
        EditText emailEditText = findViewById(R.id.editTextEmail);
        String email = emailEditText.getText().toString();

        EditText passwordEditText = findViewById(R.id.editTextPassword);
        String password = passwordEditText.getText().toString();
        Log.d(TAG, "Email: " + email + " and Password: " + password);

        /** toy example — DO NOT do this in production code! **/
        String userAllowed = retrieveSavedEmail();
        String correctPassword = retrieveSavedPassword();
        if (email.equals(userAllowed) &&
                password.equals(correctPassword)) {
            Button button = findViewById(R.id.button_login);
            Snackbar.make(button,
                    "Logged In!",
                    Snackbar.LENGTH_LONG).show();
        } else {
            AlertDialog.Builder d = new AlertDialog.Builder(this);
            d.setTitle(R.string.login_error_title);
            d.setMessage(R.string.login_error_message);
            d.setPositiveButton(android.R.string.ok, null);
            d.show();
        }
    }

    private String retrieveSavedEmail() {
        return sharedPreferences.getString(RegisterActivity.EMAIL_KEY, null);
    }

    private String retrieveSavedPassword() {
        return sharedPreferences.getString(RegisterActivity.PASSWORD_KEY, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Class changedClass = this.getClass();
        int menuId = item.getItemId();

        if (menuId == R.id.menu_house) {
            changedClass = DreamHouseActivity.class;
        } else if (menuId == R.id.menu_ambition) {
            changedClass = DreamJobActivity.class;
        } else if (menuId == R.id.menu_pet) {
            changedClass = DreamPetActivity.class;
        } else if (menuId == R.id.menu_you) {
            changedClass = DreamYouActivity.class;
        } else if (menuId == R.id.menu_register) {
            changedClass = RegisterActivity.class;
        }

        createIntentAndStartActivity(this, changedClass);
        return super.onOptionsItemSelected(item);
    }

    private boolean createIntentAndStartActivity(Context context, Class cls) {
        Intent intent = new Intent(context, cls);
        startActivity(intent);
        return true;
    }
}