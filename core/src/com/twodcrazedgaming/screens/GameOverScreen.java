package com.twodcrazedgaming.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.twodcrazedgaming.common.Assets;

/**
 * Created by DJHURLEY on 21/02/2015.
 */
public class GameOverScreen implements Screen {
    private static final String TAG = GameOverScreen.class.getName();
    private final Game game;

    private SpriteBatch batch;
    private Sprite gameOverSprite;

    public GameOverScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        gameOverSprite = new Sprite(Assets.instance.getGameOverTexture());
        gameOverSprite.setPosition(0, Gdx.graphics.getHeight()/4);
        gameOverSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/3);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        gameOverSprite.draw(batch);
        batch.end();
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
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
