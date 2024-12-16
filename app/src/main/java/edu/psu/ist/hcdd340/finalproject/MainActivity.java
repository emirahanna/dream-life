package edu.psu.ist.hcdd340.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {


    public static final String CURRENT_STATE = "CURRENT_STATE";
    private TextView quoteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = this.getSharedPreferences(CURRENT_STATE, MODE_PRIVATE);
        //not logged in
        if (!sharedPreferences.getBoolean(UserManager.CURRENT_STATE_KEY, false)) {
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
            finish();
        }
            setContentView(R.layout.activity_main);


        // Bind views
        TextView greetingTextView = findViewById(R.id.greetingTextView);
        quoteTextView = findViewById(R.id.quoteTextView);
        CardView quoteCard = findViewById(R.id.quoteCard);
        CardView visionBoardCard = findViewById(R.id.visionBoardCard);
        CardView dreamsLifeCard = findViewById(R.id.createDreamLifeCard);

        // Set the greeting (You can fetch the user's name dynamically)
        String username = sharedPreferences.getString(UserManager.CURRENT_USER_KEY, "bestie");
        String greetingText = "Welcome back,\n" + username + "!";
        greetingTextView.setText(greetingText);

        // Set a random quote
        quoteTextView.setText(getRandomQuote());

        // Set button functionality (using lambdas!!!) Love this for us
        visionBoardCard.setOnClickListener(e -> {

            createIntentAndStartActivity(this, VisionBoardRecyclerView.class);
        });
        quoteCard.setOnClickListener(e -> quoteTextView.setText(getRandomQuote()));
        dreamsLifeCard.setOnClickListener(e -> {
            Log.v("FUCK", "Button clicked");
            createIntentAndStartActivity(this, DreamYouActivity.class);});
    }

    private String getRandomQuote() {
        String[] quotes = {"Do it for your future self.", "You’re not behind in life. There’s no timeline.", "Small progress is still progress.", "You have survived 100% of your bad days.", "Your vibe attracts your tribe.", "Dream big, start small, but most importantly, start.", "Be the energy you want to attract.", "Sometimes, what didn't work out for you really worked out for you.", "Focus on the step in front of you, not the whole staircase.", "The universe is not in a hurry. You are.", "Take a deep breath and remind yourself of who you are.", "You are allowed to be both a masterpiece and a work in progress.", "Growth is growth, no matter how small.", "Don’t just exist; live.", "Everything you want is on the other side of fear.", "Trust the timing of your life.", "Do what you can, with what you have, where you are.", "Protect your peace.", "Your potential is endless.", "Be the reason someone believes in the goodness of people.", "What is coming is better than what is gone.", "Even the darkest night will end, and the sun will rise.", "You are braver than you believe and stronger than you seem.", "Keep shining; the world needs your light.", "One day or day one. You decide.", "Your journey is not the same as anyone else’s.", "Bloom where you are planted.", "It’s not too late to start over.", "You’re allowed to take up space."};
        Random random = new Random();
        return quotes[random.nextInt(quotes.length)];
    }

    //gives menu functionality to flip between screens
    public boolean onOptionsItemSelected(MenuItem item) {
        Class changedClass = this.getClass();
        int menuId = item.getItemId();

        createIntentAndStartActivity(this, AppHelper.processItemsSelectedMainMenu(menuId, changedClass, this));
        return super.onOptionsItemSelected(item);
    }

    private boolean createIntentAndStartActivity(Context context, Class cls) {
        Intent intent = new Intent(context, cls);
        startActivity(intent);
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}