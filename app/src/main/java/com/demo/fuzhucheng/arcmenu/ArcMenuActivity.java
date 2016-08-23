package com.demo.fuzhucheng.arcmenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.demo.fuzhucheng.R;
import com.demo.fuzhucheng.arcmenu.RayMenu;

public class ArcMenuActivity extends Activity {
    private static final int[] ITEM_DRAWABLES = {R.drawable.composer_camera, R.drawable
            .composer_music,
            R.drawable.composer_place, R.drawable.composer_sleep, R.drawable.composer_thought, R
            .drawable.composer_with};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arc_menu);
        com.demo.fuzhucheng.arcmenu.ArcMenu arcMenu = (com.demo.fuzhucheng.arcmenu.ArcMenu) findViewById(R.id.arc_menu);
        com.demo.fuzhucheng.arcmenu.ArcMenu arcMenu2 = (com.demo.fuzhucheng.arcmenu.ArcMenu) findViewById(R.id.arc_menu_2);

        initArcMenu(arcMenu, ITEM_DRAWABLES);
        initArcMenu(arcMenu2, ITEM_DRAWABLES);

        RayMenu rayMenu = (RayMenu) findViewById(R.id.ray_menu);
        final int itemCount = ITEM_DRAWABLES.length;
        for (int i = 0; i < itemCount; i++) {
            ImageView item = new ImageView(this);
            item.setImageResource(ITEM_DRAWABLES[i]);

            final int position = i;
            rayMenu.addItem(item, new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(ArcMenuActivity.this, "position:" + position, Toast.LENGTH_SHORT)
                            .show();
                }
            });// Add a menu item
        }
    }



    private void initArcMenu(com.demo.fuzhucheng.arcmenu.ArcMenu menu, int[] itemDrawables) {
        final int itemCount = itemDrawables.length;
        for (int i = 0; i < itemCount; i++) {
            ImageView item = new ImageView(this);
            item.setImageResource(itemDrawables[i]);

            final int position = i;
            menu.addItem(item, new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(ArcMenuActivity.this, "position:" + position, Toast.LENGTH_SHORT)
                            .show();
                }
            });
        }
    }
}
