package pomodoroApp;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import java.time.Duration;

public class PomodoroTimer {

    private Timeline mTimer;
    private Duration mDuration;
    private SimpleStringProperty mCurrentTime;

    private boolean mPaused;

    public PomodoroTimer(){
        final int DEFAULT_POMODORO_MINS = 25;
        mDuration = Duration.ofMinutes(DEFAULT_POMODORO_MINS);
        mTimer = new Timeline(new KeyFrame(javafx.util.Duration.seconds(1), event -> updateTimerProperty()));
        mTimer.setCycleCount(Timeline.INDEFINITE);
        mCurrentTime = new SimpleStringProperty("00:00:00");
        mPaused = false;
    }

    private void updateTimerProperty(){
        if(mDuration.toSeconds() > 0) {
            mDuration = mDuration.minusSeconds(1);
            mCurrentTime.setValue(PomodoroUtil.ConvertDurationToTimeString(mDuration));
        }
        else {
            mPaused = false;
        }
    }

    public void start() {
        mTimer.play();
        mPaused = false;
    }

    public void pause () {
        mTimer.pause();
        mPaused = true;
    }

    public void reset (Duration duration) {
        mTimer.stop();
        mDuration = duration;
        mCurrentTime.setValue(PomodoroUtil.ConvertDurationToTimeString(duration));
        mPaused = false;
    }

    public SimpleStringProperty getCurrentTime(){
        return mCurrentTime;
    }

    public void setTime(Duration duration){
        mDuration = duration;
        mTimer.setCycleCount((int)duration.toSeconds());
        mCurrentTime.setValue(PomodoroUtil.ConvertDurationToTimeString(duration));
    }

    public boolean isPaused() {
        return mPaused;
    }

    public void setOnTimerFinshed(EventHandler<ActionEvent> event){
        mTimer.setOnFinished(event);
    }
}