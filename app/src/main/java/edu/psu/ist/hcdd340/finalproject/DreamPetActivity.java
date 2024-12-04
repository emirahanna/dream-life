package edu.psu.ist.hcdd340.finalproject;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;

final class PetProfile {
    private final String petName;
    private final int petImageID;

    PetProfile(String petName, int petImageID)
    {
        this.petName = petName;
        this.petImageID =petImageID;
    }

    public int getPetImageID() {
        return petImageID;
    }

    public String getPetName() {
        return petName;
    }


    public class DreamPetActivity extends AppCompatActivity implements View.OnClickListener {

        public static final String TAG = "DREAM_LIFE_ACTIVITY";

        private final int[] ACTION_ICON_IDS =
                {
                        R.id.prev_button,
                        R.id.next_button,
                        R.id.next_button
                };

        private final PetProfile[] PET_PROFILES = {
                new PetProfile("Dog", R.drawable.dog),
                new PetProfile("Gray Cat", R.drawable.graycat),
                new PetProfile("Orange Cat", R.drawable.orangecat),
                new PetProfile("Special Animal", R.drawable.specialanimal),
                new PetProfile("Squirrel", R.drawable.squirrel)
        };

        private int index = 0;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_pet);//have to make this an xml file and then itll be fine

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

            PetProfile petProfile = getCurrentProfile();


        }

        private PetProfile moveToNextProfile() {
            index = (index + 1) % PET_PROFILES.length;
            return PET_PROFILES[index];
        }

        private PetProfile moveToPreviousProfile() {
            index = index - 1;
            if (index < 0)
                index = PET_PROFILES.length - 1;
            return PET_PROFILES[index];
        }

        private PetProfile getCurrentProfile() {
            return PET_PROFILES[index];
        }
    }
}
