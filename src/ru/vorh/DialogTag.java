package ru.vorh;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

/**
 * Created by yaroslav on 4/7/17.
 */
public class DialogTag extends DialogWrapper{


    public DialogTag(@Nullable Project project, boolean canBeParent) {
        super(project, canBeParent);
        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel root = new JPanel(new GridBagLayout());
        root.setPreferredSize(new Dimension(300,100));
        ComboBox<Object> objectComboBox = new ComboBox<>();
        objectComboBox.addItem(new Label("1"));
        objectComboBox.addItem(new Label("2"));
        objectComboBox.addItem(new Label("3"));


        JTextField jTextField = new JTextField("TEWR");
        jTextField.setPreferredSize(new Dimension(300,20));
        JLabel labelLastTags = new JLabel("Last tags");

        root.add(labelLastTags);

        root.add(objectComboBox);
        root.add(jTextField);
        return root;
    }
}
