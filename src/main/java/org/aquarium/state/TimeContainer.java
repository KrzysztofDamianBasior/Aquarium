package org.aquarium.state;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;

public class TimeContainer implements ActionListener {

    int time;
    Timer clock;
    int numberOfLevels;
    private ArrayList<Integer> levelsDurationTime = new ArrayList<Integer>(200);
    private static final TimeContainer instance = new TimeContainer();

    public static TimeContainer getInstance() {
        return instance;
    }

    private TimeContainer() {
        GameParameters gp = GameParameters.getInstance();
        time = 0;
        clock = new Timer(1000, this);
        clock.start();
        numberOfLevels = gp.getNumberOfLevels();

        for (int i = 0; i < numberOfLevels; i++) {
            levelsDurationTime.add(gp.getLevelDurationTimeValue(i));
        }
    }
    public void actionPerformed(ActionEvent e) {
        LevelInfo li = LevelInfo.getInstance();

        if (time > 1000)        //protects against exceeding the range
            time = 0;

        if(time < ( levelsDurationTime.get(li.getLevelNumber()-1) )) {
            time++;
        }
    }
}