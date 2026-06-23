import RozbudowaneStrukturyJava.Kolokwium2025.Game.Duel;
import RozbudowaneStrukturyJava.Kolokwium2025.Game.Gesture;
import RozbudowaneStrukturyJava.Kolokwium2025.Game.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
//Bardzo ważny import dla assercji
//Intelij ma z nim problem, żeby go dać samemu

public class PlayerTest {

    @Test
    public void playerTestIsDuelingReturnTrueIfPlayerIs(){
        //given, czyli inicjalizacja zmiennych
        Player player1= new Player();
        Player player2= new Player();
        Duel duel = new Duel(player1,player2);

        //when, czyli jakaś akcja
        //tutaj puste


        //then, czyli assercje
        assertTrue(player1.isDueling());
        assertTrue(player2.isDueling());
    }

    @Test
    public void playerTestEvaluateTheFirstPlayerIsWinner(){
        Player player1= new Player();
        Player player2= new Player();
        Duel duel = new Duel(player1,player2);

        player1.makeGesture(Gesture.PAPER);
        player2.makeGesture(Gesture.ROCK);


        assertEquals(duel.evaluate(),new Duel.Result(player1,player2));
    }

    @Test
    public void playerTestEvaluateTheFirstPlayerIsNull(){
        Player player1= new Player();
        Player player2= new Player();
        Duel duel = new Duel(player1,player2);

        player1.makeGesture(Gesture.ROCK);
        player2.makeGesture(Gesture.ROCK);


        assertNull(duel.evaluate());
    }


}
