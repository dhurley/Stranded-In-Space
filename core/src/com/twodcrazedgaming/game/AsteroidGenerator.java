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
    private final int noOfAsteroids;
    private List<Asteroid> asteroids;
    private long score;

    public AsteroidGenerator(final Vector2 worldSize, final int noOfAsteroids){
        asteroids = new ArrayList<Asteroid>();
        this.worldSize = worldSize;
        this.noOfAsteroids = noOfAsteroids;
        score = 0;
    }

    public void createAsteroid(){
        Gdx.app.debug(TAG, "Asteroid created.");

        float randomSizeRatio = MathUtils.random(12, 15);
        Vector2 size = new Vector2(Gdx.graphics.getWidth() / randomSizeRatio, Gdx.graphics.getWidth() / randomSizeRatio);

        float randomPositionX = MathUtils.random(size.x, Gdx.graphics.getWidth() - size.x);
        Vector2 position = new Vector2(randomPositionX, Gdx.graphics.getHeight());

        float randomVelocityX = MathUtils.random(-0.5f, 0.5f);
        float randomVelocityY = MathUtils.random(-0.1f, -2);
        Vector2 velocity = new Vector2(randomVelocityX, randomVelocityY);

        float rotation = MathUtils.random(1, 5);

        Asteroid asteroid = new Asteroid(size, position, velocity, rotation);
        asteroids.add(asteroid);
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

        while(noOfAsteroids != asteroids.size()){
            createAsteroid();
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
