package ru.vorh;

import com.intellij.openapi.ui.Messages;

/**
 * Created by yaroslav on 3/31/17.
 */
public class ModalTag {
    private String caption;
    private String title;

    public ModalTag(String caption , String title) {
        this.caption = caption;
        this.title = title;
    }

    public void show(){
        Messages.showInfoMessage(caption,title);
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
