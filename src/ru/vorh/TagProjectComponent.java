package ru.vorh;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import org.eclipse.jgit.api.Git;
import org.jetbrains.annotations.NotNull;
import ru.vorh.services.GitService;
import ru.vorh.services.GitServiceBasic;

import java.io.File;
import java.io.IOException;

/**
 * Created by yaroslav on 3/31/17.
 */
public class TagProjectComponent implements ProjectComponent {
    private Project project;
    private GitService gitService;

    public TagProjectComponent(Project project) {
        this.project = project;

    }

    @Override
    public void initComponent() {
        System.out.println("CREATE COMPONENT ");

        gitService = createGitService();
        if (gitService != null) {
            ActionManager actionManager = ActionManager.getInstance();


            TagAction tagAction = new TagAction(project, gitService);
            DialogTagAction dialogTagAction = new DialogTagAction(gitService);

            DefaultActionGroup actionGroup = (DefaultActionGroup) actionManager.getAction("VcsGroups");
            actionGroup.add(tagAction);
            actionGroup.add(dialogTagAction);

            actionManager.registerAction("TagAction", tagAction);
            actionManager.registerAction("DialogTagAction", dialogTagAction);

            System.out.println("FINISH SETTING COMPONENT");
        }else {
            System.out.println("GIT NOT FOUND");
        }
    }

    private GitService createGitService() {
        Git git;
        try {
            git = Git.open(new File(project.getBasePath()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return new GitServiceBasic(git);
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
