package pomodoroApp;

import javafx.beans.property.SimpleStringProperty;

import java.time.Duration;

public class PomodoroTask {

    private Duration mTime;
    private SimpleStringProperty mTaskName;

    public PomodoroTask(String taskName, Duration time)
    {
        mTaskName = new SimpleStringProperty(taskName);
        mTime = time;
    }

    public Duration getmTime() {
        return mTime;
    }

    public void setmTime(Duration mTime) {
        this.mTime = mTime;
    }

    public String getmTaskName() {
        return mTaskName.get();
    }

    public void setmTaskName(String mTaskName) {
        this.mTaskName.set(mTaskName);
    }

    public SimpleStringProperty mTaskNameProperty() {
        return mTaskName;
    }
}
