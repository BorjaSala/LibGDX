package com.mygdx.jpf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Rider extends Elemento
{

    private Animaciones riderrunA;
    private Animaciones riderstandA;
    private Animaciones riderattackA;
    private Animaciones riderjumpA;
    float tiempoFinAtaque;
//    BitmapFont font;


    Proyectil p;

    private int direccion;

    public MirandoHacia getMirandoHacia() {
        return mirandoHacia;
    }

    public void setMirandoHacia(MirandoHacia mirandoHacia) {
        this.mirandoHacia = mirandoHacia;
    }

    public enum MirandoHacia {
        Izquierda,Derecha,Ataque,Salto,Quieto;
    }

    private MirandoHacia mirandoHacia;

    public Rider(Sprites sprites, float x, float y) {

//        font = new BitmapFont();

        riderrunA = sprites.riderrunA;
        riderstandA = sprites.riderstandA;
        riderattackA = sprites.riderattackA;
        riderjumpA = sprites.riderjumpA;

        posicion = new Vector2(x, y);
        velocidad = new Vector2(0, 0);
        TextureRegion tr = sprites.riderstandA.izquierda.getKeyFrame(0);
        mascaraVital = mascaraBloqueo = new Rectangle(x, y, tr.getRegionWidth(), tr.getRegionHeight());
        mirandoHacia = MirandoHacia.Quieto;
    }

    @Override
    public void render(SpriteBatch sb, float tiempo)
    {
        sb.begin();
//        font.draw(sb, "Espacio para saltar", posicion.x-100, 150);

        float dt = Gdx.graphics.getDeltaTime();

        //velocidad.scl(dt);
        //posicion.add(velocidad);

        if (mirandoHacia==MirandoHacia.Quieto && velocidad.y == 0)
        {
            if (direccion==0)
            {
                sb.draw(riderstandA.izquierda.getKeyFrame(tiempo),posicion.x,posicion.y);
            }
            else if(direccion==1)
            {
                sb.draw(riderstandA.derecha.getKeyFrame(tiempo),posicion.x,posicion.y);
            }
        }



        if (mirandoHacia==MirandoHacia.Ataque)
        {

            if (direccion==0)
            {
                sb.draw(riderattackA.izquierda.getKeyFrame(tiempoFinAtaque-tiempo),posicion.x,posicion.y);
                if ((tiempoFinAtaque-tiempo)<0.1f)
                {
                    p.visibleOk = true;
                    p.posicion = new Vector2(posicion.x+55,posicion.y+35);
                    //35
                }
            }
            else if(direccion==1)
            {
                sb.draw(riderattackA.derecha.getKeyFrame(tiempoFinAtaque-tiempo),posicion.x-100,posicion.y);
                if ((tiempoFinAtaque-tiempo)<0.1f)
                {
                    p.visibleOk = true;
                    p.posicion = new Vector2(posicion.x-100,posicion.y+35);
                }
            }
        }

        if (mirandoHacia==MirandoHacia.Derecha && velocidad.y == 0)
        {
            sb.draw(riderrunA.derecha.getKeyFrame(tiempo),posicion.x,posicion.y);
            direccion = 0;
        }

        if (mirandoHacia==MirandoHacia.Izquierda && velocidad.y == 0)
        {
            sb.draw(riderrunA.izquierda.getKeyFrame(tiempo),posicion.x,posicion.y);
            direccion = 1;

        }

        if (
                //mirandoHacia==MirandoHacia.Salto
                mirandoHacia!=MirandoHacia.Ataque
                &&
                velocidad.y != 0
        )
        {
            if (direccion==0)
            {
                sb.draw(riderjumpA.izquierda.getKeyFrame(tiempo),posicion.x,posicion.y);
            }
            else if(direccion==1)
            {
                sb.draw(riderjumpA.derecha.getKeyFrame(tiempo),posicion.x,posicion.y);
            }
        }

        sb.end();

    }


    public void derecha() {
        mirandoHacia=MirandoHacia.Derecha;
        if(velocidad.y == 0)
            velocidad.set(150, velocidad.y);
    }

    public void izquierda() {
        mirandoHacia=MirandoHacia.Izquierda;
        if(velocidad.y == 0)
            velocidad.set(-150, velocidad.y);

    }

    public void quieto() {
        mirandoHacia = MirandoHacia.Quieto;
        if(velocidad.y == 0)
            velocidad.set(0, velocidad.y);
    }

    public void ataque(float tiempo, Proyectil p) {
        if (mirandoHacia!= MirandoHacia.Ataque)
        {
            this.p = p;
            tiempoFinAtaque = tiempo+0.4f;
            mirandoHacia = MirandoHacia.Ataque;

           /* Vector2 vel;
            Vector2 pos;
            if(direccion== 1) {
                vel = new Vector2(-400+velocidad.x, 0);
                pos = new Vector2(posicion.x+20, posicion.y + mascaraBloqueo.height*0.4f);

                p.lanzar(pos, vel);
            } else if(direccion==0){
                vel = new Vector2(400+velocidad.x, 0);
                pos = new Vector2(posicion.x-20, posicion.y + mascaraBloqueo.height*0.4f);
                p.lanzar(pos, vel);
            }
*/
            if(velocidad.y == 0)
                velocidad.set(0, velocidad.y);

        }

    }

    public void saltar() {
        mirandoHacia = MirandoHacia.Salto;
        if(velocidad.y == 0)
            velocidad.set(velocidad.x, 8);
        if (mirandoHacia == MirandoHacia.Derecha)
        {
            velocidad.set(60, 8);
        }

    }

    public void pararAtaque(float tiempo)
    {
        if (tiempo>tiempoFinAtaque && mirandoHacia == MirandoHacia.Ataque)
        {
            mirandoHacia = MirandoHacia.Quieto;
            p.visibleOk = false;
        }
    }

    public void siColisionBloqueoEjeX() { }
    public void siColisionBloqueoEjeY() { }
}
