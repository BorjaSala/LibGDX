package com.mygdx.jpf;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

class Door extends Elemento {
    public Texture door;

    public Door(Sprites sprites, float x, float y) {
        door = sprites.door;
        posicion = new Vector2(x, y);
        mascaraBloqueo = new Rectangle(x, y, door.getWidth(), door.getHeight());
    }
    
    public void render(SpriteBatch sb, float tiempo) {
        sb.begin();
        sb.draw(door, posicion.x, posicion.y);
        sb.end();
    }
}