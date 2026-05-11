package Kolekcje;

import Pliki.Person;
import Pliki.ReaderFromFIle;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Lista {
    public List<Person> lists=new ArrayList<>();

    public static List<Person> sorting(List <Person> lists){
        return lists.stream().sorted(Comparator.comparing(Person::getPoints)).toList();
    }
    public static List<Person> sortingModificableList(List <Person> lists){
        return lists.stream().sorted(Comparator.comparing(Person::getPoints)).collect(Collectors.toList());
    }
    public static List<Person> sortingModificableListArrayList(List <Person> lists){
        //jak chcemy mieć konkretnie ARRAYLIST
        //ten operator :: to nic innego jak krócej zapisana lambda
        //to referencja do metod w klasie w tym wypadku do konstruktora ArrayList
        return lists.stream().sorted(Comparator.comparing(Person::getPoints)).collect(Collectors.toCollection(ArrayList::new));

    }



    public void methodsList(){
        /*--------------------------Metody na listach--------------------------*/
        /*--------------------------.add()--------------------------*/
        //dodawanie do listy
        lists.add(new Person("x",10));

        /*--------------------------.addAll()--------------------------*/
        //można też dodać kilka obiektów naraz
        lists.addAll(List.of(new Person("y",20),new Person("d",30),new Person("z",40)));

        /*--------------------------.contains()--------------------------*/
        //czy zawiera dany obiekt
        //UWAGA:Jeśli nie mamy nadpisanej metody equals to porównuje obiekty po adresach w pamięci, nie polach
        System.out.println("contains: "+lists.contains(new Person("x",10)));

        /*--------------------------.containsAll()--------------------------*/
        //czy zawiera listę obiektów
        //pamiętajcie sprawdzamy dłuższą listę, czy zawiera krótszą, nie na odwrót!!!
        System.out.println("containsAll: "+lists.containsAll(List.of(new Person("y",20),new Person("d",30),new Person("z",40))));

        /*--------------------------.isEmpty()--------------------------*/
        //czy jest pusta
        System.out.println("empty: "+lists.isEmpty());

        /*--------------------------.Set()--------------------------*/
        //modyfikacja elementu
        //dajemy index, nowy element
        lists.set(1,new Person("x",10));

        /*--------------------------.clear()--------------------------*/
        //wyczyść
        this.lists.clear();

        /*--------------------------BARDZO WAŻNA UWAGA --------------------------*/
        //1.
        // Jeśli stosujemy stream na przykład do posortowania listy to metoda .toList()
        // spowoduje to utworzenie listy nie modyfikowalnej,
        //a więc wywołanie metody .add() spowoduje, że program się wykrzaczy
        //zamiast .toList() robimy starszą .collect(Collectors.toList())

        //2.
        //Stosujemy raczej wrapery typów prostych, czyli Integer zamiast int, Double zamiast double w typie generycznym.
        // Tam powinna być dana klasa, nie, zwykła zmienna jak int.
        //int->Integer
        //double->Double
        //float->FLoat
        //char->Character
        //byte->Byte
        //boolean->Boolean
        //short->Short
        //long->Long

    }

    public static void main(String[] args) throws FileNotFoundException {
        ReaderFromFIle readerFromFile=new ReaderFromFIle();
        readerFromFile.fromCsv("src/Pliki/plik.txt");


        Lista list=new Lista();
        Lista list1=new Lista();
        list.lists=Lista.sorting(readerFromFile.getLista());
        list1.lists=readerFromFile.getLista();

        System.out.println(list.lists);
        list1.methodsList();

        /*--------------------------Iteracja po listach--------------------------*/
        System.out.println("\n");
        for (Person person : list.lists) {
            System.out.println(person);
        }
        System.out.println("\n");
        for (int i = 0; i < list.lists.size(); i++) {
            System.out.println(list.lists.get(i));
        }





    }

}


