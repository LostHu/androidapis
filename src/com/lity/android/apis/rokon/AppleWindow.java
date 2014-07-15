package com.lity.android.apis.rokon;


import android.renderscript.Font;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.stickycoding.rokon.CirclePolygon;
import com.stickycoding.rokon.DrawPriority;
import com.stickycoding.rokon.LineSprite;
import com.stickycoding.rokon.PhysicalSprite;
import com.stickycoding.rokon.PolygonSprite;
import com.stickycoding.rokon.RokonActivity;
import com.stickycoding.rokon.Sprite;
import com.stickycoding.rokon.TextSprite;
import com.stickycoding.rokon.TextTexture;

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
	    setDrawPriority(DrawPriority.PRIORITY_NORMAL);
	    setGraphicsPath("textures/");
	    
//	    setScene(scene)
	    createEngine(true);
	    World world = new World(new Vector2(0f, 0f), false);
	    BodyDef def = new BodyDef();
	    def.type = BodyType.DynamicBody;
	    world.createBody(def);
	}
	
	@Override
	public void onLoadComplete() {
	    Textures.load();
	    scene = new GameScene();
	    setScene(scene);
	    
//	    TextTexture tt = Font.createFromAsset(rs, res, path, pointSize)
//	    TextSprite ts = new TextSprite(0, 0, 100, 100);
//	    ts.setTexture(Textures.atlas);
//	    ts.setText("hello, world");
	    
	    LineSprite ls = new LineSprite(0, 0, 100, 100);
//	    ls.setSpeed(10, 10);
	  
	    
	    
//	    Sprite ps = new Sprite(0, 0, 10, 10);
//	    PolygonSprite ps = new PolygonSprite(polygon, x, y, width, height)
	    PhysicalSprite ps = new PhysicalSprite(0, 0, 100, 100);
//	    ps.
//	    ps.setTexture(Textures.atlas);
//	    ps.createCircleShape();
//	    ps.setSpeedX(10);
//	    scene.add(ps);
	    
	    scene.add(ls);
	    
	}
}
