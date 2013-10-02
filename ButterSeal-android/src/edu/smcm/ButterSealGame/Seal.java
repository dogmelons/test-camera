package edu.smcm.ButterSealGame;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Seal implements ApplicationListener
{
	private int WIDTH;
	private int HEIGHT;
	private int cWIDTH = 300;
	private int cHEIGHT = 180;
	private int mWIDTH;
	private int mHEIGHT;
	
	private int dHEIGHT;
	private int dWIDTH;
	private int dHEIGHT3;
	private int dWIDTH3;
	
	private Texture pikaImage;
	private Texture dpadImage;
	private Rectangle dpad;
	private Music DreamMusic;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Vector3 touchPos;
	private TileMapRenderer tileMapRenderer;
	
	@Override
	public void create() 
	{
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		
		Gdx.gl.glClearColor(0f,0,0.0f,1);
		
		pikaImage = new Texture(Gdx.files.internal("pika.png"));
		dpadImage = new Texture(Gdx.files.internal("dpad.png"));
		dHEIGHT = dpadImage.getHeight();
		dWIDTH = dpadImage.getWidth();
		dHEIGHT3 = dHEIGHT/3;
		dWIDTH3 = dWIDTH/3;
		DreamMusic = Gdx.audio.newMusic(Gdx.files.internal("Dream.mp3"));
		
		dpad = new Rectangle();
		dpad.x = 0;
		dpad.y = 0;
		dpad.width = dWIDTH;
		dpad.height = dHEIGHT;
		
		batch = new SpriteBatch();
		
		touchPos = new Vector3();
		
		TiledMap tiledMap = TiledLoader.createMap(Gdx.files.internal("collisionmap/collisionTestDesert.tmx"));
		TileAtlas tileAtlas = new TileAtlas(tiledMap, Gdx.files.internal("collisionmap"));
		tileMapRenderer = new TileMapRenderer(tiledMap, tileAtlas, 12, 12);
		
		mWIDTH = tiledMap.width * tiledMap.tileWidth;
		mHEIGHT = tiledMap.height * tiledMap.tileHeight;
		
		//DreamMusic.setLooping(true);
		//DreamMusic.play();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, cWIDTH*2, cHEIGHT*2);
		camera.position.set(cWIDTH, cHEIGHT, 0);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() 
	{
		handleInput();

		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT );
		
		camera.update();
		tileMapRenderer.render(camera);
		batch.begin();
		batch.draw(dpadImage, dpad.x, dpad.y);
		batch.end();
	}
	
	private void handleInput()
	{
		if(Gdx.input.isTouched()) 
		{
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			
			//move left
			if(touchPos.y > (HEIGHT - (dHEIGHT3*2)) && (touchPos.y < (HEIGHT - dHEIGHT3)) && (touchPos.x < dWIDTH3))
			{
				if(camera.position.x > (cWIDTH))					//check
					camera.translate(-3, 0, 0);
			}
			//move right
			else if(touchPos.y > (HEIGHT - (dHEIGHT3*2)) && (touchPos.y < (HEIGHT - dHEIGHT3)) && (touchPos.x > dWIDTH3*2)
					&& (touchPos.x < dWIDTH))
			{
				if(camera.position.x < (mWIDTH-cWIDTH))
					camera.translate(3, 0, 0);
			}
			//move up
			else if((touchPos.y > (HEIGHT - dHEIGHT)) && (touchPos.y < (HEIGHT - dHEIGHT3*2)) && (touchPos.x > dWIDTH3)
					&& (touchPos.x < (dWIDTH3*2)))
			{
				if(camera.position.y < (mHEIGHT-cHEIGHT))			//out of bounds check
					camera.translate(0, 3, 0);
			}
			//move down
			else if((touchPos.y > (HEIGHT - dHEIGHT3)) && (touchPos.x > dWIDTH3) && (touchPos.x < (dWIDTH3*2)))
			{
				if(camera.position.y > (cHEIGHT))
					camera.translate(0, -3, 0);	
			}
						
			
			/*if(touchPos.y < (HEIGHT/2))
			{		
				if(touchPos.x < touchPos.y)								//move left
				{
					if(camera.position.x > (cWIDTH))					//out of bounds check
						camera.translate(-3, 0, 0);
				}
				else if(touchPos.y > (WIDTH - touchPos.x)) 				//move right
				{
					if(camera.position.x < (mWIDTH-cWIDTH))
						camera.translate(3, 0, 0);
				}
				else													//move up
				{
					if(camera.position.y < (mHEIGHT-cHEIGHT))			//out of bounds check
						camera.translate(0, 3, 0);
				}
			}
			
			else
			{
				if(touchPos.x < (HEIGHT - touchPos.y))					//move left
				{
					if(camera.position.x > (cWIDTH))					//check
						camera.translate(-3, 0, 0);
				}
				else if((HEIGHT - touchPos.y) < (WIDTH - touchPos.x))	//move down
				{
					if(camera.position.y > (cHEIGHT))
						camera.translate(0, -3, 0);					
				}
				else													//move right
				{
					if(camera.position.x < (mWIDTH-cWIDTH))
						camera.translate(3, 0, 0);
				}
			}*/
			
		}
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}