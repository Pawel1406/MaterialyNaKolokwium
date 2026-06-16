package Podstawy_Javy.KolokwiumLinieLotnicze;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FlightMonth {
    private List<Airline> airlines = new ArrayList<>();
    private List<RouteRecord> records=new ArrayList<>();


    public FlightMonth(List<Airline> airlines) {
        this.airlines = airlines;
    }


    public List<Airline> getAirlines() {
        return airlines;
    }

    public List<RouteRecord> getRecords() {
        return records;
    }


    public void loadData(String path) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(path));
        sc.nextLine();//zawsze sprawdzamy nagłówek
        RouteRecord record;
        int i=1;
        while (sc.hasNextLine()) {
            try{
                 record=(RouteRecord.fromCsvLine(sc.nextLine(),airlines));
                 i++;
            }
            catch (CorruptedDataException e){
                System.err.println(i+": "+ e.getMessage());
                continue;
            }
            records.add(record);
        }
    }


    public Airline worstAirline()throws NoDelaysException{
        RouteRecord record=new RouteRecord().aggregate(records);
        Airline worstAirline=null;
        Integer worstAirlineNumber=0;

        for (Map.Entry<Airline, Integer> airlineIntegerEntry : record.getDelaysByAirline().entrySet()) {

            if(worstAirline==null){
                worstAirline=airlineIntegerEntry.getKey();
                worstAirlineNumber=airlineIntegerEntry.getValue();
            }
            else if(worstAirlineNumber<airlineIntegerEntry.getValue()){
                worstAirline=airlineIntegerEntry.getKey();
                worstAirlineNumber=airlineIntegerEntry.getValue();
            }
        }
        if (worstAirlineNumber==0){
            throw new NoDelaysException("Suma opoznien jest zerowa");
        }
        return worstAirline;
    }

    /*--------------------------------Statyczny Polimorfizm--------------------------------*/
    public RouteRecord aggregateData(){
       return  new RouteRecord().aggregate(records);
    }
    public RouteRecord aggregateData(List<String> routeFilter){
        return new RouteRecord().aggregate(RouteRecord.filterByLocation(records, routeFilter));

    }



}
