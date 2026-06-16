package Podstawy_Javy.KlasyStandardowe;

public class KlasaZStringBuilder {
    public static void main() {
        /*--------------------------StringBuileder->modyfikowalny--------------------------*/
        StringBuilder s = new StringBuilder();
        s.append("Ala");
        s.append(" ");
        s.append("ma");
        s.append(" ");
        s.append("kota ");
        s.append(String.format("%.4f",10.0909090));//tu wystarczy dowolna wartość zamiast 4, w zależności od potrzebnej precyzji
        String s1 = s.toString();

        /*--------------------------String->nieModyfikowalny--------------------------*/
        //symulujemy tu tworzenie adresów
        //Są inne
        System.out.println("\n");
        String s10="ALa ma kota";
        System.out.println(s10.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(s10)));
        s10="jkj";
        System.out.println(s10.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(s10)));
        System.out.println("\n");

        System.out.println(s1);
        //String builder powstał po to, by mieć możliwość modyfikacji obiektów tekstowych, czego nie ma w klasie String.
        String s2 = new String("test");
        String s3 = new String("test");

        System.out.println("\"==\" zwróci false: " + s2 == s3);      // false (inne obiekty)
        System.out.println(".equals() zwróci true:"+s2.equals(s3)); // true (ta sama treść) <-tą wersję się powinno robić
    }
}
