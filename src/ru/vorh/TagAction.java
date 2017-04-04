package ru.vorh;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
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

//        Messages.showWarningDialog(tags.toString(), "Last Tags");
        Notification  notification = new Notification(IdeActions.GROUP_EDITOR_POPUP, "Last tags",tags.toString(), NotificationType.INFORMATION);
        notification.notify(project);
    }

    @Override
    protected void setShortcutSet(ShortcutSet shortcutSet) {
        super.setShortcutSet(shortcutSet);
    }
}
