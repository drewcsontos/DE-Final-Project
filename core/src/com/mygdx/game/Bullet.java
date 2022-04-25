package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import box2dLight.PointLight;

public class Bullet extends GameObject{

    static Texture[] textures;
    int animationIndex = 0;
    MapObjects walls;
    List<GameObject> objectList;
    Vector2 direction;
    public Bullet(Vector2 spawnPosition, Vector2 direction, float speed, MapObjects walls, List<GameObject> objectList, PointLight light) {
        super(new Sprite(new Texture("bullet.png")), light);
        sprite.setOrigin(0,0);
        sprite.setPosition(spawnPosition.x, spawnPosition.y);
        if(textures == null){
            textures = new Texture[]{new Texture("bullet.png") };
        }
        direction.scl(speed);
        this.direction = direction;
        this.walls = walls;
        this.objectList = objectList;

    }
    @Override
    public void render(Batch batch){
        super.render(batch);
        defaultUpdateLight();
        for (RectangleMapObject rectangleObject : walls.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = new Rectangle(rectangleObject.getRectangle());
            rectangle.setX(rectangle.getX() + direction.x * Gdx.graphics.getDeltaTime());
            rectangle.setY(rectangle.getY() + direction.y * Gdx.graphics.getDeltaTime());
            if (Intersector.overlaps(rectangle, this.sprite.getBoundingRectangle())) {
                System.out.println("Collision");
                this.remove();
            }
        }
        sprite.translate(direction.x * Gdx.graphics.getDeltaTime(), direction.y * Gdx.graphics.getDeltaTime());
    }
}
