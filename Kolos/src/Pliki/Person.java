package Pliki;

import java.util.Objects;

public class Person {
    private String name;
    private int points;

    public Person(String name,int points) {
        this.points = points;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return name+" "+points;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return points == person.points && Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, points);
    }
}
