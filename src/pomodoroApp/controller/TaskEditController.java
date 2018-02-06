package pomodoroApp.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pomodoroApp.model.PomodoroTask;
import pomodoroApp.util.PomodoroUtil;

import static pomodoroApp.controller.PomodoroController.MAIN_VIEW_CSS_PATH_CONTROLLER_REL;


public class TaskEditController {

    private static final Integer MAX_TIME_FIELD = 59;

    @FXML
    private TextField mTaskNameTextBox;

    @FXML
    private TextField mTimeHrTxtBox;

    @FXML
    private TextField mTimeMinTxtBox;

    @FXML
    private TextField mTimeSecTxtBox;

    private Stage mDialogStage;
    private PomodoroTask mTask;
    private Boolean mOkClicked;

    @FXML
    private void initialize() {
        mOkClicked = false;
        setupTextFields();
    }

    @FXML
    private void ok_OnAction() {
        if (isInputValid()) {
            mTask.setTaskName(mTaskNameTextBox.getText());
            String timeString = String.format("%s:%s:%s",
                    mTimeHrTxtBox.getText(), mTimeMinTxtBox.getText(), mTimeSecTxtBox.getText());
            mTask.setTime(PomodoroUtil.ConvertTimeStringToDuration(timeString));
            mOkClicked = true;
            mDialogStage.close();
        }
    }

    @FXML
    private void cancel_OnAction() {
        mOkClicked = false;
        mDialogStage.close();
    }

    public PomodoroTask getTask() {
        return mTask;
    }

    public Boolean getOkClicked() {
        return mOkClicked;
    }

    public void setDialogStage(Stage dialogStage) {
        this.mDialogStage = dialogStage;
    }

    public void setTask(PomodoroTask task) {
        this.mTask = task;

        mTaskNameTextBox.setText(task.getTaskName());
        String timeString = PomodoroUtil.ConvertDurationToTimeString(task.getTime());
        String timeSplit[] = timeString.split(":");
        mTimeHrTxtBox.setText(timeSplit[0].trim());
        mTimeMinTxtBox.setText(timeSplit[1].trim());
        mTimeSecTxtBox.setText(timeSplit[2].trim());
    }

    private boolean isInputValid() {
        StringBuilder errorMessage = new StringBuilder();

        String taskName = mTaskNameTextBox.getText();
        String hr = mTimeHrTxtBox.getText();
        String min = mTimeMinTxtBox.getText();
        String sec = mTimeSecTxtBox.getText();

        if(taskName.length() == 0)
            errorMessage.append("Task name cannot be empty.\n");

        try{
            if(Integer.parseInt(hr) <= 0 && Integer.parseInt(min) <= 0 && Integer.parseInt(sec) <= 0)
                errorMessage.append("Time limit for the task cannot not be zero\n");
        }
        catch (NumberFormatException e){
            errorMessage.append("Please enter a time limit for the task\n");
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(mDialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage.toString());
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource(MAIN_VIEW_CSS_PATH_CONTROLLER_REL).toExternalForm());
            alert.showAndWait();

            return false;
        }
    }

    private void setupTextFields() {
        mTaskNameTextBox.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length() > 25){
                    mTaskNameTextBox.setText(oldValue);
                }
            }
        });

        mTimeHrTxtBox.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                validateInput(mTimeHrTxtBox, oldValue, newValue);
            }
        });

        mTimeMinTxtBox.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(validateInput(mTimeMinTxtBox, oldValue, newValue)){
                    validateTimeValue(mTimeMinTxtBox, newValue);
                }
            }
        });

        mTimeSecTxtBox.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(validateInput(mTimeSecTxtBox, oldValue, newValue)){
                    validateTimeValue(mTimeSecTxtBox, newValue);
                }
            }
        });
    }

    private static void validateTimeValue(TextField txtField, String newValue) {
        try
        {
            int currentValue = Integer.parseInt(newValue);
            if(currentValue > MAX_TIME_FIELD){
                txtField.setText(MAX_TIME_FIELD.toString());
            }
        }
        catch (NumberFormatException e){
        }
    }

    private static boolean validateInput(TextField txtField, String oldValue, String newValue) {
        boolean error = true;
        if (!newValue.matches("\\d*")) {
            txtField.setText(newValue.replaceAll("[^\\d]", ""));
            error = false;
        }
        if(newValue.length() > 2){
            txtField.setText(oldValue);
            error = false;
        }
        return error;
    }
}
