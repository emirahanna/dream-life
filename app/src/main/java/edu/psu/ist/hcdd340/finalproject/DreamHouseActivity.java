package edu.psu.ist.hcdd340.finalproject;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;

final class HouseProfile{
    private final String houseName;
    private final int houseImageID;

    HouseProfile(String houseName, int houseImageID)
    {
        this.houseName = houseName;
        this.houseImageID =houseImageID;
    }

    public int getHouseImageID() {
        return houseImageID;
    }

    public String getHouseName() {
        return houseName;
    }


public class DreamHouseActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "DREAM_LIFE_ACTIVITY";

    private final int[] ACTION_ICON_IDS =
            {
                    R.id.prev_button,
                    R.id.next_button,
                    R.id.next_button
            };

    private final PetProfile[] HOUSE_PROFILES = {
            new PetProfile("Barn house", R.drawable.barnhouse),
            new PetProfile("Floating Island", R.drawable.floatingisland),
            new PetProfile("Hidden Island", R.drawable.hiddenisland),
            new PetProfile("Night Watch House", R.drawable.nightwatchhouse),
            new PetProfile("Robot House", R.drawable.robothouse)
    };

    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dreamhouse);//have to make this an xml file and then itll be fine

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

        PetProfile houseProfile = getCurrentProfile();


    }

    private PetProfile moveToNextProfile() {
        index = (index + 1) % HOUSE_PROFILES.length;
        return HOUSE_PROFILES[index];
    }

    private PetProfile moveToPreviousProfile() {
        index = index - 1;
        if (index < 0)
            index = HOUSE_PROFILES.length - 1;
        return HOUSE_PROFILES[index];
    }

    private PetProfile getCurrentProfile() {
        return HOUSE_PROFILES[index];
    }
}
}
