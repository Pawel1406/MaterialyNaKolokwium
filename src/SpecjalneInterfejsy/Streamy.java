package SpecjalneInterfejsy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Streamy {
    public static void main(String[] args) {
        /*--------------------------Różne rodzaje stream--------------------------*/
        List<Komparator> list=new ArrayList<>();
        list.add(new Komparator(1,2,3));
        list.add(new Komparator(2,3,4));
        list.add(new Komparator(3,4,5));
        list.add(new Komparator(5,6,7));

        /*--------------------------Wykorzystujemy w sorted comparator z interfejsu Comparable --------------------------*/
        System.out.println("Wykorzystujemy w sorted comparator z interfejsu Comparable");
        list.stream().sorted().forEach(System.out::println);
        System.out.println("\n");

        /*--------------------------Wykorzystujemy w sorted comparator z interfejsu Comparable,ale odwracamy --------------------------*/
        System.out.println("Wykorzystujemy w sorted comparator z interfejsu Comparable,ale odwracamy");
        list.stream().sorted(Comparator.reverseOrder()).forEach(System.out::println);
        System.out.println("\n");

        /*--------------------------Wykorzystujemy w sorted lambdę --------------------------*/
        System.out.println("Wykorzystujemy w sorted lambdę");
        list.stream().sorted((a,b)->Integer.compare(a.getX(),b.getX())).forEach(System.out::println);
        System.out.println("\n");

        /*--------------------------Wykorzystujemy w sorted lambdę, ale odwrócona kolejność --------------------------*/
        System.out.println("Wykorzystujemy w sorted lambdę, ale odwrócona kolejność");
        list.stream().sorted((a,b)->Integer.compare(b.getX(),a.getX())).forEach(System.out::println);
        System.out.println("\n");

        /*--------------------------Wykorzystujemy w sorted obiekt Comparator i jego metodę .comparing() --------------------------*/
        //Jako argument podajemy nazwę klasy::metodaZwracająca zmienną na podstawie której porównać
        //operator :: to operator referencji do metody
        System.out.println("Wykorzystujemy w sorted obiekt Comparator i jego metodę .comparing()");
        list.stream().sorted(Comparator.comparing(Komparator::getX)).forEach(System.out::println);

        /*--------------------------Teraz zapisujemy,ale w sposób tworzący listę niemodyfikowalną --------------------------*/
        System.out.println("Teraz zapisujemy,ale w sposób tworzący listę niemodyfikowalną");
        List<Komparator> listNieModyfikowalna=list.stream().sorted().limit(2).toList();
        listNieModyfikowalna.forEach(System.out::println);
        //listNieModyfikowalna.add(new Komparator(1,2,3)); <- to rzuci wyjątek, możecie zobaczyć jak odkomentujecie
        System.out.println("\n");

        /*--------------------------Teraz zapisujemy,ale w sposób tworzący listę modyfikowalną --------------------------*/
        System.out.println("Teraz zapisujemy,ale w sposób tworzący listę modyfikowalną");
        List<Komparator> listModyfikowalna=list.stream().sorted().limit(2).collect(Collectors.toList());
        listModyfikowalna.forEach(System.out::println);
        System.out.println("\n");
        listModyfikowalna.add(new Komparator(1,2,3));
        listModyfikowalna.forEach(System.out::println);

        /*--------------------------Inne metody --------------------------*/
        //.max() -> zwraca największy obiekt na podstawie jakiegoś komparatora
        //.count() -> zwraca ile mamy obiektów
        //.anyMatch() ->Zwraca true/false czy jest obiekt pasujący do wzorca
        System.out.println(".anyMatch() ->Zwraca true/false czy jest obiekt pasujący do wzorca");
        System.out.println(list.stream().anyMatch(x -> x.getX() < 10));
        System.out.println("\n");
        //.allMatch()->Zwraca true/false czy wszystko pasuje do wzorca
        //.map()->mapuje się obiekt na coś innego
        System.out.println(".map()->mapuje się obiekt na coś innego");
        list.stream().map(Komparator::getX).forEach(System.out::println);
        System.out.println("\n");
        list.stream().map(x->"x "+x.getX()+" z "+x.getZ()).forEach(System.out::println);
        System.out.println("\n");
        System.out.println(list.stream().mapToInt(Komparator::getZ).sum());
    }
}
