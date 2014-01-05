package com.lity.android.apis.rokon;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.stickycoding.rokon.DrawPriority;
import com.stickycoding.rokon.RokonActivity;

public class AppleWindow extends RokonActivity {
	public static final float GAME_WIDTH = 4.8f;
	public static final float GAME_HEIGHT = 3.2f;
	private GameScene scene;
	
	@Override
	public void onCreate() {
	    debugMode();
	    forceFullscreen();
	    forceLandscape();
	    setGameSize(GAME_WIDTH, GAME_HEIGHT);
	    setDrawPriority(DrawPriority.PRIORITY_VBO);
	    setGraphicsPath("textures/");
	    createEngine(true);
	    World world = new World(new Vector2(0f, 0f), false);
	    world.createBody(new BodyDef());
	}
	
	@Override
	public void onLoadComplete() {
	    Textures.load();
	    scene = new GameScene();
	    setScene(scene);
	}
}
