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

final class JobProfile {
    private final String JobName;
    private final int jobImageID;

    JobProfile(String jobName, int jobImageID) {
        this.JobName = jobName;
        this.jobImageID = jobImageID;
    }

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

    private final JobProfile[] JOB_PROFILES = {
            //fill in once assets are input

            /*new JobProfile("Barn house", R.drawable.barnhouse),
            new JobProfile("Floating Island", R.drawable.floatingisland),
            new JobProfile("Hidden Island", R.drawable.hiddenisland),
            new JobProfile("Night Watch House", R.drawable.nightwatchhouse),
            new JobProfile("Robot House", R.drawable.robothouse)
        */};

    private static int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dream);//have to make this an xml file and then itll be fine

        //Set event handler for icons
        for (int id : ACTION_ICON_IDS) {
            findViewById(id).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if ((id == R.id.next_button)) {
            updateImage(moveToNextProfile());
        } else if (id == R.id.prev_button) {
            updateImage(moveToPreviousProfile());
        } else if (id == R.id.save_button) {
            Log.d(TAG, "saves it: " + id);
        } else Log.d(TAG, "Unknown ID: " + id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //updates screen to show a given DreamHouse
    private void updateImage(JobProfile jobProfile) {
        ImageView img = findViewById(R.id.image_preview);
        img.setImageResource(jobProfile.getJobImageID());
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
        } else if (menuId == R.id.menu_profile) {
            changedClass = VisionBoardActivity.class;
        }

        createIntentAndStartActivity(this, changedClass);
        return super.onOptionsItemSelected(item);
    }

    private boolean createIntentAndStartActivity(Context context, Class cls) {
        Intent intent = new Intent(context, cls);
        startActivity(intent);
        return true;
    }

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

