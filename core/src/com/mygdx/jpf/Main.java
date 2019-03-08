package com.mygdx.jpf;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	Juego juego;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		juego = new Juego();
		Gdx.gl.glClearColor(0, 0, 0, 1);

	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		juego.render(batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		juego.dispose();
	}
}
