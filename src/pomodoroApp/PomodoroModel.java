package pomodoroApp;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class PomodoroModel {

    private ObservableList<PomodoroTask> mTaskList;

    public PomodoroModel(){
        mTaskList = FXCollections.observableList(new LinkedList<PomodoroTask>());
    }

    public ObservableList<PomodoroTask> getmTaskList() {
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

    public void testList(){
        addTask("Test1", Duration.ofMinutes(25));
        addTask("Test2", Duration.ofMinutes(30));
        addTask("Test3", Duration.ofMinutes(45));
        addTask("Test4", Duration.ofMinutes(60));
    }
}
