package PodstawowyProjekt;

public class Dostepnosc {
    public int a;
    private int b;
    protected int c;
    int d;
    public static int e;
    public final int f;

    public Dostepnosc(int a, int b, int c, int d,int e,int f) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e=e;
        this.f=f;
    }

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
        //nie ma problemu jesteśmy w tej samej klasie, patrz Main.main
    }

}
