package org.aquarium.gui;

import org.aquarium.state.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Menu extends JPanel {
    private int width;
    private JButton start;
    private JButton pause;
    private JButton end;
    private JButton nextLevel;

    private JLabel score;
    private JLabel time;
    private JLabel levelNumber;
    private JLabel title;

    private GameBoard gameBoard;
    private CoreService cs;
    ParametersService gp;
    private MainFrame mainFrame;

    private boolean isGamePaused;

    TimeService ts;

    public Menu(int width) {
        super();
        this.width = width;
        gp = ParametersService.getInstance();
        ts = TimeService.getInstance();
        ts.connectToGUI(this);
        cs = CoreService.getInstance();

        isGamePaused = false;

        setLayout(null);

        start = new JButton("start");
        pause = new JButton("pause");
        end = new JButton("end");
        nextLevel = new JButton("next level");

        score = new JLabel("score: 0/0");
        time = new JLabel("time: 0/0");
        levelNumber = new JLabel("level: 0");
        title = new JLabel(gp.getGameName() + "." + gp.getExtensionName());

        title.setBounds(width / 3, gp.getInitialBoardHeight() / 12, 200, 30);
        levelNumber.setBounds(width / 3, 2 * gp.getInitialBoardHeight() / 12, 200, 30);
        score.setBounds(width / 3, 3 * gp.getInitialBoardHeight() / 12, 200, 30);
        time.setBounds(width / 3, 4 * gp.getInitialBoardHeight() / 12, 200, 30);
        start.setBounds(width / 3, 5 * gp.getInitialBoardHeight() / 12, 100, 30);
        pause.setBounds(width / 3, 6 * gp.getInitialBoardHeight() / 12, 100, 30);
        end.setBounds(width / 3, 7 * gp.getInitialBoardHeight() / 12, 100, 30);
        nextLevel.setBounds(width / 3, 8 * gp.getInitialBoardHeight() / 12, 100, 30);

        start.addActionListener((ActionEvent event) -> startGame());
        pause.addActionListener((ActionEvent event) -> pauseGame());
        end.addActionListener((ActionEvent event) -> endGame()); //new ActionListener() { public void actionPerformed(ActionEvent e){} }
        nextLevel.addActionListener((ActionEvent event) -> startNextLevel());

        add(title);
        add(levelNumber);
        add(score);
        add(time);
        add(start);
        add(pause);
        add(end);
        add(nextLevel);
    }

    public void paint(Graphics g) {
        ParametersService gp = ParametersService.getInstance();
        if (gp.getMenuBackground().equals("solid")) {
            super.paintComponent(g);
            this.paintChildren(g);
            setBackground(new Color(gp.getMenuBackgroundColor(0), gp.getMenuBackgroundColor(1), gp.getMenuBackgroundColor(2)));
        } else {
            this.paintChildren(g);
            setLayout(new BorderLayout());
            JLabel background = new JLabel(new ImageIcon(gp.getMenuBackgroundFilePathname()));
            add(background);
        }
    }

    public Dimension getPreferredSize() {
        ParametersService gp = ParametersService.getInstance();
        return new Dimension(width, gp.getInitialBoardHeight());
    }

    public void setScore() {
        PointsService cp = PointsService.getInstance();
        score.setText("score: " + cp.getPoints());
    }

    public void setLevel() {
        levelNumber.setText("level: " + cs.getCurrentLevelNumber() + "/" + gp.getNumberOfLevels());
    }

    public void setTime(int currentTime) {
        time.setText("Remaining time: " + currentTime + "/" + gp.getLevelDurationTimeValue(cs.getCurrentLevelNumber() - 1));
    }

    public void connectToGUI(GameBoard gameBoard, MainFrame mainFrame) {
        this.gameBoard = gameBoard;
        this.mainFrame = mainFrame;
    }

    public void startGame() {
        if (!cs.isGameRunning()) {
            String[] options = new String[(int) gp.getNumberOfDifficultyLevels()];
            for (int i = 0; i < gp.getNumberOfDifficultyLevels(); i++)
                options[i] = Integer.toString(i + 1);

            int difficultyLevel = JOptionPane.showOptionDialog(null, "Choose a difficulty level: ", "Difficulty level selection", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
            gameBoard.setGameObjectsSpeed(difficultyLevel + 1);

            cs.goToNextLevel();
        }
    }

    public void pauseGame() {
        if (cs.isGameRunning()) {
            if (!isGamePaused) {
                isGamePaused = true;
                ts.stopTimer();
                cs.togglePause();
            } else {
                isGamePaused = false;
                ts.resumeTimer();
                cs.togglePause();
            }
        }
    }

    public void startNextLevel() {
        if (cs.isGameRunning()) {
            if (cs.getCurrentLevelNumber() < gp.getNumberOfLevels()) {
                cs.endLevel();
                cs.goToNextLevel();
            }
        }
        mainFrame.setAnimationSize();
    }

    public void endGame() {
        cs.endGame();
    }
}


