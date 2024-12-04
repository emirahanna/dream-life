package edu.psu.ist.hcdd340.finalproject;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;

final class JobProfile {
    private final String JobName;
    private final int jobImageID;

    JobProfile(String jobName, int jobImageID)
    {
        this.JobName = jobName;
        this.jobImageID =jobImageID;
    }

    public int getJobImageID() {
        return jobImageID;
    }

    public String getJobName() {
        return JobName;
    }


    public class DreamJobActivity extends AppCompatActivity implements View.OnClickListener {

        public static final String TAG = "DREAM_LIFE_ACTIVITY";

        private final int[] ACTION_ICON_IDS =
                {
                        R.id.prev_button,
                        R.id.next_button,
                        R.id.next_button
                };

        private final JobProfile[] JOB_PROFILES = {
                //fill in once assets are input

                /*new JobProfile("Barn house", R.drawable.barnhouse),
                new JobProfile("Floating Island", R.drawable.floatingisland),
                new JobProfile("Hidden Island", R.drawable.hiddenisland),
                new JobProfile("Night Watch House", R.drawable.nightwatchhouse),
                new JobProfile("Robot House", R.drawable.robothouse)
            */
        };

        private int index = 0;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_dreamjob);//have to make this an xml file and then itll be fine

            //Set event handler for icons
            ShapeableImageView prev = findViewById(R.id.prev_button);
            prev.setOnClickListener(this);

            ShapeableImageView save = findViewById(R.id.save_button);
            save.setOnClickListener(this);

            ShapeableImageView next = findViewById(R.id.next_button);
            next.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int eventSourceId = view.getId();

            JobProfile jobProfile = getCurrentProfile();


        }

        private JobProfile moveToNextProfile() {
            index = (index + 1) % JOB_PROFILES.length;
            return JOB_PROFILES[index];
        }

        private JobProfile moveToPreviousProfile() {
            index = index - 1;
            if (index < 0)
                index = JOB_PROFILES.length - 1;
            return JOB_PROFILES[index];
        }

        private JobProfile getCurrentProfile() {
            return JOB_PROFILES[index];
        }
    }
}
