package jrp.progra.vampiresurvival_1;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;

import jrp.progra.piezas.HombreLobo;
import jrp.progra.piezas.Muerte;
import jrp.progra.piezas.Piezas;
import jrp.progra.piezas.Vampiro;
import jrp.progra.piezas.Zombie;
import jrp.progra.piezas.Esqueleto;
import jrp.progra.piezas.Bruja;

public class Tablero extends JPanel {
    public int tCasillas = 120;

    int col = 6;
    int fil = 6;

    ArrayList<Piezas> piezasJuego = new ArrayList<>();

    public ArrayList<Piezas> capturadasBlancas = new ArrayList<>();
    public ArrayList<Piezas> capturadasNegras = new ArrayList<>();

    // Pieza que se encuentra elegida por mover
    public Piezas piezaElegida;

    // Cuando el jugador elige una habilidad del Necromante se guarda mientras se
    // espera que se elija el destino
    public String habilidadPendiente = null;
    public Piezas piezaHabilidadPendiente = null;

    // Cada cara de la ruleta esta unida a una carta especifica
    // Orden: 0-1 Vampiro, 2-3 HombreLobo, 4-5 Muerte/Bruja.
    public Piezas[] piezasRuletaBlancas = new Piezas[6];
    public Piezas[] piezasRuletaNegras = new Piezas[6];

    public boolean turnoBlanco = true;
    public Piezas piezaPermitida = null; // la ficha exacta que la ruleta habilito para este turno
    public int girosUsados = 0;

    // Fin de la partida
    public boolean partidaTerminada = false;
    public String mensajeFinal = null;

    // Ultimo mensaje del combate
    public String ultimoMensaje = null;

    public Jugador jugadorBlanco; // Jugador con sesion iniciada
    public Jugador jugadorNegro; // Jugador oponente
    public GestorJugadores gestor; // Variable para sumarle puntos al jugador ganador

    public String nombreDe(boolean esBlanca) {
        if (esBlanca) {
            if (jugadorBlanco != null) {
                return jugadorBlanco.getUsuario(); // Nombre del jugador con las piezas blancas
            }
            return "Blanco"; // Error si no se detecta el nombre
        } else {
            if (jugadorNegro != null) {
                return jugadorNegro.getUsuario(); // Nombre del jugador con las piezas negras
            }
            return "Negro"; // Error si no se detecta el nombre
        }
    }

    Entrada entra = new Entrada(this);

    Image im1 = new ImageIcon(getClass().getResource("/Casilla2_1.png")).getImage().getScaledInstance(80, 80,
            Image.SCALE_SMOOTH);
    Image im2 = new ImageIcon(getClass().getResource("/Casilla2_2.png")).getImage().getScaledInstance(80, 80,
            Image.SCALE_SMOOTH);

    public Tablero() {
        this.setPreferredSize(new Dimension(col * tCasillas, fil * tCasillas));
        generarPiezas();

        this.addMouseListener(entra);
        this.addMouseMotionListener(entra);

        ToolTipManager.sharedInstance().registerComponent(this);
    }

    @Override
    public String getToolTipText(MouseEvent event) {
        int columnaM = event.getX() / tCasillas;
        int filaM = event.getY() / tCasillas;

        Piezas piezaBajoMouse = getPieza(columnaM, filaM);

        if (piezaBajoMouse == null) {
            return null;
        }

        String info = "";
        if (piezaBajoMouse.getAlias().equals("Muerte") || piezaBajoMouse.getAlias().equals("Bruja")) {
            info = "Click derecho para ver las habilidades especiales de la carta";
        }
        if (piezaBajoMouse.getAlias().equals("Vampiro")) {
            info = "Click derecho sobre una pieza para robar vida";
        }
        if(piezaBajoMouse.getAlias().equals("HombreLobo")){
            info="Se puede mover dos casillas a la vez";
        }
        if(piezaBajoMouse.getAlias().equals("Zombie")||piezaBajoMouse.getAlias().equals("Esqueleto")){
            info="Estorba";
        }
        return "<html>" + piezaBajoMouse.getAlias()
                + "<br>Vida: " + piezaBajoMouse.getVida()
                + "<br>Ataque: " + piezaBajoMouse.getAtaque()
                + "<br>Escudo: " + piezaBajoMouse.getEscudo()
                + "<br>Tip:  " + info
                + "</html>";
    }

    public boolean esMovimientoValido(Mover mov) {
        if (mismoEquipo(mov.pieza, mov.captura)) {
            return false;
        }

        if (mov.pieza instanceof Zombie) {
            Zombie zombie = (Zombie) mov.pieza;
            if (mov.captura == null || !zombie.puedeAtacar) {
                return false;
            }
        }

        if (!mov.pieza.esMovimientoValido(mov.nCol, mov.nFil)) {
            return false;
        }

        if (mov.pieza.chocaPieza(mov.nCol, mov.nFil)) {
            return false;
        }

        return true;
    }

    // Cantidad de piezas que ha perdido el jugador del turno actual
    public int piezasPerdidasTurnoActual() {
        return turnoBlanco ? capturadasBlancas.size() : capturadasNegras.size();
    }

    // Segun la regla: 2 piezas perdidas = 2 giros, 4 piezas perdidas = 3 giros
    public int girosPermitidos() {
        int perdidas = piezasPerdidasTurnoActual();
        if (perdidas >= 4) {
            return 3;
        } else if (perdidas >= 2) {
            return 2;
        }
        return 1;
    }

    // Se llama cuando la ruleta termina de girar, con el indice (0-5)
    public void establecerResultadoRuleta(int indiceCara) {
        girosUsados = girosUsados + 1;

        // Se eleige el arreglo de piezas segun de quien es el turno
        Piezas[] arregloEquipo;
        if (turnoBlanco) {
            arregloEquipo = piezasRuletaBlancas;
        } else {
            arregloEquipo = piezasRuletaNegras;
        }

        Piezas fichaDeLaCara = null;
        if (indiceCara >= 0 && indiceCara < arregloEquipo.length) {
            fichaDeLaCara = arregloEquipo[indiceCara];
        }

        boolean sigueViva = false;
        if (fichaDeLaCara != null && piezasJuego.contains(fichaDeLaCara)) {
            sigueViva = true;
        }

        if (sigueViva) {
            piezaPermitida = fichaDeLaCara;
        } else if (girosUsados >= girosPermitidos()) {
            // Ya se acabaron los giros y la ficha de esa cara esta muerte, asi que se
            // pierde turno
            pasarTurno();
        }
        // Si la ficha ya murio pero todavia quedan giros, el jugador puede volver a
        // girar
        repaint();
    }

    // El jugador que tiene el turno en este momento se retira de la partida.
    public void retirarse() {
        boolean ganoElBlanco = !turnoBlanco; // el que tenia el turno es el que se retira

        String nombreRetirado = nombreDe(turnoBlanco);
        String nombreGanador = nombreDe(ganoElBlanco);

        mensajeFinal = nombreRetirado + " se ha retirado. ¡Felicidades, " + nombreGanador + ", has ganado la partida y conseguido 3 puntos!";
        partidaTerminada = true;

        registrarResultado(ganoElBlanco);

        piezaElegida = null;
        habilidadPendiente = null;
        piezaHabilidadPendiente = null;

        repaint();
    }

    // Suma los 3 puntos al jugador ganador y guarda el resultado en el historial de
    // cada jugador
    private void registrarResultado(boolean ganoElBlanco) {
        if (gestor == null) {
            return;
        }

        if (jugadorBlanco != null) {
            if (ganoElBlanco) {
                jugadorBlanco.sumarPuntos(3);
            }
            gestor.agregarAlHistorial(jugadorBlanco.getUsuario() + " - " + mensajeFinal);
        }

        if (jugadorNegro != null) {
            if (!ganoElBlanco) {
                jugadorNegro.sumarPuntos(3);
            }
            gestor.agregarAlHistorial(jugadorNegro.getUsuario() + " - " + mensajeFinal);
        }
    }

    public void pasarTurno() {
        turnoBlanco = !turnoBlanco;
        piezaPermitida = null;
        girosUsados = 0;
        piezaElegida = null;
        habilidadPendiente = null;
        piezaHabilidadPendiente = null;
        repaint();
    }

    public boolean mismoEquipo(Piezas p1, Piezas p2) {
        if (p1 == null || p2 == null) {
            return false;
        }
        return p1.esBlanca == p2.esBlanca;
    }

    public void moverPieza(Mover mov) {
        if (mov.captura != null) {
            boolean muereE = atacar(mov.pieza, mov.captura);

            if (mov.pieza instanceof Zombie) {
                ((Zombie) mov.pieza).puedeAtacar = false;
            }

            if (muereE) {
                capturar(mov);
                moverPosicion(mov);
            } else {
                mov.pieza.xPos = mov.aCol * tCasillas;
                mov.pieza.yPos = mov.aFil * tCasillas;
            }
        } else {
            moverPosicion(mov);
        }

        pasarTurno();
    }

    private void moverPosicion(Mover mov) {
        mov.pieza.col = mov.nCol;
        mov.pieza.fil = mov.nFil;
        mov.pieza.xPos = mov.nCol * tCasillas;
        mov.pieza.yPos = mov.nFil * tCasillas;
    }

    public void capturar(Mover mov) {
        piezasJuego.remove(mov.captura);

        if (mov.captura.esBlanca) {
            capturadasBlancas.add(mov.captura);
        } else {
            capturadasNegras.add(mov.captura);
        }

        verificarVictoria(mov.captura.esBlanca);
    }

    public boolean atacar(Piezas atacante, Piezas defensor) {
        int danoAtaque = atacante.getAtaque();
        boolean murio = defensor.recibirHit(danoAtaque);

        armarMensajeAtaque(atacante, defensor, danoAtaque, murio);

        return murio;
    }

    // Mensaje de combate que se muestra en el panel lateral
    private void armarMensajeAtaque(Piezas atacante, Piezas defensor, int danoAtaque, boolean murio) {
        String nombreDefensor = nombreDe(defensor.esBlanca);
        String nombreAtacante = nombreDe(atacante.esBlanca);

        if (murio) {
            ultimoMensaje = nombreAtacante + " destruyo la pieza " + defensor.getAlias() + " del jugador "
                    + nombreDefensor;
        } else {
            ultimoMensaje = nombreAtacante + " ataco la pieza " + defensor.getAlias() + " y se le quitaron "
                    + danoAtaque
                    + " puntos; le quedan " + defensor.getEscudo() + " puntos de escudo y "
                    + defensor.getVida() + " de vida";
        }
    }

    // Revisa si el equipo indicado se quedo sin ninguna pieza en el tablero. Si sí,
    // declara ganador al otro equipo.
    private void verificarVictoria(boolean equipoAfectado) {
        boolean quedanPiezas = false;

        for (Piezas p : piezasJuego) {
            if (p.esBlanca == equipoAfectado) {
                quedanPiezas = true;
            }
        }

        if (quedanPiezas == false) {
            boolean ganoElBlanco = !equipoAfectado;

            String nombrePerdedor = nombreDe(equipoAfectado);
            String nombreGanador = nombreDe(ganoElBlanco);

            mensajeFinal = nombreGanador + " vencio a " + nombrePerdedor + ". ¡Felicidades, has ganado 3 puntos!";
            partidaTerminada = true;

            registrarResultado(ganoElBlanco);
        }
    }

    // Devuelve las claves de las habilidades especiales que la pieza puede usar
    // contra la casilla
    public List<String> habilidadesDisponibles(Piezas origen, int colDestino, int filDestino) {
        List<String> lista = new ArrayList<>();
        Piezas objetivo = getPieza(colDestino, filDestino);

        // Distancia en casillas
        int distCol = Math.abs(colDestino - origen.getCol());
        int distFil = Math.abs(filDestino - origen.getFil());
        int dist = Math.max(distCol, distFil);

        if (origen instanceof Vampiro) {
            if (objetivo != null && !mismoEquipo(origen, objetivo) && dist == 1) {
                lista.add("absorber");
            }
        }

        if (origen instanceof Muerte) {

            if (objetivo == null) {
                // casilla vacia, se puede invocar un zombie ahi
                lista.add("invocarZombie");

            } else if (mismoEquipo(origen, objetivo) == false) {
                // hay un enemigo en esa casilla

                boolean alineado = (origen.getCol() == colDestino || origen.getFil() == filDestino);

                if (dist == 2 && alineado) {
                    lista.add("lanza");
                }

                Piezas zombiePropio = buscarZombieAdyacente(colDestino, filDestino, origen.esBlanca);
                if (dist > 2 && zombiePropio != null) {
                    lista.add("ordenarZombie");
                }

            } else if (objetivo instanceof Zombie) {
                // hay un zombie propio en esa casilla, se puede ordenar
                lista.add("ordenarZombie");
            }
        }

        return lista;
    }

    public boolean esHabilidadValidaEnDestino(String clave, Piezas origen, int colDestino, int filDestino) {
        return habilidadesDisponibles(origen, colDestino, filDestino).contains(clave);
    }

    // Ejecuta la habilidad especial elegida por el jugador
    public void ejecutarHabilidad(String clave, Piezas origen, int colDestino, int filDestino) {
        Piezas objetivo = getPieza(colDestino, filDestino);

        if (clave.equals("absorber")) {
            Vampiro vampiro = (Vampiro) origen;
            boolean murioAbsorbido = vampiro.absorberSangre(objetivo);
            armarMensajeAtaque(origen, objetivo, 1, murioAbsorbido);
            if (murioAbsorbido) {
                removerPorMuerte(objetivo);
            }

        } else if (clave.equals("lanza")) {
            Muerte necromante = (Muerte) origen;
            int danoLanza = necromante.getAtaque() / 2;
            boolean murioLanza = necromante.ataqueLanza(objetivo);
            armarMensajeAtaque(origen, objetivo, danoLanza, murioLanza);
            if (murioLanza) {
                removerPorMuerte(objetivo);
            }

        } else if (clave.equals("invocarZombie")) {
            Zombie nuevoZombie;
            if (origen.esBlanca) {
                nuevoZombie = new Esqueleto(this, colDestino, filDestino, true);
            } else {
                nuevoZombie = new Zombie(this, colDestino, filDestino, false);
            }
            nuevoZombie.dueño = origen;
            piezasJuego.add(nuevoZombie);

        } else if (clave.equals("ordenarZombie")) {
            if (objetivo instanceof Zombie && mismoEquipo(origen, objetivo)) {
                // Click en un Zombie propio, lo habilita para que etaque
                ((Zombie) objetivo).puedeAtacar = true;
            } else {
                // Click en un enemigo lejano, el Zombie adyacente ataca
                Piezas zombiePropio = buscarZombieAdyacente(colDestino, filDestino, origen.esBlanca);
                if (atacar(zombiePropio, objetivo)) {
                    removerPorMuerte(objetivo);
                }
            }
        }

        pasarTurno();
    }

    private void removerPorMuerte(Piezas objetivo) {
        piezasJuego.remove(objetivo);
        if (objetivo.esBlanca) {
            capturadasBlancas.add(objetivo);
        } else {
            capturadasNegras.add(objetivo);
        }

        verificarVictoria(objetivo.esBlanca);
    }

    private Piezas buscarZombieAdyacente(int col, int fil, boolean esBlanca) {
        return buscarZombieAdyacente(col, fil, esBlanca, 0);
    }

    // Version recursiva: revisa la pieza en la posicion "indice" de la lista,
    private Piezas buscarZombieAdyacente(int col, int fil, boolean esBlanca, int indice) {
        if (indice >= piezasJuego.size()) {
            return null; // caso base:se revisaron todas las piezas
        }

        Piezas p = piezasJuego.get(indice); // se consigue el indice de la pieza

        if (p instanceof Zombie && p.esBlanca == esBlanca) {
            int dist = Math.max(Math.abs(col - p.getCol()), Math.abs(fil - p.getFil()));
            if (dist == 1) {
                return p;
            }
        }

        return buscarZombieAdyacente(col, fil, esBlanca, indice + 1);
    }

    public Piezas getPieza(int col, int fil) {
        for (Piezas p : piezasJuego) {
            if (p.getCol() == col && p.getFil() == fil) {
                return p;
            }
        }
        return null;
    }

    public void generarPiezas() {
        // Piezas lado inferior (fil 5) - Equipo Blanco
        Vampiro vb1 = new Vampiro(this, 4, 5, true);
        Vampiro vb2 = new Vampiro(this, 1, 5, true);
        HombreLobo hb1 = new HombreLobo(this, 0, 5, true);
        HombreLobo hb2 = new HombreLobo(this, 5, 5, true);
        Muerte mb1 = new Muerte(this, 2, 5, true);
        Bruja bb1 = new Bruja(this, 3, 5, true);

        // Se agregan las piezas al tablero en sus posiciones (Piezas Blancas)
        piezasJuego.add(mb1);
        piezasJuego.add(bb1);
        piezasJuego.add(vb1);
        piezasJuego.add(vb2);
        piezasJuego.add(hb1);
        piezasJuego.add(hb2);

        // Orden de la ruleta: 0-1 Vampiro, 2-3 HombreLobo, 4-5 Muerte/Bruja
        piezasRuletaBlancas[0] = vb1;
        piezasRuletaBlancas[1] = vb2;
        piezasRuletaBlancas[2] = hb1;
        piezasRuletaBlancas[3] = hb2;
        piezasRuletaBlancas[4] = mb1;
        piezasRuletaBlancas[5] = bb1;

        // Piezas lado superior (fil 0) - Equipo Negro
        Vampiro vn1 = new Vampiro(this, 4, 0, false);
        Vampiro vn2 = new Vampiro(this, 1, 0, false);
        HombreLobo hn1 = new HombreLobo(this, 0, 0, false);
        HombreLobo hn2 = new HombreLobo(this, 5, 0, false);
        Muerte mn1 = new Muerte(this, 2, 0, false);
        Bruja bn2 = new Bruja(this, 3, 0, false);

        // Se agregan las piezas al tablero en sus posiciones (Piezas Negras)
        piezasJuego.add(mn1);
        piezasJuego.add(bn2);
        piezasJuego.add(vn1);
        piezasJuego.add(vn2);
        piezasJuego.add(hn1);
        piezasJuego.add(hn2);

        // Orden de la ruleta: 0-1 Vampiro, 2-3 HombreLobo, 4-5 Muerte/Bruja
        piezasRuletaNegras[0] = vn1;
        piezasRuletaNegras[1] = vn2;
        piezasRuletaNegras[2] = hn1;
        piezasRuletaNegras[3] = hn2;
        piezasRuletaNegras[4] = mn1;
        piezasRuletaNegras[5] = bn2;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (int i = 0; i < fil; i++) {
            for (int j = 0; j < col; j++) {
                // Se dibujan las casillas del tablero
                g2d.drawImage((j + i) % 2 == 0 ? im1 : im2, j * tCasillas, i * tCasillas, tCasillas, tCasillas, this);
            }
        }

        // Dibujar las casillas a las que se puede mover o atacar
        if (piezaElegida != null)
            for (int f = 0; f < fil; f++) {
                for (int c = 0; c < col; c++) {
                    if (esMovimientoValido(new Mover(this, piezaElegida, c, f))) {
                        boolean esAtaque = getPieza(c, f) != null;

                        if (esAtaque) {
                            g2d.setColor(new Color(200, 40, 40, 190));
                        } else {
                            g2d.setColor(new Color(68, 180, 57, 190));
                        }

                        g2d.fillRect(c * tCasillas, f * tCasillas, tCasillas, tCasillas);
                    }
                }
            }

        // Dibujar las casillas validas para la habilidad especial que se usaran
        if (habilidadPendiente != null)
            for (int f = 0; f < fil; f++) {
                for (int c = 0; c < col; c++) {
                    if (esHabilidadValidaEnDestino(habilidadPendiente, piezaHabilidadPendiente, c, f)) {
                        g2d.setColor(colorHabilidad(habilidadPendiente, c, f));
                        g2d.fillRect(c * tCasillas, f * tCasillas, tCasillas, tCasillas);
                    }
                }
            }

        // Dibujar las piezas que estén en el tablero
        for (Piezas p : piezasJuego) {
            if (p != null) {
                p.paint(g2d);
            }
        }

        // Resaltar la pieza seleccionada
        dibujarResaltados(g2d);
    }

    private Color colorHabilidad(String clave, int c, int f) {
        if (clave.equals("lanza")) {
            return new Color(150, 50, 200, 190);
        } else if (clave.equals("invocarZombie")) {
            return new Color(68, 180, 57, 190);
        } else if (clave.equals("ordenarZombie")) {
            Piezas objetivo = getPieza(c, f);

            if (objetivo instanceof Zombie) {
                // amarillo si es un zombie propio (se va a habilitar)
                return new Color(230, 190, 30, 190);
            } else {
                // rojo si es un enemigo lejano (se va a atacar)
                return new Color(200, 40, 40, 190);
            }
        }
        return new Color(120, 120, 120, 190);
    }

    private void dibujarResaltados(Graphics2D g2d) {
        // La pieza que habilito la ruleta se marca con borde amarillo
        if (piezaPermitida != null) {
            dibujarBorde(g2d, piezaPermitida);
        }

        // La pieza que el jugador tiene clickeada tambien se marca (si es otra)
        if (piezaElegida != null && piezaElegida != piezaPermitida) {
            dibujarBorde(g2d, piezaElegida);
        }
    }

    private void dibujarBorde(Graphics2D g2d, Piezas pieza) {
        int columna = pieza.getCol();
        int fila = pieza.getFil();

        g2d.setColor(Color.YELLOW);
        g2d.setStroke(new BasicStroke(6));
        g2d.drawRect(columna * tCasillas + 3, fila * tCasillas + 3, tCasillas - 6, tCasillas - 6);
    }
}
