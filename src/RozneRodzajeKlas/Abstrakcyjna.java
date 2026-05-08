package RozneRodzajeKlas;

public abstract class Abstrakcyjna {
    private int a,b;
    private  int aADDb(){
        return a+b;
    }

    public static void main(String[] args) {
        //Abstrakcyjna x=new Abstrakcyjna(); ->nie można stworzyć obiektu  klasy abstrakcyjnej
        //Abstrakcyjna x=new Abstrakcyjna(){...}; ->nie róbcie tak raczej, bo się pogubicie
    }

}
