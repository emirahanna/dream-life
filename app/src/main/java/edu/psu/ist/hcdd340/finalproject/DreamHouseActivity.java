package edu.psu.ist.hcdd340.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.snackbar.Snackbar;

final class DreamHouse extends DreamLifeOptions {
    //constructor
    public DreamHouse(String name, int imageId) {
        super(name, imageId);
    }
}

public class DreamHouseActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "DREAM_HOUSE_ACTIVITY";

    //Array of possible options for a dream house
    private final DreamHouse[] DREAM_HOUSES = {
            new DreamHouse("Cloud Island", R.drawable.floatingisland),
            new DreamHouse("Night Watch House", R.drawable.nightwatchhouse),
            new DreamHouse("Hidden Island", R.drawable.hiddenisland),
            new DreamHouse("Barn", R.drawable.barnhouse)};

    private static final int[] ACTION_ICON_IDS = {R.id.next_button, R.id.save_button, R.id.prev_button};

    private TextView dynamicText;
    private TextView nameText;

    private static int index = 0;//to track house option in order to move from one to the next

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dream);
        updateImage(getCurrentProfile()); //displays image

        dynamicText = findViewById(R.id.dynamicText);
        dynamicText.setText(getString(R.string.choose_house));

        nameText = findViewById(R.id.nameText);
        nameText.setText("Cloud Island");

        //attach action listeners to buttons
        for (int id : ACTION_ICON_IDS) {
            findViewById(id).setOnClickListener(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //updates screen to show a given DreamHouse
    private void updateImage(DreamHouse dreamHouse) {
        ImageView img = findViewById(R.id.image_preview);
        img.setImageResource(dreamHouse.getImageID());
    }

    /**
     * this method gives functionality to the buttons displayed on the screen
     * @param view links to the dream house view
     */
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if ((id == R.id.next_button)) {
            DreamHouse nextProfile = moveToNextProfile();
            updateImage(nextProfile);
            updateName(nextProfile);

        } else if (id == R.id.prev_button) {
            DreamHouse previousProfile = moveToPreviousProfile();
            updateImage(previousProfile);
            updateName(previousProfile);

        } else if (id == R.id.save_button) {
            saveCurrentProfile();
            ShapeableImageView icon = findViewById(R.id.save_button);
            Snackbar.make(icon,
                    R.string.save_confirmation,
                    Snackbar.LENGTH_SHORT).show();
        } else Log.d(TAG, "Unknown ID: " + id);
    }

    //saves currently selected profile
    private void saveCurrentProfile(){
        DreamHouse currentProfile = getCurrentProfile();

        SharedPreferences sharedPreferences = getSharedPreferences("UserSelections", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //save the profile's name and image ID
        editor.putString("houseName", currentProfile.getName());
        editor.putInt("houseImageID", currentProfile.getImageID());
        editor.apply();

        Log.d(TAG, "Profile saved: " + currentProfile.getName());
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


    //the following three methods allow the user to move between assets or select the displayed asset
    private DreamHouse moveToNextProfile() {
        index = (index + 1) % DREAM_HOUSES.length;
        return DREAM_HOUSES[index];
    }

    private DreamHouse moveToPreviousProfile() {
        index = index - 1;
        if (index < 0) index = DREAM_HOUSES.length - 1;
        return DREAM_HOUSES[index];
    }

    private void updateName(DreamHouse profile)
    {
        TextView nameTextView = findViewById(R.id.nameText);
        nameTextView.setText(profile.getName());
    }

    private DreamHouse getCurrentProfile() {
        return DREAM_HOUSES[index];
    }
}

