package com.twodcrazedgaming.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.twodcrazedgaming.common.Assets;

/**
 * Created by DJHURLEY on 25/01/2015.
 */
public class FuelBar {
    private static final String TAG = FuelBar.class.getName();

    private TextureRegion currentFuelBar;
    private Sprite sprite;
    private Vector2 size;
    private Vector2 position;

    public FuelBar(){
        size = new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getWidth()/13);
        position = new Vector2((Gdx.graphics.getWidth()/2), Gdx.graphics.getHeight() - (size.y));

        updateFuelBar(100);
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        sprite.draw(batch);
        batch.end();
    }

    public void updateFuelBar(int level){
        currentFuelBar = Assets.instance.getFuelBarTexture(level);
        sprite = new Sprite(currentFuelBar);
        sprite.setSize(size.x, size.y);
        sprite.setPosition(position.x, position.y);
    }
}
