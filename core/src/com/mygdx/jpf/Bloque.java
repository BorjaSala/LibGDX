package com.mygdx.jpf;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;
import java.util.Random;

class Bloque extends Elemento {
    private TextureRegion bloque;
    
    public Bloque(Sprites sprites, float x, float y) {
        bloque = sprites.bloque;
        posicion = new Vector2(x, y);
        mascaraBloqueo = new Rectangle(x, y, sprites.bloque.getRegionWidth(), sprites.bloque.getRegionHeight() - 3);
    }
    
    public void render(SpriteBatch sb, float tiempo) {
        sb.begin();
        sb.draw(bloque, posicion.x, posicion.y);
        sb.end();
    }
}