package org.aquarium;

import org.aquarium.gui.MainFrame;
import org.aquarium.state.ParametersService;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        ParametersService gp = ParametersService.getInstance();

        EventQueue.invokeLater(() -> {
            JFrame frame = new MainFrame();
        });
    }
}