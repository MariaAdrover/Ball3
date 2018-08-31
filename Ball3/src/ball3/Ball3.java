/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ball3;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author miaad
 */
public class Ball3 extends JFrame implements Runnable, ActionListener {

    Container contenedor;
    GridBagConstraints grid;
    Tablero tablero;
    JButton botonStart;
    JButton botonReset;
    JButton botonPause;
    JButton botonDatos;
    JLabel labelX;
    JLabel labelY;
    JLabel labelVx;
    JLabel labelVy;
    JLabel labelAx;
    JLabel labelAy;
    JTextField pelotaX;
    JTextField pelotaY;
    JTextField pelotaVx;
    JTextField pelotaVy;
    JTextField pelotaAx;
    JTextField pelotaAy;
    boolean botonDatosActivado;// para intro coordenadas
    boolean isOn; // encender aplicacion
    private volatile boolean runningAnimation;

    public Ball3() {
        super("Ball3 - MOVIMIENTO RECTILINEO");
        this.setBackground(Color.white);

        this.contenedor = this.getContentPane();
        this.contenedor.setLayout(new GridBagLayout());
        this.grid = new GridBagConstraints();

        this.tablero = new Tablero(this, 1200, 1000);
        this.setGridConstraints(0, 0, 1, 10, 1, 1, 1, 0);
        this.contenedor.add(tablero, grid);

        this.crearBotones();
        this.crearPanelDatos();

        this.setEstadoJTextField(false);
        this.botonDatosActivado = false;
        this.centrarPelota();

        this.pack();
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.isOn = true;
        this.runningAnimation = false;

        Thread tableroThread = new Thread(this.tablero);
        Thread simulacion = new Thread(this);
        simulacion.start();
        tableroThread.start();
        this.setVisible(true);
    }

    private void actualizarValoresJLabel() {
        this.labelX.setText("x --> " + this.tablero.getPelota().getX());
        this.labelY.setText("y --> " + this.tablero.getPelota().getY());
        this.labelVx.setText("vX --> " + this.tablero.getPelota().getVx());
        this.labelVy.setText("vY --> " + this.tablero.getPelota().getVy());
        this.labelAx.setText("aX --> " + this.tablero.getPelota().getAx());
        this.labelAy.setText("aY --> " + this.tablero.getPelota().getAy());
    }
    
    private void centrarPelota() {
        this.pelotaX.setText(Integer.toString((this.tablero.getAncho() / 2) - (this.tablero.getPelota().getAncho() / 2)));
        this.pelotaY.setText(Integer.toString((this.tablero.getAlto() / 2) - (this.tablero.getPelota().getAlto() / 2)));
        this.pelotaVx.setText("0");
        this.pelotaVy.setText("0");
        this.pelotaAx.setText("0");
        this.pelotaAy.setText("0");
        
        this.setDatosPelota();
    }
    
    private void crearBotones() {
        this.botonStart = new JButton("START");
        this.botonStart.setFont(new Font("Courier New", Font.BOLD, 20));
        this.botonStart.setPreferredSize(new Dimension(800, 30)); // revisar
        this.botonStart.addActionListener(this);
        this.setGridConstraints(1, 0, 1, 1, 0, 0.1, 1, 0);
        contenedor.add(botonStart, grid);

        this.botonReset = new JButton("RESET");
        this.botonReset.setFont(new Font("Courier New", Font.BOLD, 20));
        this.botonReset.addActionListener(this);
        this.setGridConstraints(1, 6, 1, 1, 0, 0.1, 1, 0);
        contenedor.add(botonReset, grid);

        this.botonPause = new JButton("PAUSE");
        this.botonPause.setFont(new Font("Courier New", Font.BOLD, 20));
        this.botonPause.addActionListener(this);
        this.botonPause.setEnabled(false);
        this.setGridConstraints(1, 7, 1, 1, 0, 0.1, 1, 0);
        contenedor.add(botonPause, grid);
    }

    private void crearPanelDatos() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        // Panel IZQUIERDO
        JPanel panel1 = new JPanel();
        panel1.setBorder(BorderFactory.createTitledBorder("VALORES"));
        panel1.setLayout(new GridBagLayout());

        this.labelX = new JLabel("x --> " + this.tablero.getPelota().getX());
        this.labelX.setFont(new Font("Courier New", Font.BOLD, 22));
        this.setGridConstraints(0, 0, 1, 1, 0, 0.2, 1, 0);
        panel1.add(labelX, grid);

        this.labelY = new JLabel("y --> " + this.tablero.getPelota().getY());
        this.labelY.setFont(new Font("Courier New", Font.BOLD, 22));
        this.setGridConstraints(0, 1, 1, 1, 0, 0.2, 1, 0);
        panel1.add(labelY, grid);

        this.labelVx = new JLabel("vX --> " + this.tablero.getPelota().getVx());
        this.labelVx.setFont(new Font("Courier New", Font.BOLD, 22));
        this.setGridConstraints(0, 2, 1, 1, 0, 0.2, 1, 0);
        panel1.add(labelVx, grid);

        this.labelVy = new JLabel("vY --> " + this.tablero.getPelota().getVy());
        this.labelVy.setFont(new Font("Courier New", Font.BOLD, 22));
        this.setGridConstraints(0, 3, 1, 1, 0, 0.2, 1, 0);
        panel1.add(labelVy, grid);

        this.labelAx = new JLabel("aX --> " + this.tablero.getPelota().getAx());
        this.labelAx.setFont(new Font("Courier New", Font.BOLD, 22));
        this.setGridConstraints(0, 4, 1, 1, 0, 0.2, 1, 0);
        panel1.add(labelAx, grid);

        this.labelAy = new JLabel("aY --> " + this.tablero.getPelota().getAy());
        this.labelAy.setFont(new Font("Courier New", Font.BOLD, 22));
        this.setGridConstraints(0, 5, 1, 1, 0, 0.2, 1, 0);
        panel1.add(labelAy, grid);

        this.setGridConstraints(0, 0, 1, 5, 0.4, 1, 1, 0);
        panel.add(panel1, grid);

        // Panel DERECHO
        JPanel panel2 = new JPanel();
        panel2.setBorder(BorderFactory.createTitledBorder("DATOS INICIALES"));
        panel2.setLayout(new GridBagLayout());

        this.botonDatos = new JButton("SET INITIAL DATA");
        this.botonDatos.setFont(new Font("Courier New", Font.BOLD, 22));
        this.botonDatos.addActionListener(this);
        this.setGridConstraints(0, 0, 2, 1, 0, 0.2, 0, 0);
        panel2.add(this.botonDatos, grid);

        JLabel ballX = new JLabel("x --> ");
        ballX.setFont(new Font("Courier New", Font.BOLD, 22));
        this.setGridConstraints(0, 1, 1, 1, 0, 0.2, 0, 0);
        panel2.add(ballX, grid);

        JLabel ballY = new JLabel("y --> ");
        ballY.setFont(new Font("Courier New", Font.BOLD, 22));
        this.setGridConstraints(0, 2, 1, 1, 0, 0.2, 0, 0);
        panel2.add(ballY, grid);

        JLabel ballVx = new JLabel("vX --> ");
        ballVx.setFont(new Font("Courier New", Font.BOLD, 22));
        this.setGridConstraints(0, 3, 1, 1, 0, 0.2, 0, 0);
        panel2.add(ballVx, grid);

        JLabel ballVy = new JLabel("vY --> ");
        ballVy.setFont(new Font("Courier New", Font.BOLD, 22));
        this.setGridConstraints(0, 4, 1, 1, 0, 0.2, 0, 0);
        panel2.add(ballVy, grid);

        JLabel ballAx = new JLabel("aX --> ");
        ballAx.setFont(new Font("Courier New", Font.BOLD, 22));
        this.setGridConstraints(0, 5, 1, 1, 0, 0.2, 0, 0);
        panel2.add(ballAx, grid);

        JLabel ballAy = new JLabel("aY --> ");
        ballAy.setFont(new Font("Courier New", Font.BOLD, 22));
        this.setGridConstraints(0, 6, 1, 1, 0, 0.2, 0, 0);
        panel2.add(ballAy, grid);

        this.pelotaX = new JTextField("");
        this.pelotaX.setFont(new Font("Courier New", Font.BOLD, 30));
        //this.pelotaX.setEnabled(false);
        this.setGridConstraints(1, 1, 1, 1, 0, 0.2, 0, 0);
        panel2.add(this.pelotaX, grid);

        this.pelotaY = new JTextField("");
        this.pelotaY.setFont(new Font("Courier New", Font.BOLD, 30));
        //this.pelotaY.setEnabled(false);
        this.setGridConstraints(1, 2, 1, 1, 0, 0.2, 0, 0);
        panel2.add(this.pelotaY, grid);

        this.pelotaVx = new JTextField("");
        this.pelotaVx.setFont(new Font("Courier New", Font.BOLD, 30));
        //this.pelotaVx.setEnabled(false);
        this.setGridConstraints(1, 3, 1, 1, 0, 0.2, 0, 0);
        panel2.add(this.pelotaVx, grid);

        this.pelotaVy = new JTextField("");
        this.pelotaVy.setFont(new Font("Courier New", Font.BOLD, 30));
        //this.pelotaVy.setEnabled(false);
        this.setGridConstraints(1, 4, 1, 1, 0, 0.2, 0, 0);
        panel2.add(this.pelotaVy, grid);

        this.pelotaAx = new JTextField("");
        this.pelotaAx.setFont(new Font("Courier New", Font.BOLD, 30));
        //this.pelotaAx.setEnabled(false);
        this.setGridConstraints(1, 5, 1, 1, 0, 0.2, 0, 0);
        panel2.add(this.pelotaAx, grid);

        this.pelotaAy = new JTextField("");
        this.pelotaAy.setFont(new Font("Courier New", Font.BOLD, 30));
        //this.pelotaAy.setEnabled(false);
        this.setGridConstraints(1, 6, 1, 1, 0, 0.2, 0, 0);
        panel2.add(this.pelotaAy, grid);

        this.setGridConstraints(1, 0, 1, 2, 0.6, 1, 1, 0);
        panel.add(panel2, grid);

        // Añadir al frame
        this.setGridConstraints(1, 1, 1, 5, 0, 0.5, 1, 0);
        this.contenedor.add(panel, grid);
    }

    private void pulsarBotonDatos() {
        if (this.botonDatosActivado) {
            this.botonDatos.setText("SET INITIAL DATA");
            this.botonStart.setEnabled(true);
            this.setEstadoJTextField(false);
            this.setDatosPelota();
        } else {
            this.botonDatos.setText("OK");
            this.botonStart.setEnabled(false);
            this.setEstadoJTextField(true);
        }
    }

    private void pulsarBotonPausa() {
        if (this.runningAnimation) {
            this.botonPause.setText("RESUME");
            this.runningAnimation = false;
        } else {
            this.botonPause.setText("PAUSE");
            this.runningAnimation = true;            
        }
    }

    private void reset() {
        this.runningAnimation = false;
        this.botonStart.setEnabled(true);
        this.botonPause.setEnabled(false);
        this.switchUserButton();
        this.centrarPelota();
        this.repaint();
        this.tablero.repaint();
    }

    private void setDatosPelota() {
        this.tablero.getPelota().setX(Integer.parseInt(this.pelotaX.getText()));
        this.tablero.getPelota().setY(Integer.parseInt(this.pelotaY.getText()));
        this.tablero.getPelota().setVx(Double.parseDouble(this.pelotaVx.getText()));
        this.tablero.getPelota().setVy(Double.parseDouble(this.pelotaVy.getText()));
        this.tablero.getPelota().setAx(Double.parseDouble(this.pelotaAx.getText()));
        this.tablero.getPelota().setAy(Double.parseDouble(this.pelotaAy.getText()));
    }

    private void setEstadoJTextField(boolean estado) {
        this.pelotaX.setEnabled(estado);
        this.pelotaY.setEnabled(estado);
        this.pelotaVx.setEnabled(estado);
        this.pelotaVy.setEnabled(estado);
        this.pelotaAx.setEnabled(estado);
        this.pelotaAy.setEnabled(estado);

    }

    // NONE --> 0
    // BOTH --> 1
    // HORIZONTAL --> 2
    // VERTICAL --> 3    
    private void setGridConstraints(int x, int y, int width,
            int height, double weightX, double weightY, int fill, int inset) {
        this.grid.gridx = x;
        this.grid.gridy = y;
        this.grid.gridwidth = width;
        this.grid.gridheight = height;
        this.grid.weightx = weightX;
        this.grid.weighty = weightY;
        this.grid.fill = fill;
        // diseny.insets = this.setInset(inset);
        //this.grid.anchor = GridBagConstraints.WEST;
    }

    private void startAnimation() {
        this.runningAnimation = true;
        this.botonPause.setEnabled(true);
        this.botonStart.setEnabled(false);
    }

    private void switchUserButton() {
        this.botonDatosActivado = !(this.botonDatosActivado);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.botonStart) {
            this.startAnimation();
        } else if (e.getSource() == this.botonReset) { // boton 
            this.reset();
            this.repaint();
        } else if (e.getSource() == this.botonPause) {
            this.pulsarBotonPausa();
        } else if (e.getSource() == this.botonDatos) {
            this.pulsarBotonDatos();
            this.tablero.repaint();// >>>>>>>>>>>>QUITAR?
            this.switchUserButton();
        }
    }

    @Override
    public void run() {
        while (this.isOn) {
            if (this.runningAnimation) {
                this.tablero.getPelota().mover();
                this.actualizarValoresJLabel();
                this.repaint();
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
            }
        }
    }

    public boolean isRunningAnimation() {
        return runningAnimation;
    }

    public void setRunningAnimation(boolean runningAnimation) {
        this.runningAnimation = runningAnimation;
    }

    public static void main(String[] args) {
        Ball3 ball2 = new Ball3();
    }

}
