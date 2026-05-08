package SpecjalneInterfejsy;

import java.util.ArrayList;
import java.util.List;

public class Komparator implements Comparable{
    private int x,y;
    private Integer z;
    public Komparator(int x,int y,Integer z){
        this.x=x;
        this.y=y;
        this.z=z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Integer getZ() {
        return z;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(Integer z) {
        this.z = z;
    }

    @Override
    public int compareTo(Object o) {
        Komparator k=(Komparator)o;
        return Integer.compare(x,k.x);
        //return z.compareTo(k.z);
    }

    @Override
    public String toString() {
        return "Komparator{" +
                "x= " + x +
                ", y= " + y +
                ", z= " + z +
                '}';
    }
}
