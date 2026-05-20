package Wyjatki;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileNotFound {
    /*--------------------------FileNotFoundException--------------------------*/
    //Scanner rzuca FileNotFoundException
    //Można go obsłużyć poprzez rzucenie dalej(readFromFIleOne), albo już wewnątrz tej klasy w bloku try, catch(readFromFIleSecond)
    public void readFromFIleOne(String file) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(file));
        while (sc.hasNextLine()) {
            sc.nextLine();
        }
        sc.close();
    }
    public void readFromFIleSecond(String file)  {
        Scanner sc = null;
        try {
            sc = new Scanner(new File(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
            //RunTimeException to wyjątek którego nie musimy obsługiwać program robi to automatycznie(?)
            //Wejście do bloku catch spowoduje, że dalsza część funkcji się nie wykona, przez fakt rzucania nowego wyjątku
            //Jeśli byłoby tu tylko wyświetlenie napisu, o błędzie to trzeba dodać return
        }
        while (sc.hasNextLine()) {
            sc.nextLine();
        }
        sc.close();
    }
    public void readFromFIleThird(String file)  {
        Scanner sc = null;
        try {
            sc = new Scanner(new File(file));
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            return;//Można zakomentować, żeby zobaczyć błąd który wywali
            //Konkretnie sc jest null, więc mamy NullPointerException
        }
        while (sc.hasNextLine()) {
            System.out.println(sc.nextLine());
        }
        System.out.println("PLIK");
        sc.close();
    }

    //dodawanie do listy, jeśli jakaś metoda wewnętrzna rzuca wyjątek
    //Dodajemy do listy, tylko gdy nie ma wyjątku
    public void loadData(String path) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(path));
        String line;
        //RouteRecord routeRecord;->jakieś dane

        while (scanner.hasNextLine()) {
            try {
                line = scanner.nextLine().trim();
               // routeRecord=RouteRecord.fromCsvLine(line, airlines);->
               //->Jakaś metoda rzucajaca jakiś wyjątek, np. własny
            } catch (Exception e) {
                System.err.println(e.getMessage());
                continue;
            }
            //records.add(routeRecord);->dodanie do jakiejś listy
        }
    }

    static void main() {
        FileNotFound obj = new FileNotFound();
        obj.readFromFIleThird("src/Pliki/plik7.txt");//plik.txt to poprawna nazwa, reszta ścieżki jest git
    }
}
