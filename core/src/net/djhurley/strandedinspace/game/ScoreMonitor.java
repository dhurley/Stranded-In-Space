package net.djhurley.strandedinspace.game;

import net.djhurley.strandedinspace.game.objects.Asteroid;
import net.djhurley.strandedinspace.game.objects.Spaceship;

import java.util.Iterator;
import java.util.List;

/**
 * Created by djhurley on 07/04/15.
 */
public class ScoreMonitor {

    private AsteroidGenerator asteroidGenerator;
    private Spaceship spaceship;

    public ScoreMonitor(AsteroidGenerator asteroidGenerator, Spaceship spaceship){
        this.asteroidGenerator = asteroidGenerator;
        this.spaceship = spaceship;
    }

    public int getScore(){
        int score = asteroidGenerator.getNumberOfDestroyedAsteroids();

        float spaceshipPositionY = spaceship.getPosition().y;
        List<Asteroid> asteroids = asteroidGenerator.getAsteroids();

        Iterator<Asteroid> iterator = asteroids.iterator();
        while (iterator.hasNext()) {
            Asteroid asteroid = iterator.next();
            float asteroidPositionY = asteroid.getPosition().y + asteroid.getSize().y;
            if(asteroidPositionY < spaceshipPositionY){
                score++;
            }
        }

        return score;
    }
}
