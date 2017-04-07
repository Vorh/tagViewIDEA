package ru.vorh;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBTextField;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

import static ru.vorh.SwingUtil.addComponent;

/**
 * Created by yaroslav on 4/7/17.
 */
public class DialogTag extends DialogWrapper{

    private JBPanel root;

    public DialogTag(@Nullable Project project, boolean canBeParent) {
        super(project, canBeParent);
        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        root = new JBPanel<>(new GridBagLayout());
        root.setPreferredSize(new Dimension(300,100));


        JBTextField filedTag = new JBTextField("test");
//        filedTag.setPreferredSize(new Dimension(250,30));
        JBLabel labelLastTags = new JBLabel("Last tags");
        Font font = new Font("Monospaced",Font.PLAIN,12);
        labelLastTags.setFont(font);

        addComponent(root,labelLastTags,0,0,1,1,GridBagConstraints.HORIZONTAL,0.55,0.5);
        addComponent(root,filedTag,0,1,2,1,GridBagConstraints.HORIZONTAL,0.90,0.20);

        return root;
    }
}
