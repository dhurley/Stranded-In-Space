package net.djhurley.strandedinspace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import net.djhurley.strandedinspace.StrandedInSpace;
import net.djhurley.strandedinspace.common.Assets;
import net.djhurley.strandedinspace.common.Constants;
import net.djhurley.strandedinspace.tween.SpriteAccessor;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

/**
 * Created by DJHURLEY on 04/04/2015.
 */
public class SplashScreen implements Screen {

    private final StrandedInSpace game;
    private final boolean isSoundOn;
    private SpriteBatch spriteBatch;
    private Sprite sprite;
    private TweenManager tweenManager;

    public SplashScreen(StrandedInSpace game, boolean isSoundOn) {
        this.game = game;
        this.isSoundOn = isSoundOn;
    }

    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        tweenManager = new TweenManager();
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());

        sprite = new Sprite(Assets.instance.getSplashScreenTexture());
        sprite.setSize(Constants.getWidth(), Constants.getHeight());

        Tween.set(sprite, SpriteAccessor.ALPHA).target(0).start(tweenManager);
        Tween.to(sprite, SpriteAccessor.ALPHA, 3).target(1).repeatYoyo(1, 1).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                game.setScreen(new MainMenuScreen(game, isSoundOn));
            }
        }).start(tweenManager);
    }

    @Override
    public void hide() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tweenManager.update(delta);

        spriteBatch.begin();
        sprite.draw(spriteBatch);
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}