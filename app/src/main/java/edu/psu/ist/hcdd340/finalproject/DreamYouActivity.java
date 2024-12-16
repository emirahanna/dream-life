package edu.psu.ist.hcdd340.finalproject;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

final class DreamYou extends DreamLifeOptions {

    //constructor
    public DreamYou(String name, int imageID) {
        super(name, imageID);
    }
}

public class DreamYouActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "DREAM_YOU_ACTIVITY";
    private TextView dynamicText;
    private Spinner spinner;

    private static final int[] ACTION_ICON_IDS = {R.id.next_button, R.id.save_button, R.id.prev_button, R.id.createVisionBoardButton};

    //array of possible options for a dream house
    private final DreamYou[] CHARACTER_PROFILES = {new DreamYou("Hercules", R.drawable.hercules), new DreamYou("Zayne", R.drawable.cool_guy), new DreamYou("Cleopatra", R.drawable.cleopatra), new DreamYou("Regina", R.drawable.fancylady), new DreamYou("Samantha", R.drawable.lady), new DreamYou("Dean", R.drawable.someguy)};

    private static int index = 0;//to track character option in order to move from one to the next

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dream);
        updateImage(getCurrentProfile());//displays image

        dynamicText = findViewById(R.id.dynamicText);
        dynamicText.setText(getString(R.string.choose_character));

//        nameText = findViewById(R.id.nameText);
//        nameText.setText("Hercules");

        for (int id : ACTION_ICON_IDS) {
            findViewById(id).setOnClickListener(this);
        }
        setUpSpinner();
    }

    private void setUpSpinner() {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.you_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = findViewById(R.id.character_options_spinner);
        spinner.setAdapter(adapter);

        // Set the OnItemSelectedListener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                              @Override
                                              public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                  // Get the selected item
                                                  // Fetch the selected DreamYou object using the position
                                                  DreamYou selectedDreamYou = CHARACTER_PROFILES[position];

                                                  // Update the image based on the selected item
                                                  updateImage(selectedDreamYou);
                                              }

                                              @Override
                                              public void onNothingSelected(AdapterView<?> parent) {

                                              }

                                          });
    }
    /**
     * this method gives functionality to the buttons displayed on the screen
     *
     * @param view links to the dream character view
     */
    @Override
    public void onClick(View view) {
        int id = view.getId();

        if ((id == R.id.next_button)) {
            DreamYou nextProfile = moveToNextProfile();
            updateImage(nextProfile);
            updateName(nextProfile);


        } else if (id == R.id.prev_button) {
            DreamYou previousProfile = moveToPreviousProfile();
            updateImage(previousProfile);
            updateName(previousProfile);

        } else if (id == R.id.save_button) {
            saveCurrentProfile();
            Button icon = findViewById(R.id.createVisionBoardButton);
            Snackbar.make(icon, R.string.save_confirmation, Snackbar.LENGTH_SHORT).show();
        } else if (id == R.id.createVisionBoardButton) {
            VisionBoardManager vbManager = new VisionBoardManager(this);
            if (vbManager.canCreateVisionBoard()) {
                AppHelper.showInputDialog(this);

            } else {
                AlertDialog.Builder d = new AlertDialog.Builder(this);
                d.setTitle("Incomplete information");
                d.setMessage("Please select a Job, House, Pet and Character to create vision board" );
                d.setPositiveButton(android.R.string.ok, null);
                d.show();
            }
        } else Log.d(TAG, "Unknown ID: " + id);
    }

    //saves currently selected profile
    private void saveCurrentProfile() {
        DreamYou currentProfile = getCurrentProfile();

        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //save the profile's name and image ID
        editor.putString("you_name", currentProfile.getName());
        editor.putInt("you_image_ID", currentProfile.getImageID());
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //updates screen to show a given Dream character
    private void updateImage(DreamYou dreamYou) {
        ImageView youImage = findViewById(R.id.image_preview);
        youImage.setImageResource(dreamYou.getImageID());
    }

    private void updateName(DreamYou profile) {
        spinner.setSelection(index);
    }

    //the following three methods allow the user to move between assets or select the displayed asset
    private DreamYou moveToNextProfile() {
        index = (index + 1) % CHARACTER_PROFILES.length;
        return CHARACTER_PROFILES[index];
    }

    private DreamYou moveToPreviousProfile() {
        index = index - 1;
        if (index < 0) index = CHARACTER_PROFILES.length - 1;
        return CHARACTER_PROFILES[index];
    }

    private DreamYou getCurrentProfile() {
        return CHARACTER_PROFILES[index];
    }

}





