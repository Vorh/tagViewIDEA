package ru.vorh;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

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

        DefaultActionGroup actionGroup = (DefaultActionGroup) actionManager.getAction("VcsGroups");
        actionGroup.add(tagAction);

        actionManager.registerAction("TagAction",tagAction);

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
