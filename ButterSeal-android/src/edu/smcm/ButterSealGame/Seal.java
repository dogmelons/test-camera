package edu.smcm.ButterSealGame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Seal implements ApplicationListener
{

	static final int WIDTH = 480;
	static final int HEIGHT = 320;
	
	private Texture buzzImage;
	private Texture map;
	private Music DreamMusic;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Mesh mesh;
	private Rectangle glViewport;
	private Vector3 touchPos;
	
	@Override
	public void create() 
	{
		buzzImage = new Texture(Gdx.files.internal("buzz.png"));
		DreamMusic = Gdx.audio.newMusic(Gdx.files.internal("Dream.mp3"));
		map = new Texture(Gdx.files.internal("sc_map.png"));
		
		DreamMusic.setLooping(true);
		DreamMusic.play();
		
		mesh = new Mesh(true, 4, 6, new VertexAttribute(VertexAttributes.Usage.Position, 3, "attr._position"),
						new VertexAttribute(Usage.TextureCoordinates, 2, "attr_texCoords"));
		
		mesh.setVertices(new float[] {
						-1024f, -1024f, 0, 0, 1,
						 1024f, -1024f, 0, 1, 1,
						 1024f,  1024f, 0, 1, 0,
						-1024f,  1024f, 0, 0, 0
		});
		mesh.setIndices(new short[] {0,1,2,2,3,0});
		
		camera = new OrthographicCamera(WIDTH, HEIGHT);
		camera.position.set(WIDTH/2, HEIGHT/2, 0);
		glViewport = new Rectangle(0, 0, WIDTH, HEIGHT);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() 
	{
		handleInput();
		GL10 gl = Gdx.graphics.getGL10();     
         
	    // Camera --------------------- /
	    gl.glClear(GL10.GL_COLOR_BUFFER_BIT); 
	    gl.glViewport((int) glViewport.x, (int) glViewport.y,
	    		(int) glViewport.width, (int) glViewport.height);     
	                
	    camera.update();                 
	    camera.apply(gl);

	    // Texturing --------------------- /
	    gl.glActiveTexture(GL10.GL_TEXTURE0);  
	    gl.glEnable(GL10.GL_TEXTURE_2D);    
	    map.bind();                
	                
	    mesh.render(GL10.GL_TRIANGLES);
		
	}
	
	private void handleInput()
	{
		if(Gdx.input.isTouched()) 
		{
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			
			if(touchPos.x < WIDTH/2 && touchPos.y < HEIGHT/2) 	//if screen is touched in bottom left quadrant
			{
				if(touchPos.x > touchPos.y) 			//if touched in bottom triangle, move down
				{
					if(camera.position.y < 1024)		//out of bounds check
						camera.translate(0, -3, 0);
				}
				else 									//if touched in top triangle, move left
				{
					if(camera.position.x > 0)			//out of bounds check
						camera.translate(-3, 0, 0);
				}
			}
			
			else if(touchPos.x < WIDTH/2) 					//if screen is touched in top left quadrant
			{
				if(touchPos.x > (HEIGHT - touchPos.y)) 	//move up
				{	
					if(camera.position.y < 1024)		//check
						camera.translate(0,3,0);
				}
				else 									//move left
				{
					if(camera.position.x > 0)			//check
						camera.translate(-3,0,0);
				}
			}
			
			else if(touchPos.y < HEIGHT/2) 					//if screen is touched in bottom right quadrant
			{
				if(touchPos.y > (WIDTH - touchPos.x)) 	//move right
				{
					if(camera.position.x < 1024)
						camera.translate(3,0,0);
				}
				else 									//move down
				{
					if(camera.position.y > 0)
						camera.translate(0,-3,0);
				}
			}
			
			else 										//if screen is touched in top right quadrant
			{										
				if(touchPos.x > touchPos.y) 			//move right
				{
					if(camera.position.x < 1024)
						camera.translate(3,0,0);
				}
				else 									//move up
				{
					if(camera.position.y < 1024)
						camera.translate(0,3,0);
				}
			}
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