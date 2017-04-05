package ru.vorh;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import ru.vorh.services.GitService;

import java.util.Set;

/**
 * Created by yaroslav on 3/31/17.
 */
public class TagAction extends AnAction {

    private ModalTag modalTag;
    private GitService gitService;
    private Project project;

    public TagAction(Project project ,GitService gitService) {
        this.project = project;
        this.gitService = gitService;

        Presentation templatePresentation = getTemplatePresentation();
        templatePresentation.setText("Get last tags");
        templatePresentation.setDescription("Extract last tags");

    }

    @Override
    public void actionPerformed(AnActionEvent e) {

        RevCommit lastCommit = gitService.getLastCommit();
        Set<Ref> tagCommit = gitService.getTagCommit(lastCommit);
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
