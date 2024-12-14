package edu.psu.ist.hcdd340.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    public static final String SHARED_PREF_NAME = "CURRENT_STATE";
    private SharedPreferences sharedPreferences;
    private TextView greetingTextView;
    private TextView quoteTextView;
    private TextView authorTextView;
    private CardView quoteCard;
    private CardView visionBoardCard;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = this.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        //not logged in
        if (!sharedPreferences.getBoolean(UserManager.CURRENT_STATE_KEY, false)) {
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
        } else {
            setContentView(R.layout.activity_main);
        }

        // Bind views
        greetingTextView = findViewById(R.id.greetingTextView);
        quoteTextView = findViewById(R.id.quoteTextView);
        authorTextView = findViewById(R.id.authorTextView);
        quoteCard = findViewById(R.id.quoteCard);
        visionBoardCard = findViewById(R.id.visionBoardCard);

        // Set the greeting (You can fetch the user's name dynamically)
        String username = sharedPreferences.getString(UserManager.CURRENT_USER_KEY, "bestie");
        greetingTextView.setText("Hello, " + username + "!");

        // Set a random quote
        String[] quotes = {
                "“Dream big and dare to fail.”",
                "“The future belongs to those who believe in the beauty of their dreams.”",
                "“Success is not the key to happiness. Happiness is the key to success.”",
                "“Believe you can and you're halfway there.”",
                "“Don't watch the clock; do what it does. Keep going.”"
        };
        Random random = new Random();
        String randomQuote = quotes[random.nextInt(quotes.length)];
        quoteTextView.setText(randomQuote);


        // Set click listener for Vision Board card
        visionBoardCard.setOnClickListener(v -> {
            Intent intent = new Intent(this, VisionBoardActivity.class);
            startActivity(intent);
        });
    }

    private String[] getRandomQuoteFromCSV() {
        List<String[]> quotes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("quotes.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",", 2); // Split into quote and author
                    quotes.add(split);
            }
        } catch (IOException e) {
           // e.printStackTrace();
        }

            Random random = new Random();
            return quotes.get(random.nextInt(quotes.size()));
    }

}