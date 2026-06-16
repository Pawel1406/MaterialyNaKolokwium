package Podstawy_Javy.KolokwiumLinieLotnicze;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TrafficSystem {
    //!!!!!Pamiętajcie o tym z new, bo bez tego jest nullem
    private List<Airline> airlines=new ArrayList<>();

    private FlightMonth januaryData=new FlightMonth(airlines);
    private FlightMonth februaryData;//czyli jest nullem, jak chcą w poleceniu
    //Skoro karzą wam napisać gettery to zmienne muszą być typu private

    private Airline mostDelayed;

    public List<Airline> getAirlines() {
        return new ArrayList<>(airlines);
    }

    public FlightMonth getJanuaryData() {
        return januaryData;
    }

    public FlightMonth getFebruaryData() {
        return februaryData;
    }

    public Airline getMostDelayes() {
        return mostDelayed;
    }

    public void populateAirlines(String path) throws FileNotFoundException {
        Scanner input = new Scanner(new File(path));
        while (input.hasNextLine()) {
            airlines.add(new Airline(input.nextLine()));
        }
    }

    public void initialize() throws FileNotFoundException {
        populateAirlines("src/Podstawy_Javy.KolokwiumLinieLotnicze/linieLotnicze.txt");
        januaryData.loadData("src/Podstawy_Javy.KolokwiumLinieLotnicze/styczen.csv");
        try{
            mostDelayed=januaryData.worstAirline();
            //jak rzuci wyjątek to mostDelayed dalej będzie null
        }catch(NoDelaysException e){
            februaryData=new FlightMonth(airlines);
            februaryData.loadData("src/Podstawy_Javy.KolokwiumLinieLotnicze/luty.csv");
            try {
                mostDelayed=februaryData.worstAirline();
            } catch (NoDelaysException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

}
