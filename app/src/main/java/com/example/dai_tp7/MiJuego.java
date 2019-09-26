package com.example.dai_tp7;

import android.util.Log;

import org.cocos2d.layers.Layer;
import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.Scene;
import org.cocos2d.nodes.Sprite;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.types.CCPoint;
import org.cocos2d.types.CCSize;

import java.util.Random;

public class MiJuego {
    CCGLSurfaceView _VistaJuego;
    CCSize _Pantalla;
    Sprite _Jugador;
    public MiJuego(CCGLSurfaceView Vista)
    {
        Log.d("MiJuego","Comenzar el constructor");
        _VistaJuego=Vista;
    }
    public void ComenzarJuego(){
        Log.d("Comenzar","Comenzar el juego");
        Director.sharedDirector().attachInView(_VistaJuego);

        _Pantalla=Director.sharedDirector().displaySize();
        Log.d("Comenzar","Pantalla Ancho: "+_Pantalla.width +" - Alto:"+_Pantalla.getHeight());

        Log.d("Comenzar","Declaro e instancio la escena");
        Scene EscenaAUsar=ComienzoEscena();

        Log.d("Comenzar","Le digo al director que inicie la escena");
        Director.sharedDirector().runWithScene(EscenaAUsar);
    }
    private Scene ComienzoEscena()
    {
        Log.d("ComienzoEscena","comienzo");
        Scene EscenaAUsar=Scene.node();

        Log.d("ComienzoEscena","Voy na agregar la capa");
        CapaJuego micapa=new CapaJuego();
        EscenaAUsar.addChild(micapa);

        Log.d("ComienzoEscena","Devulvo la escena creada");
        return EscenaAUsar;
    }
    class CapaJuego extends Layer
    {
        public CapaJuego()
        {
            Log.d("CapaJuego","Comienzo el constructor");

            Log.d("CapaJuego","Voy a ubicar el jugador");
            ponerJugador();

            Log.d("CapaJuego","Voy a ubicar el fondo");
            ponerImagenFondo();
        }
        void ponerJugador()
        {

            Log.d("PonerJugador","Le asigno la imagen al jugador");
            _Jugador=Sprite.sprite("peron.jpg");

            Log.d("PonerJugador","Calculo posiciones random");
            Random random=new Random();
            CCPoint position=new CCPoint();
            position.x=random.nextInt((int) (_Pantalla.getWidth()-_Jugador.getWidth()));
            position.x+=_Jugador.getWidth()/2;
            position.y=random.nextInt((int) (_Pantalla.getHeight()-_Jugador.getHeight()));
            position.y+=_Jugador.getHeight()/2;

            Log.d("PonerJugador","La oriento en la posicion inicial");
            _Jugador.setPosition(position.x,position.y);

            Log.d("PonerJugador","Lo agrego a la capa");
            super.addChild(_Jugador,1);
        }
        void ponerImagenFondo()
        {
            Sprite ImagenFondo;
            ImagenFondo=Sprite.sprite("casablanca.jpg");
            ImagenFondo.setPosition(_Pantalla.getWidth()/2,_Pantalla.getHeight()/2);
            super.addChild(ImagenFondo,0);
        }
    }
}
