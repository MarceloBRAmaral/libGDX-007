package com.test.game;

//import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
//import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class TestBox2D extends ApplicationAdapter {
	//SpriteBatch batch;
	//Texture img;
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera camera;

	static private final float TIMESTEP = 1/60f;
	static private final int VELOCITYITERATIONS = 8;
	static private final int POSITIONITERATIONS = 3;

	private Body ball1;
	private Body ball2;
	private Body ground;

	@Override
	public void create() {
		world = new World(new Vector2(0,-9.81f),true);
		debugRenderer = new Box2DDebugRenderer();
		camera = new OrthographicCamera();



		//common for bodies and ground
		BodyDef bodyDef = new BodyDef();

		//body definition
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(0, .5f);

		//body shape definition
		CircleShape bodyShape = new CircleShape();
		bodyShape.setRadius(.05f);

		//fixture definition
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = bodyShape;
		fixtureDef.density = 2.5f;
		fixtureDef.friction = .25f;
		fixtureDef.restitution = .7f;

		//takes all definitions and create a body with that
		ball1 = world.createBody(bodyDef);
		ball1.createFixture(fixtureDef);

		bodyShape.dispose();

		////////////////////second ball
		//body definition
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(0, 1);

		//body shape definition
		CircleShape bodyShape2 = new CircleShape();
		bodyShape2.setRadius(.05f);

		//fixture definition
		//FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = bodyShape2;
		fixtureDef.density = 2.5f;
		fixtureDef.friction = .25f;
		fixtureDef.restitution = .7f;

		//takes all definitions and create a body with that
		ball2 = world.createBody(bodyDef);
		ball2.createFixture(fixtureDef);

		bodyShape2.dispose();

		//ground definition
		//body definition
		//BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.position.set(0, 0);

		//body shape definition
		ChainShape groundShape = new ChainShape();
		groundShape.createChain(new Vector2[]{new Vector2(-.5f,1), new Vector2(-.5f,-1f),
			new Vector2(.51f,-1.01f), new Vector2(.51f,1)});

		//fixture definition
		//FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = groundShape;
		//fixtureDef.density = 2.5f;
		fixtureDef.friction = .5f;
		fixtureDef.restitution = 0;

		//takes all definitions and create a body with that
		ground = world.createBody(bodyDef);
		ground.createFixture(fixtureDef);

		groundShape.dispose();

	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		debugRenderer.render(world, camera.combined);

		world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);

		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			ball1.setTransform(touchPos.x,touchPos.y,0);
			ball1.setAwake(true);
		}

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			ball2.setAwake(true);
			ball2.setTransform(ball2.getPosition().x -= 1 * Gdx.graphics.getDeltaTime(), ball2.getPosition().y,
				ball2.getAngle()+.3f);}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			ball2.setAwake(true);
			ball2.setTransform(ball2.getPosition().x += 1 * Gdx.graphics.getDeltaTime(), ball2.getPosition().y,
				ball2.getAngle()-.3f);}
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width/300;
		camera.viewportHeight = height/300;
		camera.update();

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}


	@Override
	public void dispose() {
		world.dispose();
		debugRenderer.dispose();


	}

//	@Override
//	public void create () {
//		batch = new SpriteBatch();
//		img = new Texture("badlogic.jpg");
//	}

//	@Override
//	public void render () {
//		Gdx.gl.glClearColor(1, 1, 1, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		batch.begin();
//		batch.draw(img, 0, 0);
//		batch.end();
//	}

//	@Override
//	public void dispose () {
//		batch.dispose();
//		img.dispose();
//	}
}
