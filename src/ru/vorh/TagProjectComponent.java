package ru.vorh;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;

/**
 * Created by yaroslav on 3/31/17.
 */
public class TagProjectComponent implements ProjectComponent {
    private Project project;

    public TagProjectComponent(Project project) {
        this.project = project;

    }

    @Override
    public void initComponent() {

        System.out.println("CREATE COMPONENT ");
        ActionManager actionManager = ActionManager.getInstance();
        TagAction tagAction = new TagAction(project);

        KeyboardShortcut keyboardShortcut = new KeyboardShortcut(KeyStroke.getKeyStroke(KeyEvent.CTRL_MASK,1),KeyStroke.getKeyStroke("]"));
        CustomShortcutSet customShortcutSet = new CustomShortcutSet(keyboardShortcut);
        tagAction.setShortcutSet(customShortcutSet);

        DefaultActionGroup actionGroup = (DefaultActionGroup) actionManager.getAction("VcsGroups");
        actionGroup.add(tagAction);
        AnAction[] childActionsOrStubs = actionGroup.getChildActionsOrStubs();
        System.out.println(tagAction.getTemplatePresentation().getText());

        for (AnAction anAction : Arrays.asList(childActionsOrStubs)) {
            System.out.println(anAction.toString());
            System.out.println("");
        }
        actionManager.registerAction("TagAction",tagAction);

        // TODO: insert component initialization logic here
        System.out.println("FINISH SETTING COMPONENT");
    }

    @Override
    public void disposeComponent() {
        // TODO: insert component disposal logic here
    }

    @Override
    @NotNull
    public String getComponentName() {
        return "TagProjectComponent";
    }

    @Override
    public void projectOpened() {
        // called when project is opened
    }

    @Override
    public void projectClosed() {
        // called when project is being closed
    }
}
