package net.djhurley.strandedinspace.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import net.djhurley.strandedinspace.common.Assets;
import net.djhurley.strandedinspace.common.Constants;
import net.djhurley.strandedinspace.game.objects.Asteroid;
import net.djhurley.strandedinspace.game.objects.Explosion;
import net.djhurley.strandedinspace.game.objects.Spaceship;

import java.util.Calendar;
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
    private Explosion explosion;
    private ScoreMonitor scoreMonitor;
    private AsteroidGenerator asteroidGenerator;
    private int maxNoOfAsteroids = 15;

    private Sprite backgroundSprite;
    private BitmapFont font;

    private boolean isSoundOn;
    private Sound explosionSound = Assets.instance.getExplosionSound();
    private long timeSinceLastAsteroidCreated;

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

        asteroidGenerator = new AsteroidGenerator(getWorldSize());
        asteroidGenerator.createAsteroid();
        timeSinceLastAsteroidCreated = Calendar.getInstance().getTimeInMillis();

        scoreMonitor = new ScoreMonitor(asteroidGenerator, spaceship);

        worldController = new WorldController(spaceship);
        Gdx.input.setInputProcessor(worldController);

        font = Assets.instance.getDroidSansFont();
    }

    public void render(){
        generateNewAsteroid();

        renderBackground();

        if(isSpaceshipCollidingWithAsteroid()){
            if(explosion == null){
                explosion = new Explosion(spaceship.getPosition(), spaceship.getSize());
                if(isSoundOn) {
                    long id = explosionSound.play();
                    explosionSound.setVolume(id, 0.1f);
                }
            }
            explosion.render(batch);
        }else {
            spaceship.render(batch);
        }

        asteroidGenerator.render(batch);

        font.setColor(1, 1, 1, 1);
        renderScore();
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
        //explosionSound.dispose();
    }

    public boolean isSpaceshipOffScreen() {
        Vector2 worldSize = getWorldSize();
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
        List<Asteroid> asteroids = getAsteroids();
        List<Polygon> spaceshipShapes = spaceship.getPolygonShapes();

        for(Asteroid asteroid: asteroids){
            Circle asteroidShape = asteroid.getCircleShape();
            for(Polygon spaceshipShape: spaceshipShapes){
                if(CollisionDetector.isColliding(spaceshipShape, asteroidShape)){
                    asteroid.stopMovement();
                    spaceship.stopMovement();
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isGameOver() {
        if(explosion == null){
            return isSpaceshipOffScreen();
        }else {
            return isSpaceshipOffScreen() || explosion.isEndOfAnimation();
        }
    }

    public List<Asteroid> getAsteroids() {
        return asteroidGenerator.getAsteroids();
    }

    public int getScore(){
        return scoreMonitor.getScore();
    }

    private void generateNewAsteroid() {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (asteroidGenerator.getAsteroids().size() < maxNoOfAsteroids && currentTime - timeSinceLastAsteroidCreated > 1000) {
            timeSinceLastAsteroidCreated = currentTime;
            asteroidGenerator.createAsteroid();
        }
    }

    private void initializeBackground() {
        backgroundSprite = new Sprite(Assets.instance.getSpaceBackgroundTexture());
        backgroundSprite.setPosition(0, 0);
        backgroundSprite.setSize(Constants.getWidth(), Constants.getHeight());
    }

    private void renderBackground() {
        batch.begin();
        backgroundSprite.draw(batch);
        batch.end();
    }

    private void renderScore() {
        batch.begin();
        font.draw(batch, "Score: " + scoreMonitor.getScore(), Constants.getWidth()/12, getFontPositionY());
        batch.end();
    }

    private int getFontPositionY() {
        return Constants.getHeight() - Constants.getHeight()/30;
    }

    private Vector2 getWorldSize() {
        return new Vector2(Constants.getWidth(), Constants.getHeight());
    }
}
