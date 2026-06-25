package RozbudowaneStrukturyJava.Kolokwium2022;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class ClientControler {

    @FXML
    private TextField filterField=new TextField();

    @FXML
    private ListView<String> wordList=new ListView<>();

    @FXML
    private Label wordCountLabel=new Label();

    private ObservableList<String> observableList= FXCollections.observableArrayList();
    private FilteredList<String> filteredList=new FilteredList<>(observableList);
    private SortedList<String> sortedList=new SortedList<>(filteredList);

    @FXML
    public void initialize(){
        wordList.setItems(sortedList);

        sortedList.setComparator((entry1,entry2)->{
            String word1=entry1.substring(10).trim().toLowerCase();
            String word2=entry2.substring(10).trim().toLowerCase();

            word1=removePolishChars(word1);
            word2=removePolishChars(word2);

            return word1.compareTo(word2);
        });




        filterField.textProperty().addListener((observable,oldValue,newValue)->{
            filteredList.setPredicate(word->{
                if(newValue==null || newValue.isEmpty()){
                    return true;
                }
                return word.substring(10).toLowerCase().startsWith(newValue.toLowerCase());
            });
        });



        new Thread(this::connectToServer).start();
    }



    private void connectToServer(){
        try(Socket socket=new Socket("localhost",12345)){
            BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            while((line=in.readLine())!=null){
                Word word=new Word(line);
                Platform.runLater(()->{
                    observableList.add(word.toString());
                    wordCountLabel.setText(""+observableList.size());
                });
            }
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }

    }

    private String removePolishChars(String s) {
        return s.replace("ą","a")
                .replace("ć","c")
                .replace("ę","e")
                .replace("ł","l")
                .replace("ń","n")
                .replace("ś", "s")
                .replace("ź", "z")
                .replace("ż", "z")
                .replace("ó", "o");

    }

}
