package Podstawy_Javy.KolokwiumLinieLotnicze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteRecord {
    private Map<Airline,Integer> delaysByAirline=new HashMap();
    private List<String> routeDetails=new ArrayList();

    @Override
    public String toString() {
        StringBuilder s=new StringBuilder();
        int sum=0;
        for (Integer value : delaysByAirline.values()) {
            sum+=value;
        }
        for (Map.Entry<Airline, Integer> airlineIntegerEntry : delaysByAirline.entrySet()) {
            s.append(airlineIntegerEntry.getKey());
            s.append(": ");
            s.append(String.format("%.2f",   delayShare(airlineIntegerEntry.getKey(),sum)));
            s.append("\n");
        }
        return s.toString();
    }


    //zawsze jak robicie coś private to robicie gettery do tego
    public Map<Airline, Integer> getDelaysByAirline() {
        return delaysByAirline;
    }

    public List<String> getRouteDetails() {
        return routeDetails;
    }

    public static RouteRecord fromCsvLine(String line, List<Airline> airlines)throws CorruptedDataException {
        RouteRecord routeRecord=new RouteRecord();
        String[] parts=line.split(",");
        if (parts.length!=9){
            throw new CorruptedDataException("Invalid line format");
        }
        for (int i = 0; i < 3; i++) {
            routeRecord.routeDetails.add(parts[i]);
        }
        for (int i = 3; i < parts.length; i++) {
            if(!Character.isDigit(parts[i].charAt(0))) {
                throw new CorruptedDataException("Tekst zamiast cyfry");
            }
            routeRecord.delaysByAirline.put(airlines.get(i-3),Integer.parseInt(parts[i]));
        }
        return routeRecord;
    }

    public RouteRecord aggregate(List<RouteRecord> records) {
        RouteRecord toReturn=new RouteRecord();
        for (RouteRecord record : records) {
            for (Airline airline : record.delaysByAirline.keySet()) {
                toReturn.delaysByAirline.put(airline, toReturn.delaysByAirline.getOrDefault(airline,0)+record.delaysByAirline.getOrDefault(airline,0));
            }
        }
        return toReturn;
    }
    public int delays(Airline a){
        return delaysByAirline.get(a);
    }
    public double delayShare(Airline a, int sum){
        return (double)delaysByAirline.get(a)/ sum;
    }

    public void delayHigher100(Airline a){
        DataFilter<RouteRecord> flightMoreThan100=flight->flight.delaysByAirline.get(a)>100;
        System.out.println(flightMoreThan100.test(this));

    }

    public static List<RouteRecord> filterByLocation(List<RouteRecord> records, List<String> locations) {
        return records.stream()
                .filter( r->{
                    List<String> locationList=r.getRouteDetails();
                    if (locations.size()==0)return true;
                    if(locations.size()>=1 && !locationList.contains(locations.get(0)))return false;
                    if(locations.size()>=2 && !locationList.contains(locations.get(1)))return false;
                    if(locations.size()==3 && !locationList.contains(locations.get(2)))return false;
                    return true;

                })
                .toList();


        //Tutaj się trochę dzieje i nigdy tego nie robiliśmy
        //Narazie sama logika
        //Jeśli mamy długość 0 to nie ma co filtrować
        //Jeśli mamy długość 1 i więcej to jeśli nie zgadza się kraj to nie ma sensu sprawdzać dalej
        //tak samo potem, jak kolejna składowa się nie zgadza, nie sprawdzamy dalej
        // jak się wszystko zgadza, zwracamy true
        //a teraz co się dzieje w kodzie
        //.filter() w środku ma lambdę która przyjmuje czasem wartości true, czasem false
        // a ponieważ jest to rozbudowany warunek to robi osobny blok kodu.
        // Równie dobrze możemy zrobić funkcję zwracającą true, false, której będziemy przekazywać odpowiednie parametry
        // wtedy .filter(r->funckja(r.getRouteDetails,location.size)

    }
}
