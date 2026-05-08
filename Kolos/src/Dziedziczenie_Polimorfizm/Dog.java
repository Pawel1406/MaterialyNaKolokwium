package Dziedziczenie_Polimorfizm;

public class Dog extends Animal{
    public Dog(int id, int age) {
        super(id, age);
    }

    @Override
    public String dajGlos() {
        return "Hau";
    }
}
