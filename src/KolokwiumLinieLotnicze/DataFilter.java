package KolokwiumLinieLotnicze;

public interface DataFilter <T>{
    public boolean test(T data);
}


/*
//Dwa różne sposoby na implementację, dla jakiejś klasy flight
// Tworzymy przykładowy obiekt lotu, który ma 120 opóźnień
        Flight sampleFlight = new Flight("LO3801", 120);

        // --- SPOSÓB A: Implementacja za pomocą Klasy Anonimowej ---
        DataFilter<Flight> anonymousFilter = new DataFilter<Flight>() {
            @Override
            public boolean test(Flight flight) {
                return flight.getDelaysCount() > 100;
            }
        };

        // --- SPOSÓB B: Implementacja za pomocą Wyrażenia Lambda (Szybciej i ładniej) ---
        DataFilter<Flight> lambdaFilter = flight -> flight.getDelaysCount() > 100;
 */
