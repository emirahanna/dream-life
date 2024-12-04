package edu.psu.ist.hcdd340.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //Array of possible options for a dream house
    private final DreamHouse[] DREAM_HOUSES = {new DreamHouse("Cloud Island", R.drawable.floatingisland), new DreamHouse("Night Watch House", R.drawable.nightwatchhouse), new DreamHouse("Hidden Island", R.drawable.hiddenisland), new DreamHouse("Barn", R.drawable.barnhouse)};

    private static final int[] ACTION_ICON_IDS = {R.id.next_button, R.id.save_button, R.id.prev_button};

    private static int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dreamhouse);
        showDreamHouse(getCurrentProfile());

        //attach action listeners to buttons
        for (int id : ACTION_ICON_IDS) {
            findViewById(id).setOnClickListener(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.d("FUCK", "menu inflated");
        return true;
    }

    //updates screen to show a given DreamHouse
    private void showDreamHouse(DreamHouse dreamHouse) {
        ImageView houseImage = findViewById(R.id.house_image);
        houseImage.setImageResource(dreamHouse.getHouseImageId());

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if ((id == R.id.next_button)) {
            showDreamHouse(moveToNextProfile());
        } else if (id == R.id.prev_button) {
            showDreamHouse(moveToPreviousProfile());
        } else if (id == R.id.save_button) {
            Log.d("FUCK", "saves it: " + id);
        } else Log.d("FUCK", "Unknown ID: " + id);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("FUCK", "pre-intent");
        int menuId = item.getItemId();
        Log.d("FUCK", "post get item id");

        if (menuId == R.id.menu_profile) {
            Intent intent = new Intent(this, DreamYouActivity.class);
            startActivity(intent);
            Log.d("FUCK", "post-intent");
            return true;
        }

        return super.onOptionsItemSelected(item);
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