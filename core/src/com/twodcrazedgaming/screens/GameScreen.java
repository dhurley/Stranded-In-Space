package com.twodcrazedgaming.screens;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.twodcrazedgaming.common.Assets;
import com.twodcrazedgaming.game.WorldRenderer;
import com.twodcrazedgaming.game.objects.Asteroid;
import com.twodcrazedgaming.game.objects.Spaceship;

import java.util.List;

/**
 * Created by DJHURLEY on 25/01/2015.
 */
public class GameScreen implements Screen {
    private static final String TAG = GameScreen.class.getName();
    private final Game game;

    private WorldRenderer worldRenderer;

    private boolean isSoundOn;
    private boolean paused;

    public GameScreen(Game game, boolean isSoundOn){
        this.game = game;
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
        if(isSpaceshipOffScreen() || isSpaceshipCollidingWithAsteroid()){
            dispose();
            game.setScreen(new GameOverScreen(game, isSoundOn));
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
        Vector2 worldSize = worldRenderer.getWorldSize();
        Vector2 spaceshipPosition = spaceship.getPosition();
        Vector2 spaceshipSize = spaceship.getSize();
        if(spaceshipPosition.x + spaceshipSize.x < 0 || spaceshipPosition.x > worldSize.x){
            return true;
        }else if(spaceshipPosition.y + spaceshipSize.y < 0 || spaceshipPosition.y > worldSize.y){
            return true;
        }else {
            return false;
        }
    }

    public boolean isSpaceshipCollidingWithAsteroid() {
        Spaceship spaceship = worldRenderer.getSpaceship();
        List<Asteroid> asteroids = worldRenderer.getAsteroids();
        Rectangle spaceshipBoundingRectangle = spaceship.getBoundingRectangle();
        for(Asteroid asteroid: asteroids){
            if(spaceshipBoundingRectangle.overlaps(asteroid.getBoundingRectangle())){
                return true;
            }
        }

        return false;
    }
}
