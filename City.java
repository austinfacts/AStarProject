import java.util.List;

/**
 * Created by lacke on 10/19/2017.
 */
public class City {
    private int x, y, destx, desty;
    private double StraightDistance; // The destinations are important for the heuristics
    private String name;
    private List<String> connections;



    public City(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.StraightDistance = Double.NaN;
    }

    public void setConnections(List<String> connections) { this.connections = connections;}

    public List<String> getConnections() {
        return connections;
    }

    public int getDestx() {
        return destx;
    }

    public double getStraightDistance() {
        if(StraightDistance == Double.NaN) {
            StraightDistance = Math.sqrt(Math.pow(this.x - this.destx, 2) + Math.pow(this.y - this.desty, 2));
            return StraightDistance;
        } else
            return StraightDistance;
    }

    public void setDestx(int destx) {
        this.destx = destx;
    }

    public int getDesty() {
        return desty;
    }

    public void setDesty(int desty) {
        this.desty = desty;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
