package net.djhurley.strandedinspace.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import net.djhurley.strandedinspace.common.Assets;

/**
 * Created by DJHURLEY on 01/03/2015.
 */
public class Asteroid {
    private static final String TAG = Asteroid.class.getName();

    private Vector2 size;
    private Vector2 position;
    private Vector2 velocity;
    private float initialRotation;
    private float rotation;

    private Sprite sprite;

    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    public Asteroid(final Vector2 size, final Vector2 position, final Vector2 velocity, final float rotation){
        this.size = size;
        this.position = position;
        this.velocity = velocity;
        this.initialRotation = rotation;
        this.rotation = rotation;

        Texture texture = Assets.instance.getAsteroidTexture();
        sprite = new Sprite(texture);
        sprite.setSize(size.x, size.y);
        sprite.setOrigin(size.x / 2, size.y / 2);
    }

    public void render(SpriteBatch batch) {
        addVelocity();
        addRotation();

        batch.begin();
        sprite.draw(batch);
        batch.end();
    }

    public Vector2 getSize(){
        return size;
    }

    public Vector2 getPosition(){
        return position;
    }

    public Circle getCircleShape() {
        Circle shape = new Circle(new Vector2(position.x + size.x / 2, position.y + size.y / 2), size.x / 2);
        return shape;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public void stopMovement(){
        this.velocity = new Vector2(0, 0);
    }

    private void addRotation() {
        rotation = rotation + initialRotation;
        if(rotation > 360){
            rotation = initialRotation;
        }
        sprite.setRotation(rotation);
    }

    private void addVelocity() {
        position.y = position.y + velocity.y;
        position.x = position.x + velocity.x;
        sprite.setPosition(position.x, position.y);
    }
}
