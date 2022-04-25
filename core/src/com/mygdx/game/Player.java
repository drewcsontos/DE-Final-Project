package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import box2dLight.PointLight;

public class Player extends GameObject{
    float moveSpeed = 37.5f;
    MapObjects walls;
    static Texture[] playerLeftTextures;
    static Texture[] playerRightTextures;
    static Texture defaultPlayerTexture;
    int playerAnimationIndex = 0;

    public Player(MapObjects walls, PointLight light){
        super(new Sprite(new Texture("mainCharacter1.png")), light);
        defaultPlayerTexture = new Texture("mainCharacter1.png");
        //playerLeftTextures = new Texture[]{new Texture("leftFacing1.png")};
        //playerRightTextures = new Texture[]{new Texture("rightFacing1.png")};

        playerLeftTextures = new Texture[]{new Texture("leftFacing1.png"), new Texture("leftFacing2.png")};
        playerRightTextures = new Texture[]{new Texture("rightFacing1.png"), new Texture("rightFacing2.png")};
        sprite.setOrigin(0,0);
        this.walls = walls;
    }
    @Override
    public void render(Batch batch){
        super.render(batch);
        defaultUpdateLight();
        boolean hasMoved = false;
        if(time > 2f){
            playerAnimationIndex += 1;
            if(playerAnimationIndex == playerRightTextures.length){
                playerAnimationIndex = 0;
            }
            time = 0f;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            hasMoved = true;
            boolean canMove = true;
            for (RectangleMapObject rectangleObject : walls.getByType(RectangleMapObject.class)) {
                Rectangle rectangle = new Rectangle(rectangleObject.getRectangle());
                rectangle.setX(rectangle.getX() - 2);
                if (Intersector.overlaps(rectangle, this.sprite.getBoundingRectangle())) {
                    System.out.println("Collision");
                    canMove = false;
                }
            }
            if(canMove){
                sprite.translateX(Math.round(moveSpeed * Gdx.graphics.getDeltaTime()));
            }
            sprite.setTexture(playerRightTextures[playerAnimationIndex]);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            hasMoved = true;
            boolean canMove = true;
            for (RectangleMapObject rectangleObject : walls.getByType(RectangleMapObject.class)) {
                Rectangle rectangle = new Rectangle(rectangleObject.getRectangle());
                rectangle.setX(rectangle.getX() + 2);
                if (Intersector.overlaps(rectangle, this.sprite.getBoundingRectangle())) {
                    System.out.println("Collision");
                    canMove = false;
                }
            }
            if(canMove){
                sprite.translateX(Math.round(-1 * moveSpeed * Gdx.graphics.getDeltaTime()));
            }
            sprite.setTexture(playerLeftTextures[playerAnimationIndex]);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            hasMoved = true;
            boolean canMove = true;
            for (RectangleMapObject rectangleObject : walls.getByType(RectangleMapObject.class)) {
                Rectangle rectangle = new Rectangle(rectangleObject.getRectangle());
                rectangle.setY(rectangle.getY() + 2);
                if (Intersector.overlaps(rectangle, this.sprite.getBoundingRectangle())) {
                    System.out.println("Collision");
                    canMove = false;
                }
            }
            if(canMove){
                sprite.translateY(Math.round(-1 * moveSpeed * Gdx.graphics.getDeltaTime()));
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            hasMoved = true;
            boolean canMove = true;
            for (RectangleMapObject rectangleObject : walls.getByType(RectangleMapObject.class)) {
                Rectangle rectangle = new Rectangle(rectangleObject.getRectangle());
                rectangle.setY(rectangle.getY() - 2);
                if (Intersector.overlaps(rectangle, this.sprite.getBoundingRectangle())) {
                    System.out.println("Collision");
                    canMove = false;
                }
            }
            if(canMove){
                sprite.translateY(Math.round(moveSpeed * Gdx.graphics.getDeltaTime()));
            }
        }
//        if(!hasMoved){
            sprite.setTexture(defaultPlayerTexture);
       // }

    }
}
