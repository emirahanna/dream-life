package edu.psu.ist.hcdd340.finalproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VisionBoardManager {
    public static final String VISION_BOARD_MANAGER_TAG = "VISION_BOARD";
    private SharedPreferences sharedPreferencesVisionBoard;
    private SharedPreferences sharedPreferencesSelections;
    private Context context;

    public VisionBoardManager(Context context) {
        this.context = context;
        sharedPreferencesVisionBoard = context.getSharedPreferences(VISION_BOARD_MANAGER_TAG, Context.MODE_PRIVATE);
        sharedPreferencesSelections = context.getSharedPreferences(MainActivity.CURRENT_STATE, Context.MODE_PRIVATE);
    }


    public void saveVisionBoard(String title) {
        SharedPreferences.Editor editor = sharedPreferencesVisionBoard.edit();

        // Get the current username
        String userName = sharedPreferencesSelections.getString("curr_user", "");
        if (userName.isEmpty()) {
            Log.e("SaveVisionBoard", "No current user found. Aborting save.");
            return;
        }

        // Get the existing list of vision boards for this user
        String json = sharedPreferencesVisionBoard.getString(userName, "[]");
        Gson gson = new Gson();
        Type type = new TypeToken<List<VisionBoard>>() {
        }.getType();
        List<VisionBoard> visionBoardList;

        try {
            // Attempt to deserialize JSON into a list of VisionBoard objects
            visionBoardList = gson.fromJson(json, type);

            // Ensure the list is not null
            if (visionBoardList == null) {
                visionBoardList = new ArrayList<>();
            }
        } catch (Exception e) {
            // Handle invalid JSON by creating a new empty list
            visionBoardList = new ArrayList<>();
            Log.e("SaveVisionBoard", "Error parsing vision boards JSON. Resetting to empty list.", e);
        }

        // Add the new vision board
        VisionBoard newBoard = createVisionBoard(sharedPreferencesSelections, userName, title);
        visionBoardList.add(newBoard);

        // Save the updated list back to SharedPreferences
        String updatedJson = gson.toJson(visionBoardList);
        editor.putString(userName, updatedJson); // Key is the username
        editor.apply();

        SharedPreferences.Editor editSelections = sharedPreferencesSelections.edit();
        editSelections.remove("pet_name");
        editSelections.remove("pet_image_ID");
        editSelections.remove("you_name");
        editSelections.remove("you_image_ID");
        editSelections.remove("job_name");
        editSelections.remove("job_image_ID");
        editSelections.remove("house_name");
        editSelections.remove("house_image_ID");
        editSelections.apply();

        Log.d("SaveVisionBoard", "Vision board saved successfully for user: " + userName);
    }


    private VisionBoard createVisionBoard(SharedPreferences sp, String u, String title) {
        String dreamHouseName = sp.getString("house_name", "");
        int dreamHouseImageID = sp.getInt("house_image_ID", 0);

        String dreamYouName = sp.getString("you_name", "");
        int dreamYouImageID = sp.getInt("you_image_ID", 0);

        String dreamPetName = sp.getString("pet_name", "");
        int dreamPetImageID = sp.getInt("pet_image_ID", 0);

        String dreamJobName = sp.getString("job_name", "");
        int dreamJobImageID = sp.getInt("job_image_ID", 0);

        return new VisionBoard(u, title, new Date().toString(), new DreamHouse(dreamHouseName, dreamHouseImageID), new DreamJob(dreamJobName, dreamJobImageID), new DreamYou(dreamYouName, dreamYouImageID), new DreamPet(dreamPetName, dreamPetImageID));
    }

    public boolean canCreateVisionBoard() {
        String dreamHouse = sharedPreferencesSelections.getString("house_name", "");
        String dreamYou = sharedPreferencesSelections.getString("job_name", "");
        String dreamJob = sharedPreferencesSelections.getString("pet_name", "");
        String dreamPet = sharedPreferencesSelections.getString("you_name", "");

        return !dreamHouse.isEmpty() && !dreamJob.isEmpty() && !dreamPet.isEmpty() && !dreamYou.isEmpty();
    }

    public List<VisionBoard> getVisionBoards(String userName) {
        String json = sharedPreferencesVisionBoard.getString(userName, "[]");
        Gson gson = new Gson();
        Type type = new TypeToken<List<VisionBoard>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public void removeVisionBoard(String title) {
        SharedPreferences.Editor editor = sharedPreferencesVisionBoard.edit();

        // Get the current username
        String userName = sharedPreferencesSelections.getString("curr_user", "");
        if (userName.isEmpty()) {
            Log.e("RemoveVisionBoard", "No current user found. Aborting remove.");
            return;
        }

        // Get the existing list of vision boards for this user
        String json = sharedPreferencesVisionBoard.getString(userName, "[]");
        Gson gson = new Gson();
        Type type = new TypeToken<List<VisionBoard>>(){}.getType();
        List<VisionBoard> visionBoardList;

        try {
            // Attempt to deserialize JSON into a list of VisionBoard objects
            visionBoardList = gson.fromJson(json, type);

            // Ensure the list is not null
            if (visionBoardList == null) {
                visionBoardList = new ArrayList<>();
            }
        } catch (Exception e) {
            // Handle invalid JSON by creating a new empty list
            visionBoardList = new ArrayList<>();
            Log.e("RemoveVisionBoard", "Error parsing vision boards JSON. Resetting to empty list.", e);
        }

        // Find and remove the vision board with the specified title
        boolean removed = false;
        for (int i = 0; i < visionBoardList.size(); i++) {
            if (visionBoardList.get(i).getVbTitle().equals(title)) {
                visionBoardList.remove(i);
                removed = true;
                break;
            }
        }

        if (removed) {
            // Save the updated list back to SharedPreferences
            String updatedJson = gson.toJson(visionBoardList);
            editor.putString(userName, updatedJson); // Key is the username
            editor.apply();

            Log.d("RemoveVisionBoard", "Vision board with title '" + title + "' removed successfully.");
        } else {
            Log.e("RemoveVisionBoard", "No vision board found with title '" + title + "'.");
        }
    }

}
