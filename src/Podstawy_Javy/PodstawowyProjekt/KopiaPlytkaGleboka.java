package Podstawy_Javy.PodstawowyProjekt;

import java.util.ArrayList;
import java.util.List;

public class KopiaPlytkaGleboka {


    static void main() {
        /*--------------------------Kopie na klasach niemodyfikowalnych(Integer, String itp.)--------------------------*/
        List<Integer> listaOryginalna = new ArrayList(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        List<Integer> listaTaSama = listaOryginalna;//Wskazuje na ten sam obiekt w pamięci
        List<Integer> kopiaPlytka = new ArrayList<>(listaOryginalna);
        List<Integer> kopiaGleboka = new ArrayList<>();
        for (Integer i : listaOryginalna) {
            kopiaGleboka.add(new Integer(i));
        }

        kopiaGleboka.add(170);
        listaOryginalna.add(87);
        System.out.println(listaOryginalna);
        System.out.println(listaTaSama);
        System.out.println(kopiaPlytka);
        System.out.println(kopiaGleboka);

        //W tym wypadku nie widać faktycznej różnicy między kopią płytką, a głęboką.
        // Wynika to z niemodyfikowalności kopii płytkiej; zachowuje się tak samo jak kopia głęboka

        System.out.println("\n");

        /*--------------------------Kopie na klasach modyfikowalnych--------------------------*/

        List<Student> originalList = new ArrayList<>();
        originalList.add(new Student("Ania"));
        originalList.add(new Student("Bartek"));

        // 2. KOPIA PŁYTKA (Shallow Copy)
        // Tworzymy nową listę, ale zawiera ona te same obiekty (referencje)
        List<Student> shallowCopy = new ArrayList<>(originalList);

        // 3. KOPIA GŁĘBOKA (Deep Copy) - przy użyciu pętli
        // Tworzymy nową listę i fizycznie tworzymy NOWY obiekt dla każdego elementu
        List<Student> deepCopy = new ArrayList<>();
        for (Student s : originalList) {
            deepCopy.add(new Student(s.name)); // Tworzymy nową instancję Student
        }

        // 4. MODYFIKACJA ORYGINAŁU
        originalList.get(0).name = "Anna (Zmieniona)";

        // 5. PREZENTACJA WYNIKÓW
        System.out.println("Oryginał: " + originalList);

        // Kopia płytka ucierpiała, bo "widzi" ten sam obiekt co oryginał
        System.out.println("Kopia płytka: " + shallowCopy);

        // Kopia głęboka zachowała stare imię, bo ma własny, osobny obiekt
        System.out.println("Kopia głęboka: " + deepCopy);

    }

    //może być klasa w klasie, ale potrzebujemy st
    public static class Student {
        String name;

        Student(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }


}
