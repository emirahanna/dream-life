package edu.psu.ist.hcdd340.finalproject;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class RegisterActivity extends AppCompatActivity  implements View.OnClickListener {

    private static final String TAG = "REGISTER_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button registerButton = findViewById(R.id.button_register);
        registerButton.setOnClickListener(this);

        Button cancelButton = findViewById(R.id.button_cancel_registration);
        cancelButton.setOnClickListener(this);
        TextView signUpLink = findViewById(R.id.log_in_text_link);
        signUpLink.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.button_cancel_registration) {
            finish();
        } else if (id == R.id.button_register) {
            if (validateInput()) {
                registerUser();
                Button button = findViewById(R.id.button_register);
                Snackbar.make(button,
                        "Registered!",
                        Snackbar.LENGTH_LONG).show();

                Intent intent = new Intent(this, DreamYouActivity.class);
                startActivity(intent);
            }
            else{
                AlertDialog.Builder d = new AlertDialog.Builder(this);
                d.setTitle(R.string.register_error_title);
                d.setMessage(R.string.register_error_message);
                d.setPositiveButton(android.R.string.ok, null);
                d.show();
            }
        }
        else if (id == R.id.log_in_text_link){
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
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
        String userName = getInputFromEditText(R.id.editFirstName);

        UserManager userManager = new UserManager(this);
        Button btn = findViewById(R.id.button_register);

        if (userManager.isUsernameTaken(userName)) {
            Snackbar.make(btn, "Username already exists!", Snackbar.LENGTH_SHORT).show();
        } else {
            User newUser = new User(userName, password);
            userManager.addUser(newUser);
            Snackbar.make(btn, "User registered successfully!", Snackbar.LENGTH_SHORT).show();
        }

        Log.d(TAG, "Email: " + email + ", first name: " + userName);
    }

    /**
     * Ensures that all text fields have been filled
     * @return
     */
    boolean validateInput(){
        String email = getInputFromEditText(R.id.editTextEmail);
        String password = getInputFromEditText(R.id.editTextPassword);
        String userName = getInputFromEditText(R.id.editFirstName);

        return !email.isEmpty() && !password.isEmpty() && !userName.isEmpty();
    }

}


