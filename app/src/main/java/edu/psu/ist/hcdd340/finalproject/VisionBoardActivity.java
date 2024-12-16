package edu.psu.ist.hcdd340.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class VisionBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visionboard);

        // Retrieve the position passed from the previous activity
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", -1); // Default to -1 if no position is passed

        SharedPreferences sp = getSharedPreferences(MainActivity.CURRENT_STATE, MODE_PRIVATE);
        String userName = sp.getString("curr_user", "");
        VisionBoardManager vbManager = new VisionBoardManager(this);
        List<VisionBoard> visionBoardList = vbManager.getVisionBoards(userName);
        if (position != -1) {
            // Assuming visionBoardList is accessible here
            VisionBoard selectedVisionBoard = visionBoardList.get(position);

            String title = selectedVisionBoard.getVbTitle();
            // Example: Set the title in a TextView
            TextView titleTextView = findViewById(R.id.visionBoardTitle);
            titleTextView.setText(title);

            String date = selectedVisionBoard.getDate();
            // Example: Set the title in a TextView
            TextView dateTextView = findViewById(R.id.dateText);
            dateTextView.setText(date);

            ImageView ivSelf = findViewById(R.id.imageViewSelf);
            ImageView ivHome = findViewById(R.id.imageViewHome);
            ImageView ivJob = findViewById(R.id.imageViewJob);
            ImageView ivPet = findViewById(R.id.imageViewPet);

            // Assuming you have image resource IDs in the VisionBoard object
            ivSelf.setImageResource(selectedVisionBoard.getDreamYouID());
            ivHome.setImageResource(selectedVisionBoard.getDreamHouseID());
            ivJob.setImageResource(selectedVisionBoard.getDreamJobID());
            ivPet.setImageResource(selectedVisionBoard.getDreamPetID());

            Button btnClear = findViewById(R.id.buttonClear);
            btnClear.setOnClickListener(v -> {
                int defaultAvatar = R.drawable.avtr;

                ivSelf.setImageResource(defaultAvatar);
                ivHome.setImageResource(defaultAvatar);
                ivJob.setImageResource(defaultAvatar);
                ivPet.setImageResource(defaultAvatar);

                SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.CURRENT_STATE, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.remove("selfProfileImageID");
                editor.remove("homeImageID");
                editor.remove("jobImageID");
                editor.remove("petImageID");
                editor.apply();
                vbManager.removeVisionBoard(selectedVisionBoard.getVbTitle());
                createIntentAndStartActivity(this, VisionBoardRecyclerView.class);
            });

            ImageButton btnHistory = findViewById(R.id.historyButton);
            btnHistory.setOnClickListener( e -> createIntentAndStartActivity(this, VisionBoardRecyclerView.class));


        } else {
            // Handle the case where the position is not valid (optional)
            Log.e("VisionBoardActivity", "Invalid position passed");
        }


    }


    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Class changedClass = this.getClass();
        int menuId = item.getItemId();

        createIntentAndStartActivity(this, AppHelper.processItemsSelectedMainMenu(menuId, changedClass, this));
        return super.onOptionsItemSelected(item);
    }

    private boolean createIntentAndStartActivity(Context context, Class cls) {
        Intent intent = new Intent(context, cls);
        startActivity(intent);
        return true;
    }

}