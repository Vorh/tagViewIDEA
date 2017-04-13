package ru.vorh.util;

import javax.swing.*;
import java.awt.*;

/**
 * Created by yaroslav on 4/13/17.
 */
public class BuilderPosition {

    private static GridBagConstraints gbc = new GridBagConstraints();

    private JPanel jPanel;
    private JComponent jComponent;
    private int x;
    private int y;
    private int widthX;
    private int heightY;
    private int fill;
    private int weightX;
    private int weightY;
    private Insets insets;


    public BuilderPosition(JPanel jPanel, JComponent jComponent) {
        this.jPanel = jPanel;
        this.jComponent = jComponent;
    }


    public BuilderPosition addXY(int x , int y){
        this.x = x;
        this.y= y;
        return this;
    }

    public BuilderPosition addSize(int x, int y){
        weightX = x;
        weightY = y;
        return this;
    }

    public BuilderPosition addFill(int fill){
        this.fill = fill;
        return this;
    }

    public BuilderPosition addInsert(Insets insets){
        this.insets = insets;
        return this;
    }

    public void build(){
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = widthX;
        gbc.gridheight = heightY;
        gbc.fill = fill;
        gbc.weightx = weightX;
        gbc.weighty = weightY;

        jPanel.add(jComponent,gbc);
    }

}
