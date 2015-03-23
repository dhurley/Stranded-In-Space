package com.twodcrazedgaming.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.twodcrazedgaming.game.objects.Asteroid;

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
    private long score;

    public AsteroidGenerator(final Vector2 worldSize){
        asteroids = new ArrayList<Asteroid>();
        this.worldSize = worldSize;
        score = 0;
    }

    public void createAsteroid(){
        Asteroid asteroid;

        while(true) {
            float randomSizeRatio = MathUtils.random(12, 15);
            Vector2 size = new Vector2(Gdx.graphics.getWidth() / randomSizeRatio, Gdx.graphics.getWidth() / randomSizeRatio);

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

    private boolean isCollidingWithOtherAsteroids(Asteroid asteroid) {
        Iterator<Asteroid> iterator = asteroids.iterator();
        while (iterator.hasNext()) {
            Asteroid existingAsteroid = iterator.next();

            if(CollisionDetector.isColliding(existingAsteroid.getCircleShape(), asteroid.getCircleShape())){
                return true;
            }
        }

        return false;
    }

    public void render(final SpriteBatch batch){
        Iterator<Asteroid> iterator = asteroids.iterator();
        while (iterator.hasNext()) {
            Asteroid asteroid = iterator.next();

            asteroid.render(batch);

            if(isAsteroidOffScreen(asteroid)){
                Gdx.app.debug(TAG, "Asteroid destroyed.");
                iterator.remove();
                score++;
            }
        }
    }

    private boolean isAsteroidOffScreen(final Asteroid asteroid) {
        Vector2 asteroidPosition = asteroid.getPosition();
        Vector2 asteroidSize = asteroid.getSize();
        if(asteroidPosition.x + asteroidSize.x < 0 || asteroidPosition.x > worldSize.x){
            return true;
        }else if(asteroidPosition.y + asteroidSize.y < 0){
            return true;
        }else {
            return false;
        }
    }

    public long getScore() {
        return score;
    }

    public List<Asteroid> getAsteroids() {
        return asteroids;
    }
}
