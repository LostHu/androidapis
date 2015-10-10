package com.lity.android.apis.rokon;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.stickycoding.rokon.Scene;
import com.stickycoding.rokon.background.FixedBackground;
public class GameScene extends Scene {
    private FixedBackground background;
    public GameScene() {
        super();
        background = new FixedBackground(Textures.background);
        setBackground(background);
        
//        setBackground(background)
        
		setWorld(new World(new Vector2(0, 6), false));
//		world.setContactListener(contactListener = new MyContactListener());
    }
    @Override
    public void onGameLoop() {
    	
    }
    @Override
    public void onPause() {
    }
    @Override
    public void onResume() {
    }
    @Override
    public void onReady() {
    	
    }
}
