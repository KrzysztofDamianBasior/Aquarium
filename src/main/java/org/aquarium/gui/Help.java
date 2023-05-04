package org.aquarium.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class Help implements ActionListener {
    public void actionPerformed(ActionEvent event)
    {
        String text = ( "Author: Krzysztof Basior\n\n"
                + "click on the objects appearing on the board\n"
                + "a successful click will give you a point\n"
                + "the best results are saved with the date they were achieved\n"
                + "each level has its own maximum time\n"
                + "you can finish the level earlier by pressing the next button");

        JOptionPane.showMessageDialog(null, text);
    }
}
