package edu.psu.ist.hcdd340.finalproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class VisionBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visionboard);

        SharedPreferences sharedPreferences = getSharedPreferences("USerSelections", MODE_PRIVATE);

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
}