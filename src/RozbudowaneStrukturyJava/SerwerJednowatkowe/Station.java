package RozbudowaneStrukturyJava.SerwerJednowatkowe;

import java.io.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Station {
    private String stationId;
    private int handles;
    private Map<Integer, Vehicle> vehicleMap;

    public Station(String stationId, int handles) {
        this.stationId = stationId;
        this.handles = handles;
        this.vehicleMap = new HashMap<>();
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public int getHandles() {
        return handles;
    }

    public void setHandles(int handles) {
        this.handles = handles;
    }

    public Map<Integer, Vehicle> getVehicleMap() {
        return vehicleMap;
    }

    public void setVehicleMap(Map<Integer, Vehicle> vehicleMap) {
        this.vehicleMap = vehicleMap;
    }

    public Vehicle rentVehicle(int id){
        if(vehicleMap.containsKey(id)){
            Vehicle vehicle= vehicleMap.remove(id);
            dump();
            return vehicle;
        }
        return null;
    }

    public void returnVehicle(Vehicle vehicle){
        if(vehicleMap.size()>=handles){
            throw new FullStationException("FULL STATION");
        }
        vehicleMap.put(vehicle.getId(), vehicle);
        dump();
    }

    public Bicycle rentBicycle(){
        for (Vehicle value : vehicleMap.values()) {
            if(value instanceof Bicycle){
                Bicycle bicycle=((Bicycle)value);
                vehicleMap.remove(bicycle.getId());
                dump();
                return bicycle;
            }
        }
        return null;
    }

    public Scooter rentScooter(){
        Scooter scooter=vehicleMap.values().stream()
                .filter(Scooter.class::isInstance)
                .map(Scooter.class::cast)
                .max(Comparator.comparingInt(Scooter::getBateryLevel))
                .orElse(null);
        vehicleMap.remove(scooter);
        dump();
        return scooter;
    }

    public void dump(){
        String name=stationId+".txt";
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(name))){
            out.writeObject(vehicleMap);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public static Station generateStation(String stationId){
        String name=stationId+".txt";
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(name))){
            return (Station) in.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }



}
