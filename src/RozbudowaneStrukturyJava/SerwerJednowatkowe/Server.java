package RozbudowaneStrukturyJava.SerwerJednowatkowe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

//testów nie pisałem masz już kilka
public class Server {
    public static final int PORTNUMBER=12345;

    public static void main(String[] args) {
        if(args.length<1){
            System.out.println("Za mało argumentów");
            return;
        }
        String id=args[0];
        Station station= Station.generateStation(id);

        try(ServerSocket serverSocket=new ServerSocket(PORTNUMBER)){
            while(true){
                try(Socket socket=serverSocket.accept();
                    PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
                    BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()))
                ){
                    out.println(wybierzOpcje());
                    out.flush();
                    String line=in.readLine();
                    doSomething(line,station);



                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String wybierzOpcje(){
        return """
        =================================================================
                          SYSTEM OBSŁUGI STACJI POJAZDÓW                 
        =================================================================
        Dostępne polecenia:

        1. RENT <id>
           -> Wypożycza pojazd o podanym identyfikatorze ID.
              Przykład: RENT 101

        2. RENT_BIKE
           -> Wypożycza dowolny dostępny rower ze stacji.

        3. RENT_SCOOTER
           -> Wypożycza hulajnogę o najwyższym poziomie naładowania baterii.

        4. RETURN BIKE <id>
           -> Zwraca rower o podanym ID do stacji.
              Przykład: RETURN BIKE 101

        5. RETURN SCOOTER <id> <bateria_0_100>
           -> Zwraca hulajnogę o podanym ID oraz poziomie baterii (0-100).
              Przykład: RETURN SCOOTER 202 85
              """;

    }
    private static String doSomething(String line, Station station){
        String[] split=line.split(" ");
        return switch (split[0]) {
            case "RENT" -> {
                if (split.length < 2) {
                    yield "Błedne dane";
                }
                yield station.getVehicleMap().get(split[1]).toString();
            }
            default -> "Blad";
            //nie miałem czasu dokończyć wszystkiego
        };
    }
}
