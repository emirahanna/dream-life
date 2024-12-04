package edu.psu.ist.hcdd340.finalproject;


import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;

final class HouseProfile {
    private final String characterName;
    private final int characterImageID;

    HouseProfile(String characterName, int characterImageID)
    {
        this.characterName= characterName;
        this.characterImageID=characterImageID;
    }

    public int getProfileImageID() {
        return characterImageID;
    }

    public String getProfileName() {
        return characterName;
    }

    public class DreamYouActivity extends AppCompatActivity implements View.OnClickListener{

        public static final String TAG = "DREAM_LIFE_ACTIVITY";

        private final int[] ACTION_ICON_IDS =
                {
                  R.id.prev_button,
                  R.id.next_button,
                  R.id.next_button

                };

        private final PetProfile[] CHARACTER_PROFILES = {
                new PetProfile("Hercules", R.drawable.hercules),
                new PetProfile("Cool Guy", R.drawable.cool_guy),
                new PetProfile("Cleopatra", R.drawable.cleopatra),
                new PetProfile("Fancy Lady", R.drawable.fancylady),
                new PetProfile("Lady", R.drawable.lady),
                new PetProfile("Some Guy", R.drawable.someguy)
        };

        private int index = 0;

        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_dreamcharacter);

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

        PetProfile characterProfile = getCurrentProfile();


        }

        private PetProfile moveToNextProfile()
        {
            index= (index + 1) % CHARACTER_PROFILES.length;
            return CHARACTER_PROFILES[index];
        }

        private PetProfile moveToPreviousProfile()
        {
            index = index - 1;
            if (index <0)
                index = CHARACTER_PROFILES.length - 1;
            return CHARACTER_PROFILES[index];
        }

        private PetProfile getCurrentProfile ()
        {
            return CHARACTER_PROFILES[index];
        }


    }
}




