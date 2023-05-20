package org.aquarium.gui;

import org.aquarium.state.ParametersService;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.Graphics2D;

public class GameObject {

    private int xPosition, yPosition, xVelocity = 2, yVelocity = 2;
    private boolean isObjectMoving;
    private double scale;
    private String objectType;
    private ImageIcon gameObjectImageIcon;
    private Image gameObjectImage;
    private String objectShape;
    private GameBoard gameBoard;

    public GameObject(GameBoard gameBoard) {
        ParametersService parametersService = ParametersService.getInstance();
        scale = (parametersService.getInitialWidthOfTheGameObjectAsAPercentageOfBoardInitialWidth());
        this.gameBoard = gameBoard;
        isObjectMoving = true;

        xPosition = (int) (Math.random() * gameBoard.getWidth());
        yPosition = (int) (Math.random() * gameBoard.getHeight());


        objectType = parametersService.getGameObjectsType();

        if (objectType.equals("graphicFile")) {
            double draw = Math.random();
            if (draw <= 0.2) {
                gameObjectImageIcon = new ImageIcon("./src/main/resources/assets/fishes/fish1.png");
            } else if (draw <= 0.4) {
                gameObjectImageIcon = new ImageIcon("./src/main/resources/assets/fishes/fish2.png");
            } else if (draw <= 0.6) {
                gameObjectImageIcon = new ImageIcon("./src/main/resources/assets/fishes/fish3.png");
            } else if (draw <= 0.8) {
                gameObjectImageIcon = new ImageIcon("./src/main/resources/assets/fishes/fish4.png");
            } else {
                gameObjectImageIcon = new ImageIcon("./src/main/resources/assets/fishes/fish5.png");
            }

            gameObjectImage = gameObjectImageIcon.getImage();
        } else {
            double draw = Math.random();
            if (draw <= 0.25) {
                objectShape = "square";
            } else if (draw <= 0.5) {
                objectShape = "rectangle";
            } else if (draw <= 0.75) {
                objectShape = "triangle";
            } else {
                objectShape = "circle";
            }
        }
    }

    public int getXPosition() {
        return this.xPosition;
    }

    public int getYPosition() {
        return this.yPosition;
    }

    public void pause() {
        isObjectMoving = false;
    }

    public void resume() {
        isObjectMoving = true;
    }

    public void move() {
        if (isObjectMoving) {
            xPosition += xVelocity;
            yPosition += yVelocity;
            if (xPosition < 0) {
                xVelocity = (int) (Math.random() * 10);
            } else if (yPosition < 0) {
                yVelocity = (int) (Math.random() * 10);
            } else if (xPosition >= (int) (gameBoard.getWidth() - (gameBoard.getWidth() * scale / 100))) {
                xVelocity = -((int) (Math.random() * 3));
                xPosition = (int) (gameBoard.getWidth() - (gameBoard.getWidth() * scale / 100));
            } else if (yPosition >= (int) (gameBoard.getHeight() - 2 * (gameBoard.getWidth() * scale / 100))) {
                yVelocity = -((int) (Math.random() * 3));
                yPosition = (int) (gameBoard.getHeight() - 2 * (gameBoard.getWidth() * scale / 100));
            }
        }
    }

    public void paint(Graphics g) {
        if (objectType.equals("graphicFile")) {
            g.drawImage(gameObjectImage, xPosition, yPosition, (int) (gameBoard.getWidth() * scale / 100), (int) (gameBoard.getWidth() * scale / 100), null);
        } else {
            if (objectShape.equals("square")) {
                g.setColor(Color.RED);
                g.fillRect(xPosition, yPosition, (int) (gameBoard.getWidth() * scale / 100), (int) (gameBoard.getWidth() * scale / 100));
            } else if (objectShape.equals("rectangle")) {
                g.setColor(Color.RED);
                g.fillRect(xPosition, yPosition, (int) (gameBoard.getWidth() * scale / 100), (int) (gameBoard.getWidth() * scale / 100));
            } else if (objectShape.equals("triangle")) {
                TriangleShape triangleShape;
                Graphics2D g2d = (Graphics2D) g.create();
                int h = (int) (gameBoard.getWidth() * scale / 100);
                triangleShape = new TriangleShape(new Point2D.Double(xPosition, yPosition), new Point2D.Double(xPosition + h, yPosition + h), new Point2D.Double(xPosition - h, yPosition + h));
                g2d.setColor(Color.RED);
                g2d.fill(triangleShape);
            } else {
                g.setColor(Color.RED);
                g.fillOval(xPosition, yPosition, (int) (gameBoard.getWidth() * scale / 100), (int) (gameBoard.getWidth() * scale / 100));
            }
        }
    }

    class TriangleShape extends Path2D.Double {
        public TriangleShape(Point2D... points) {
            moveTo(points[0].getX(), points[0].getY());
            lineTo(points[1].getX(), points[1].getY());
            lineTo(points[2].getX(), points[2].getY());
            closePath();
        }
    }
}