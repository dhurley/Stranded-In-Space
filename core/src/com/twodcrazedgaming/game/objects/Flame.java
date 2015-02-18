package com.twodcrazedgaming.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.twodcrazedgaming.common.Assets;

/**
 * Created by DJHURLEY on 16/02/2015.
 */
public class Flame {

    private static final String TAG = Flame.class.getName();

    private Texture texture = Assets.instance.getFlameTexture();
    private Sprite sprite = new Sprite(texture);
    private Vector2 size;
    private Vector2 position;
    private float rotation;

    public Flame(Vector2 position, float rotation){
        Gdx.app.debug(TAG, "flame position: " + position);
        this.size = new Vector2(Gdx.graphics.getWidth() / 18, Gdx.graphics.getWidth() / 18);
        this.position = position;
        this.rotation = rotation + 90;

        sprite.setSize(size.x, size.y);
        sprite.setOrigin(size.x / 2, size.y / 2);
    }

    public void render(SpriteBatch batch) {
        sprite.setPosition(position.x, position.y);
        sprite.setRotation(rotation);

        batch.begin();
        sprite.draw(batch);
        batch.end();
    }

}
