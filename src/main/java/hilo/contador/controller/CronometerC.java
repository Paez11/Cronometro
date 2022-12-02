package hilo.contador.controller;

import hilo.contador.model.Chronometer;
import hilo.contador.model.DAO.MarkDao;
import hilo.contador.utils.Log;
import hilo.contador.utils.Shows;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CronometerC implements Initializable,Runnable {

    MarkDao mark = new MarkDao();

    List<Chronometer> marks = new ArrayList<Chronometer>();
    private ObservableList<Chronometer> observableMarks = FXCollections.observableArrayList(marks);

    private Chronometer c;
    private Chronometer caux;
    private Chronometer cMark;

    //private static Read c = new Read();
    //private static Thread t = new Thread(c);
    private static Thread t;

    private String milisecs;
    private String secs;
    private String min;
    private String hours;

    private boolean isRunning;
    private boolean reset = false;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ImageView imagenPlay;

    @FXML
    private Label Label_Minuto;
    @FXML
    private Label Label_Segundo;
    @FXML
    private Label Label_Hora;
    @FXML
    private Label Label_Milisegundo;

    @FXML
    private TableView<Chronometer> tableMarks;
    @FXML
    private TableColumn<Chronometer, Integer> columnMarkHour;
    @FXML
    private TableColumn<Chronometer, Integer> columnMarkMinute;
    @FXML
    private TableColumn<Chronometer, Integer> columnMarkSecond;
    @FXML
    private TableColumn<Chronometer, Integer> columnMarkMilisecond;

    @FXML
    private Button playbtn;
    @FXML
    private Button resetbtn;
    @FXML
    private Button markbtn;
    @FXML
    private Button marksBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Label_Hora.setText("00");
        Label_Minuto.setText("00");
        Label_Segundo.setText("00");
        Label_Milisegundo.setText("000");

        tableMarks.setItems(observableMarks);
        marksList();

        Platform.runLater(()->{
            Shows.closeRequest((Stage) anchorPane.getScene().getWindow());
        });
    }

    public void startCronometer(){
        File playPath = new File("src\\main\\resources\\icons\\ic_play.png");
        File pausePath = new File("src\\main\\resources\\icons\\ic_pause.png");
        Image play = new Image(playPath.toURI().toString());
        Image pause = new Image(pausePath.toURI().toString());

        if (reset){
            c=null;
            caux=null;
            reset=false;
        }
        if(caux == null){
            if (!isRunning){
                c = new Chronometer();
                t = new Thread(this);
                t.start();
                isRunning = true;
                this.imagenPlay.setImage(pause);
            }else{
                stopCronometer();
                this.imagenPlay.setImage(play);
            }
        }else {
            reanudeCronometer();
            this.imagenPlay.setImage(pause);
            caux = null;
        }
    }

    public void reanudeCronometer(){
        isRunning = true;
        int milisegundos = caux.getMilisecond();
        int segundos = caux.getSecond();
        int minutos = caux.getMinute();
        int horas = caux.getHour();
        c = new Chronometer(horas, minutos, segundos, milisegundos);
        t = new Thread(this);
        t.start();
    }

    public void stopCronometer(){

        isRunning = false;
        int mvar = Integer.parseInt(Label_Milisegundo.getText());
        int svar = Integer.parseInt(Label_Segundo.getText());
        int mivar = Integer.parseInt(Label_Minuto.getText());
        int hvar = Integer.parseInt(Label_Hora.getText());
        caux = new Chronometer(hvar, mivar, svar, mvar);

        /*
        int milisegundos = Integer.parseInt(c.getFraseMilisegundo().getValue());
        int segundos = Integer.parseInt(c.getFraseSegundo().getValue());
        int minutos = Integer.parseInt(c.getFraseMinuto().getValue());
        int horas = Integer.parseInt(c.getFraseHora().getValue());
        caux = new Chronometer(horas, minutos, segundos, milisegundos);
        t.interrupt();
         */

    }

    public void resetCronometer(){
        reset = true;
        isRunning = false;
        if (t.isAlive()){
            t.interrupt();
            Label_Milisegundo.setText("000");
            Label_Segundo.setText("00");
            Label_Minuto.setText("00");
            Label_Hora.setText("00");
        }else {
            Label_Milisegundo.setText("000");
            Label_Segundo.setText("00");
            Label_Minuto.setText("00");
            Label_Hora.setText("00");
        }
        this.imagenPlay.setImage(new Image(new File("src\\main\\resources\\icons\\ic_play.png").toURI().toString()));
    }


    public void marksList(){
        columnMarkHour.setCellValueFactory(mark ->{
            ObservableValue<Integer> in = new SimpleIntegerProperty().asObject();
            ((ObjectProperty<Integer>) in).setValue(mark.getValue().getHour());
            return in;
        });
        columnMarkMinute.setCellValueFactory(mark ->{
            ObservableValue<Integer> in = new SimpleIntegerProperty().asObject();
            ((ObjectProperty<Integer>) in).setValue(mark.getValue().getMinute());
            return in;
        });
        columnMarkSecond.setCellValueFactory(mark ->{
            ObservableValue<Integer> in = new SimpleIntegerProperty().asObject();
            ((ObjectProperty<Integer>) in).setValue(mark.getValue().getSecond());
            return in;
        });
        columnMarkMilisecond.setCellValueFactory(mark ->{
            ObservableValue<Integer> in = new SimpleIntegerProperty().asObject();
            ((ObjectProperty<Integer>) in).setValue(mark.getValue().getMilisecond());
            return in;
        });
    }

    public void markChronometer(){
        int mvar = Integer.parseInt(Label_Milisegundo.getText());
        int svar = Integer.parseInt(Label_Segundo.getText());
        int mivar = Integer.parseInt(Label_Minuto.getText());
        int hvar = Integer.parseInt(Label_Hora.getText());

        cMark = new Chronometer(hvar, mivar, svar, mvar);
        mark = new MarkDao(hvar, mivar, svar, mvar);
        marks.add(cMark);
        mark.save();
        refresh();
    }

    public void refresh(){
        observableMarks.removeAll(observableMarks);
        observableMarks.addAll(marks);
    }


    public void switchMarks(){
        App.loadScene(new Stage(), "Marks","Cronometer",true,false);
    }

    @Override
    public void run() {
        try {
            while (isRunning) {
                Thread.sleep(4);
                int milli = c.getMilisecond();
                milli += 6;
                c.setMilisecond(milli);

                if (c.getMilisecond() >= 1000) {
                    c.setMilisecond(0);
                    int sec = c.getSecond();
                    sec++;
                    c.setSecond(sec);

                    if (c.getSecond() == 60) {
                        c.setSecond(0);
                        int min = c.getMinute();
                        min++;
                        c.setMinute(min);

                        if (c.getMinute() == 60) {
                            c.setMinute(0);
                            int h = c.getHour();
                            h++;
                            c.setHour(h);

                            if (c.getHour() == 24) {
                                c.setHour(0);
                            }
                        }
                    }
                }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (c.getMilisecond() < 100) {
                            Label_Milisegundo.setText("0" + c.getMilisecond());

                            if (c.getMilisecond() < 10) {
                                Label_Milisegundo.setText("0" + "0" + c.getMilisecond());
                            }
                        } else {
                            Label_Milisegundo.setText(String.valueOf(c.getMilisecond()));
                        }
                        if (c.getSecond() < 10) {
                            Label_Segundo.setText("0" + c.getSecond());
                        } else {
                            Label_Segundo.setText(String.valueOf(c.getSecond()));
                        }

                        if (c.getMinute() < 10) {
                            Label_Minuto.setText("0" + c.getMinute());
                        } else {
                            Label_Minuto.setText(String.valueOf(c.getMinute()));
                        }

                        if (c.getHour() < 10) {
                            Label_Hora.setText("0" + c.getHour());
                        } else {
                            Label_Hora.setText(String.valueOf(c.getHour()));
                        }
                    }
                });
            }
        } catch (Exception e) {
            Log.info(e.getMessage());
        }
    }
}