package RozbudowaneStrukturyJava.Kolokwium2025.Game;

public class Player {
    private Duel duel ;

    public void makeGesture(Gesture gesture){
        duel.handleGesture(this, gesture);
    }
    public void enterDuel(Duel duel){
        this.duel = duel;
    }
    public void leaveDuel(){
        duel = null;
    }
    public Boolean isDueling(){
        return duel != null;
    }


}
