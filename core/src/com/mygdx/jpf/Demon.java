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

class Demon extends Elemento {
    private Animaciones demonA;
    int vidas = 2;
    
    public Demon(Sprites sprites, float x, float y) {
        demonA = sprites.demonA;
        posicion = new Vector2(x, y);
        velocidad = new Vector2(-100, 0);
        TextureRegion tr = sprites.demonA.izquierda.getKeyFrame(0);
        mascaraVital = mascaraBloqueo = mascaraAtaque = new Rectangle(x, y, tr.getRegionWidth(), tr.getRegionHeight());
    }
    
    public void render(SpriteBatch sb, float tiempo) {
        if(visibleOk) {
            sb.begin();
            if(velocidad.x > 0)
                sb.draw(demonA.derecha.getKeyFrame(tiempo), posicion.x, posicion.y+20);
            else
                sb.draw(demonA.izquierda.getKeyFrame(tiempo), posicion.x, posicion.y+20);
            sb.end();
        }
    }
    
    @Override
    public void siColisionBloqueoEjeX() {
        float direccion = velocidad.x;    
        if(direccion > 0) 
            velocidad.set(-80, 0);
        else
            velocidad.set(100, 0);
    }
}