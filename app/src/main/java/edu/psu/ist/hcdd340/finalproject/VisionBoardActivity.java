package edu.psu.ist.hcdd340.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class VisionBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visionboard);

        SharedPreferences sharedPreferences = getSharedPreferences("UserSelections", MODE_PRIVATE);

        int selfImageResId = sharedPreferences.getInt("selfProfileImageID", R.drawable.avtr);
        int homeImageResId = sharedPreferences.getInt("homeImageID", R.drawable.avtr);
        int jobImageResId = sharedPreferences.getInt("jobImageID", R.drawable.avtr);
        int petImageResId = sharedPreferences.getInt("petImageID", R.drawable.avtr);

        ImageView ivSelf = findViewById(R.id.ivSelf);
        ImageView ivHome = findViewById(R.id.ivHome);
        ImageView ivJob = findViewById(R.id.ivJob);
        ImageView ivPet = findViewById(R.id.ivPet);

        ivSelf.setImageResource(selfImageResId);
        ivHome.setImageResource(homeImageResId);
        ivJob.setImageResource(jobImageResId);
        ivPet.setImageResource(petImageResId);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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



}