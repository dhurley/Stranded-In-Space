package com.twodcrazedgaming.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.twodcrazedgaming.common.Assets;
import com.twodcrazedgaming.common.Constants;
import com.twodcrazedgaming.game.objects.Spaceship;
import com.twodcrazedgaming.screens.GameOverScreen;
import com.twodcrazedgaming.screens.GameScreen;

/**
 * Created by DJHURLEY on 20/01/2015.
 */
public class WorldRenderer implements Disposable {

    private static final String TAG = WorldRenderer.class.getName();

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private WorldController worldController;
    private Spaceship spaceship;
    private Sprite backgroundSprite;

    private boolean isSoundOn;

    public WorldRenderer(boolean isSoundOn){
        this.isSoundOn = isSoundOn;
        init();
    }

    private void init(){
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.position.set(0, 0, 0);
        camera.update();

        initializeBackground();
        spaceship = new Spaceship(isSoundOn);
        worldController = new WorldController(spaceship);
        Gdx.input.setInputProcessor(worldController);
    }

    public void render(){
        renderBackground();
        spaceship.render(batch);
    }

    public void resize(int width, int height){
        camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
        camera.update();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    private void initializeBackground() {
        backgroundSprite = new Sprite(Assets.instance.getSpaceBackgroundTexture());
        backgroundSprite.setPosition(0, 0);
        backgroundSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private void renderBackground() {
        batch.begin();
        backgroundSprite.draw(batch);
        batch.end();
    }

    public Spaceship getSpaceship() {
        return spaceship;
    }
}
