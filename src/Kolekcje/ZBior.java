package Kolekcje;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ZBior {
    Set<Integer> mapka = new HashSet<>();
    public void MetodyNaZbiorze(){
        /*--------------------------Metody na mapie--------------------------*/
        /*--------------------------.add(), czyli dodawanie--------------------------*/
        mapka.add(1);
        mapka.add(2);

        /*--------------------------.add(), tego samego elementu nie rzuca wyjątku, tylko nie dodaje elementu--------------------------*/
        mapka.add(1);
        System.out.println(mapka);

        /*--------------------------.addAll(), dodaje listę--------------------------*/
        mapka.addAll(List.of(3,4,5,6,7));

        /*--------------------------.contains(), sprawdza czy zawiera--------------------------*/
        //musi być nadpisana w klasie metoda .equals(), bo domyślnie porównuje po adresach
        System.out.println(mapka.contains(1));

        /*--------------------------.containsAll(), sprawdza czy zawiera listę--------------------------*/
        //musi być nadpisana w klasie metoda .equals(), bo domyślnie porównuje po adresach
        System.out.println(mapka.containsAll(List.of(3,4,5,6,7)));

        /*--------------------------.containsAll(), sprawdza czy zawiera listę--------------------------*/
        //musi być nadpisana w klasie metoda .equals(), bo domyślnie porównuje po adresach
    }

    public static void main() {
        ZBior zbior = new ZBior();
        zbior.MetodyNaZbiorze();
        for (Integer i : zbior.mapka) {
            System.out.println(i);
        }
    }
}
