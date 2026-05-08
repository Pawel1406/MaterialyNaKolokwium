package PodstawowyProjekt;

import java.util.ArrayList;
import java.util.List;

public class KopiaPlytkaGleboka {


    static void main() {
        List<Integer> listaOryginalna = new ArrayList(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        List<Integer> listaTaSama = listaOryginalna;//Wskazuje na ten sam obiekt w pamięci
        List<Integer> kopiaPlytka = new ArrayList<>(listaOryginalna);
        List<Integer> kopiaGleboka = new ArrayList<>();
        for (Integer i : listaOryginalna) {
            kopiaGleboka.add(i);
        }
    }


}
