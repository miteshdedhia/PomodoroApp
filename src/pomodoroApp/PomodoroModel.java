package pomodoroApp;

import javafx.beans.property.StringProperty;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class PomodoroModel {

    private LinkedList<PomodoroTask> mTaskList;

    public void PomodoroModel(){
        mTaskList = new LinkedList<PomodoroTask>();
    }

    public Queue<PomodoroTask> getTaskList() {
        return mTaskList;
    }

    public void addTask(String name, Duration time){
        mTaskList.add(new PomodoroTask(name, time));
    }

    public void removeTask(int index){
        mTaskList.remove(index);
    }

    public void editTask(String name, Duration time, int index){
        PomodoroTask task = mTaskList.get(index);
        task.setmTaskName(name);
        task.setmTime(time);
    }
}
