package com.twodcrazedgaming.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.twodcrazedgaming.common.Assets;
import com.twodcrazedgaming.common.Constants;
import com.twodcrazedgaming.game.objects.Asteroid;
import com.twodcrazedgaming.game.objects.Spaceship;

import java.util.List;

/**
 * Created by DJHURLEY on 20/01/2015.
 */
public class WorldRenderer implements Disposable {

    private static final String TAG = WorldRenderer.class.getName();

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private WorldController worldController;
    private Spaceship spaceship;
    private AsteroidGenerator asteroidGenerator;
    private int noOfAsteroids = 10;

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

        asteroidGenerator = new AsteroidGenerator(getWorldSize(), noOfAsteroids);
        for(int x = 1; x <= noOfAsteroids; x++){
            asteroidGenerator.createAsteroid();
        }

        worldController = new WorldController(spaceship);
        Gdx.input.setInputProcessor(worldController);

        font = Assets.instance.getDroidSansFont();
    }

    public void render(){
        renderBackground();
        spaceship.render(batch);
        asteroidGenerator.render(batch);
        renderBanner();
        renderScore();
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
        blackBannerSprite.setPosition(0, getBannerPositionY());
        blackBannerSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() - getBannerPositionY());
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

    private void renderScore() {
        batch.begin();
        font.draw(batch, "Score: " + asteroidGenerator.getScore(), Gdx.graphics.getWidth()/12, getFontPositionY());
        batch.end();
    }

    private void renderFuelLevel() {
        batch.begin();
        font.draw(batch, "Fuel: " + spaceship.getFuelLevel() + "%", ((3 * Gdx.graphics.getWidth())/4), getFontPositionY());
        batch.end();
    }

    private int getBannerPositionY() {
        return Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/15;
    }

    private int getFontPositionY() {
        return Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/30;
    }

    public Spaceship getSpaceship() {
        return spaceship;
    }

    public Vector2 getWorldSize() {
        return new Vector2(Gdx.graphics.getWidth(), getBannerPositionY());
    }

    public List<Asteroid> getAsteroids() {
        return asteroidGenerator.getAsteroids();
    }
}
