package com.mygdx.jpf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

public class Juego
{
    static public final int WIDTH = 800;
    static public final int HEIGHT = 480;

    BitmapFont font,score;
    static String texto = "no llega nada";
    String puntuacionTexto = "Score: 0";
    int puntuacion = 0;

    OrthographicCamera camara;
    Sprites sprites;
    Vector<Elemento> bloques;
    Vector<Elemento> proyectiles;
    Vector<Elemento> demons;
    Vector<Elemento> walls;
    Door door;
    Rider rider;
    int nivel;
    float tiempo,tiempoSiguienteDisparo;
    Sound sonido,sonidoDoor,sonidoEnemyDead;
    Music musica;


    public Juego()
    {

        font = new BitmapFont();
        font.setColor(Color.WHITE);

        score = new BitmapFont();
        score.setColor(Color.WHITE);

        sprites = new Sprites();
        demons = new Vector<Elemento>();
        bloques = new Vector<Elemento>();
        proyectiles = new Vector<Elemento>();
        for(int i=0; i<1; i++)
            proyectiles.add(new Proyectil(sprites, 0 , 0));
        nivel = 0;
        camara = new OrthographicCamera();
        iniciarEscenario();

        sonido = Gdx.audio.newSound(Gdx.files.internal("chain.mp3"));
        sonidoDoor = Gdx.audio.newSound(Gdx.files.internal("door.mp3"));
        sonidoEnemyDead = Gdx.audio.newSound(Gdx.files.internal("enemydead.mp3"));

        musica = Gdx.audio.newMusic(Gdx.files.internal("vampirekiller.mp3"));

        musica.play();
        musica.setVolume(0.2f);
        musica.setLooping(true);
    }


    public void cargarNivel(int nivel) {
        try {
            FileHandle file = Gdx.files.internal("niveles.txt");
            InputStream stream = file.read();
            BufferedReader in = new BufferedReader(new InputStreamReader(stream));
            // Se busca el nivel
            for(int n=0; n<nivel; n++) {
                for(int i=0; i<10; i++) {
                    String linea = in.readLine();
                }
            }
            // Se lee el nivel
            float y = HEIGHT;
            for(int i=0;i<10; i++) {
                y -= sprites.bloque.getRegionHeight();
                String linea = in.readLine();
                if(linea == null) {
                    this.nivel = 0;
                    iniciarEscenario();
                }
                float x = 0f;
                for(int n=0; n<linea.length(); n++) {
                    char ch = linea.charAt(n);
                    switch(ch) {
                       case '.':
                            walls.add(new Wall(sprites,x,y));
                            break;
                        case '#':
                            bloques.add(new Bloque(sprites, x, y));
                            break;

                        case 'd':
                            demons.add(new Demon(sprites, x, y));
                            walls.add(new Wall(sprites,x,y));
                            break;

                        case 'b':
                            demons.add(new Bat(sprites, x, y));
                            walls.add(new Wall(sprites,x,y));
                            break;



                        case 'p':
                            door = new Door(sprites,x,y);
                            walls.add(new Wall(sprites,x,y));
                            break;
                    }
                    x += sprites.bloque.getRegionWidth();
                }



            }
            in.close();
        } catch(Exception e) {
            System.out.println(e);
        }
    }


    public void colisionAtaque() {




        if (rider.colisionBloqueo(door))
        {
            sonidoDoor.play(1.0f);
            nivel++;

            iniciarEscenario();
        }

        for(Elemento e : demons) {
            if(e.visibleOk) {
                if (rider.colisionAtaque(e)) {
                    // El héroe es eliminado
                    rider.posicion.set(sprites.bloque.getRegionWidth(), HEIGHT);
                }





                for (Elemento p : proyectiles) {
                    if (p.visibleOk) {
                        if (e.colisionAtaque(p)) {

                            if (e instanceof Demon)
                            {
                                ((Demon) e).vidas--;


                                if (((Demon) e).vidas==0)
                                {
                                    sonidoEnemyDead.play(0.5f);
                                    e.visibleOk =  false;
                                    puntuacion = puntuacion+100;
                                    puntuacionTexto = "Score: "+puntuacion;
                                }


                            }

                            if (e instanceof Bat)
                            {
                                ((Bat) e).vidas--;


                                if (((Bat) e).vidas==0)
                                {
                                    sonidoEnemyDead.play(0.5f);
                                    e.visibleOk =  false;
                                    puntuacion = puntuacion+50;
                                    puntuacionTexto = "Score: "+puntuacion;
                                }


                            }

                            p.visibleOk = false;
                            p.posicion = new Vector2(0,0);
                            // Se cuentan los esqueletos que quedan
                            int nDemons = 0;
                            for (Elemento es : demons)
                                if (es.visibleOk) {
                                    nDemons++;
                                    break;
                                }
                            break;
                        }
                    }
                }

            }
        }
    }

    private void iniciarEscenario() {
        bloques = new Vector<Elemento>();
        demons = new Vector<Elemento>();
        walls = new Vector<Elemento>();
        if(rider == null)
            rider = new Rider(sprites,sprites.bloque.getRegionWidth(),HEIGHT - 2*sprites.bloque.getRegionHeight());
            //caballero = new Caballero(sprites,  , );
        for(Elemento p : proyectiles)
            p.visibleOk = false;

        //rider.posicion.set(sprites.bloque.getRegionWidth() , HEIGHT);
        rider.posicion.set(sprites.bloque.getRegionWidth(), HEIGHT);

        //caballero.posicion.set(sprites.bloque.getRegionWidth() , HEIGHT);
        cargarNivel(nivel);
    }

   public void actualizarPosicion(float dt) {
        rider.actualizarPosicion(dt, bloques);

       for(Elemento e : demons)
       {
           if (e instanceof Demon)
           {
               e.actualizarPosicion(dt, bloques);
           }
           if (e instanceof Bat)
           {
               e.actualizarPosicionSinGravedad(dt, bloques);
           }
       }

       for(Elemento p : proyectiles)
           p.actualizarPosicionSinGravedad(dt, bloques);
    }

    public void handleInput() {



        if (rider.getMirandoHacia()!= Rider.MirandoHacia.Ataque)
        {
            //if(Gdx.input.isKeyJustPressed(Keys.A))
            //rider.ataque(tiempo);


            if(tiempo > tiempoSiguienteDisparo && (Gdx.input.isKeyPressed(Keys.A)
                    ||
                    (tiempo > tiempoSiguienteDisparo && Gdx.input.isTouched()
                        && Gdx.input.getX() > Gdx.graphics.getWidth()/3 && Gdx.input.getX() < Gdx.graphics.getWidth()/1.5 && Gdx.input.getY() > Gdx.graphics.getHeight()/2 ))) {
                for(Elemento p : proyectiles) {
                    if(!p.visibleOk) {
                        //rider.lanzar((Proyectil)p);
                        rider.ataque(tiempo,(Proyectil)p);
                        sonido.play(1.0f);

                        break;
                    }
                }
                tiempoSiguienteDisparo = tiempo + 1.0f;
            }

            else if(Gdx.input.isKeyPressed(Keys.LEFT))
                rider.izquierda();
            else if(Gdx.input.isKeyPressed(Keys.RIGHT))
                rider.derecha();
            else
                rider.quieto();

            //Controles Android

            if (Gdx.input.isTouched()) {

                if (Gdx.input.getX() < Gdx.graphics.getWidth() / 3)
                {
                    rider.izquierda();
                }

//                if (Gdx.input.getX() > Gdx.graphics.getWidth()/3 && Gdx.input.getY() < Gdx.graphics.getWidth()/1.5 )

                if (Gdx.input.getX() > Gdx.graphics.getWidth()/1.5)
                {
                    rider.derecha();
                }

                if (Gdx.input.getY() < Gdx.graphics.getHeight()/2)
                {
                    rider.saltar();
                }

            }


//           Gdx.app.graphics.getWidth();
//           Gdx.app.graphics.getHeight();

            if(Gdx.input.isKeyJustPressed(Keys.S))
                rider.saltar();
        }

    }



    public void render(SpriteBatch sb) {


        float dt = Gdx.graphics.getDeltaTime();
        tiempo += dt;

        colisionAtaque();
        actualizarPosicion(dt);
        handleInput();

        sb.setProjectionMatrix(camara.combined);
        camara.setToOrtho(false, WIDTH, HEIGHT);
        camara.translate(rider.posicion.x - WIDTH/7, -300);
        //camara.translate(0, 0);

        camara.update();

        rider.pararAtaque(tiempo);



        // Se dibujan los elementos



        for(Elemento b : walls)
            b.render(sb, tiempo);

        door.render(sb,tiempo);
        for(Elemento b : demons)
        {
            if (b instanceof Demon)
            {
                b.render(sb, tiempo);
            }
            if (b instanceof Bat)
            {
                b.render(sb, tiempo);
                ((Bat) b).desaparecerAlFinal(Gdx.graphics.getHeight());
            }
        }

        for(Elemento b : bloques)
            b.render(sb, tiempo);


        rider.render(sb, tiempo);

        for(Elemento b : proyectiles)
            b.render(sb, tiempo);

        renderTexto(sb);

    }

    public void renderTexto(SpriteBatch sb) {
        sb.begin();
        font.draw(sb, texto, camara.position.x-350, 150);
        score.draw(sb,puntuacionTexto,camara.position.x+300,150);
        sb.end();
    }


    public void dispose() {
        sprites.dispose();
        sonido.dispose();
        sonidoDoor.dispose();
    }

    public static String getTexto() {
        return texto;
    }

    public static void setTexto(String texto) {
        Juego.texto = texto;
    }
}
