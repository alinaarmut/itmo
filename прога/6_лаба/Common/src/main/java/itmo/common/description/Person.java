package itmo.common.description;

import java.io.Serializable;

public class Person implements Serializable {
    private  String nm; //Поле не может быть null, Строка не может быть пустой
    private  Double weight; //Поле может быть null, Значение поля должно быть больше 0
    private  Color eyeColor; //Поле может быть null
    private  ColorHair hairColor; //Поле не может быть null
    private Location location; //Поле не может быть null

    public Person(String nm,Double weight,Color eyeColor, ColorHair hairColor, Location location) {
        this.nm = nm;
        this.weight = weight;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.location = location;
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public Double getWeight() {
        return weight;
    }

    public String getNm() {
        return nm;
    }

    public Location getLocation() {
        return location;
    }

    public ColorHair getHairColor() {
        return hairColor;
    }

    public Person(){}

    public void setEyeColor(Color eyeColor) {
        this.eyeColor = eyeColor;
    }

    public void setHairColor(ColorHair hairColor) {
        this.hairColor = hairColor;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setNm(String nm) {
        this.nm = nm;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Person{" +
                "nm='" + nm + '\'' +
                ", weight=" + weight +
                ", eyeColor=" + eyeColor +
                ", hairColor=" + hairColor +
                ", location=" + location +
                '}';
    }
}
