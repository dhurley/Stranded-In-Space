package net.djhurley.strandedinspace.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import net.djhurley.strandedinspace.game.objects.Asteroid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by DJHURLEY on 01/03/2015.
 */
public class AsteroidGenerator {
    private static final String TAG = AsteroidGenerator.class.getName();

    private final Vector2 worldSize;
    private List<Asteroid> asteroids;
    private int destroyedAsteroids;

    public AsteroidGenerator(final Vector2 worldSize){
        asteroids = new ArrayList<Asteroid>();
        this.worldSize = worldSize;
        destroyedAsteroids = 0;
    }

    public void createAsteroid(){
        Asteroid asteroid;

        while(true) {
            float randomSizeRatio = MathUtils.random(12, 15);
            float radius = Gdx.graphics.getWidth() / randomSizeRatio;
            Vector2 size = new Vector2(radius, radius);

            float randomPositionX = MathUtils.random(size.x, Gdx.graphics.getWidth() - size.x);
            Vector2 position = new Vector2(randomPositionX, Gdx.graphics.getHeight());

            float randomVelocityX = MathUtils.random(-0.5f, 0.5f);
            float randomVelocityY = MathUtils.random(-0.5f, -2);
            Vector2 velocity = new Vector2(randomVelocityX, randomVelocityY);

            float rotation = MathUtils.random(1, 3);

            asteroid = new Asteroid(size, position, velocity, rotation);

            if(!isCollidingWithOtherAsteroids(asteroid)){
                break;
            }

        }

        Gdx.app.debug(TAG, "Asteroid created.");
        asteroids.add(asteroid);
    }

    public void render(final SpriteBatch batch){
        Iterator<Asteroid> iterator = asteroids.iterator();
        while (iterator.hasNext()) {
            Asteroid asteroid = iterator.next();

            if(isCollidingWithOtherAsteroids(asteroid)){
                applyVelocityFromAsteroids(asteroid);
            }else if(isCollidingWithSideWalls(asteroid)){
                applyVelocityFromSideWalls(asteroid);
            }

            asteroid.render(batch);

            if(isAsteroidOffScreen(asteroid)){
                Gdx.app.debug(TAG, "Asteroid destroyed.");
                iterator.remove();
                destroyedAsteroids++;
            }
        }
    }

    private boolean isCollidingWithOtherAsteroids(Asteroid asteroid) {
        Iterator<Asteroid> iterator = asteroids.iterator();
        while (iterator.hasNext()) {
            Asteroid existingAsteroid = iterator.next();

            if(existingAsteroid == asteroid){
                continue;
            }

            if(CollisionDetector.isColliding(existingAsteroid.getCircleShape(), asteroid.getCircleShape())){
                return true;
            }
        }

        return false;
    }

    private boolean isCollidingWithSideWalls(Asteroid asteroid) {
        Vector2 asteroidPosition = asteroid.getPosition();
        Vector2 asteroidSize = asteroid.getSize();
        if(asteroidPosition.x < 0 || asteroidPosition.x + asteroidSize.x > worldSize.x){
            return true;
        }
        return false;
    }

    private boolean applyVelocityFromAsteroids(Asteroid asteroid) {
        Iterator<Asteroid> iterator = asteroids.iterator();
        while (iterator.hasNext()) {
            Asteroid existingAsteroid = iterator.next();

            if(existingAsteroid == asteroid){
                continue;
            }

            if(CollisionDetector.isColliding(existingAsteroid.getCircleShape(), asteroid.getCircleShape())){
                Vector2 asteroidVelocity = asteroid.getVelocity();
                Vector2 existingAsteroidVelocity = existingAsteroid.getVelocity();
                float asteroidSize = asteroid.getSize().x;
                float existingAsteroidSize = existingAsteroid.getSize().x;

                float newVelocityX1 = (asteroidVelocity.x * (asteroidSize - existingAsteroidSize)
                                       + (2 * existingAsteroidSize * existingAsteroidVelocity.x)) / (asteroidSize + existingAsteroidSize);
                float newVelocityY1 = (asteroidVelocity.y * (asteroidSize - existingAsteroidSize)
                                       + (2 * existingAsteroidSize * existingAsteroidVelocity.y)) / (asteroidSize + existingAsteroidSize);
                float newVelocityX2 = (existingAsteroidVelocity.x * (existingAsteroidSize - asteroidSize)
                                       + (2 * asteroidSize * asteroidVelocity.x)) / (asteroidSize + existingAsteroidSize);
                float newVelocityY2 = (existingAsteroidVelocity.y * (existingAsteroidSize - asteroidSize)
                                       + (2 * asteroidSize * asteroidVelocity.y)) / (asteroidSize + existingAsteroidSize);

                asteroid.setVelocity(new Vector2(newVelocityX1, newVelocityY1));
                existingAsteroid.setVelocity(new Vector2(newVelocityX2, newVelocityY2));
            }
        }

        return false;
    }

    private void applyVelocityFromSideWalls(Asteroid asteroid) {
        Vector2 velocity = asteroid.getVelocity();
        Vector2 newVelocity = new Vector2(-1 * velocity.x, velocity.y);
        asteroid.setVelocity(newVelocity);
    }

    private boolean isAsteroidOffScreen(final Asteroid asteroid) {
        Vector2 asteroidPosition = asteroid.getPosition();
        Vector2 asteroidSize = asteroid.getSize();
        if(asteroidPosition.y + asteroidSize.y < 0){
            return true;
        }else {
            return false;
        }
    }

    public int getNumberOfDestroyedAsteroids() {
        return destroyedAsteroids;
    }

    public List<Asteroid> getAsteroids() {
        return asteroids;
    }
}
