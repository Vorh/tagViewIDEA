package ru.vorh;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;

/**
 * Created by yaroslav on 4/7/17.
 */
public class DialogTagAction extends AnAction{

    private DialogTag dialogTag;
    public DialogTagAction() {

        Presentation presentation = getTemplatePresentation();
        presentation.setText("Create Tag");
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        DialogTag dialogTag = new DialogTag(e.getProject(),false);
        dialogTag.setSize(400,400);
        dialogTag.setTitle("Create New Tag");
        dialogTag.show();
    }
}
