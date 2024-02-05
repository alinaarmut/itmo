package itmo.prog.description;
/**
 * Класс Location
 */
public class Location {
    private  double x;
    private   Float y; //Поле не может быть null
    private String name; //Поле не может быть null

    public Location(double x, float y, String name) {
        this.x =x;
        this.y = y;
        this.name = name;
    }
public Location() {

}

    public void setName(String name) {
        this.name = name;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(Float y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public Float getY() {
        return y;
    }


    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                ", name='" + name + '\'' +
                '}';
    }
}
