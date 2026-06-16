package Podstawy_Javy.Kolekcje;

import Podstawy_Javy.Pliki.Person;

import java.util.HashMap;
import java.util.Map;

public class Mapy {
    Map<String, Integer> mapka=new HashMap<>();

    public void metodyNaMapie(){
        /*--------------------------Metody na mapie--------------------------*/
        /*--------------------------.put(), czyli dodawanie--------------------------*/
        this.mapka.put("ala",12);

        /*--------------------------.get(), czyli pobieranie--------------------------*/
        System.out.println("get(\"ala\"): "+this.mapka.get("ala"));
        System.out.println("get(\"x\") nie ma takiego: "+this.mapka.get("x"));

        /*--------------------------.getOrDefault(), czyli pobieranie--------------------------*/
        System.out.println("getOrDefault(\"x\") nie ma takiego: "+this.mapka.getOrDefault("x",0));

        /*--------------------------.containsKey(), czyli czy zawiera klucz--------------------------*/
        System.out.println("containsKey(\" ala\"): "+this.mapka.containsKey("ala"));

        /*--------------------------.containsValue(), czyli czy zawiera value--------------------------*/
        System.out.println("containsValue(\" 12\"): "+this.mapka.containsValue(12));

        /*--------------------------.clear(), czyli czyszczenie--------------------------*/
        //this.mapka.clear();
        //żeby było widać co robią pętle
    }

    public void petlePoMapie(){
        /*--------------------------Pętla po kluczach--------------------------*/
        for (String s : mapka.keySet()) {
            System.out.println(s+" : " + mapka.get(s));
        }

        /*--------------------------Pętla po wartościach--------------------------*/
        for (Integer s : mapka.values()) {
            System.out.println(s);
        }

        /*--------------------------Pętla po kluczach i wartości--------------------------*/
        //piszemy mapka.entrySet().for i tab
        for (Map.Entry<String, Integer> stringIntegerEntry : mapka.entrySet()) {
            System.out.println(stringIntegerEntry.getKey()+" : "+stringIntegerEntry.getValue());
        }
    }

    public static void main() {
        Mapy myMap=new Mapy();
        myMap.metodyNaMapie();
        System.out.println("\n");
        myMap.petlePoMapie();

        /*--------------------------Tworzenie map, gdzie klucz to nasza klasa--------------------------*/
        //To zadziała bo klasa person implementuje interface comparable
        //Map<Person,Integer> map=new TreeMap<>();

        //To zadziała bo klasa ma nadpisane metodę .hashCode()
        Map<Person,Integer> map=new HashMap<>();

        //To zwróci błąd, ponieważ metoda ta nie nadpisanej metody .hashCode()(można ją szybko wygenerować)
        //Map<ReaderFromFIle, Integer> map1=new HashMap<>();
        map.put(new  Person("ala",12),12);
        map.put(new  Person("x",0),0);
    }
}

