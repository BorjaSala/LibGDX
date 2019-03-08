package com.mygdx.jpf;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
public class Sprites
{
    public Texture riderstand;
    public Texture riderattack;
    public Texture riderjump;
    public Texture riderrun;
    public Texture demon;
    public Texture bat;
    public Texture iconos;
    public Texture azulejos;
    public Texture door;
    public Texture azulejos2;
    public Texture cadena;

    public Animaciones riderrunA;
    public Animaciones riderstandA;
    public Animaciones riderattackA;
    public Animaciones riderjumpA;
    public Animaciones demonA;
    public Animaciones batA;


    public TextureRegion bloque;
//    public TextureRegion manzana;
    public TextureRegion wall;




    public Sprites()
    {
        riderstand = new Texture("stand.png");
        riderrun = new Texture("run.png");
        riderattack= new Texture("attack.png");
        riderjump = new Texture("jump.png");
        azulejos = new Texture("jungle tileset.png");
        demon = new Texture("demon.png");
        bat = new Texture("bat.png");
        cadena = new Texture("chain.png");

        iconos = new Texture("icon_recycled.png");
        door = new Texture("gate.png");
        azulejos2 = new Texture("dark tileset.png");

        bloque = new TextureRegion(azulejos, 0, 16, 80, 80);
        //manzana = new TextureRegion(iconos, 0, 32, 32, 32);
//        manzana = new TextureRegion(iconos, 32, 32, 32, 32);
        wall = new TextureRegion(azulejos2, 0, 16, 80, 80);

        riderrunA = new Animaciones();
        riderrunA.izquierda = getAnimation(riderrun, 0.1f, 8, true,true);
        riderrunA.derecha = getAnimation(riderrun, 0.1f, 8, false,true);

        riderstandA = new Animaciones();
        riderstandA.izquierda = getAnimation(riderstand, 0.2f, 4, false,true);
        riderstandA.derecha = getAnimation(riderstand, 0.2f, 4, true,true);

        riderattackA = new Animaciones();
        riderattackA.izquierda = getAnimation(riderattack, 0.1f, 3, false,false);
        riderattackA.derecha = getAnimation(riderattack, 0.1f, 3, true,false);

        riderjumpA = new Animaciones();
        riderjumpA.izquierda = getAnimation(riderjump, 0.1f, 2, false,true);
        riderjumpA.derecha = getAnimation(riderjump, 0.1f, 2, true,true);

        demonA = new Animaciones();
        demonA.izquierda = getAnimation(demon, 0.2f, 3, true,true);
        demonA.derecha = getAnimation(demon, 0.2f, 3, false,true);

        batA = new Animaciones();
        batA.izquierda = getAnimation(bat, 0.1f, 8, true,true);
        batA.derecha = getAnimation(bat, 0.1f, 8, false,true);

    }

    public Animation<TextureRegion> getAnimation(Texture img, float duracion, int nFotogramas, boolean flipOk, boolean loopOk) {
        TextureRegion textureRegion = new TextureRegion(img);
        Array<TextureRegion> fotogramas = new Array<TextureRegion>();
        int anchuraFotograma = img.getWidth() / nFotogramas;
        for(int i=0; i < nFotogramas; i++) {
            TextureRegion reg = new TextureRegion(textureRegion, i*anchuraFotograma, 0, anchuraFotograma, img.getHeight());
            reg.flip(flipOk, false);
            fotogramas.add(reg);
        }
        Animation<TextureRegion> animation;
        if(loopOk)
            animation = new Animation<TextureRegion>(duracion, fotogramas, Animation.PlayMode.LOOP);
        else
            animation = new Animation<TextureRegion>(duracion, fotogramas, Animation.PlayMode.REVERSED);

        return animation;
    }




    public void dispose() {
        riderstand.dispose();
        riderrun.dispose();
        riderattack.dispose();
        demon.dispose();
        cadena.dispose();
        riderjump.dispose();
        azulejos.dispose();
        iconos.dispose();
        azulejos2.dispose();
        door.dispose();
        bat.dispose();
    }


}
