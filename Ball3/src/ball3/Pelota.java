package ball3;

public class Pelota {

    private Tablero tablero;
    private volatile int x;
    private volatile int y;
    private volatile double vX;
    private volatile double vY;
    private volatile double aX;
    private volatile double aY;
    private int ancho;
    private int alto;

    public Pelota(Tablero t, int ancho, int alto) {
        this.tablero = t;
        this.ancho = ancho;
        this.alto = alto;
    }

    public void mover() {
        if (this.x + this.vX + this.aX < 0 || 
                this.x + this.vX + this.aX > this.tablero.getAncho() - this.getAncho()) {
            this.vX = -(this.vX);
            this.aX = -(this.aX);
        }

        if (this.y + this.vY + this.aY < 0 || 
                this.y + this.vY + this.aY >  this.tablero.getAlto() - this.getAlto()) {
            this.vY = -(this.vY);
            this.aY = -(this.aY);
        }
        
        this.vX += this.aX;
        this.vY += this.aY;       
        
        this.x += this.vX;        
        this.y += this.vY;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getAncho() {
        return this.ancho;
    }

    public int getAlto() {
        return this.alto;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getVx() {
        return vX;
    }

    public void setVx(double vX) {
        this.vX = vX;
    }

    public double getVy() {
        return vY;
    }

    public void setVy(double vY) {
        this.vY = vY;
    }

    public double getAx() {
        return aX;
    }

    public void setAx(double aX) {
        this.aX = aX;
    }

    public double getAy() {
        return aY;
    }

    public void setAy(double aY) {
        this.aY = aY;
    }
}
