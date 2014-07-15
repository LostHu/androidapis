package com.lity.android.apis.rokon;

import com.stickycoding.rokon.Texture;
import com.stickycoding.rokon.TextureAtlas;

public class Textures {
	public static TextureAtlas atlas;
	public static Texture background, boss;
	public static void load() {
	    atlas = new TextureAtlas();
	    atlas.insert(background = new Texture("background.png"));
	    atlas.insert(boss = new Texture("qq.png"));
	    atlas.complete();
	}
}
