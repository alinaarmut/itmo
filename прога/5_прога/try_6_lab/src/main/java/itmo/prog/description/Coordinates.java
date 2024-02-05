package itmo.prog.description;
/**
 * Класс Coordinates
 */
public class Coordinates  {
        private Integer x; //Поле не может быть null
        private double y;

    public Coordinates(Integer x,double y) {
        this.x = x;
        this.y = y;
    }
    public Coordinates() {

    }

    public Integer getX() {

        return x;
    }

    public double getY() {
        return y;
    }


    public void setX(Integer x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

