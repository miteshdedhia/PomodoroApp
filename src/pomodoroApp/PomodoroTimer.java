package pomodoroApp;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;

import java.time.Duration;

public class PomodoroTimer {

    final int DEFAULT_POMODORO_MINS = 25;
    private Timeline mTimer;
    private Duration mDuration;
    private SimpleStringProperty mCurrentTime;

    public PomodoroTimer(){

        mDuration = Duration.ofMinutes(DEFAULT_POMODORO_MINS);
        mTimer = new Timeline(new KeyFrame(javafx.util.Duration.seconds(1), event -> updateTimerProperty()));
        mTimer.setCycleCount(Timeline.INDEFINITE);
        mCurrentTime = new SimpleStringProperty("00:00:00");
    }

    public void updateTimerProperty(){
        if(mDuration.toSeconds() > 0) {
            mDuration = mDuration.minusSeconds(1);
            mCurrentTime.setValue(PomodoroUtil.ConvertDurationToTimeString(mDuration));
        }
        else
            mTimer.stop();
    }

    public void start() {
        mTimer.play();
    }

    public void pause () {
        mTimer.pause();
    }

    public void reset (Duration duration) {
        mTimer.stop();
        mDuration = duration;
        mCurrentTime.setValue(PomodoroUtil.ConvertDurationToTimeString(duration));
    }

    public SimpleStringProperty getCurrentTime(){
        return mCurrentTime;
    }

    public void setTime(Duration duration){
        mDuration = duration;
        mCurrentTime.setValue(PomodoroUtil.ConvertDurationToTimeString(duration));
    }
}