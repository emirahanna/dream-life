package edu.psu.ist.hcdd340.finalproject;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class RegisterActivity extends AppCompatActivity  implements View.OnClickListener {

    private static final String TAG = "REGISTER_ACTIVITY";

    public static final String SHARED_PREF_NAME = "PENN_STATE_ID";
    public static final String EMAIL_KEY = "EMAIL";
    public static final String PASSWORD_KEY = "PASSWORD";
    public static final String FIRST_NAME_KEY = "FIRST_NAME";
    public static final String LAST_NAME_KEY = "LAST_NAME";
    public static final String COLLEGE_KEY = "COLLEGE";

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button registerButton = findViewById(R.id.button_register);
        registerButton.setOnClickListener(this);

        Button cancelButton = findViewById(R.id.button_cancel_registration);
        cancelButton.setOnClickListener(this);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.button_cancel_registration) {
            finish();
        } else if (id == R.id.button_register) {
            registerUser();
            Button button = findViewById(R.id.button_register);
            Snackbar.make(button,
                    "Registered!",
                    Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * Returns user entered text from an EditText instance
     *
     * @param id: Id of the EditText instance
     * @return User entered text
     */
    String getInputFromEditText(int id) {
        EditText v = findViewById(id);
        return v.getText().toString();
    }

    /**
     * Registers a new user.
     * It involves two steps: 1) extract user inputs; and 2) save those values.
     */
    void registerUser() {
        String email = getInputFromEditText(R.id.editTextEmail);
        String password = getInputFromEditText(R.id.editTextPassword);
        String firstName = getInputFromEditText(R.id.editFirstName);
        String lastName = getInputFromEditText(R.id.editLastName);


        Log.d(TAG, "Email: " + email + ", first name: " + firstName +
                ", last name: " + lastName);

        saveUserInformation(email, password, firstName, lastName);
    }

    /**
     * Saves user information using SharedPreferences
     */
    void saveUserInformation(String email, String password, String firstName,
                             String lastName) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMAIL_KEY, email);
        editor.putString(PASSWORD_KEY, password);
        editor.putString(FIRST_NAME_KEY, firstName);
        editor.putString(LAST_NAME_KEY, lastName);
        editor.apply();
    }
}


