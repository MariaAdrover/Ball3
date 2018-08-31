package ball3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

public class Tablero extends JPanel implements Runnable {

    private int ancho;
    private int alto;
    private Pelota pelota;
    private Ball3 simulacion;

    public Tablero(Ball3 simulacion, int ancho, int alto) {
        this.setAncho(ancho);
        this.setAlto(alto);
        this.setPreferredSize(new Dimension(this.ancho, this.alto));
        this.setBackground(Color.black);
        this.setFocusable(true);
        
        this.simulacion = simulacion;
        this.pelota = new Pelota(this, 100, 100);
    }
    
    public void paintComponent(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, this.ancho, this.alto);
        g.setColor(Color.white);
        g.fillOval(this.pelota.getX(), this.pelota.getY(), this.pelota.getAncho(), this.pelota.getAlto());

    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public Pelota getPelota() {
        return pelota;
    }

    @Override
    public void run() {
        while (true) { // sustituir condicion
            if (this.simulacion.isRunningAnimation()) {
                this.repaint();
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
            }
        }
    }
}
