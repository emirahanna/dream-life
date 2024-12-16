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

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.snackbar.Snackbar;

final class DreamPet extends DreamLifeOptions {
    //constructor
    public DreamPet(String name, int imageID) {
        super(name, imageID);
    }
}

public class DreamPetActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "DREAM_LIFE_ACTIVITY";
    private TextView dynamicText;
    private Spinner spinner;

    private final int[] ACTION_ICON_IDS = {R.id.prev_button, R.id.next_button, R.id.next_button,R.id.createVisionBoardButton};

    //array of possible options for a dream pet
    private final DreamPet[] PET_PROFILES = {new DreamPet("Ralph", R.drawable.dog), new DreamPet("Michael", R.drawable.graycat), new DreamPet("Sandy", R.drawable.squirrel), new DreamPet("Mittens", R.drawable.orangecat), new DreamPet("Nathaniel", R.drawable.specialanimal)};

    private int index = 0;//to track pet option in order to move from one to the next

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dream);
        updateImage(getCurrentProfile());

        ShapeableImageView saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(this);

        dynamicText = findViewById(R.id.dynamicText);
        dynamicText.setText(getString(R.string.choose_pet));

        //attach action listeners to buttons
        for (int id : ACTION_ICON_IDS) {
            findViewById(id).setOnClickListener(this);
        }
        setUpSpinner();
    }

    private void setUpSpinner() {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.pet_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = findViewById(R.id.character_options_spinner);
        spinner.setAdapter(adapter);

        // Set the OnItemSelectedListener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item
                // Fetch the selected DreamYou object using the position
                DreamPet selectedDreamYou = PET_PROFILES[position];

                // Update the image based on the selected item
                updateImage(selectedDreamYou);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //updates screen to show a given Dream Pet
    private void updateImage(DreamPet dreamPet) {
        ImageView img = findViewById(R.id.image_preview);
        img.setImageResource(dreamPet.getImageID());
    }

    /**
     * this method gives functionality to the buttons displayed on the screen
     *
     * @param view links to the dream pet view
     */
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if ((id == R.id.next_button)) {
            DreamPet nextProfile = moveToNextProfile();
            updateImage(nextProfile);
            updateName();

        } else if (id == R.id.prev_button) {
            DreamPet previousProfile = moveToPreviousProfile();
            updateImage(previousProfile);
            updateName();

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
                d.setMessage("Please select a Job, House, Pet and Character to create vision board");
                d.setPositiveButton(android.R.string.ok, null);
                d.show();
            }
        }
        else Log.d(TAG, "Unknown ID: " + id);
    }

    //saves currently selected profile
    private void saveCurrentProfile() {
        DreamPet currentProfile = getCurrentProfile();

        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.CURRENT_STATE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //save the profile's name and image ID
        editor.putString("pet_name", currentProfile.getName());
        editor.putInt("pet_image_ID", currentProfile.getImageID());
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
    private DreamPet moveToNextProfile() {
        index = (index + 1) % PET_PROFILES.length;
        return PET_PROFILES[index];
    }

    private DreamPet moveToPreviousProfile() {
        index = index - 1;
        if (index < 0) index = PET_PROFILES.length - 1;
        return PET_PROFILES[index];
    }

    private void updateName() {
        spinner.setSelection(index);
    }

    private DreamPet getCurrentProfile() {
        return PET_PROFILES[index];
    }
}

