package org.aquarium.gui;

import org.aquarium.state.ParametersService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class Help implements ActionListener {
    public void actionPerformed(ActionEvent event)
    {
        ParametersService parametersService = ParametersService.getInstance();
        String gameName = parametersService.getGameName();
        String text = (gameName + """
                
                click on the objects appearing on the board
                a successful click will give you a point
                the best results are saved with the date they were achieved
                each level has its own maximum time
                you can finish the level earlier by pressing the next button""");

        JOptionPane.showMessageDialog(null, text);
    }
}
