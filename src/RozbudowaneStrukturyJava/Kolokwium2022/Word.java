package RozbudowaneStrukturyJava.Kolokwium2022;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Word {
    public final LocalTime date;
    public final String word;
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    public Word(String word) {
        this.date = LocalTime.now();
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    @Override
    public String toString() {
        return date.format(FORMATTER)+": "+word;
    }
}
