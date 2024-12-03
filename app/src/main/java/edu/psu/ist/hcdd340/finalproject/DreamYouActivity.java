package edu.psu.ist.hcdd340.finalproject;


import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

final class CharacterProfile{
    private final String characterName;
    private final int characterImageID;

    CharacterProfile(String characterName, int characterImageID)
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

        private final CharacterProfile[] CHARACTER_PROFILES = {
                new CharacterProfile("Hercules", R.drawable.hercules),
                new CharacterProfile("Cool Guy", R.drawable.cool_guy),
                new CharacterProfile("Cleopatra", R.drawable.cleopatra),
                new CharacterProfile("Fancy Lady", R.drawable.fancylady),
                new CharacterProfile("Lady", R.drawable.lady),
                new CharacterProfile("Some Guy", R.drawable.someguy)};

        @Override
        public void onClick(View view) {

        }
    }


}




