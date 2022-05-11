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

public class Gun extends GameObject{

    static Texture[] textures;
    int animationIndex = 0;
    MapObjects walls;
    List<GameObject> objectList;
    int movingRight = 1;
    int health = 10;
    public Gun(Vector2 spawnPosition, float speed, MapObjects walls, List<GameObject> objectList, PointLight light) {
        super(new Sprite(new Texture("gunEnemy.png")), light);
        sprite.setOrigin(0,0);
        sprite.setPosition(spawnPosition.x, spawnPosition.y);
//        if(textures == null){
//            textures = new Texture[]{new Texture("bullet.png") };
//        }
        this.walls = walls;
        this.objectList = objectList;

    }
    @Override
    public void render(Batch batch){
        super.render(batch);

        //sprite.translate(direction.x * Gdx.graphics.getDeltaTime(), direction.y * Gdx.graphics.getDeltaTime());
    }
    public void update(){
        defaultUpdateLight();
        float change = 5f * Gdx.graphics.getDeltaTime() * movingRight;
        for (RectangleMapObject rectangleObject : walls.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = new Rectangle(rectangleObject.getRectangle());
            rectangle.setX(rectangle.getX() + change * Gdx.graphics.getDeltaTime());
            if (Intersector.overlaps(rectangle, this.sprite.getBoundingRectangle())) {
                System.out.println("Collision");
                movingRight *= -1;
                change *= -1;
                sprite.flip(true,false);
            }
            sprite.translateX(change);
        }
        for(GameObject obj : objectList){
            if(obj instanceof Bullet){
                if (Intersector.overlaps(obj.sprite.getBoundingRectangle(), this.sprite.getBoundingRectangle())){
                    health--;
                    obj.remove();
                }
            }
        }
        if(health<1){
            remove();
        }
    }
}
