package edu.psu.ist.hcdd340.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.Date;

final class VisionBoard{
    private String userName;
    private String date;
    private String vbTitle;
    private Date createdOn;
    private DreamHouse dreamHouse;
    private DreamJob dreamJob;
    private DreamYou dreamYou;
    private DreamPet dreamPet;

    public VisionBoard(String userName, String date, String vbTitle, Date createdOn, DreamHouse dreamHouse, DreamJob dreamJob, DreamYou dreamYou, DreamPet dreamPet) {
        this.userName = userName;
        this.date = date;
        this.vbTitle = vbTitle;
        this.createdOn = createdOn;
        this.dreamHouse = dreamHouse;
        this.dreamJob = dreamJob;
        this.dreamYou = dreamYou;
        this.dreamPet = dreamPet;
    }
}

public class VisionBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visionboard);

//        ImageView ivSelf = findViewById(R.id.ivSelf);
//        ImageView ivHome = findViewById(R.id.ivHome);
//        ImageView ivJob = findViewById(R.id.ivJob);
//        ImageView ivPet = findViewById(R.id.ivPet);
//
//        Button btnClear = findViewById(R.id.btnClear);
//
//        btnClear.setOnClickListener(v ->{
//            int defaultAvatar = R.drawable.avtr;
//
//            ivSelf.setImageResource(defaultAvatar);
//            ivHome.setImageResource(defaultAvatar);
//            ivJob.setImageResource(defaultAvatar);
//            ivPet.setImageResource(defaultAvatar);
//
//            SharedPreferences sharedPreferences = getSharedPreferences("UserSelection", MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//
//            editor.remove("selfProfileImageID");
//            editor.remove("homeImageID");
//            editor.remove("jobImageID");
//            editor.remove("petImageID");
//            editor.apply();
//
//            Snackbar.make(v, "All selections cleared!", Snackbar.LENGTH_SHORT).show();
//        });
//
//        SharedPreferences sharedPreferences = getSharedPreferences("UserSelections", MODE_PRIVATE);
//
//        int selfImageId = sharedPreferences.getInt("selfProfileImageID", R.drawable.avtr);
//        int homeImageId = sharedPreferences.getInt("houseImageID", R.drawable.avtr);
//        int jobImageId = sharedPreferences.getInt("jobImageID", R.drawable.avtr);
//        int petImageId = sharedPreferences.getInt("petImageID", R.drawable.avtr);
//
//        ivSelf.setImageResource(selfImageId);
//        ivHome.setImageResource(homeImageId);
//        ivJob.setImageResource(jobImageId);
//        ivPet.setImageResource(petImageId);

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
        } else if (menuId == R.id.menu_logout) {
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