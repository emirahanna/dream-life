package edu.psu.ist.hcdd340.finalproject;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.snackbar.Snackbar;

final class DreamYou {
    //fields
    private final String youName;
    private final int youImageID;

    //constructor
    DreamYou(String youName, int youImageID) {
        this.youName = youName;
        this.youImageID = youImageID;
    }

    //getters
    public int getProfileImageID() {
        return youImageID;
    }

    public String getProfileName() {
        return youName;
    }

}
    public class DreamYouActivity extends AppCompatActivity implements View.OnClickListener{

        public static final String TAG = "DREAM_YOU_ACTIVITY";
        private TextView dynamicText;
        private TextView nameText;

        private static final int[] ACTION_ICON_IDS = {R.id.next_button, R.id.save_button, R.id.prev_button};

        //array of possible options for a dream house
        private final DreamYou[] CHARACTER_PROFILES = {
                new DreamYou("Hercules", R.drawable.hercules),
                new DreamYou("Zayne", R.drawable.cool_guy),
                new DreamYou("Cleopatra", R.drawable.cleopatra),
                new DreamYou("Regina", R.drawable.fancylady),
                new DreamYou("Samantha", R.drawable.lady),
                new DreamYou("Dean", R.drawable.someguy)
        };

        private static int index = 0;//to track character option in order to move from one to the next

        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_dream);
            updateImage(getCurrentProfile());//displays image

            dynamicText = findViewById(R.id.dynamicText);
            dynamicText.setText(getString(R.string.choose_character));

            nameText = findViewById(R.id.nameText);
            nameText.setText("Hercules");

            for (int id : ACTION_ICON_IDS) {
                findViewById(id).setOnClickListener(this);
            }
        }

        /**
         * this method gives functionality to the buttons displayed on the screen
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
                ShapeableImageView icon = findViewById(R.id.save_button);
                Snackbar.make(icon,
                        R.string.save_confirmation,
                        Snackbar.LENGTH_SHORT).show();
            } else Log.d(TAG, "Unknown ID: " + id);
        }

        //gives menu functionality to flip between screens
        public boolean onOptionsItemSelected(MenuItem item) {
            Class changedClass = this.getClass();
            int menuId = item.getItemId();

            if (menuId == R.id.menu_house)
            {
                changedClass = DreamHouseActivity.class;

            } else if (menuId == R.id.menu_ambition)
            {
                changedClass = DreamJobActivity.class;

            } else if (menuId == R.id.menu_pet)
            {
                changedClass = DreamPetActivity.class;

            } else if (menuId == R.id.menu_you)
            {
                changedClass = DreamYouActivity.class;

            } else if (menuId == R.id.menu_register)
            {
                changedClass = RegisterActivity.class;

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


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        //updates screen to show a given Dream character
        private void updateImage(DreamYou dreamYou) {
            ImageView youImage = findViewById(R.id.image_preview);
            youImage.setImageResource(dreamYou.getProfileImageID());
        }

        private void updateName(DreamYou profile)
        {
            TextView nameTextView = findViewById(R.id.nameText);
            nameTextView.setText(profile.getProfileName());
        }

        //the following three methods allow the user to move between assets or select the displayed asset
        private DreamYou moveToNextProfile()
        {
            index= (index + 1) % CHARACTER_PROFILES.length;
            return CHARACTER_PROFILES[index];
        }

        private DreamYou moveToPreviousProfile()
        {
            index = index - 1;
            if (index <0)
                index = CHARACTER_PROFILES.length - 1;
            return CHARACTER_PROFILES[index];
        }

        private DreamYou getCurrentProfile ()
        {
            return CHARACTER_PROFILES[index];
        }


    }





