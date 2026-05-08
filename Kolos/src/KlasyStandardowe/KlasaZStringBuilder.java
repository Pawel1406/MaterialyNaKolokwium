package KlasyStandardowe;

public class KlasaZStringBuilder {
    public static void main(String[] args) {
        StringBuilder s = new StringBuilder();
        s.append("Ala");
        s.append(" ");
        s.append("ma");
        s.append(" ");
        s.append("kota ");
        s.append(String.format("%.4f",10.0909090));//tu wystarczy dowolna wartość zamiast 4, w zależności od potrzebnej precyzji
        String s1 = s.toString();

        System.out.println(s1);
        //String builder powstał po to, by mieć możliwość modyfikacji obiektów tekstowych, czego nie ma w klasie String.
        String s2 = new String("test");
        String s3 = new String("test");

        System.out.println(s2 == s3);      // false (inne obiekty)
        System.out.println(s2.equals(s3)); // true (ta sama treść) <-tą wersję się powinno robić
    }
}
