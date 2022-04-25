package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import box2dLight.PointLight;

public class GameObject implements Comparable<GameObject>{
    public Sprite sprite;
    public float time = 0;
    public boolean shouldRemove = false;
    public PointLight light;

    public GameObject(Sprite sprite, PointLight light){
        this.sprite = sprite;
        this.light = light;
    }
    public void render(Batch batch){
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
    @Override
    public int compareTo(GameObject gameObject) {
        if(gameObject instanceof Bullet){
            return Integer.MAX_VALUE;
        }
        return (int) (gameObject.getY() - getY());
    }
}
