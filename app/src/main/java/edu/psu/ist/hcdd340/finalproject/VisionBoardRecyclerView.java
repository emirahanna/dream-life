package edu.psu.ist.hcdd340.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VisionBoardRecyclerView extends AppCompatActivity {
    public static VisionBoardAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_visionboard_recycler_view);

        VisionBoardManager vbManager = new VisionBoardManager(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get vision boards for the current user
        String currentUserName = getCurrentUserName();
        List<VisionBoard> visionBoards = vbManager.getVisionBoards(currentUserName);

        // Set up the adapter
        adapter = new VisionBoardAdapter(visionBoards, this);
        recyclerView.setAdapter(adapter);
    }

    private String getCurrentUserName() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(MainActivity.CURRENT_STATE, MODE_PRIVATE);
        return sharedPreferences.getString(UserManager.CURRENT_USER_KEY, "bestie");
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