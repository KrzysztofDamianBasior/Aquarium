package org.aquarium.gui;

import org.aquarium.state.CoreService;
import org.aquarium.state.ParametersService;
import org.aquarium.state.PointsService;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameBoard extends JPanel implements Runnable {
    enum BoardState {
        TIME_OVER, PAUSED, DISABLED, ENABLED,
    }

    private int height;
    private int width;
    private ArrayList<GameObject> gameObjects = new ArrayList<>(200);
    Thread thread = null;
    private int gameObjectsNumber;
    private int objectsSpeed;
    private int difficultyLevel;
    private double objectSize;

    JLabel background;

    private ParametersService ps;
    private CoreService cs;

    BoardState boardState;
    Menu menu;

    public GameBoard(Menu side_menu) {
        super();
        ps = ParametersService.getInstance();
        cs = CoreService.getInstance();

        height = ps.getInitialBoardHeight();
        width = ps.getInitialBoardWidth();

        difficultyLevel = 1;
        boardState = BoardState.DISABLED;

        Toolkit tk = Toolkit.getDefaultToolkit();
        Image img = tk.getImage("./src/main/resources/assets/cursors/gameCursor.png");
        Cursor myCursor = tk.createCustomCursor(img, new Point(10, 10), "dynamite stick");
        setCursor(myCursor);

        objectSize = ps.getInitialWidthOfTheGameObjectAsAPercentageOfBoardInitialWidth();
        gameObjectsNumber = 20;
        for (int i = 0; i < gameObjectsNumber; i++) {
            gameObjects.add(new GameObject(this));
        }
        objectsSpeed = 50;

        addMouseListener(new MouseHandler());
        menu = side_menu;

        this.background = new JLabel(new ImageIcon(ps.getMenuBackgroundFilePathname()));
    }

    public boolean deleteObject(int x, int y) {
        int size = (int) (width * objectSize / 100);
        for (int i = 0; i < gameObjects.size(); i++) {
            if (x >= gameObjects.get(i).getXPosition()
                    && y >= gameObjects.get(i).getYPosition()
                    && x <= (gameObjects.get(i).getXPosition() + size)
                    && y <= (gameObjects.get(i).getYPosition() + size)) {
                gameObjects.remove(i);
                gameObjectsNumber--;
                return true;
            }
        }
        return false;
    }

    public void paint(Graphics g) {
        this.paintChildren(g);
        setLayout(new BorderLayout());
        this.background = new JLabel(new ImageIcon(ps.getLevelBackgroundFilePathname(0)));
        add(background);

        if (boardState == BoardState.ENABLED) {
            for (int i = 0; i < gameObjects.size(); i++) {
                (gameObjects.get(i)).paint(g);
            }
        }
        if (boardState == BoardState.TIME_OVER) {
            g.setColor(Color.red);
            g.drawString("End of Level-> go to the next one or end the game", 50, 50);
        }
    }

    public void timeOver() {
        boardState = BoardState.TIME_OVER;
    }

    public void animationPause() {
        if (boardState == BoardState.ENABLED) {
            boardState = BoardState.PAUSED;
            for (int i = 0; i < gameObjects.size(); i++) {
                gameObjects.get(i).pause();
            }
        } else if (boardState == BoardState.PAUSED) {
            boardState = BoardState.ENABLED;
            for (int i = 0; i < gameObjects.size(); i++) {
                gameObjects.get(i).resume();
            }
        }
    }

    public Dimension getPreferredSize() {
        ParametersService gp = ParametersService.getInstance();
        return new Dimension(gp.getInitialBoardWidth(), gp.getInitialBoardHeight());
    }

    public void objectsReset(int newGameObjectsNumber) {
        gameObjects.clear();
        for (int i = 0; i < newGameObjectsNumber; i++) {
            gameObjects.add(new GameObject(this));
        }
        this.gameObjectsNumber = newGameObjectsNumber;
    }

    public void changeTheBoardSize(int width, int height) {
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void startGame() {
        boardState = BoardState.ENABLED;
        repaint();
    }

    public void start() {
        boardState = BoardState.ENABLED;
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    public void stop() {
        boardState = BoardState.DISABLED;
        if (thread != null) {
            thread = null;
        }
    }

    public void pause() {
        try {
            thread.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        thread.notify();
    }

    public void run() {
        while (thread != null) {
            try {
                Thread.sleep(objectsSpeed);

                for (int i = 0; i < gameObjects.size(); i++) {
                    gameObjects.get(i).move();
                    repaint();
                }
            } catch (InterruptedException e) {
            }
        }
    }

    public void setGameObjectsSpeed(int level) {
        ParametersService gp = ParametersService.getInstance();
        objectsSpeed = 200 - (20 * difficultyLevel);

        for (int i = 0; i < level; i++) {
            objectsSpeed = (int) (objectsSpeed * gp.getAmountOfChangeInDifficulty() / 100);
        }
    }

    public void addManyGameObjects(int gameObjectsNumber) {
        for (int i = 0; i < gameObjectsNumber; i++) {
            gameObjects.add(new GameObject(this));
        }
    }

    public void addOneGameObject() {
        gameObjects.add(new GameObject(this));
    }

    private class MouseHandler extends MouseAdapter {
        public void mousePressed(MouseEvent event) {
        }

        public void mouseClicked(MouseEvent event) {
            if (boardState != BoardState.PAUSED) {
                boolean removed = deleteObject(event.getX(), event.getY());
                PointsService cp = PointsService.getInstance();
                if (removed) {
                    cp.addPoint();
                    repaint();
                }
                menu.setScore();
            }
        }
    }

}
