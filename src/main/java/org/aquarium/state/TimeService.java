package org.aquarium.state;

import org.aquarium.gui.Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class TimeService implements ActionListener {
    private int time;
    private Timer clock;
    private Menu sideMenu;
    private TimerState timerState;

    private static final TimeService instance = new TimeService();

    public static TimeService getInstance() {
        return instance;
    }

    private ParametersService parametersService;
    private CoreService coreService;

    private TimeService() {
        time = 0;
        timerState = TimerState.DISABLED;
        clock = new Timer(1000, this);
        clock.start();
    }

    public void connectToGUI(Menu sideMenu) {
        parametersService = ParametersService.getInstance();
        coreService = CoreService.getInstance();
        this.sideMenu = sideMenu;
    }

    public void resumeTimer() {
        timerState = TimerState.ENABLED;
    }

    public void resetAndStartTimer() {
        timerState = TimerState.ENABLED;
        time = 0;
    }

    public void stopTimer() {
        timerState = TimerState.DISABLED;
    }

    public void actionPerformed(ActionEvent e) {
        if (time > 1000) {        //protects against exceeding the range
            time = 0;
        }

        if (coreService != null && parametersService != null && sideMenu != null) {
            if (timerState == TimerState.ENABLED) {
                if (time < (parametersService.getLevelDurationTimeValue(coreService.getCurrentLevelNumber() - 1))) {
                    sideMenu.setTime(time);
                    double draw = Math.random();
                    if (draw <= 0.2) {
                        coreService.addOneGameObjectToGameBoard();
                    }
                    time++;
                } else {
                    if (parametersService.getNumberOfLevels() > coreService.getCurrentLevelNumber()) {
                        stopTimer();
                        sideMenu.startNextLevel();
                    } else {
                        stopTimer();
                        sideMenu.endGame();
                    }
                }
            }
        }
    }

    private enum TimerState {
        ENABLED, DISABLED
    }
}