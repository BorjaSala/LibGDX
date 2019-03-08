package com.mygdx.jpf;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

class Wall extends Elemento {
    private TextureRegion wall;

    public Wall(Sprites sprites, float x, float y) {
        wall = sprites.wall;
        posicion = new Vector2(x, y);
        //mascaraBloqueo = new Rectangle(x, y, sprites.wall.getRegionWidth(), sprites.wall.getRegionHeight() - 3);
    }
    
    public void render(SpriteBatch sb, float tiempo) {
        sb.begin();
        sb.draw(wall, posicion.x, posicion.y);
        sb.end();
    }
}