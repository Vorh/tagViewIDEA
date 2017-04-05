package ru.vorh;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;

/**
 * Created by yaroslav on 4/5/17.
 */
public class TagToolbar extends AnAction{

    private Project project;

    public TagToolbar(Project project) {
        this.project = project;
    }

    @Override
    public void actionPerformed(AnActionEvent e) {

    }
}
