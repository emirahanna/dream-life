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

final class DreamJob extends DreamLifeOptions {

    //constructor
    public DreamJob(String name, int imageID) {
        super(name, imageID);
    }
}

public class DreamJobActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "DREAM_LIFE_ACTIVITY";
    private Spinner spinner;

    private static final int[] ACTION_ICON_IDS = {R.id.next_button, R.id.save_button, R.id.prev_button, R.id.createVisionBoardButton};


    //array of possible options for a dream job
    private final DreamJob[] JOB_PROFILES = {

            new DreamJob("Lawyer", R.drawable.gavel),
            new DreamJob("Doctor", R.drawable.stethoscope),
            new DreamJob("Construction Worker", R.drawable.hardhat),
            new DreamJob("Fireman", R.drawable.firetruck)
        };

    private static int index = 0;//to track job option in order to move from one to the next

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dream);
        updateImage(getCurrentProfile());//displays image

        ShapeableImageView saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(this);

        TextView dynamicText = findViewById(R.id.dynamicText);
        dynamicText.setText(getString(R.string.choose_job));

//        TextView nameText = findViewById(R.id.nameText);
//        nameText.setText("Lawyer");

        //Set event handler for icons
        for (int id : ACTION_ICON_IDS) {
            findViewById(id).setOnClickListener(this);
        }
        setUpSpinner();
    }

    private void setUpSpinner() {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.job_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = findViewById(R.id.character_options_spinner);
        spinner.setAdapter(adapter);

        // Set the OnItemSelectedListener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item
                // Fetch the selected DreamYou object using the position
                DreamJob selectedDreamYou = JOB_PROFILES[position];

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
     * @param view links to the dream job view
     */
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if ((id == R.id.next_button)) {
            DreamJob nextProfile = moveToNextProfile();
            updateImage(nextProfile);
            updateName();

        } else if (id == R.id.prev_button) {
            DreamJob previousProfile = moveToPreviousProfile();
            updateImage(previousProfile);
            updateName();

        } else if (id == R.id.save_button) {
            saveCurrentProfile();
            Button icon = findViewById(R.id.createVisionBoardButton);
            Snackbar.make(icon,
                    R.string.save_confirmation,
                    Snackbar.LENGTH_SHORT).show();
        } else if (id == R.id.createVisionBoardButton) {
            VisionBoardManager vbManager = new VisionBoardManager(this);
            if (vbManager.canCreateVisionBoard()) {
                vbManager.saveVisionBoard();
                Button icon = findViewById(R.id.createVisionBoardButton);
                Snackbar.make(icon, R.string.vb_saved, Snackbar.LENGTH_SHORT).show();
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
    private void saveCurrentProfile(){
        DreamJob currentProfile = getCurrentProfile();

        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //save the profile's name and image ID
        editor.putString("job_name", currentProfile.getName());
        editor.putInt("job_image_ID", currentProfile.getImageID());
        editor.apply();

        Log.d(TAG, "Profile saved: " + currentProfile.getName());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //updates screen to show a given Dream Job
    private void updateImage(DreamJob jobProfile) {
        ImageView img = findViewById(R.id.image_preview);
        img.setImageResource(jobProfile.getImageID());
    }

    //gives menu functionality to flip between screens
    public boolean onOptionsItemSelected(MenuItem item) {
        Class<? extends DreamJobActivity> changedClass = this.getClass();
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
    private DreamJob moveToNextProfile() {
        index = (index + 1) % JOB_PROFILES.length;
        return JOB_PROFILES[index];
    }

    private DreamJob moveToPreviousProfile() {
        index = index - 1;
        if (index < 0) index = JOB_PROFILES.length - 1;
        return JOB_PROFILES[index];
    }

    private void updateName()
    {
        spinner.setSelection(index);
    }

    private DreamJob getCurrentProfile() {
        return JOB_PROFILES[index];
    }
}

