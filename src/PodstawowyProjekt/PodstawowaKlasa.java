package PodstawowyProjekt;

public class PodstawowaKlasa {
    //Pola==atrybuty==zmienne
    private int x;
    private int y;
    public int z=10;
    private static int counter=0;

    //Konstruktory
    public PodstawowaKlasa() {
        counter++;
    }
    public PodstawowaKlasa(int x,int y){
        this.x=x;
        this.y=y;
        counter++;
    }

    public PodstawowaKlasa(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    //Settery(mutatory)
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    //Gettery(akcesory)
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public static int getCounter() {
        return counter;
    }

    /*Metoda wytwórcza klasy.
     To nie to samo co konstruktor*/
    public PodstawowaKlasa create(int x,int y){
        return new PodstawowaKlasa(x,y);
    }

    //Metody, czyli funkcje w klasie
    private int add(){
        return this.x+this.y;
    }
    public int add(int x,int y){
        add();
        return x+y;
    }

    @Override
    public String toString() {
        return "PodstawowaKlasa{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public static void main(String[] args) {
        PodstawowaKlasa p1=new PodstawowaKlasa(10,20);
        PodstawowaKlasa p2=new PodstawowaKlasa(9,20);
        System.out.println(PodstawowaKlasa.getCounter());
        System.out.println(p1);
        System.out.println(p2);
    }
}
/*skróty ułatwiające pisanie
alt+insert ->generate
5.fori->pętla po indeksach
lista.for-> pętla po zmiennych
 */

/*jak mówi o referencjach to w javie oznacza to wprost nazwę zmiennej, nie jakiś wskaźnik itp.
Na przykład: Dodaj do listy referencję na ten obiekt
czyli lista.add(obiekt)*/