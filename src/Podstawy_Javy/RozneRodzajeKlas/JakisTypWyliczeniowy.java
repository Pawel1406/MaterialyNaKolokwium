package Podstawy_Javy.RozneRodzajeKlas;

public enum JakisTypWyliczeniowy {
    Jablko,Gruszka,Pomarncza;

    public static void main(String[] args) {
        JakisTypWyliczeniowy typ=Jablko;
        typ=Gruszka;
        typ=Pomarncza;
        System.out.println(typ);
    }
}


