import java.util.List;

/**
 * Created by lacke on 10/19/2017.
 */
public class City {
    private int x, y, destx, desty;
    private double StraightDistance; // The destinations are important for the heuristics
    private String name;
    private List<String> connections;



    public City(int x, int y, int destx, int desty, String name, List<String> connections) {
        this.x = x;
        this.y = y;
        this.destx = destx;
        this.desty = desty;
        this.name = name;
        this.StraightDistance = Math.sqrt(Math.pow(x - destx, 2) + Math.pow(y - desty, 2));
        this.connections = connections;

    }

    public List<String> getConnections() {
        return connections;
    }

    public int getDestx() {
        return destx;
    }

    public double getStraightDistance() {
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
