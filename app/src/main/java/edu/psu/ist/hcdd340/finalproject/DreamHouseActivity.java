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

final class DreamHouse {
    private final String houseName;
    private final int houseImageId;

    public DreamHouse(String houseName, int houseImageId) {
        this.houseName = houseName;
        this.houseImageId = houseImageId;
    }

    public String getHouseName() {
        return houseName;
    }

    public int getHouseImageId() {
        return houseImageId;
    }
}

public class DreamHouseActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "DREAM_HOUSE_ACTIVITY";

    //Array of possible options for a dream house
    private final DreamHouse[] DREAM_HOUSES = {new DreamHouse("Cloud Island", R.drawable.floatingisland), new DreamHouse("Night Watch House", R.drawable.nightwatchhouse), new DreamHouse("Hidden Island", R.drawable.hiddenisland), new DreamHouse("Barn", R.drawable.barnhouse)};

    private static final int[] ACTION_ICON_IDS = {R.id.next_button, R.id.save_button, R.id.prev_button};

    private static int index = 0;

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
    private void updateImage(DreamHouse dreamHouse) {
        ImageView img = findViewById(R.id.image_preview);
        img.setImageResource(dreamHouse.getHouseImageId());
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

    private DreamHouse moveToNextProfile() {
        index = (index + 1) % DREAM_HOUSES.length;
        return DREAM_HOUSES[index];
    }

    private DreamHouse moveToPreviousProfile() {
        index = index - 1;
        if (index < 0) index = DREAM_HOUSES.length - 1;
        return DREAM_HOUSES[index];
    }

    private DreamHouse getCurrentProfile() {
        return DREAM_HOUSES[index];
    }
}

