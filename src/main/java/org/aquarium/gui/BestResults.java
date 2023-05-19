package org.aquarium.gui;

import org.aquarium.utils.logger.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Properties;
import javax.swing.JOptionPane;

public class BestResults {
    final private String lsep = System.getProperty("line.separator");
    private int[] scoresTable;
    private String[] datesTable;

    private final String bestResultsPathname = "./src/main/resources/persistence/bestResults.txt";
    private static final BestResults instance = new BestResults();

    public static BestResults getInstance() {
        return instance;
    }
    private BestResults() {
        scoresTable = new int[10];
        datesTable = new String[10];
        Properties props = new Properties();

        try (Reader r = new BufferedReader(new FileReader(bestResultsPathname))) {
            props.load(r);
        } catch (FileNotFoundException fnfe) {
            Logger.writeMessagesAndExit("\r\n" +
                            "parametric file not found",
                    "BestResults", fnfe);
        } catch (IOException ioe) {
            Logger.writeMessagesAndExit("Occurred error in reading a parametric file",
                    "BestResults", ioe);
        }

        for (int i = 0; i < 10; i++) {
            scoresTable[i] = Integer.parseInt(props.getProperty("Place" + (i + 1)));
        }

        for (int i = 0; i < 10; i++) {
            datesTable[i] = props.getProperty("Date" + (i + 1));
        }
    }

    public void save(int score, String date) {
        boolean isAmongTheBest = false;
        int place = 0;

        for (int i = 0; i < 10; i++) {
            if (score > scoresTable[i]) {
                isAmongTheBest = true;
                place = i;
                break;
            }
        }
        if (isAmongTheBest) {
            PrintWriter pw = null;
            try {
                pw = new PrintWriter(new BufferedWriter(new FileWriter(bestResultsPathname)));
            } catch (IOException ioe) {
                Logger.writeMessagesAndExit("Failed to open the best results file", "BestResults", ioe);
            }

            for (int i = 9; i > place; i--) {
                scoresTable[i] = scoresTable[i - 1];
            }
            scoresTable[place] = score;

            for (int i = 9; i > place; i--) {
                datesTable[i] = datesTable[i - 1];
            }
            datesTable[place] = date;

            for (int i = 0; i < place; i++) {
                pw.print("Place" + (i + 1) + "=" + scoresTable[i] + lsep);
                pw.print("Date" + (i + 1) + "=" + datesTable[i] + lsep);
            }

            pw.print("Place" + (place + 1) + "=" + scoresTable[place] + lsep);
            pw.print("Date" + (place + 1) + "=" + datesTable[place] + lsep);

            for (int i = (place + 1); i < 10; i++) {
                pw.print("Place" + (i + 1) + "=" + scoresTable[i - 1] + lsep);
                pw.print("Date" + (i + 1) + "=" + datesTable[i - 1] + lsep);
            }
            pw.close();
        }
    }

    public String getDatesTable(int i) {
        return datesTable[i];
    }

    public int getScoresTable(int i) {
        return scoresTable[i];
    }

    public void showScores() {
        String scores = "best scores>\n";

        for (int i = 0; i < 10; i++) {
            scores += Integer.toString(i + 1) + ". Points- " + Integer.toString(getScoresTable(i)) + " day- " + getDatesTable(0) + "\n";
        }

        JOptionPane.showMessageDialog(null, scores);
    }
}