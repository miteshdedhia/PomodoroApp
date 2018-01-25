package pomodoroApp;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

import java.time.Duration;
import java.util.Queue;

public class PomodoroController {

    @FXML private TableView<PomodoroTask> mTaskTblView;
    @FXML private TableColumn<PomodoroTask, String> mTaskCol;
    @FXML private TableColumn<PomodoroTask, String> mTimeCol;

    @FXML private Label mTimerLbl;
    @FXML private Label mTaskLbl;

    @FXML private Button mStartBtn;
    @FXML private Button mPauseBtn;
    @FXML private Button mResetBtn;

    @FXML private Button mAddBtn;
    @FXML private Button mDelBtn;

    private PomodoroModel mModel;
    private PomodoroTimer mTimer;

    public PomodoroController(){

        mModel = new PomodoroModel();
        mTimer = new PomodoroTimer();
        mModel.testList();
    }

    @FXML
    public void initialize() {
        SetupTableView();
        mTimerLbl.textProperty().bind(mTimer.getCurrentTime());
        mPauseBtn.setDisable(true);
    }

    @FXML
    public void start_OnAction(ActionEvent actionEvent) {
        Duration duration = Duration.ofSeconds(10);


        mPauseBtn.setDisable(false);
        mAddBtn.setDisable(true);
        mDelBtn.setDisable(true);
        mTaskTblView.setEditable(false);

        mTimer.setTime(duration);
        mTimer.start();

    }

    @FXML
    public void pause_OnAction(ActionEvent actionEvent) {
        mTimer.pause();
        mStartBtn.setDisable(false);
        mPauseBtn.setDisable(true);
    }

    @FXML
    public void reset_OnAction(ActionEvent actionEvent){
        mTimer.reset(Duration.ofSeconds(10));
        mStartBtn.setDisable(false);
    }

    @FXML
    public void add_OnAction(ActionEvent actionEvent) {

    }

    @FXML
    public void delete_OnAction(ActionEvent actionEvent) {

        int index = mTaskTblView.getSelectionModel().getSelectedIndex();
        if (index < 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mTaskTblView.getScene().getWindow());
            alert.setTitle("No Selection");
            alert.setHeaderText("No task selected");
            alert.setContentText("Please select a task in the table.");

            alert.showAndWait();
        }
        else{
            mTaskTblView.getItems().remove(index);
        }
    }

    private void SetupTableView(){

        mTaskTblView.setItems(mModel.getmTaskList());

        mTaskCol.setMinWidth(100);
        mTaskCol.setCellValueFactory(taskData -> taskData.getValue().mTaskNameProperty());

        mTimeCol.setMinWidth(100);
        mTimeCol.setCellValueFactory(taskData -> new SimpleStringProperty(PomodoroUtil.ConvertDurationToTimeString(taskData.getValue().getmTime())));
    }
}
