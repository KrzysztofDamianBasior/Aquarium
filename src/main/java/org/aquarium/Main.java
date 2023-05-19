package org.aquarium;

import org.aquarium.gui.MainFrame;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new MainFrame();
        });
    }
}