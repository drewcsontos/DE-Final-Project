package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import box2dLight.PointLight;

public class Cat extends GameObject{

    static Texture[] catTextures;
    int catAnimationIndex = 0;
    float catLerp = .5f;
    Player player;
    public Cat(Player mainPlayer, PointLight light) {
        super(new Sprite(new Texture("cat3.png")), light);
        sprite.setOrigin(0,0);
        if(catTextures == null){
            catTextures = new Texture[]{new Texture("cat3.png"), new Texture("cat4.png"), new Texture("cat5.png"), new Texture("cat6.png")};
        }
        sprite.setPosition(-5,-5);
        player = mainPlayer;

    }
    @Override
    public void render(Batch batch){
        super.render(batch);

    }
    public void update(){
        defaultUpdateLight();
        direction = new Vector2(0,0);
        if(sprite.getX() < player.sprite.getX() && sprite.isFlipX()){
            sprite.flip(true, false);
        }
        else if (sprite.getX() > player.sprite.getX() && ! sprite.isFlipX() ){
            sprite.flip(true, false);
        }
        if(time > .1f){
            catAnimationIndex += 1;
            if(catAnimationIndex == catTextures.length){
                catAnimationIndex = 0;
            }
            time = 0f;
        }
        if(Math.abs(sprite.getX() - player.sprite.getX()) <= 35 && Math.abs(sprite.getY() - player.sprite.getY()) <= 15){
            catAnimationIndex = 0;
        }
        sprite.setTexture(catTextures[catAnimationIndex]);
        if(Math.abs(sprite.getX() - player.sprite.getX()) > 32 ){
//			if((Math.round(player.getX() - 30 - sprite.getX()) * catLerp * Gdx.graphics.getDeltaTime()) > 0 && sprite.isFlipX()){
//				sprite.flip(true,false);
//			}
//			if((Math.round(player.getX() - 30 - sprite.getX()) * catLerp * Gdx.graphics.getDeltaTime()) < 0 && ! sprite.isFlipX()) {
//				sprite.flip(true, false);
//			}
            if(sprite.getX() - player.sprite.getX() > 0){
                direction.x = -1;
                sprite.translateX((Math.round(player.sprite.getX() + 30 - sprite.getX()) * catLerp * Gdx.graphics.getDeltaTime()));
            }
            else{
                direction.x = 1;
                sprite.translateX((Math.round(player.sprite.getX() - 30 - sprite.getX()) * catLerp * Gdx.graphics.getDeltaTime()));
            }
            //light.setPosition(sprite.getX(),sprite.getY());
        }
        if(Math.abs(sprite.getY() - player.sprite.getY()) > 12 ) {
            if(sprite.getY() - player.sprite.getY() > 0){
                sprite.translateY((player.sprite.getY() + 10 - sprite.getY()) * catLerp * Gdx.graphics.getDeltaTime());
            }
            else{
                sprite.translateY((player.sprite.getY() - 10 - sprite.getY()) * catLerp * Gdx.graphics.getDeltaTime());
            }
        }
    }
}
