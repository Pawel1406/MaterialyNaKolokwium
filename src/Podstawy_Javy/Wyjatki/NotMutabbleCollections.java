package Podstawy_Javy.Wyjatki;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NotMutabbleCollections {
    /*--------------------------NotMuttableCollections--------------------------*/
    public void chechNumberIsTwoDigitNumber(int number) {
        if (number < 9 ||number>100) {
            throw new IllegalArgumentException("Number is out of range");
        }
    }

    public void returnListOfTwoDigitNumbers(List<Integer> lista) {

        for (Integer i : lista) {
            try {
                chechNumberIsTwoDigitNumber(i);
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
                System.out.println("Czy mimo to chcesz dodać:[Y/N]");
                Scanner sc = new Scanner(System.in);
                sc.nextLine();
                if (sc.equals("Y")){
                    lista.add(i);
                }
                continue;
            }
            lista.add(i);
        }
    }

    static void main() {
        NotMutabbleCollections obj = new NotMutabbleCollections();
        //teraz rzuca wyjątek, że do niemodyfikowalnej listy próbujemy coś dodać
        //Jak przyjmujemy listę w parametrze to jest ona niemodyfikowalna

        System.out.println("1: Tworzymy listę z użyciem List.of\n2: Tworzymy ArrayListę\n3: Jak się wyświetlą wyjątki, bez ich obsłużenia\nKliknij w konsolę i wybierz jedną z tych opcji");
        Scanner sc=new Scanner(System.in);
        String str=sc.nextLine();
        switch (str){
            case "1":
                try {
                    obj.returnListOfTwoDigitNumbers(List.of(15, 16, 17, 18, 19, 20, 21, 22, 23, 3, 190));
                }
                catch (Throwable e) {
                    System.err.println("Tutaj nic nie działa, wywala wyjatki domyślnie wyjatki");
                }
            break;
            case "2":
                try {
                    ArrayList <Integer> lista = new ArrayList<>();
                    lista.add(10);
                    lista.add(20);
                    lista.add(3);
                    lista.add(40);
                    obj.returnListOfTwoDigitNumbers(lista);
                }
                catch (Throwable e) {
                    System.err.println("Tutaj nic nie działa, wywala wyjatki domyślnie wyjatki");
                }

            break;
            case "3":
                obj.returnListOfTwoDigitNumbers(List.of(15, 16, 17, 18, 19, 20, 21, 22, 23, 3, 190));
                break;
            //Trzecia opcja, żeby pokazać jak to wygląda jak tego nie obsłużycie
            default:
                System.out.println(str);
                break;
        }




    }

}
