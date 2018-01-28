package pomodoroApp;

import javafx.beans.property.SimpleStringProperty;

import java.time.Duration;

public class PomodoroTask {

    private static final String DEFAULT_TASK_NAME = "Your Task";
    private static final int DEFAULT_TASK_DURATION = 25;
    private static final String BREAK_TASK_NAME = "BREAK";
    private static final int BREAK_TASK_TIME = 5;

    private Duration mTime;
    private SimpleStringProperty mTaskName;

    public PomodoroTask(String taskName, Duration time)
    {
        mTaskName = new SimpleStringProperty(taskName);
        mTime = time;
    }

    public static PomodoroTask CreateDefaultTask(){
        return new PomodoroTask(DEFAULT_TASK_NAME, Duration.ofMinutes(DEFAULT_TASK_DURATION));
    }

    public static PomodoroTask CreateBreakTask(){
        return new PomodoroTask(BREAK_TASK_NAME, Duration.ofMinutes(BREAK_TASK_TIME));
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
