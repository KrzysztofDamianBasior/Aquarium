package org.aquarium.utils.configuration;

import org.aquarium.exceptions.ExceptionHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Properties;
import java.util.Scanner;

public class RandomGameScenarioGenerator {
    final private String lsep = System.getProperty("line.separator");
    private int numberOfLevels;

    public RandomGameScenarioGenerator() {
        System.out.println("Enter the number of levels");
        Scanner read = new Scanner(System.in);
        numberOfLevels = read.nextInt();
        System.out.println();
    }


//pathname
//levelsDurationTimeValues

    void generateAndSaveRandomScenarioFile(String scenarioFilePathname) {
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(scenarioFilePathname)));
            EffectsDraw effectsGenerator = new EffectsDraw();

            pw.println("#Game scenario generator" + lsep
                    + "#" + "It allows you to generate scenarios for separate parametric files" + lsep
                    + "#" + lsep
                    + "numberOfLevels=" + numberOfLevels);
            pw.printf("levelsMusicFilesPathnames=");
            for (int i = 0; i < numberOfLevels; i++) {
                pw.printf(effectsGenerator.drawMusicFilePathname() + " ");
            }
            pw.print(lsep);
            pw.print("levelsBackgroundFilesPathnames=");
            for (int i = 0; i < numberOfLevels; i++) {
                pw.printf(effectsGenerator.drawBackgroundFilePathname() + " ");
            }
            pw.print(lsep);
            pw.printf("levelsDurationTimeValues=");
            for (int i = 0; i < numberOfLevels; i++) {
                pw.printf(effectsGenerator.drawLevelDurationTimeValue() + " ");
            }
            pw.print(lsep);
            pw.close();
        } catch (IOException ioe) {
            ExceptionHandler.writeMessagesAndExit("Failed to open scenario file ", scenarioFilePathname, ioe);
        }
    }


    void loadAndPrintScenarioFile(String fileName) {
        Properties props = new Properties();
        try (Reader r = new BufferedReader(new FileReader(fileName))) {
            props.load(r);
        } catch (FileNotFoundException fnfe) {
            ExceptionHandler.writeMessagesAndExit("Scenario file not found",
                    fileName, fnfe);
        } catch (IOException ioe) {
            ExceptionHandler.writeMessagesAndExit("There was an error reading the scenario file",
                    fileName, ioe);
        }
        props.forEach((parameterName, parameterValue) -> {
            System.out.println("[" + parameterName + "]=[" + parameterValue + "]");
        });
    }

    public static void main(String[] args) {
        String scenarioFilePathname = "./src/main/resources/configuration/scenario.txt";
        RandomGameScenarioGenerator gsg = new RandomGameScenarioGenerator();
        gsg.generateAndSaveRandomScenarioFile(scenarioFilePathname);
        gsg.loadAndPrintScenarioFile(scenarioFilePathname);
    }
}
