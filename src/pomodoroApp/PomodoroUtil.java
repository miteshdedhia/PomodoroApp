package pomodoroApp;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class PomodoroUtil {

    public static Duration ConvertTimeStringToDuration(String time){
        int minutes;
        int seconds;
        String[] parts = time.split ( ":" );
        Duration d = Duration.ZERO;

        if ( parts.length == 3 ) {
            int hours = Integer.parseInt(parts[0]);
            minutes = Integer.parseInt(parts[1]);
            seconds = Integer.parseInt(parts[2]);
            d = d.plusHours(hours).plusMinutes(minutes).plusSeconds(seconds);
        } else if ( parts.length == 2 ) {
            minutes = Integer.parseInt(parts[0]);
            seconds = Integer.parseInt(parts[1]);
            d = d.plusMinutes(minutes).plusSeconds(seconds);
        } else {
            System.out.println ( "ERROR - Unexpected input." );
        }
        return d;
    }

    public static String ConvertDurationToTimeString(Duration totalTime){

        long millis = totalTime.toMillis();
        return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
    }
}

