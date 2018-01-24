package pomodoroApp;;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.FXML;

import java.time.Duration;
import java.util.Queue;

public class PomodoroController {

    @FXML private TableColumn mTaskCol;
    @FXML private TableColumn mTimeCol;

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
    }

    @FXML
    public void initialize() {
        mTimerLbl.textProperty().bind(mTimer.getCurrentTime());
        mPauseBtn.setDisable(true);
    }


    @FXML
    public void start_OnAction(ActionEvent actionEvent) {
        Duration duration = Duration.ofSeconds(10);
        mTimer.setTime(duration);
        mTimer.start();

        mStartBtn.setDisable(true);
        mPauseBtn.setDisable(false);
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

    }

    private void UpdateTableViewFromModel(){
        Queue<PomodoroTask> taskList = mModel.getTaskList();
        for (PomodoroTask task:
             taskList) {
        }
    }
}
