package RozbudowaneStrukturyJava.SerwerJednowatkowe;

public class Scooter extends Vehicle {
    private int bateryLevel;

    public Scooter(int id,int bateryLevel) {
        super(id);
        this.bateryLevel = bateryLevel;
    }

    public int getBateryLevel() {
        return bateryLevel;
    }

    public void setBateryLevel(int bateryLevel) {
        this.bateryLevel = bateryLevel;
    }
}
