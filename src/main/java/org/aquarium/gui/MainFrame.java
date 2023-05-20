package org.aquarium.gui;

import org.aquarium.state.ParametersService;
import org.aquarium.state.CoreService;
import org.aquarium.state.TimeService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.*;

public class MainFrame extends JFrame {
    private final int menuWidth = 300;
    private GameBoard gameBoard;
    private Menu sideMenu;
    private CoreService coreService;
    private TimeService timeService;

    public MainFrame() {
        super();
        ParametersService parametersService = ParametersService.getInstance();

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu gameMenu = new JMenu("GAME");
        JMenu helpMenu = new JMenu("HELP");

        menuBar.add(gameMenu);
        menuBar.add(helpMenu);

        JMenuItem start = new JMenuItem("Start");
        JMenuItem end = new JMenuItem("Exit");
        JMenuItem scores = new JMenuItem("Best scores");
        JMenuItem information = new JMenuItem("Information");

        gameMenu.add(start);
        gameMenu.add(new JSeparator());
        gameMenu.add(end);
        gameMenu.add(new JSeparator());
        gameMenu.add(scores);
        helpMenu.add(information);

        this.setSize(parametersService.getInitialBoardWidth() + menuWidth, parametersService.getInitialBoardHeight());
        setLayout(new BorderLayout());

        sideMenu = new Menu(menuWidth);
        gameBoard = new GameBoard(sideMenu);


        sideMenu.connectToGUI(gameBoard, this);

        add(gameBoard, BorderLayout.CENTER);
        add(sideMenu, BorderLayout.EAST);

        timeService = TimeService.getInstance();
        timeService.connectToGUI(sideMenu);

        coreService = CoreService.getInstance();
        coreService.connectToGUI(gameBoard, sideMenu);

        setLocationByPlatform(true);
        setTitle(parametersService.getGameName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(true);
        Image imageIcon = new ImageIcon("src/main/resources/assets/fishes/fish1.png").getImage();
        setIconImage(imageIcon);

        end.addActionListener((ActionEvent event) -> {
                    sideMenu.endGame();
                    System.exit(0);
                }
        );

        scores.addActionListener((ActionEvent event) -> {
                    BestResults NW = BestResults.getInstance();
                    NW.showScores();
                }
        );

        information.addActionListener(new Help());

        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (coreService.isGameRunning() == false) {
                    coreService.goToNextLevel();
                }
            }
        });

        this.addComponentListener(new ComponentListener() {
                                      public void componentHidden(ComponentEvent event) {
                                      }

                                      public void componentMoved(ComponentEvent event) {
                                      }

                                      public void componentResized(ComponentEvent event) {
                                          int boardWidth = getWidth() - menuWidth;
                                          int boardHeight = getHeight();
                                          gameBoard.changeTheBoardSize(boardWidth, boardHeight);
                                      }

                                      public void componentShown(ComponentEvent event) {
                                      }
                                  }
        );
    }

    public void setAnimationSize() {
        int boardWidth = getWidth() - menuWidth;
        int boardHeight = getHeight();
        gameBoard.changeTheBoardSize(boardWidth, boardHeight);
        gameBoard.repaint();
    }
}