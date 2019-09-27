package com.example.dai_tp7;

import android.util.Log;

import org.cocos2d.actions.interval.MoveTo;
import org.cocos2d.actions.interval.ScaleBy;
import org.cocos2d.layers.Layer;
import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.Scene;
import org.cocos2d.nodes.Sprite;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.types.CCPoint;
import org.cocos2d.types.CCSize;

import java.util.ArrayList;
import java.util.Random;

public class MiJuego {
    CCGLSurfaceView _VistaJuego;
    CCSize _Pantalla;
    //Sprite _Jugador;
    ArrayList _listaJugadores;

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
            //ponerJugador();
            _listaJugadores=new ArrayList();
            super.schedule("ponerJugador", 3.0f);

            Log.d("CapaJuego","Voy a ubicar el fondo");
            ponerImagenFondo();

            Log.d("CapaJuego","Detectar llegada");
            super.schedule("detectarLlegada", 0.01f);
        }
        public void ponerJugador(float diferenciaTiempo)
        {

            Log.d("PonerJugador","Le asigno la imagen al jugador");
            Sprite Jugador;
            Jugador=Sprite.sprite("peron.jpg");

            Log.d("PonerJugador","Calculo posiciones random");
            Random random=new Random();
            CCPoint position=new CCPoint();
            position.x=random.nextInt((int) (_Pantalla.getWidth()-Jugador.getWidth()));
            position.x+=Jugador.getWidth()/2;
            position.y=random.nextInt((int) (_Pantalla.getHeight()-Jugador.getHeight()));
            position.y+=Jugador.getHeight()/2;

            Log.d("PonerJugador","La oriento en la posicion inicial");
            Jugador.setPosition(position.x,position.y);

            CCPoint posicionFinal=new CCPoint();
            if(position.x>_Pantalla.getWidth()/2){
                posicionFinal.x=0+Jugador.getWidth()/2;
                if(position.y>_Pantalla.getHeight()/2){
                    posicionFinal.y=0+Jugador.getHeight()/2;
                }else{
                    posicionFinal.y=_Pantalla.getHeight()-Jugador.getHeight()/2;
                }
            }else{
                posicionFinal.x=_Pantalla.getWidth()-Jugador.getWidth()/2;
                if(position.y>_Pantalla.getHeight()/2){
                    posicionFinal.y=0+Jugador.getHeight()/2;
                }else{
                    posicionFinal.y=_Pantalla.getHeight()-Jugador.getHeight()/2;
                }
            }
            Log.d("PonerJugador","Inicia Movimiento");
            Jugador.runAction(MoveTo.action(3, posicionFinal.x, posicionFinal.y));

            Log.d("PonerJugador","Lo agrego a la lista");
            _listaJugadores.add(Jugador);

            Log.d("PonerJugador","Lo agrego a la capa");
            super.addChild(Jugador,1);
        }
        void ponerImagenFondo()
        {
            Sprite ImagenFondo;
            Log.d("ponerImagenFondo","Le asigno la imagen al sprite de fondo");
            ImagenFondo=Sprite.sprite("casablanca.jpg");

            Log.d("ponerImagenFondo","Lo ubico");
            ImagenFondo.setPosition(_Pantalla.getWidth()/2,_Pantalla.getHeight()/2);

            Log.d("ponerImagenFondo","Lo escalo");
            ImagenFondo.runAction(ScaleBy.action(0.01f, 3, _Pantalla.getHeight()/ImagenFondo.getHeight()));

            Log.d("ponerImagenFondo","Le agrego a la capa");
            super.addChild(ImagenFondo,0);
        }
        public void detectarLlegada(float deltaTiempo)
        {
            Log.d("detectarLlegada", "entro");
            CCPoint position=new CCPoint();
            boolean llego;
            llego=false;
            ArrayList JugadoresQueLlegaron=new ArrayList();
            for(int punteroJugadores=0; punteroJugadores<_listaJugadores.size(); punteroJugadores++) {
                Sprite UnJugadorAVerificar=(Sprite) _listaJugadores.get(punteroJugadores);
                position.x = UnJugadorAVerificar.getPositionX();
                position.y = UnJugadorAVerificar.getPositionY();

                Log.d("detectarLlegada", "Posicion x: " + position.x + "   Posicion y: " + position.y);
                Log.d("detectarLlegada", "Posicion x pantalla: " + _Pantalla.getWidth() + "   Posicion y pantalla: " + _Pantalla.getHeight());
                if (position.x == _Pantalla.getWidth() - UnJugadorAVerificar.getWidth() / 2|| position.x==0+UnJugadorAVerificar.getWidth()/2) {
                    if (position.y == _Pantalla.getHeight() - UnJugadorAVerificar.getHeight() / 2|| position.y==0+UnJugadorAVerificar.getHeight()/2) {
                        Log.d("detectarLlegada", "remuevo el jugador");
                        llego=true;
                        super.removeChild(UnJugadorAVerificar, true);
                        JugadoresQueLlegaron.add(punteroJugadores);
                    }
                }
            }
            if(llego){
                for(int punteroJugadores=JugadoresQueLlegaron.size()-1; punteroJugadores>=0; punteroJugadores--)
                {
                    _listaJugadores.remove(punteroJugadores);
                }
            }
        }
    }
}
