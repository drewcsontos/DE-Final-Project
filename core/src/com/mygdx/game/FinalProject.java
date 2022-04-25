package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.graphics.ParticleEmitterBox2D;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import box2dLight.PointLight;
import box2dLight.RayHandler;

public class FinalProject extends ApplicationAdapter {
	private ArrayList<GameObject> gameObjects;
	private OrthographicCamera camera;
	private Viewport viewport;
	SpriteBatch batch;
	Texture img;
	Texture background;
	float lerp = 2;
	int deltaX = 10;
	int deltaY = 10;
	int width;
	int height;
	int worldZoom = 7;
	public Player mainPlayer;
	public Cat cat;
	RayHandler handler;
	TiledMapRenderer tiledMapRenderer;
	int width1;
	int height1;
	MapObjects walls;
	World world;

	//int x = 0;
	//int y = 0;
	@Override
	public void create () {
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		width = Gdx.graphics.getDisplayMode().width;
		height = Gdx.graphics.getDisplayMode().height;
		Gdx.graphics.setWindowedMode(width / 3,height / 3);
		camera = new OrthographicCamera();
		viewport = new FitViewport(width / worldZoom, height / worldZoom, camera);
		camera.translate(10,10);
		world = new World(new Vector2(0,0),false);
		handler = new RayHandler(world);
		handler.setCombinedMatrix(camera);
		handler.useDiffuseLight(true);
		handler.setAmbientLight(1);
		handler.setCulling(true);
		handler.setShadows(true);
		PointLight lightOrange = new PointLight(handler, 10000 , new Color(.96f,.6f,.32f, 1),100,-5,-5);

		PointLight lightWhite = new PointLight(handler, 100 , new Color(1,1,1, 1),100,-5,-5);

		batch = new SpriteBatch();

		//img = new Texture("zeldaPng.png");
		//background = new Texture("background.png");
		//background.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
		//img.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		TiledMap tiledMap = new TmxMapLoader().load("map1.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		MapLayer collisionObjectLayer = (MapLayer)tiledMap.getLayers().get(0);
		walls = collisionObjectLayer.getObjects();
		for(RectangleMapObject rectangleObject : walls.getByType(RectangleMapObject.class)){
			PolygonShape shape = new PolygonShape();
			shape.set(new Vector2[]{new Vector2(0,0), new Vector2(0, 10), new Vector2(10,10), new Vector2(10, 0)});
		}
		mainPlayer = new Player(walls, lightWhite);
		mainPlayer.sprite.translate(30,30);
		cat = new Cat(mainPlayer, lightOrange);
		gameObjects = new ArrayList<GameObject>();
		gameObjects.add(mainPlayer);
		gameObjects.add(cat);
		createSquareBody();
		//camera.zoom = 5;
	}

	@Override
	public void render () {
		viewport.update(width1, height1);
		ScreenUtils.clear(0, 0, 0, 1);
		batch.setProjectionMatrix(camera.combined);
		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
			PointLight red = new PointLight(handler, 100, new Color(1,0,0,1), 20, 0f, 0f);
			gameObjects.add(new Bullet(new Vector2(mainPlayer.getX() + 10, mainPlayer.getY() + 5), new Vector2(1,0), 50f, walls, gameObjects, red));
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
			PointLight red = new PointLight(handler, 100, new Color(1,0,0,1), 20, 0f, 0f);
			gameObjects.add(new Bullet(new Vector2(mainPlayer.getX() + 10, mainPlayer.getY() + 5), new Vector2(-1,0), 50f, walls, gameObjects, red));
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
			PointLight red = new PointLight(handler, 100, new Color(1,0,0,1), 20, 0f, 0f);
			gameObjects.add(new Bullet(new Vector2(mainPlayer.getX() + 10, mainPlayer.getY() + 5), new Vector2(0,1), 50f, walls, gameObjects, red));
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
			PointLight red = new PointLight(handler, 100, new Color(1,0,0,1), 20, 0f, 0f);
			gameObjects.add(new Bullet(new Vector2(mainPlayer.getX() + 10, mainPlayer.getY() + 5), new Vector2(0,-1), 50f, walls, gameObjects, red));
		}
		batch.begin();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
		//batch.draw(img, 0, 0);
		//batch.draw(background,0,0);
		Collections.sort(gameObjects);
		for(int i=0;i<gameObjects.size();i++){
			GameObject obj = gameObjects.get(i);
			obj.render(batch);
			if(obj.shouldRemove){
				gameObjects.remove(i);
				i--;
			}
		}
 		for(GameObject object : gameObjects){
			object.render(batch);
		}

		camera.position.x += (Math.floor(mainPlayer.sprite.getX() + deltaX - camera.position.x) * lerp * Gdx.graphics.getDeltaTime());
		camera.position.y += (Math.floor(mainPlayer.sprite.getY() + deltaY - camera.position.y) * lerp * Gdx.graphics.getDeltaTime());

		//lightWhite.setPosition(mainPlayer.getX() + 10, mainPlayer.getY() + 10);
		camera.update();

		// Movement code

		batch.end();
		if(Gdx.input.isKeyJustPressed(Input.Keys.F)){
			if(Gdx.graphics.isFullscreen()){
				Gdx.graphics.setWindowedMode(width / 3,height / 3);
			}
			else{
				Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
			}
		}

		handler.setCombinedMatrix(camera);
		handler.updateAndRender();
	}
	@Override
	public void resize(int width, int height) {
		width1 = width;
		height1 = height;
		viewport.update(width, height);
	}
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
	private void createSquareBody() {
		//create definition
		BodyDef squareBodyDef = new BodyDef();
		squareBodyDef.type = BodyDef.BodyType.StaticBody;

		//set position
		squareBodyDef.position.set(40, 40);

		//create body and add it to the world
		Body squareBody = world.createBody(squareBodyDef);



		//create shape
		PolygonShape squareBox = new PolygonShape();
		squareBox.setAsBox(20, 20);

		//create a fixture from the shape and add it to body
		squareBody.createFixture(squareBox, 2.0f);

		squareBox.dispose();
	}
}
