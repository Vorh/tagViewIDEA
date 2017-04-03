package ru.vorh;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.eclipse.jgit.api.Git;

import java.io.IOException;

/**
 * Created by yaroslav on 3/31/17.
 */
public class TagAction extends AnAction {

    private ModalTag modalTag;
    private GitService gitService;
    private Git git;

    public TagAction() {

        try {
            git = Git.open(new java.io.File(""));
        } catch (IOException e) {
            e.printStackTrace();
        }
        gitService = new GitServiceBasic(git);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
    }
}
