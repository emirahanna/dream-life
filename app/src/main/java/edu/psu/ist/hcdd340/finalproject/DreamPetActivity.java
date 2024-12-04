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

final class DreamPet {
    private final String petName;
    private final int petImageID;

    DreamPet(String petName, int petImageID) {
        this.petName = petName;
        this.petImageID = petImageID;
    }

    public int getPetImageID() {
        return petImageID;
    }

    public String getPetName() {
        return petName;
    }
}

    public class DreamPetActivity extends AppCompatActivity implements View.OnClickListener {

        public static final String TAG = "DREAM_LIFE_ACTIVITY";

        private final int[] ACTION_ICON_IDS =
                {
                        R.id.prev_button,
                        R.id.next_button,
                        R.id.next_button
                };

        private final DreamPet[] PET_PROFILES = {
                new DreamPet("Dog", R.drawable.dog),
                new DreamPet("Gray Cat", R.drawable.graycat),
                new DreamPet("Orange Cat", R.drawable.orangecat),
                new DreamPet("Special Animal", R.drawable.specialanimal),
                new DreamPet("Squirrel", R.drawable.squirrel)
        };

        private int index = 0;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_dream);
            updateImage(getCurrentProfile());

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
        private void updateImage(DreamPet dreamPet) {
            ImageView img = findViewById(R.id.image_preview);
            img.setImageResource(dreamPet.getPetImageID());
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

        private DreamPet getCurrentProfile() {
            return PET_PROFILES[index];
        }
    }

