package com.twodcrazedgaming.screens;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.twodcrazedgaming.common.Assets;
import com.twodcrazedgaming.game.WorldRenderer;

/**
 * Created by DJHURLEY on 25/01/2015.
 */
public class GameScreen implements Screen {
    private static final String TAG = GameScreen.class.getName();

    private WorldRenderer worldRenderer;

    private boolean paused;

    @Override
    public void show() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        Assets.instance.init(new AssetManager());
        worldRenderer = new WorldRenderer();
        paused = false;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        worldRenderer.render();
    }

    @Override
    public void resize(int width, int height) {
        worldRenderer.resize(width, height);
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        Assets.instance.init(new AssetManager());
        paused = false;
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        worldRenderer.dispose();
    }
}
