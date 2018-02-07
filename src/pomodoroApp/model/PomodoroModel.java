package pomodoroApp.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.Duration;
import java.util.LinkedList;

public class PomodoroModel {

    final private ObservableList<PomodoroTask> mTaskList;
    private Duration mTotalDuration;

    public PomodoroModel(){
        mTaskList = FXCollections.observableList(new LinkedList<PomodoroTask>());
        mTotalDuration = Duration.ofSeconds(0);
    }

    public ObservableList<PomodoroTask> getTaskList() {
        return mTaskList;
    }

    public void addTask(PomodoroTask task){
        mTotalDuration = mTotalDuration.plusSeconds(task.getTime().toSeconds());
        mTaskList.add(task);
        if(mTotalDuration.toMinutes() <= 100){
            mTaskList.add(PomodoroTask.CreateShortBreakTask());
        }
        else {
            mTaskList.add(PomodoroTask.CreateLongBreakTask());
            mTotalDuration = Duration.ofSeconds(0);
        }
    }

    public void removeTask(int index){
        if(index < mTaskList.size()) {
            PomodoroTask task = mTaskList.get(index);
            if (!task.isBreakTask()) {
                if (mTotalDuration.toSeconds() != 0)
                    mTotalDuration = mTotalDuration.minusSeconds(task.getTime().toSeconds());
                else
                    recalculateTotalDuration();
            }
            mTaskList.remove(index);
        }
    }

    public void editTask(int index, PomodoroTask editedTask){
        PomodoroTask task = mTaskList.get(index);
        task.setTaskName(editedTask.getTaskName());
        task.setTime(editedTask.getTime());
        if(!task.isBreakTask()) {
            mTotalDuration = mTotalDuration.minusSeconds(task.getTime().toSeconds());
            mTotalDuration = mTotalDuration.plusSeconds(editedTask.getTime().toSeconds());
        }
    }

    private void recalculateTotalDuration(){
        for (PomodoroTask task:
             mTaskList) {
            mTotalDuration = mTotalDuration.plusSeconds(task.getTime().toSeconds());
        }
    }
    public int getTaskCount(){
        return mTaskList.size();
    }
}
