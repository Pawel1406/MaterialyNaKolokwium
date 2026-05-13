package Dziedziczenie_Polimorfizm;

public abstract class Animal {
    public int age, id;

    public Animal(int id, int age) {
        this.id = id;
        this.age = age;
    }
    public Animal(Animal animal)
    {
        this.id=animal.id;
        this.age=animal.age;
    }

    public String  dajGlos(){
        return "Nothing";
    }

    public static void main() {
        /*--------------------------POLIMORFIZM--------------------------*/
        Cat cat1=new Cat(10,1000009);

        Animal animal=new Cat(cat1);
        System.out.println(animal.dajGlos());

        animal=new Dog(10,1000000);
        System.out.println(animal.dajGlos());

        animal=new Husky(10,1000000);
        System.out.println(animal.dajGlos());
        //Choć Animal z racji, że jest abstract nie może mieć swojego obiektu(instancji) w kodzie, ale może stawać się klasami nieabstrakcyjnymi, które dziedziczą po Animal
        //Nazywamy to polimorfizmem
        //Jest to mega ważne szczególnie w listach to wychodzi

    }
}
