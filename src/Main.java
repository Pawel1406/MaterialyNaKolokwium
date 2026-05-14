import PodstawowyProjekt.Dostepnosc;


public class Main {
    public static void main(String[] args) {

        /*--------------------------DOSTĘPNOŚĆ--------------------------*/
        Dostepnosc dostepnosc = new Dostepnosc(10, 20, 30, 40, 50, 60);
        System.out.println(dostepnosc.a);//Można, bo jest public
        //System.out.println(dostepnosc.b); Nie można, bo jest private, czyli tylko wewnątrz klasy
        //System.out.println(dostepnosc.c); Nie można, bo jest protected, czyli tylko wewnątrz klasy i w klasach dziedziczących
        //System.out.println(dostepnosc.d); Nie można, bo jest package(nie pisze się tego), czyli tylko wewnątrz pakietu w którym jest dana klasa
        System.out.println(dostepnosc.e);
        System.out.println(dostepnosc.f);
        System.out.println("\n");
        System.out.println("\n");
    }

}