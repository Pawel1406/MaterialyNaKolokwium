package Podstawy_Javy.KlasyStandardowe;

public class IntegerNaInt {
    public static void main() {
        int a=10;
        Integer i=new Integer(10);

        System.out.println(a);
        System.out.println(i);

        System.out.println(i.equals(a));
        System.out.println(i==a);
        System.out.println("\n");

        Integer b=new Integer(i);
        System.out.println(i.equals(b));
        System.out.println(i==b);
        //Mamy dwa różne obiekty, więc == porównując po adresach zwróci fałsz, bo to inne adresy w pamięci
        //equals porównuje po wartościach, nie adresach
    }
}
