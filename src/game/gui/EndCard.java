package game.gui;

import api.utility.Vector;

import javax.swing.*;
import java.awt.*;

public class EndCard extends JPanel{

    public EndCard(int recessed) {

        JLabel titleLabel = new JLabel("FINE DELLA SERATA !");
        titleLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        add(titleLabel);

        JLabel recessedLabel = new JLabel("Total guadagno: " + recessed + "€");
        recessedLabel.setFont(new Font("Serif", Font.PLAIN, 25));
        add(recessedLabel);

        setPreferredSize(new Dimension(500, 300));
    }
}
