package RozbudowaneStrukturyJava.Kolokwium2025Okienkowo.Game;

public class Duel {
    Player player1;
    Player player2;
    Gesture gesture1;
    Gesture gesture2;
    private Runnable onEnd;

    public Duel(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        player1.enterDuel(this);
        player2.enterDuel(this);
        //Bardzo fajnie testy jednostkowe pokazują błędny, których byśmy się nigdy nie spodziewali
        //Przez przypadek zrobiłem dwa razy "player1.enterDuel(this);", zamiast drugiej linijki dla player2
        //Test jednostkowy bardzo szybko to wykrył, że się cos nie zgadza
    }

    public void setOnEnd(Runnable onEnd) {
        this.onEnd = onEnd;
    }


    public void handleGesture(Player player, Gesture gesture) {
        if (player1.equals(player)) {
            gesture1=gesture;
        }
        else{
            gesture2=gesture;
        }
        if (gesture1!=null && gesture2!=null) {
            if(onEnd!=null){
                onEnd.run();
            }
        }
    }
    public Result evaluate(){
        int result = gesture1.compareWith(gesture2);
        //Player p1=player1;
        //Player p2=player2;
        player1.leaveDuel();
        player2.leaveDuel();

        switch (result){
            case -1:
                return new Result(player2,player1);
            case 1:
                return new Result(player1,player2);
            default:
                return null;
            //nie ma case 0, bo i tak musimy dać opcję default
        }
    }

    public record Result(Player winner, Player loser){}


}
