package com.mygdx.jpf;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Vector;

public abstract class Elemento {
    public Rectangle mascaraAtaque, mascaraVital, mascaraBloqueo;
    public boolean visibleOk = true;
    public Vector2 velocidad, posicion;
    
    abstract public void render(SpriteBatch sb, float tiempo);
    
    public boolean colisionBloqueo(Elemento el) {
        if(!visibleOk)
            return false;
        boolean colisionOk = el.mascaraBloqueo.overlaps(mascaraBloqueo);
        return colisionOk;
    }
    
    public boolean colisionAtaque(Elemento el) {
        if(!visibleOk)
            return false;
        return mascaraVital.overlaps(el.mascaraAtaque);
    }
    
    public void actualizarPosicionMascaras() {
        if(mascaraAtaque != null)
            mascaraAtaque.setPosition(posicion);
        if(mascaraVital != null)
            mascaraVital.setPosition(posicion);
        if(mascaraBloqueo != null)
            mascaraBloqueo.setPosition(posicion);
    }
    
    public void actualizarPosicion(float dt, Vector<Elemento> elementos) {
        if (visibleOk) {
            velocidad.add(0, -20*dt); // Se suma la gravedad

            posicion.add(velocidad.x*dt, 0);
            actualizarPosicionMascaras();
            for (Elemento el : elementos) {
                if (colisionBloqueo(el)) {
                    posicion.add(-velocidad.x*dt, 0);
                    actualizarPosicionMascaras();
                    siColisionBloqueoEjeX();
                    break;
                }

                posicion.add(0, velocidad.y*dt);
                actualizarPosicionMascaras();
                for (Elemento e1 : elementos) {
                    if (colisionBloqueo(e1)) {
                        posicion.add(0, -velocidad.y * dt);
                        velocidad.set(velocidad.x, 0);
                        actualizarPosicionMascaras();
                        siColisionBloqueoEjeY();
                        break;
                    }
                }
            }
        }

    }


    public void actualizarPosicionSinGravedad(float dt, Vector<Elemento> elementos) {
        if (visibleOk) {
            //velocidad.add(0, -20*dt); // Se suma la gravedad
            posicion.add(velocidad.x*dt, 0);
            actualizarPosicionMascaras();
            for (Elemento el : elementos) {
                if (colisionBloqueo(el)) {
                    posicion.add(-velocidad.x*dt, 0);
                    actualizarPosicionMascaras();
                    siColisionBloqueoEjeX();
                    break;
                }

                posicion.add(0, velocidad.y*dt);
                actualizarPosicionMascaras();
                for (Elemento e1 : elementos) {
                    if (colisionBloqueo(e1)) {
                        posicion.add(0, -velocidad.y * dt);
                        velocidad.set(velocidad.x, 0);
                        actualizarPosicionMascaras();
                        siColisionBloqueoEjeY();
                        break;
                    }
                }
            }
        }

    }

    public void siColisionBloqueoEjeX() { }
    public void siColisionBloqueoEjeY() { }
}