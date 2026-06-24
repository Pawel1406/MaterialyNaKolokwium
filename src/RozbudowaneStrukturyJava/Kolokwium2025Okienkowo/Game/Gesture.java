package RozbudowaneStrukturyJava.Kolokwium2025Okienkowo.Game;

public enum Gesture {
    ROCK,
    PAPER,
    SCISSORS;
    public static Gesture fromString(String s){

        //dość specyficzny przypadek, kiedy nie stosujemy break,
        //ale to wynika z faktu, że mamy return i nie możemy i tak iść dalej,
        //jak wpiszecie break; pod return; wywali wam błąd unrechable statement

        switch (s){
            case "r":
                return ROCK;
            case "p":
                return PAPER;
            case "s":
                return SCISSORS;
            default:
                return null;
        }

        /*
        return switch (s) {
            case "r" -> ROCK;
            case "p" -> PAPER;
            case "s" -> SCISSORS;
            default -> null;
        };*/
        //fajny stosunkowo nowy switch znacznie przyjemniejszy dla oka
    }
    public int compareWith(Gesture other){
        if (this.equals(other)){
            return 0;
        }
        return switch (this){
            case ROCK -> (other==SCISSORS) ? 1 : -1;
            case PAPER -> (other==ROCK) ? 1 : -1;
            case SCISSORS -> (other==PAPER) ? 1 : -1;
        };
        //Musi być ';' na końcu
        //Robimy tutaj coś takiego:
        //Jeśli this, czyli ten, na którym wywołujemy metodę compare
        //jest ROCK, to sprawdzamy, czy other to SCISSORS.
        //Jeśli tak jest, to zwracamy 1, kamień wygrywa z nożycami.
        //Jeśli nie jest tak, to znaczy, że other jest PAPER, który niszczy kamień.
        //Nie mogą być takie same, bo mamy na początku if, który tę sytuację obsługuje.

    }
}
