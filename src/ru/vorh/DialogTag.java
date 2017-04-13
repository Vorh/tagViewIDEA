package ru.vorh;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.util.ui.JBUI;
import org.eclipse.jgit.lib.Ref;
import org.jetbrains.annotations.Nullable;
import ru.vorh.util.BuilderPosition;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.util.Set;

/**
 * Created by yaroslav on 4/7/17.
 */
public class DialogTag extends DialogWrapper{

    private JBPanel root;
    private Set<Ref> tags;

    public DialogTag(@Nullable Project project, boolean canBeParent) {
        super(project, canBeParent);
    }



    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JBPanel root = new JBPanel<>(new GridBagLayout());

        JBLabel labelTag = new JBLabel("Last tag:");
        Font font = new Font("Monospaced",Font.PLAIN,14);
        labelTag.setFont(font);

        StringBuilder tagCaption = new StringBuilder();
        if (tags != null && tags.size() != 0) {
            tags.forEach(ref -> {
                tagCaption.append(" " + ref.getName());
            });
        }


        JBLabel labelTags = new JBLabel(tagCaption.toString());
        labelTags.setFont(font);

        JFormattedTextField inputTag = new JFormattedTextField();
        try {
            MaskFormatter maskTag = new MaskFormatter("v#.##.#.#");
            maskTag.install(inputTag);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        Insets insetsLabel = JBUI.insets(0,20,-20,0);
        Insets insetsInput = JBUI.insets(0,20,0,20);

        new BuilderPosition(root,labelTags)
                .addXY(0,0)
                .addFill(GridBagConstraints.HORIZONTAL)
                .addAnchor(GridBagConstraints.FIRST_LINE_START)
                .addWeight(1,0.5)
                .addInsert(insetsLabel)
                .build();
        new BuilderPosition(root,labelTags)
                .addXY(0,1)
                .addFill(GridBagConstraints.HORIZONTAL)
                .addAnchor(GridBagConstraints.FIRST_LINE_START)
                .addWeight(1,0.5)
                .build();
        new BuilderPosition(root,inputTag)
                .addXY(0,1)
                .addWeight(1,1)
                .addFill(GridBagConstraints.HORIZONTAL)
                .addAnchor(GridBagConstraints.FIRST_LINE_START)
                .addInsert(insetsInput)
                .build();

        return root;
    }

    public void setTags(Set<Ref> tags) {
        this.tags = tags;
    }

    @Override
    public void show() {
        init();
        super.show();
    }
}
