package com.twodcrazedgaming.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
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
    private BitmapFont font;
    private Sprite blackBannerSprite;

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
        initializeBanner();
        spaceship = new Spaceship(isSoundOn);
        worldController = new WorldController(spaceship);
        Gdx.input.setInputProcessor(worldController);

        font = Assets.instance.getDroidSansFont();
    }

    public void render(){
        renderBackground();
        spaceship.render(batch);
        renderBanner();
        renderFuelLevel();
    }

    public void resize(int width, int height){
        camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
        camera.update();
    }

    @Override
    public void dispose() {
        spaceship.dispose();
        batch.dispose();
        worldController.dispose();
    }

    private void initializeBackground() {
        backgroundSprite = new Sprite(Assets.instance.getSpaceBackgroundTexture());
        backgroundSprite.setPosition(0, 0);
        backgroundSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private void initializeBanner() {
        blackBannerSprite = new Sprite(Assets.instance.getBlackBannerTexture());
        blackBannerSprite.setPosition(0, getBannerPositionX());
        blackBannerSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() - getBannerPositionX());
    }

    private void renderBackground() {
        batch.begin();
        backgroundSprite.draw(batch);
        batch.end();
    }

    private void renderBanner() {
        batch.begin();
        blackBannerSprite.draw(batch);
        batch.end();
    }

    private void renderFuelLevel() {
        batch.begin();
        font.draw(batch, "Fuel: " + spaceship.getFuelLevel() + "%", ((3 * Gdx.graphics.getWidth())/4), getFontPositionX());
        batch.end();
    }

    private int getBannerPositionX() {
        return Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/15;
    }

    private int getFontPositionX() {
        return Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/30;
    }

    public Spaceship getSpaceship() {
        return spaceship;
    }

    public Vector2 getWorldSize() {
        return new Vector2(Gdx.graphics.getWidth(), getBannerPositionX());
    }
}
