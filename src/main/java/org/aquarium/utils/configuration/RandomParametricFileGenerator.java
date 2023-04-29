package org.aquarium.utils.configuration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Properties;

public class RandomParametricFileGenerator {
    final private String lsep = System.getProperty("line.separator");
    final private String[] gameNames = {
            "Aqua", "Aquarium", "Fishes", "CatchAndGo",
    };
    final private String[] levelNames = {
            "level#", "level", "Level"
    };
    final private String[] extensionNames = {
            "txt", "properties", "prop", "props", "text", "game",
    };
    final private String[] objectFigures = {
            "squares", "rectangles", "circles"
    };

    RandomParametricFileGenerator() {
    }

    public static void main(String... args) {
        RandomParametricFileGenerator pfg = new RandomParametricFileGenerator();
        String parametricFileName = "./src/main/resources/configuration/par.txt";
        pfg.generateAndSaveRandomParametricFile(parametricFileName);
        pfg.loadAndPrintParametricFile(parametricFileName);
    }

    private String drawString(String... strings) {
        return strings[(int) (Math.random() * strings.length)];
    }

    private double randomDouble(double min, double max) {
        if (min <= max) {
            return min + Math.random() * (max - min);
        } else {
            throw new IllegalArgumentException("max must be greater than min");
        }
    }

    private int randomInt(int min, int max) {
        if (min <= max) {
            return min + (int) (Math.random() * (1L + max - min));
        } else {
            throw new IllegalArgumentException("max must be greater than min");
        }

    }

    void writeMessagesAndExit(Object... messagesTable) {
        for (int i = 0; i < messagesTable.length; i++) {
            System.err.print((i + 1) + ": ");
            if (messagesTable[i] instanceof Throwable) {
                ((Throwable) messagesTable[i]).printStackTrace();
            } else {
                System.err.println(messagesTable[i].toString());
            }
        }
        System.exit(-1);
    }

    void generateAndSaveRandomParametricFile(String fileName) {
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));

            pw.println("# Game parametric file." + lsep
                    + "# A comment starts with the # character and lasts until the end of the line." + lsep
                    + "# Parameters are written as {name}={value} pairs." + lsep
                    + "#" + lsep

                    + "# 1. Core" + lsep
                    + "gameName=" + drawString(gameNames) + lsep
                    + "numberOfLevels=" + randomInt(1, 5) + lsep
                    + "levelName=" + drawString(levelNames) + lsep
                    + "extensionName=" + drawString(extensionNames) + lsep
                    + "#" + lsep

                    + "# 2. Difficulty level" + lsep
                    + "numberOfDifficultyLevels=" + randomInt(2, 5) + lsep
                    + "amountOfChangeInDifficulty=" + randomInt(20, 45) + lsep
                    + "#" + lsep

                    + "# 3. Different sizes" + lsep
                    + "initialBoardWidth=" + randomInt(400, 1000) + lsep
                    + "initialBoardHeight=" + randomInt(250, 700) + lsep
                    + "initialWidthOfTheGameObjectAsAPercentageOfBoardInitialWidth="
                    + (float) randomDouble(5d, 9d) + lsep
                    + "#" + lsep

                    + "# 4. Background and objects"
            );
            if (randomInt(0, 1) == 0) {
                pw.println("background=plain" + lsep
                        + "backgroundColor=" + randomInt(0, 255) + " " + randomInt(0, 255) + " " + randomInt(0, 255));
            } else {
                pw.println("background=graphicFile" + lsep
                        + "menuBackground=menuBackground.jpg");
            }
            if (randomInt(0, 1) == 0) {
                pw.println("gameObjects=geometricFigures" + lsep
                        + "gameObjectFigure=" + drawString(objectFigures));
            } else {
                pw.println("gameObjects=graphicFile" + lsep
                        + "objectFile=fish1.png");
            }
            pw.close();
        } catch (IOException ioe) {
            writeMessagesAndExit("Failed to open parametric file", fileName, ioe);
        }
    }

    void loadAndPrintParametricFile(String fileName) {
        Properties props = new Properties();
        try (Reader r = new BufferedReader(new FileReader(fileName))) {
            props.load(r);
        } catch (FileNotFoundException fnfe) {
            writeMessagesAndExit("Parametric file not found",
                    fileName, fnfe);
        } catch (IOException ioe) {
            writeMessagesAndExit("There was an error reading the parametric file",
                    fileName, ioe);
        }
        props.forEach((parameterName, parameterValue) -> {
            System.out.println("[" + parameterName + "]=[" + parameterValue + "]");
        });
    }

    private int sumOfDigits(int number) {
        String s = "" + number;
        int sumOfDigits = 0;
        for (int i = 0; i < s.length(); i++) {
            sumOfDigits += (s.charAt(i)) - '0';
        }
        return sumOfDigits;
    }
}