package Pliki;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReaderFromFIle {
    List<Person> lista=new ArrayList<>();

    public List<Person> getLista() {
        return lista;
    }

    /*--------------------------Główna metoda pobierająca z pliku--------------------------*/
    public List<Person> fromCsv(String path) throws FileNotFoundException {
        Scanner scanner=new Scanner(new File(path));//Scanner rzuca wyjątek FileNotFoundException
        scanner.nextLine();//Bardzo ważne, żeby sprawdzić, czy nagłówek jest, czy nie, bo on zawsze psuje.
        String line;
        while (scanner.hasNextLine()){
            line=scanner.nextLine();
            lista.add(fromCsvLine(line));
        }
        scanner.close();//wypada zamknąć, ale się nie wywali teraz już bez tego
        return lista;
    }

    /*--------------------------Metoda pomocniczna parsująca linię--------------------------*/
    private Person fromCsvLine(String line){
        String [] parts=line.trim().split(" ");//.trim() usuwa białe znaki, split zwraca tablicę string podzieloną na podstawie regexu
        return new Person(parts[0],Integer.parseInt(parts[1]));
    }

    public static void main(String[] args)  {
        try{
        ReaderFromFIle readerFromFIle=new ReaderFromFIle();
        readerFromFIle.fromCsv("src/Pliki/plik.txt");
        System.out.println(readerFromFIle.lista);}
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
