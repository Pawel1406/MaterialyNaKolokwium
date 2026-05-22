package KolokwiumLinieLotnicze;

import java.io.FileNotFoundException;

public class KlasaDoTestowanieMain {
    static void main() {
        //Szukamy zawsze głównej klasy, posiadajacej jakąś metodę inicjalizującą wszystko.
        //W naszym wypadku jest to metoda initialize z klasy TrafficSystem
        TrafficSystem trafficSystem = new TrafficSystem();
        try {
            trafficSystem.initialize();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        //Teraz chcemy wyświetlić sobie metodę toString() z klasy RouteRecord,
        // nie jest to metoda statyczna, więc musimy wywołać tę metodę dla konkretnego obiektu RouteRecord
        //Zaczynamy szukanie jak się dostać do zainicjowanego obeiktu przez klasę Traffic System
        //W tej klasie klikamy z ctrl na tym obiektu JanuaryData, czyli FlightMonth
        //Nie klikamy na Airlines, bo to odwołuje nas do rekordu i nigdzie później, a februaryData jest nullem i obiektem tej samej klasy
        //W klasie flightMonths mamy listę obiektów RouteRecord więc doszliśmy do sedna. Osiągneliśmy to co chcieliśmy.
        //Wywołamy sobie metodę agregate(), tylko po to, żeby wyświetliło nam się krótkie podsumowanie, a nie cała lista
        //Ponieważ zgodnie z poleceniem metoda agregate() nie jest static to musimy stworzyć pomocniczy RouteRecords

        System.out.println(new RouteRecord().aggregate(trafficSystem.getJanuaryData().getRecords()));
        new RouteRecord().aggregate(trafficSystem.getJanuaryData().getRecords()).delayHigher100(new Airline("KLM"));

    }
}
