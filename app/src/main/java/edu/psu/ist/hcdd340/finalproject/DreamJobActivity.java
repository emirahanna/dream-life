package edu.psu.ist.hcdd340.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.snackbar.Snackbar;

final class JobProfile {
    //fields
    private final String JobName;
    private final int jobImageID;


    //constructor
    JobProfile(String jobName, int jobImageID) {
        this.JobName = jobName;
        this.jobImageID = jobImageID;
    }

    //getters
    public int getJobImageID() {
        return jobImageID;
    }

    public String getJobName() {
        return JobName;
    }

}

public class DreamJobActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "DREAM_LIFE_ACTIVITY";

    private final int[] ACTION_ICON_IDS = {R.id.prev_button, R.id.next_button, R.id.next_button};

    //array of possible options for a dream job
    private final JobProfile[] JOB_PROFILES = {

            new JobProfile("Lawyer", R.drawable.gavel),
            new JobProfile("Doctor", R.drawable.stethoscope),
            new JobProfile("Constructor Worker", R.drawable.hardhat),
            new JobProfile("Fireman", R.drawable.firetruck)
        };

    private static int index = 0;//to track job option in order to move from one to the next

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dream);
        updateImage(getCurrentProfile());//displays image

        //Set event handler for icons
        for (int id : ACTION_ICON_IDS) {
            findViewById(id).setOnClickListener(this);
        }
    }
    /**
     * this method gives functionality to the buttons displayed on the screen
     * @param view links to the dream job view
     */
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if ((id == R.id.next_button)) {
            updateImage(moveToNextProfile());
        } else if (id == R.id.prev_button) {
            updateImage(moveToPreviousProfile());
        } else if (id == R.id.save_button) {
            ShapeableImageView icon = findViewById(R.id.save_button);
            Snackbar.make(icon,
                    R.string.save_confirmation,
                    Snackbar.LENGTH_SHORT).show();
        } else Log.d(TAG, "Unknown ID: " + id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //updates screen to show a given Dream Job
    private void updateImage(JobProfile jobProfile) {
        ImageView img = findViewById(R.id.image_preview);
        img.setImageResource(jobProfile.getJobImageID());
    }

    //gives menu functionality to flip between screens
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
        } else if (menuId == R.id.menu_profile) {
            changedClass = VisionBoardActivity.class;
        }else if (menuId == R.id.menu_logout) {
            changedClass = LogInActivity.class;
        }

        createIntentAndStartActivity(this, changedClass);
        return super.onOptionsItemSelected(item);
    }

    private boolean createIntentAndStartActivity(Context context, Class cls) {
        Intent intent = new Intent(context, cls);
        startActivity(intent);
        return true;
    }

    //the following three methods allow the user to move between assets or select the displayed asset
    private JobProfile moveToNextProfile() {
        index = (index + 1) % JOB_PROFILES.length;
        return JOB_PROFILES[index];
    }

    private JobProfile moveToPreviousProfile() {
        index = index - 1;
        if (index < 0) index = JOB_PROFILES.length - 1;
        return JOB_PROFILES[index];
    }

    private JobProfile getCurrentProfile() {
        return JOB_PROFILES[index];
    }
}

