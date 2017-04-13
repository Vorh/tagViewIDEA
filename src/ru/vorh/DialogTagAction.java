package ru.vorh;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import ru.vorh.services.GitService;

/**
 * Created by yaroslav on 4/7/17.
 */
public class DialogTagAction extends AnAction{

    private GitService gitService;
    public DialogTagAction(GitService gitService) {
        this.gitService = gitService;

        Presentation presentation = getTemplatePresentation();
        presentation.setText("Create Tag");
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        DialogTag dialogTag = new DialogTag(e.getProject(),false);
        dialogTag.setTags(gitService.getTagCommit(gitService.getLastCommit()));
        dialogTag.setSize(400,400);
        dialogTag.setTitle("Create New Tag");
        dialogTag.show();
    }
}
