package hilo.contador.utils;

import hilo.contador.model.Chronometer;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Read extends Thread{
    private IntegerProperty hora;
    private IntegerProperty minuto;
    private IntegerProperty segundo;
    private IntegerProperty milisegundo;

    //Texto que se mostrara por pantalla
    private StringProperty StringHora;
    private StringProperty StringMinuto;
    private StringProperty StringSegundo;
    private StringProperty StringMilisegundo;

    public Read() {
        this.hora = new SimpleIntegerProperty(0);
        this.minuto = new SimpleIntegerProperty(0);
        this.segundo = new SimpleIntegerProperty(0);
        this.milisegundo = new SimpleIntegerProperty(0);
        this.StringHora = new SimpleStringProperty("00");
        this.StringMinuto = new SimpleStringProperty("00");
        this.StringSegundo = new SimpleStringProperty("00");
        this.StringMilisegundo = new SimpleStringProperty("00");
    }

    public IntegerProperty getHora() {
        return hora;
    }
    public void setHora(IntegerProperty hora) {
        this.hora = hora;
    }
    public IntegerProperty getMinuto() {
        return minuto;
    }
    public void setMinuto(IntegerProperty minuto) {
        this.minuto = minuto;
    }
    public IntegerProperty getSegundo() {
        return segundo;
    }
    public void setSegundo(IntegerProperty segundo) {
        this.segundo = segundo;
    }
    public void setMilisegundo(IntegerProperty milisegundo) {
        this.milisegundo = milisegundo;
    }
    public StringProperty getFraseHora() {
        return StringHora;
    }
    public void setFraseHora(StringProperty fraseHora) {
        this.StringHora = fraseHora;
    }
    public StringProperty getFraseMinuto() {
        return StringMinuto;
    }
    public void setFraseMinuto(StringProperty fraseMinuto) {
        this.StringMinuto = fraseMinuto;
    }
    public StringProperty getFraseSegundo() {
        return StringSegundo;
    }
    public void setFraseSegundo(StringProperty fraseSegundo) {
        this.StringSegundo = fraseSegundo;
    }
    public StringProperty getFraseMilisegundo() {
        return StringMilisegundo;
    }
    public void setFraseMilisegundo(StringProperty fraseMilisegundo) {
        this.StringMilisegundo = fraseMilisegundo;
    }

    public void run() {

        while (!this.isInterrupted()) {

            try {
                Thread.sleep(4);
                Platform.runLater(() ->{
                    milisegundo.set(milisegundo.get() + 6);
                    if (milisegundo.get()<100){
                        StringMilisegundo.set("0"+milisegundo.get());
                        if (milisegundo.get()<10){
                            StringMilisegundo.set("");
                        }
                    }else {
                        StringMilisegundo.set(milisegundo.get()+"");
                    }
                });

                if (milisegundo.get()>=1000){
                    Platform.runLater(() ->{
                        segundo.set(segundo.get() + 1);
                        if (segundo.get()<10){
                            StringSegundo.set("0"+segundo.get());
                        }else {
                            StringSegundo.set(segundo.get()+"");
                        }
                        milisegundo.set(0);
                        StringMilisegundo.set(milisegundo.get()+"0");
                    });
                    if (segundo.get() == 59) {
                        Platform.runLater(() -> {
                            minuto.set(minuto.get() + 1);
                            if(minuto.get()<10) {
                                StringMinuto.set("0"+minuto.get());
                            }else {
                                StringMinuto.set(minuto.get()+"");
                            }
                            segundo.set(0);
                            StringSegundo.set(segundo.get()+"0");
                        });

                        if (minuto.get() == 59) {
                            Platform.runLater(() -> {
                                minuto.set(0);
                                StringMinuto.set(minuto.get()+"");
                                hora.set(hora.get() + 1);
                                StringHora.set(hora.get()+"");
                            });

                            if (hora.get() == 24) {
                                Platform.runLater(() -> {
                                    hora.set(0);
                                    StringHora.set(hora.get()+"");
                                });

                            }
                        }
                    }
                }
            } catch (InterruptedException e) {
                Platform.runLater(() -> {
                    milisegundo.set(0);
                    StringMilisegundo.set(milisegundo.get()+"0");

                    segundo.set(0);
                    StringSegundo.set(segundo.get()+"0");

                    minuto.set(0);
                    StringMinuto.set(minuto.get()+"0");

                    hora.set(0);
                    StringHora.set(hora.get()+"0");

                });

            }


        }

    }

}
