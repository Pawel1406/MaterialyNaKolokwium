package Dziedziczenie_Polimorfizm;

public class Cat extends Animal {
    public Cat(int id, int age) {
        super(id, age);
    }
    public Cat(Animal animal){
        super(animal);
    }

    @Override
    public String dajGlos() {
        return "Miau";
    }
}
