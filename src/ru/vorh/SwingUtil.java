package ru.vorh;

import javax.swing.*;
import java.awt.*;

/**
 * Created by yaroslav on 4/7/17.
 */
public class SwingUtil {

    private static GridBagConstraints gbc;


    public static void addComponent(JPanel panel, JComponent comp
            , int x, int y, int gWidth
            , int gHeight, int fill
            , double weightx, double weighty){
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = gWidth;
        gbc.gridheight = gHeight;
        gbc.fill = fill;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        panel.add(comp, gbc);
    }

}
