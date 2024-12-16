package edu.psu.ist.hcdd340.finalproject;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

//Processes repetitive logic in code
public class AppHelper{

    public static Class processItemsSelectedMainMenu(int menuId, Class changedClass, Context context){
        if (menuId == R.id.menu_house)
        {
            changedClass = DreamHouseActivity.class;

        } else if (menuId == R.id.menu_ambition)
        {
            changedClass = DreamJobActivity.class;

        } else if (menuId == R.id.menu_pet)
        {
            changedClass = DreamPetActivity.class;

        } else if (menuId == R.id.menu_you)
        {
            changedClass = DreamYouActivity.class;

        }else if (menuId == R.id.menu_logout) {
            changedClass = LogInActivity.class;
            UserManager userManager = new UserManager(context);
            userManager.logOutUser();
        }else if (menuId == R.id.menu_vision){
            changedClass = VisionBoardRecyclerView.class;
        }

        return changedClass;
    }
    public static void showInputDialog(Context context) {
        // Create an EditText to take user input
        final EditText input = new EditText(context);

        // Create and configure the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Name Your Vision Board")
                .setMessage("Please enter a title:")
                .setView(input) // Set the EditText inside the dialog
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Get the text input by the user
                        String userInput = input.getText().toString();
                        VisionBoardManager vbManager = new VisionBoardManager(context);
                        vbManager.saveVisionBoard(userInput);

                    }
                })
                .setNegativeButton("Cancel", null) // Cancel button
                .show();
    }

}
