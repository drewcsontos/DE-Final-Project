package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import box2dLight.PointLight;

public class GameObject implements Comparable<GameObject>{
    public Sprite sprite;
    public float time = 0;
    public boolean shouldRemove = false;
    public PointLight light;
    public Vector2 direction;

    public GameObject(Sprite sprite, PointLight light){
        this.sprite = sprite;
        this.light = light;
    }
    public void render(Batch batch){
        if(direction!= null) {
            float x = sprite.getX();
            float y = sprite.getY();
            for(int i=2;i>0;i--){
                sprite.setPosition(x - i * direction.x, y - i * direction.y);
                sprite.draw(batch);
            }
            sprite.setPosition(x,y);
        }
        //batch.draw(sprite.getTexture(), sprite.getX(), sprite.getY());
        sprite.draw(batch);

    }
    public void defaultUpdateLight(){
        if(light != null){
            light.setPosition(getX(), getY());
            light.update();
            time += Gdx.graphics.getDeltaTime();
        }
    }
    public float getY(){
       return sprite.getY();
    }
    public float getX(){
        return sprite.getX();
    }
    public void remove(){
        shouldRemove = true;
        if(light != null){
            light.remove();
            light = null;
        }
    }
    public void update(){}

    @Override
    public int compareTo(GameObject gameObject) {
        if(gameObject instanceof Bullet){
            return Integer.MAX_VALUE;
        }
        return (int) (gameObject.getY() - getY());
    }
}
