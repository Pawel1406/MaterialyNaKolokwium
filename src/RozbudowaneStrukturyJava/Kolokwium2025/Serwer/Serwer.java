package RozbudowaneStrukturyJava.Kolokwium2025.Serwer;

import RozbudowaneStrukturyJava.Kolokwium2025.Game.Duel;
import RozbudowaneStrukturyJava.Kolokwium2025.Game.Gesture;
import RozbudowaneStrukturyJava.Kolokwium2025.Game.Player;
import RozbudowaneStrukturyJava.Serwer.Client;
import RozbudowaneStrukturyJava.Serwer.ClientHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class Serwer {

    public  int PORT=12345;
    public  List<ClientHandler> clients=new CopyOnWriteArrayList<>();
    private  Database database=new Database("src/RozbudowaneStrukturyJava/Kolokwium2025/Serwer/users.db");


    public  Database getDatabase() {
        return database;
    }

    public static void main(String[] args) {
        Serwer serwer=new Serwer();
        System.out.println("Odpalanie serwera...");
        serwer.listen();
    }
    public  void listen(){
        try(ServerSocket serverSocket = new ServerSocket(PORT)){
            while(true){
               new Thread( new ClientHandler(serverSocket.accept(),this)).start();
               //ponieważ ClientHandler nie jest wątkiem, tylko implementuje interface Runnable musimy opakować go w Thread, żeby móc go odpalić
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public void challengeToDuel(ClientHandler challenger,String challengeeLogin){
        if(challenger.username.equals(challengeeLogin)){
            challenger.sendMessage("Nie możesz siebie wywolac do gry");
            return;
        }
        for(ClientHandler client:clients){
            if(client.username.equalsIgnoreCase(challengeeLogin)){
                if(!challenger.inTournament&& !client.inTournament) {
                    startDuel(challenger,client);
                    return;
                }

                else{
                    challenger.sendMessage(client.username+" jest juz w grze");
                    return;
                }
            }
        }
        challenger.out.println("Nie ma takiego czlowieka: "+challengeeLogin);
    }
    private void startDuel(ClientHandler challenger, ClientHandler challengee){
        Duel duel=new Duel(challenger,challengee);

        duel.setOnEnd(()->{
            Duel.Result result=duel.evaluate();
            if(result==null){
                challengee.sendMessage("Remis");
                challenger.sendMessage("Remis");
            }
            else {
                ((ClientHandler) result.winner()).sendMessage("Wygrales");
                ((ClientHandler) result.loser()).sendMessage("Przegrales");
                database.updateLeaderboard(((ClientHandler) result.winner()).username,((ClientHandler) result.loser()).username);
            }

            challengee.inTournament=false;
            challenger.inTournament=false;
            challenger.printResult(database.getLeaderboard());
            challengee.printResult(database.getLeaderboard());
        });

        challenger.sendMessage("Rozpoczeto pojedynek!!!");
        challengee.sendMessage("Rozpoczeto pojedynek!!!");
        challenger.inTournament = true;
        challengee.inTournament = true;
    }

    public  void broadcast(String input, String username){
        for (ClientHandler client : this.clients) {
            if (client.username.equals(username)) continue;
            client.out.println(input);
            //To jest dobry moment na zastanowienie się, kiedy stosować pętlę fori,a kiedy for
            //Algorytmicznie nie ma to większego znaczenia, o ile korzystamy z rozbudowanych struktur jak listy
            //Ale dla czytelności kodu byłoby okropne zastosowanie tutaj fori
            //Ostatnia linijka pętli wyglądała by tak:
            //clients.get(i).out.println(input);
            //co może nie wydaje się jakoś dłużej, ale jednak trzeba chwilę pomyśleć co to robi
        }
    }

    private static class ClientHandler extends Player implements Runnable{
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        private String username;
        Serwer serwer;
        private boolean inTournament = false;

        public ClientHandler(Socket socket,Serwer serwer){
            this.socket = socket;
            this.serwer=serwer;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                String input;
                out.println("Podaj login: ");
                while ((input = in.readLine()) != null) {
                    input=input.trim();
                    if(username == null){
                        out.println("Podaj haslo: ");
                        String password=in.readLine();
                        password=password.trim();
                        out.println("Trwa autentykacja...........");
                        if(serwer.database.authenticate(input,password)) {
                            this.username = input;
                            serwer.clients.add(this);
                            out.println("Udalo sie pomyslnie polaczyc z baza danych");
                            serwer.broadcast(this.username+ " dołączył do serwera",this.username);
                            sendActiveUsers();//potrzebne, żeby mieć listę user
                        }
                        else{
                            out.println("Blad autentykacji");
                            break;
                        }
                    }
                    else if(inTournament){
                        if(input.equals("p")||input.equals("r")||input.equals("s")){
                            makeGesture(Gesture.fromString(input));
                        }
                        else out.println("Niedozwolona komenda: "+input);


                    }
                    else{
                        serwer.challengeToDuel(this, input);
                        //broadcast(input,username);
                    }
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
            finally {
                if(username != null&&!username.isEmpty()){
                    serwer.broadcast(username+"opuscil chat",username);
                    serwer.clients.remove(this);
                }
                closeEverything();
            }

        }


        public void sendMessage(String message){
            this.out.println(message);
        }
        private void closeEverything(){
            try {
                if(socket!=null) socket.close();
                if(in!=null) in.close();
                if(out!=null) out.close();

            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        public void printResult(Map<String,Integer> leaderboard){
            out.println("WYNIK:");
            out.println("login:points");
            out.println("________");
            for (Map.Entry<String, Integer> stringIntegerEntry : leaderboard.entrySet()) {
                out.println(stringIntegerEntry.getKey() + ":" + stringIntegerEntry.getValue());
            }
        }

        public void sendActiveUsers(){
            serwer.broadcast("AKTYWNI GRACZE",null);
            StringBuilder sb=new StringBuilder();


            for (ClientHandler client : serwer.clients) {
                sb.append(client.username);
                sb.append("\n");
            }
            sb.append("END");
            serwer.broadcast(sb.toString(),null);

        }
    }
}
