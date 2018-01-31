package pomodoroApp.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.Duration;
import java.util.LinkedList;

public class PomodoroModel {

    private ObservableList<PomodoroTask> mTaskList;

    public PomodoroModel(){
        mTaskList = FXCollections.observableList(new LinkedList<PomodoroTask>());
    }

    public ObservableList<PomodoroTask> getmTaskList() {
        return mTaskList;
    }

    public void addTask(PomodoroTask task){
        mTaskList.add(task);
        mTaskList.add(PomodoroTask.CreateBreakTask());
    }

    public void removeTask(int index){
        mTaskList.remove(index);
    }

    public void editTask(int index, PomodoroTask editedTask){
        PomodoroTask task = mTaskList.get(index);
        task.setmTaskName(editedTask.getmTaskName());
        task.setmTime(editedTask.getmTime());
    }

    public void testList(){
        addTask(new PomodoroTask("Test1", Duration.ofMinutes(25)));
        addTask(new PomodoroTask("Test2", Duration.ofMinutes(30)));
        addTask(new PomodoroTask("Test3", Duration.ofMinutes(45)));
        addTask(new PomodoroTask("Test4", Duration.ofMinutes(60)));
    }
}
