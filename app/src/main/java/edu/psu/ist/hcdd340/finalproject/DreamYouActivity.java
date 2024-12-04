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

final class DreamYou {
    private final String youName;
    private final int youImageID;

    DreamYou(String youName, int youImageID) {
        this.youName = youName;
        this.youImageID = youImageID;
    }

    public int getProfileImageID() {
        return youImageID;
    }

    public String getProfileName() {
        return youName;
    }

}
    public class DreamYouActivity extends AppCompatActivity implements View.OnClickListener{

        public static final String TAG = "DREAM_YOU_ACTIVITY";

        private static final int[] ACTION_ICON_IDS = {R.id.next_button, R.id.save_button, R.id.prev_button};

        private final DreamYou[] CHARACTER_PROFILES = {
                new DreamYou("Hercules", R.drawable.hercules),
                new DreamYou("Cool Guy", R.drawable.cool_guy),
                new DreamYou("Cleopatra", R.drawable.cleopatra),
                new DreamYou("Fancy Lady", R.drawable.fancylady),
                new DreamYou("Lady", R.drawable.lady),
                new DreamYou("Some Guy", R.drawable.someguy)
        };

        private static int index = 0;

        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_dream);
            updateImage(getCurrentProfile());


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


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        private void updateImage(DreamYou dreamYou) {
            ImageView youImage = findViewById(R.id.image_preview);
            youImage.setImageResource(dreamYou.getProfileImageID());
        }

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





