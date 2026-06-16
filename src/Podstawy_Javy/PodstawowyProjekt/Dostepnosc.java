package Podstawy_Javy.PodstawowyProjekt;

public class Dostepnosc {
    public int a;
    private int b;
    protected int c;
    int d;//package, nie piszemy jawnie
    //zmienne statyczne mają wartość dla całej klasy, nie tylko dla konkretnego obiektu
    //przy ich pomocy możemy zapisywać informację, czy na przykład wywołaliśmy już jakąś metodę, niezależnie w jakim obiekcie tej klasy
    public static int e;

    //zmienne typu final są inicjowane albo tutaj, albo w konstruktorze
    //nie mogą byc nidzie indziej zmieniane
    public final int f;

    public Dostepnosc(int a, int b, int c, int d,int e,int f) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e=e;
        this.f=f;
    }

    /*--------------------------Gettery i Settery--------------------------*/

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    public static void main(String[] args) {
        Dostepnosc dostepnosc=new Dostepnosc(10,20,30,40,50,60);
        System.out.println(dostepnosc.a);
        System.out.println(dostepnosc.b);
        System.out.println(dostepnosc.c);
        System.out.println(dostepnosc.d);
        //nie ma problemu jesteśmy w tej samej klasie, patrz Podstawy_Javy.Main.main
    }

}
