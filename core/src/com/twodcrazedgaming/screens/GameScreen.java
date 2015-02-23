package com.twodcrazedgaming.screens;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.twodcrazedgaming.common.Assets;
import com.twodcrazedgaming.game.WorldRenderer;
import com.twodcrazedgaming.game.objects.Spaceship;

/**
 * Created by DJHURLEY on 25/01/2015.
 */
public class GameScreen implements Screen {
    private static final String TAG = GameScreen.class.getName();

    private WorldRenderer worldRenderer;

    private boolean isSoundOn;
    private boolean paused;

    public GameScreen(boolean isSoundOn){
        this.isSoundOn = isSoundOn;
    }

    @Override
    public void show() {
        worldRenderer = new WorldRenderer(isSoundOn);
        paused = false;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        worldRenderer.render();
        if(isSpaceshipOffScreen()){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameOverScreen());
        }
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

    private boolean isSpaceshipOffScreen() {
        Spaceship spaceship = worldRenderer.getSpaceship();
        Vector2 spaceshipPosition = spaceship.getPosition();
        Vector2 spaceshipSize = spaceship.getSize();
        if(spaceshipPosition.x + spaceshipSize.x < 0 || spaceshipPosition.x > Gdx.graphics.getWidth()){
            return true;
        }else if(spaceshipPosition.y + spaceshipSize.y < 0 || spaceshipPosition.y > Gdx.graphics.getHeight()){
            return true;
        }else {
            return false;
        }
    }
}
