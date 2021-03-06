import java.awt.Graphics;
import java.util.Random;

public class Point {
  private double x, y;
  private double m;
  private Velocity v;

  private int id;

  public Point() {
    setX(0);
    setY(0);
    setM(0);
  }

  public Point(double x, double y, double m) {
    setX(x);
    setY(y);
    setM(m);
  }

  public int getID() {
    return id;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public Velocity getV() {
    return v;
  }

  public double getM() {
    return m;
  }

  public void setID(int id) {
    this.id = id;
  }

  public void setX(double x) {
    this.x = x;
  }

  public void setY(double y) {
    this.y = y;
  }

  public void setV(Velocity v) {
    this.v = v;
  }

  public void setM(double m) {
    this.m = m;
  }
  
  public double distanceTo(Point k) {
    return Math.sqrt(Math.pow(k.getX() - x, 2) + Math.pow(k.getY() - y, 2));
  }

   public boolean closeTo(Point k) {
    double r = Constants.POINT_MASS_RADIUS_RATIO*m;

    return distanceTo(k) <= r * 2;
  }

  public boolean inRange(double a, double b, double dist) {
    return Math.abs(a - b) <= dist;
  }

  public boolean closeToY() {
    double r = Constants.POINT_MASS_RADIUS_RATIO*m;
    
    return inRange(y, r, Constants.POINT_INTERACT_DIST)
      ||   inRange(y, Constants.FRAME_HEIGHT + Constants.FRAME_OFFSET - r, Constants.POINT_INTERACT_DIST);
  }
  
  public boolean closeToX() {
    double r = Constants.POINT_MASS_RADIUS_RATIO*m;
    
    return inRange(x, r, Constants.POINT_INTERACT_DIST)
      ||   inRange(x, Constants.FRAME_WIDTH - r, Constants.POINT_INTERACT_DIST);
  }
  
  public boolean closeToWall() {
     double r = Constants.POINT_MASS_RADIUS_RATIO*m;
    
    return closeToX() || closeToY();
  }

  public boolean sameAs(Point k) {
    return getID() == k.getID();
  }

  public double theta(Point k) {
    Random rand = new Random();

    return rand.nextDouble()*Constants.POINT_DEFLECTION_MAX;
  }

  public Point interact(Point k) {
    v.scale(Math.sqrt(m*m + k.m*k.m + 2*m*k.m*Math.cos(theta(k))) / (m+k.m));
    k.v.scale(2*m*Math.sin(theta(k)/2) / (m+k.m));
    return k;
  }

  public void interactWithWall() {
    if (closeToX()) {
      v.setX(-v.getX());
    }

    else if (closeToY()) {
      v.setY(-v.getY());
    }
  }
  
  // t == milliseconds since last refresh
  public void move(int t) {
    x += v.getX() * t / 1000d;
    y += v.getY() * t / 1000d;
  }

  public void draw(Graphics g) {
    double d = Constants.POINT_MASS_RADIUS_RATIO*m;
    g.fillOval((int) x, (int) y, (int) d, (int) d);
  }

  public void erase(Graphics g) {
    double d = Constants.POINT_MASS_RADIUS_RATIO*m;
    g.fillOval((int) x, (int) y, (int) d, (int) d);
  }

  public String format(double d) {
    return String.format("%.2f", d);
  }

  public String toString() {
    return "P"+id+"("+format(x)+","+format(y)+") <"+format(v.getX())+", "+format(v.getY())+">";
  }

  public void print() {
    System.out.println(toString());
  }
}
