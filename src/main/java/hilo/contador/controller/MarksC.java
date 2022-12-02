package hilo.contador.controller;

import hilo.contador.model.Chronometer;
import hilo.contador.model.DAO.MarkDao;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MarksC implements Initializable {

    MarkDao mark = new MarkDao();

    List<Chronometer> marks = mark.getAll();
    private ObservableList<Chronometer> observableMarks = FXCollections.observableArrayList(marks);

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button back;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableMarks.setItems(observableMarks);
        marksList();
        refresh();
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

    public void refresh(){
        observableMarks.removeAll(observableMarks);
        observableMarks.addAll(marks);
    }


    public void back(){
        App.closeScene((Stage) anchorPane.getScene().getWindow());
    }
}
