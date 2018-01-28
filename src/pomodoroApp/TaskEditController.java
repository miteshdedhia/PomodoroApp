package pomodoroApp;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class TaskEditController {

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
        mTimeHrTxtBox.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    mTimeHrTxtBox.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        mTimeMinTxtBox.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    mTimeMinTxtBox.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        mTimeSecTxtBox.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    mTimeSecTxtBox.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    @FXML
    private void ok_OnAction() {
        if (isInputValid()) {
            mTask.setmTaskName(mTaskNameTextBox.getText());
            String timeString = String.format("%s:%s:%s",
                    mTimeHrTxtBox.getText(), mTimeMinTxtBox.getText(), mTimeSecTxtBox.getText());
            mTask.setmTime(PomodoroUtil.ConvertTimeStringToDuration(timeString));
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

        mTaskNameTextBox.setText(task.getmTaskName());
        String timeString = PomodoroUtil.ConvertDurationToTimeString(task.getmTime());
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
        else if(taskName.length() > 100)
            errorMessage.append("Task name too long. Max limit: 100 characters.\n");

        try {
            Integer.parseInt(hr);
        }
        catch (NumberFormatException e){
            errorMessage.append("Hour should be a number.\n");
        }

        try {
            if(Integer.parseInt(min) > 59)
                errorMessage.append("Minutes cannot be more than 59.");
        }
        catch (NumberFormatException e){
            errorMessage.append("Minute should be a number.\n");
        }

        try {
            if(Integer.parseInt(sec) > 59)
                errorMessage.append("Seconds cannot be more than 59.");
        }
        catch (NumberFormatException e){
            errorMessage.append("Seconds should be a number.\n");
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

            alert.showAndWait();

            return false;
        }
    }


}
