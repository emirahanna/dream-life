package edu.psu.ist.hcdd340.finalproject;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    public static final String PREFS_NAME = "USER_DATABASE";
    private static final String USERS_KEY = "Users";
    public static final String CURRENT_STATE_KEY = "logged_in";
    public static final String CURRENT_USER_KEY = "curr_user";
    private SharedPreferences sharedPreferences;
    private SharedPreferences currentState;
    private Gson gson;

    public UserManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        currentState = context.getSharedPreferences(MainActivity.CURRENT_STATE, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    // Save all users
    public void saveUsers(List<User> users) {
        String json = gson.toJson(users);
        sharedPreferences.edit().putString(USERS_KEY, json).apply();
        currentState.edit().putBoolean(CURRENT_STATE_KEY, true).apply();
        currentState.edit().putString(CURRENT_USER_KEY, users.get(0).getUsername()).apply();
    }

    // Retrieve all users
    public List<User> getUsers() {
        String json = sharedPreferences.getString(USERS_KEY, "");
        Type userListType = new TypeToken<ArrayList<User>>() {}.getType();
        return json.isEmpty() ? new ArrayList<>() : gson.fromJson(json, userListType);
    }

    // Add a single user
    public void addUser(User newUser) {
        List<User> users = getUsers();
        users.add(newUser);
        saveUsers(users);
    }

    // Check if a username exists
    public boolean isUsernameTaken(String username) {
        for (User user : getUsers()) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    // Validate login
    public boolean validateLogin(String username, String password) {
        for (User user : getUsers()) {
            if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)) {
                currentState.edit().putBoolean(CURRENT_STATE_KEY, true).apply();
                currentState.edit().putString(CURRENT_USER_KEY, username).apply();
                return true;
            }
        }
        return false;
    }

    public boolean logOutUser(){
        currentState.edit().putBoolean(UserManager.CURRENT_STATE_KEY, false).apply();
        return true;
    }
}
