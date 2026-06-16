package Podstawy_Javy.Dziedziczenie_Polimorfizm;

public class Husky extends Dog{
    public Husky(int id, int age) {
        super(id, age);
    }

    @Override
    public String dajGlos() {
        return super.dajGlos()+" hau";
    }
}
