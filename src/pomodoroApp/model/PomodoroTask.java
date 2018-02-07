package pomodoroApp.model;

import javafx.beans.property.SimpleStringProperty;

import java.time.Duration;

public class PomodoroTask {

    private static final String DEFAULT_TASK_NAME = "Your Task";
    private static final int DEFAULT_TASK_DURATION = 25;
    private static final String SHORT_BREAK_TASK_NAME = "Short Break";
    private static final int SHORT_BREAK_TASK_TIME = 5;
    private static final String LONG_BREAK_TASK_NAME = "Long Break";
    private static final int LONG_BREAK_TASK_TIME = 15;

    private Duration mTime;
    final private SimpleStringProperty mTaskName;
    final private boolean mIsBreakTask;

    private PomodoroTask(String taskName, Duration time, boolean isBreak)
    {
        mTaskName = new SimpleStringProperty(taskName);
        mTime = time;
        mIsBreakTask = isBreak;
    }

    public static PomodoroTask CreateDefaultTask(){
        return new PomodoroTask(DEFAULT_TASK_NAME, Duration.ofMinutes(DEFAULT_TASK_DURATION), false);
    }

    public static PomodoroTask CreateShortBreakTask(){
        return new PomodoroTask(SHORT_BREAK_TASK_NAME, Duration.ofMinutes(SHORT_BREAK_TASK_TIME), true);
    }

    public static PomodoroTask CreateLongBreakTask(){
        return new PomodoroTask(LONG_BREAK_TASK_NAME, Duration.ofMinutes(LONG_BREAK_TASK_TIME), true);
    }

    public Duration getTime() {
        return mTime;
    }

    public void setTime(Duration mTime) {
        this.mTime = mTime;
    }

    public String getTaskName() {
        return mTaskName.get();
    }

    public void setTaskName(String mTaskName) {
        this.mTaskName.set(mTaskName);
    }

    public SimpleStringProperty mTaskNameProperty() {
        return mTaskName;
    }

    public boolean isBreakTask() {
        return mIsBreakTask;
    }
}
