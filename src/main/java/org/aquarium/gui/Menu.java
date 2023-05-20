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
    private CoreService coreService;
    ParametersService parametersService;
    private MainFrame mainFrame;

    private boolean isGamePaused;

    TimeService ts;

    public Menu(int width) {
        super();
        this.width = width;
        parametersService = ParametersService.getInstance();
        ts = TimeService.getInstance();
        ts.connectToGUI(this);
        coreService = CoreService.getInstance();

        isGamePaused = false;

        setLayout(null);

        start = new JButton("start");
        pause = new JButton("pause");
        end = new JButton("end");
        nextLevel = new JButton("next level");

        score = new JLabel("score: 0/0");
        score.setForeground(new Color(1, 50, 32));

        time = new JLabel("time: 0/0");
        time.setForeground(new Color(1, 50, 32));
        levelNumber = new JLabel("level: 0");
        levelNumber.setForeground(new Color(1, 50, 32));
        title = new JLabel(parametersService.getGameName() + "." + parametersService.getExtensionName());
        title.setForeground(Color.LIGHT_GRAY);
        title.setBounds(width / 3, parametersService.getInitialBoardHeight() / 12, 200, 30);
        levelNumber.setBounds(width / 3, 2 * parametersService.getInitialBoardHeight() / 12, 200, 30);
        score.setBounds(width / 3, 3 * parametersService.getInitialBoardHeight() / 12, 200, 30);
        time.setBounds(width / 3, 4 * parametersService.getInitialBoardHeight() / 12, 200, 30);
        start.setBounds(width / 3, 5 * parametersService.getInitialBoardHeight() / 12, 100, 30);
        pause.setBounds(width / 3, 6 * parametersService.getInitialBoardHeight() / 12, 100, 30);
        end.setBounds(width / 3, 7 * parametersService.getInitialBoardHeight() / 12, 100, 30);
        nextLevel.setBounds(width / 3, 8 * parametersService.getInitialBoardHeight() / 12, 100, 30);

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
        if (parametersService.getMenuBackground().equals("solid")) {
            super.paintComponent(g);
            this.paintChildren(g);
            setBackground(new Color(parametersService.getMenuBackgroundColor(0), parametersService.getMenuBackgroundColor(1), parametersService.getMenuBackgroundColor(2)));
        } else {
            this.paintChildren(g);
            setLayout(new BorderLayout());
            JLabel background = new JLabel(new ImageIcon(parametersService.getMenuBackgroundFilePathname()));
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
        levelNumber.setText("level: " + coreService.getCurrentLevelNumber() + "/" + parametersService.getNumberOfLevels());
    }

    public void setTime(int currentTime) {
        time.setText("Remaining time: " + currentTime + "/" + parametersService.getLevelDurationTimeValue(coreService.getCurrentLevelNumber() - 1));
    }

    public void connectToGUI(GameBoard gameBoard, MainFrame mainFrame) {
        this.gameBoard = gameBoard;
        this.mainFrame = mainFrame;
    }

    public void startGame() {
        if (!coreService.isGameRunning() && !coreService.isGamePaused()) {
            String[] options = new String[(int) parametersService.getNumberOfDifficultyLevels()];
            for (int i = 0; i < parametersService.getNumberOfDifficultyLevels(); i++)
                options[i] = Integer.toString(i + 1);

            int difficultyLevel = JOptionPane.showOptionDialog(null, "Choose a difficulty level: ", "Difficulty level selection", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
            gameBoard.setGameObjectsSpeed(difficultyLevel + 1);

            coreService.goToNextLevel();
        }
    }

    public void pauseGame() {
        if (coreService.isGameRunning() || coreService.isGamePaused()) {
            if (!isGamePaused) {
                isGamePaused = true;
                ts.stopTimer();
                coreService.togglePause();
            } else {
                isGamePaused = false;
                ts.resumeTimer();
                coreService.togglePause();
            }
        }
    }

    public void startNextLevel() {
        if (coreService.isGameRunning()) {
            if (coreService.getCurrentLevelNumber() < parametersService.getNumberOfLevels()) {
                coreService.endLevel();
                coreService.goToNextLevel();
            }
        }
        mainFrame.setAnimationSize();
    }

    public void endGame() {
        coreService.endGame();
    }
}


