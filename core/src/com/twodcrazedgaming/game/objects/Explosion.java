package com.twodcrazedgaming.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.twodcrazedgaming.common.Assets;

/**
 * Created by DJHURLEY on 26/03/2015.
 */
public class Explosion {
    private static final String TAG = Explosion.class.getName();

    private Vector2 size;
    private Vector2 position;

    private Animation animation;
    private TextureRegion[] walkFrames;
    private Texture texture;

    private TextureRegion currentFrame;
    private float stateTime;

    public Explosion(Vector2 position, Vector2 size){
        this.size = size;
        this.position = position;

        texture = Assets.instance.getExplosionTexture();
        TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth()/10, texture.getHeight());

        walkFrames = new TextureRegion[10];
        for (int index = 0; index < 10; index++) {
            walkFrames[index] = tmp[0][index];
        }
        animation = new Animation(0.075f, walkFrames);

        stateTime = 0f;
    }

    public void render(SpriteBatch batch){
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = animation.getKeyFrame(stateTime, true);

        batch.begin();
        batch.draw(currentFrame, position.x, position.y, size.x, size.y);
        batch.end();
    }

    public boolean isEndOfAnimation(){
        return animation.isAnimationFinished(stateTime);
    }

}
