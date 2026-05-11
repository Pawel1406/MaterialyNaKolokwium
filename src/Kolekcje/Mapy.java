package Kolekcje;

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

    public static void main(String[] args) {
        Mapy myMap=new Mapy();
        myMap.metodyNaMapie();
        System.out.println("\n");
        myMap.petlePoMapie();
    }
}

