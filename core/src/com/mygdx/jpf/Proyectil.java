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
import java.util.Vector;

class Proyectil extends Elemento {
    private Texture cadena;
    
    public Proyectil(Sprites sprites, float x, float y) {
        cadena = sprites.cadena;
        visibleOk = false;
        posicion = new Vector2(x, y);
        velocidad = new Vector2(0, 0);
        mascaraVital = mascaraBloqueo = mascaraAtaque = new Rectangle(x, y, 81, 7);
    }
    
    public void render(SpriteBatch sb, float tiempo) {
        if(visibleOk)
        {
            sb.begin();
            sb.draw(cadena, posicion.x, posicion.y);
            sb.end();
        }
    }
    
    @Override
    public void siColisionBloqueoEjeX() {
        visibleOk = false;
    }
    
    @Override
    public void siColisionBloqueoEjeY() {
        visibleOk = false;
    }
    
    public void lanzar(Vector2 posicion, Vector2 velocidad) {



        this.posicion.set(posicion);
        this.velocidad.set(velocidad);
        visibleOk = true;

    }
}