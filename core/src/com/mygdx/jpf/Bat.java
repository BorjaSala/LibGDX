package com.mygdx.jpf;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

class Bat extends Elemento {
    private Animaciones batA;
    int vidas = 1;

    public Bat(Sprites sprites, float x, float y) {
        batA = sprites.batA;
        posicion = new Vector2(x, y);
        velocidad = new Vector2(-200, 0);
        TextureRegion tr = sprites.batA.izquierda.getKeyFrame(0);
        mascaraVital = mascaraBloqueo = mascaraAtaque = new Rectangle(x, y, tr.getRegionWidth(), tr.getRegionHeight());
    }
    
    public void render(SpriteBatch sb, float tiempo) {
        if(visibleOk) {
            sb.begin();
            if(velocidad.x > 0)
                sb.draw(batA.derecha.getKeyFrame(tiempo), posicion.x, posicion.y);
            else
                sb.draw(batA.izquierda.getKeyFrame(tiempo), posicion.x, posicion.y);
            sb.end();
        }
    }
    

    public void desaparecerAlFinal(int height)
    {
        if (posicion.x<100)
        {
            posicion.x = 0;
        }
    }
}