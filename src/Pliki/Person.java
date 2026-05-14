package Pliki;

import java.util.Objects;

public class Person implements Comparable<Person>{

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

    //metoda do porównywania obiektów
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return points == person.points && Objects.equals(name, person.name);
    }

    //metoda konieczna jeśli chcemy skorzystać ze struktur tworzonych na podstawie tablic hashujących
    //Nie jest ważne czym one są ważne że ich nazwa zaczyna się od Hash np. HashMap
    //Jeśli nie będzie tego to wyrzuci nam wtedy ClassCastException
    @Override
    public int hashCode() {
        return Objects.hash(name, points);
    }


    @Override
    public int compareTo(Person o) {
        return this.name.compareTo(o.name);
    }


}
