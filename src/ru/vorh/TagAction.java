package ru.vorh;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.actionSystem.ShortcutSet;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * Created by yaroslav on 3/31/17.
 */
public class TagAction extends AnAction {

    private ModalTag modalTag;
    private GitService gitService;
    private Git git;
    private Project project;

    public TagAction(Project project) {
        Presentation templatePresentation = getTemplatePresentation();
        templatePresentation.setText("Last tags");
        templatePresentation.setDescription("Extract last tags");
        try {
            git = Git.open(new File(project.getBasePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        gitService = new GitServiceBasic(git);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {

        RevCommit lastCommit = gitService.getLastCommit(git.getRepository());
        Set<Ref> tagCommit = gitService.getTagCommit(git.getRepository(), lastCommit);
        StringBuilder tags = new StringBuilder();
        tagCommit.forEach(ref -> {
            tags.append(ref.getName().replace("refs/tags/",""));
            tags.append(" ");
        });

        Messages.showWarningDialog(tags.toString(), "Last Tags");
    }

    @Override
    protected void setShortcutSet(ShortcutSet shortcutSet) {
        super.setShortcutSet(shortcutSet);
    }
}
