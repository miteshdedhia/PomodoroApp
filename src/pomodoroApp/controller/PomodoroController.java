package pomodoroApp.controller;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pomodoroApp.*;
import pomodoroApp.model.PomodoroModel;
import pomodoroApp.model.PomodoroTask;
import pomodoroApp.model.PomodoroTimer;
import pomodoroApp.util.PomodoroUtil;

import java.io.IOException;
import java.time.Duration;

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

    private TableView.TableViewSelectionModel<PomodoroTask> mdefaultSelectionModel;

    public PomodoroController(){
        mTimer = new PomodoroTimer();
        mModel = new PomodoroModel();
    }

    @FXML
    private void initialize() {
        setupTableView();
        mTimer.setOnTimerFinshed(event -> onTimerFinished());
        mTimerLbl.textProperty().bind(mTimer.getCurrentTime());
        mPauseBtn.setDisable(true);

    }

    @FXML
    private void start_OnAction() {
        TableView.TableViewSelectionModel<PomodoroTask> selectionModel = mTaskTblView.getSelectionModel();
        PomodoroTask selectedTask = selectionModel.getSelectedItem();
        if(mTimer.isPaused()){
            setButtonStatesOnStart();
            mTimer.start();
        }
        else if(selectedTask != null) {
            setViewWithTask(selectedTask);
            setButtonStatesOnStart();
            mTimer.start();
        }
        else {
            showNoSelectionWarning();
        }
    }

    @FXML
    private void pause_OnAction() {
        setButtonStateOnPause();
        mTimer.pause();
    }

    @FXML
    private void reset_OnAction(){
        PomodoroTask selectedTask = mTaskTblView.getSelectionModel().getSelectedItem();
        if(selectedTask != null) {
            mTimer.reset(selectedTask.getTime());
            mTaskLbl.setText(selectedTask.getTaskName());
            mStartBtn.setDisable(false);
        }
        else {
            showNoSelectionWarning();
        }
    }

    @FXML
    private void add_OnAction() {
        PomodoroTask newTask = showTaskEditDialog(PomodoroTask.CreateDefaultTask());
        if(newTask != null){
            mModel.addTask(newTask);
            if(mModel.getTaskCount() == 2)
                mTaskTblView.getSelectionModel().selectFirst();
        }
    }

    @FXML
    private void edit_OnMouseClicked() {
        TableView.TableViewSelectionModel<PomodoroTask> selectionModel = mTaskTblView.getSelectionModel();
        if(selectionModel != null) {
            PomodoroTask selectedTask = selectionModel.getSelectedItem();
            int index = mTaskTblView.getSelectionModel().getSelectedIndex();
            if (selectedTask != null && index >= 0) {
                PomodoroTask editedTask = showTaskEditDialog(selectedTask);
                if (editedTask != null) {
                    mModel.editTask(index, editedTask);
                    mTaskTblView.refresh();
                }
            }
        }
    }

    @FXML
    private void delete_OnAction() {
        if(mTaskTblView.getItems().size() > 0) {
            int index = mTaskTblView.getSelectionModel().getSelectedIndex();
            if (index < 0) {
                showNoSelectionWarning();
            } else {
                mModel.removeTask(index);
            }
        }
    }

    private void showNoSelectionWarning() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(mTaskTblView.getScene().getWindow());
        alert.setTitle("No Selection");
        alert.setHeaderText("No task selected");
        alert.setContentText("Please select a task in the table.");
        alert.showAndWait();
    }

    private void showStartNextTaskDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(mTaskTblView.getScene().getWindow());
        alert.setTitle("Next Task");
        alert.setHeaderText("Start next task");
        alert.setContentText("Do you want to start the next task?");
        alert.showAndWait().ifPresent(response ->{
            if(response == ButtonType.OK){
                mTaskTblView.getSelectionModel().selectNext();
                start_OnAction();
            }
            else {
                setButtonStateOnPause();
            }
        });
    }

    private void onTimerFinished(){
        //TODO on timer done setup next task and or play music
        mTaskTblView.setSelectionModel(mdefaultSelectionModel);
        Platform.runLater(() -> showStartNextTaskDialog());
    }

    private void setupTableView(){
        mTaskTblView.setItems(mModel.getTaskList());
        mTaskCol.setCellValueFactory(taskData -> taskData.getValue().mTaskNameProperty());
        mTimeCol.setCellValueFactory(taskData -> new SimpleStringProperty(
                PomodoroUtil.ConvertDurationToTimeString(taskData.getValue().getTime())));
        mdefaultSelectionModel = mTaskTblView.getSelectionModel();
        mdefaultSelectionModel.selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if(newSelection != null)
                        setViewWithTask(newSelection);
                    else
                        resetView();
                });

        mTaskTblView.setRowFactory(tv -> {
            TableRow<PomodoroTask> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty()) ) {
                    edit_OnMouseClicked();
                }
            });
            return row ;
        });
    }

    public PomodoroTask showTaskEditDialog(PomodoroTask task) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/fxml/taskEditView.fxml"));
            Parent root = loader.load();
            TaskEditController controller = loader.getController();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Task");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mTaskTblView.getScene().getWindow());
            dialogStage.setScene(new Scene(root));

            controller.setDialogStage(dialogStage);
            controller.setTask(task);

            dialogStage.showAndWait();

            return controller.getOkClicked() ? controller.getTask() : null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setButtonStatesOnStart(){
        mStartBtn.setDisable(true);
        mResetBtn.setDisable(true);
        mPauseBtn.setDisable(false);
        mAddBtn.setDisable(true);
        mDelBtn.setDisable(true);
        mTaskTblView.setSelectionModel(null);
    }

    private void setButtonStateOnPause() {
        mPauseBtn.setDisable(true);
        mResetBtn.setDisable(false);
        mStartBtn.setDisable(false);
        mAddBtn.setDisable(false);
        mDelBtn.setDisable(false);
        mTaskTblView.setSelectionModel(mdefaultSelectionModel);
    }

    private void setViewWithTask(PomodoroTask selectedTask){
        mTimer.setTime(selectedTask.getTime());
        mTaskLbl.setText(selectedTask.getTaskName());
    }

    private void resetView(){
        mTimer.setTime(Duration.ofSeconds(0));
        mTaskLbl.setText("");
    }
}
