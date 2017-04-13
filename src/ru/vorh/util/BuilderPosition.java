package ru.vorh.util;

import javax.swing.*;
import java.awt.*;

/**
 * Created by yaroslav on 4/13/17.
 */
public class BuilderPosition {

    private GridBagConstraints gbc = new GridBagConstraints();

    private JPanel jPanel;
    private JComponent jComponent;
    private int x;
    private int y;
    private int widthX;
    private int heightY;
    private int fill;
    private double weightX;
    private double weightY;
    private int ipadX;
    private int ipadY;
    private Insets insets;
    private int anchor;


    public BuilderPosition(JPanel jPanel, JComponent jComponent) {
        this.jPanel = jPanel;
        this.jComponent = jComponent;
        widthX  = 1;
        heightY = 1;
        weightY = 0.0;
        weightY = 0.0;
        ipadY = 0;
        ipadX = 0;
        anchor = GridBagConstraints.CENTER;
    }


    public BuilderPosition addXY(int x , int y){
        this.x = x;
        this.y= y;
        return this;
    }

    public BuilderPosition addWeight(double x, double y){
        weightX = x;
        weightY = y;
        return this;
    }
    public BuilderPosition addSize(int x, int y){
        widthX = x;
        heightY = y;
        return this;
    }

    public BuilderPosition addFill(int fill){
        this.fill = fill;
        return this;
    }

    public BuilderPosition addIpad(int x, int y){
        ipadX = x;
        ipadY = y;
        return this;
    }

    public BuilderPosition addAnchor(int anchor){
        this.anchor = anchor;
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
        gbc.ipadx = ipadX;
        gbc.ipady = ipadY;
        gbc.anchor = anchor;
        if (insets != null){
            gbc.insets = insets;
        }

        jPanel.add(jComponent,gbc);
    }

}
