package Podstawy_Javy.Wyjatki;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IllegalArgument {
    /*--------------------------IllegalArgumentException--------------------------*/
    public void chechNumberIsTwoDigitNumber(int number) {
        if (number < 9 ||number>100) {
            throw new IllegalArgumentException("Number is out of range");
        }

    }

    public List<Integer> returnListOfTwoDigitNumbers(List<Integer> lista) {
        List<Integer> toReturn = new ArrayList<>();
        for (Integer i : lista) {
            try {
                chechNumberIsTwoDigitNumber(i);
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
                System.out.println("Czy mimo to chcesz dodać:[Y/N]");
                Scanner sc = new Scanner(System.in);
                sc.nextLine();
                if (sc.equals("Y")){
                    toReturn.add(i);
                }
                continue;
                //Zwróćcie uwagę na to Continue
                //Gdyby go nie było to linijka z dodawanie do listy(34) zawsze się wykona, niezależnie od ilości znaków poszczególnej liczby w liście


            }
            toReturn.add(i);

        }
        return toReturn;
    }

    static void main() {
        IllegalArgument obj = new IllegalArgument();
        List<Integer>lista=obj.returnListOfTwoDigitNumbers(List.of(15,16,17,18,19,20,21,22,23,3,190));
        System.out.println(lista);
    }
}
