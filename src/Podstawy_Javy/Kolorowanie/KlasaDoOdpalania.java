package Podstawy_Javy.Kolorowanie;

public class KlasaDoOdpalania {

        public static void main(String[] args) {
            // 1. Tworzymy kolory bazowe
            ColorRGB czerwony = new ColorRGB(1.0, 0.0, 0.0);
            ColorRGB niebieski = new ColorRGB(0.0, 0.0, 1.0);

            System.out.println("--- Kolory bazowe ---");
            printColor("Czerwony", czerwony);
            printColor("Niebieski", niebieski);
            System.out.println();

            // 2. Mieszamy kolory 50% czerwonego i 50% niebieskiego (ratio = 0.5)
            // Spodziewany wynik: fioletowy (0.5, 0.0, 0.5)
            ColorRGB fioletowy = ColorRGB.mix(czerwony, niebieski, 0.5);

            System.out.println("--- Mieszanie 50% czerwonego + 50% niebieskiego ---");
            printColor("Wynik (Fioletowy)", fioletowy);
            System.out.println();

            // 3. Mieszamy kolory 80% czerwonego i 20% niebieskiego (ratio = 0.8)
            // Spodziewany wynik: (0.8, 0.0, 0.2)
            ColorRGB duzoCzerwonego = ColorRGB.mix(czerwony, niebieski, 0.8);

            System.out.println("--- Mieszanie 80% czerwonego + 20% niebieskiego ---");
            printColor("Wynik", duzoCzerwonego);
            System.out.println();

            // 4. Test zabezpieczenia przed wartościami spoza zakresu [0, 1]
            // Przekazujemy ratio = 1.5 (powinno zostać przycięte do 1.0 -> czyli sam czerwony)
            // Oraz składowe koloru zielonego = 2.5 (powinno przyciąć do 1.0)
            ColorRGB testZaDuzi = new ColorRGB(0.0, 2.5, 0.0);
            ColorRGB wynikZabezpieczenia = ColorRGB.mix(czerwony, testZaDuzi, 1.5);

            System.out.println("--- Test zabezpieczeń (clamping) ---");
            printColor("Zielony (zamiast 2.5 powinno być 1.0)", testZaDuzi);
            printColor("Wynik mix z ratio 1.5 (powinien być czysty czerwony)", wynikZabezpieczenia);
        }

        // Pomocnicza metoda do ładnego wypisywania wartości w konsoli
        private static void printColor(String nazwa, ColorRGB kolor) {
            // Ponieważ x, y, z są chronione (protected) w klasie Vec3D,
            // a Podstawy_Javy.Main znajduje się w tym samym pakiecie, mamy do nich bezpośredni dostęp.
            System.out.printf("%s -> R: %.2f, G: %.2f, B: %.2f%n", nazwa, kolor.x, kolor.y, kolor.z);
        }
    }

