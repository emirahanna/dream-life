package edu.psu.ist.hcdd340.finalproject;

import android.content.Context;

//Processes repetitive logic in code
public class AppHelper {

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
            changedClass = VisionBoardActivity.class;
        }

        return changedClass;
    }
//    public static final int[] ACTION_ICON_IDS =
//            {
//                    R.id.prev_button,
//                    R.id.next_button,
//                    R.id.next_button
//            };
//
//    //updates screen to show a given DreamHouse
//    private static void updateImage(DreamHouse dreamHouse) {
//        ImageView houseImage = findViewById(R.id.house_image);
//        houseImage.setImageResource(dreamHouse.getHouseImageId());
//    }
}
