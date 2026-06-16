package Podstawy_Javy.RozneRodzajeKlas;

public class Wyjatek extends RuntimeException {
    public Wyjatek(String message) {
        super(message);
    }
    /*
    W wyjatku samym w sobie nie definujemy, kiedy ma być rzuczony itp.
     */
}
