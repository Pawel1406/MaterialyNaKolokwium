package RozbudowaneStrukturyJava.SerwerJednowatkowe;

public abstract class Vehicle {
    private int id;

    public Vehicle(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
