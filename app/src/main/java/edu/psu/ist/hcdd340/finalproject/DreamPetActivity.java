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

final class DreamPet {
    //fields
    private final String petName;
    private final int petImageID;

    //constructor
    DreamPet(String petName, int petImageID) {
        this.petName = petName;
        this.petImageID = petImageID;
    }

    //getters
    public int getPetImageID() {
        return petImageID;
    }

    public String getPetName() {
        return petName;
    }
}

    public class DreamPetActivity extends AppCompatActivity implements View.OnClickListener {

        public static final String TAG = "DREAM_LIFE_ACTIVITY";
        private TextView dynamicText;
        private TextView nameText;

        private final int[] ACTION_ICON_IDS =
                {
                        R.id.prev_button,
                        R.id.next_button,
                        R.id.next_button
                };
        //array of possible options for a dream pet
        private final DreamPet[] PET_PROFILES = {
                new DreamPet("Ralph", R.drawable.dog),
                new DreamPet("Michael", R.drawable.graycat),
                new DreamPet("Sandy", R.drawable.squirrel),
                new DreamPet("Mittens", R.drawable.orangecat),
                new DreamPet("Nathaniel", R.drawable.specialanimal)
        };

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

            nameText = findViewById(R.id.nameText);
            nameText.setText("Ralph");

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

        //updates screen to show a given Dream Pet
        private void updateImage(DreamPet dreamPet) {
            ImageView img = findViewById(R.id.image_preview);
            img.setImageResource(dreamPet.getPetImageID());
        }

        /**
         * this method gives functionality to the buttons displayed on the screen
         * @param view links to the dream pet view
         */
        @Override
        public void onClick(View view) {
            int id = view.getId();
            if ((id == R.id.next_button)) {
                DreamPet nextProfile = moveToNextProfile();
                updateImage(nextProfile);
                updateName(nextProfile);

            } else if (id == R.id.prev_button) {
                DreamPet previousProfile = moveToPreviousProfile();
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
            DreamPet currentProfile = getCurrentProfile();

            SharedPreferences sharedPreferences = getSharedPreferences("UserSelections", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            //save the profile's name and image ID
            editor.putString("petName", currentProfile.getPetName());
            editor.putInt("petImageID", currentProfile.getPetImageID());
            editor.apply();

            Log.d(TAG, "Profile saved: " + currentProfile.getPetName());
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
            } else if (menuId == R.id.menu_register) {
                changedClass = RegisterActivity.class;
            }else if (menuId == R.id.menu_logout) {
                changedClass = LogInActivity.class;
            }else if (menuId == R.id.menu_vision){
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


        //the following three methods allow the user to move between assets or select the displayed asset
        private DreamPet moveToNextProfile() {
            index = (index + 1) % PET_PROFILES.length;
            return PET_PROFILES[index];
        }

        private DreamPet moveToPreviousProfile() {
            index = index - 1;
            if (index < 0)
                index = PET_PROFILES.length - 1;
            return PET_PROFILES[index];
        }

        private void updateName(DreamPet profile)
        {
            TextView nameTextView = findViewById(R.id.nameText);
            nameTextView.setText(profile.getPetName());
        }

        private DreamPet getCurrentProfile() {
            return PET_PROFILES[index];
        }
    }

