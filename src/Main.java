import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.util.ui.JBUI;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevWalk;
import ru.vorh.util.BuilderPosition;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by yaroslav on 3/31/17.
 */
public class Main {

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("TEst");
        jFrame.setSize(250,120);
        jFrame.setLocationRelativeTo(null);

        JBPanel root = new JBPanel<>(new GridBagLayout());


        JBLabel labelLastTags = new JBLabel("Last tag: v1.23.4.1");
        Font font = new Font("Monospaced",Font.PLAIN,14);
        labelLastTags.setFont(font);


        JFormattedTextField inputTag = new JFormattedTextField();
        try {
            MaskFormatter maskTag = new MaskFormatter("v#.##.#.#");
            maskTag.install(inputTag);

        } catch (ParseException e) {
            e.printStackTrace();
        }
//        root.add(inputTag);
        Insets insetsLabel = JBUI.insets(0,20,-20,0);
        Insets insetsInput = JBUI.insets(0,20,0,20);

        new BuilderPosition(root,labelLastTags)
                .addXY(0,0)
//                .addSize(0,0)
                .addFill(GridBagConstraints.HORIZONTAL)
                .addAnchor(GridBagConstraints.FIRST_LINE_START)
                .addWeight(1,0.5)
                .addInsert(insetsLabel)
                .build();

        new BuilderPosition(root,inputTag)
                .addXY(0,1)
//                .addSize(0,2)
                .addWeight(1,1)
                .addFill(GridBagConstraints.HORIZONTAL)
                .addAnchor(GridBagConstraints.FIRST_LINE_START)
                .addInsert(insetsInput)
                .build();
        jFrame.add(root);
        jFrame.setVisible(true);
    }

    private static void getLastTag() {
        Git git = null;
        try {
            git = Git.open(new java.io.File("../ofd-lk/"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        Repository repository = git.getRepository();
        try {
            List<Ref> call = git.tagList().call();

            LogCommand log = git.log();

            for (Ref ref : call) {
                Ref peel = repository.peel(ref);
                if (peel.getPeeledObjectId() !=null){
                    log.add(peel.getPeeledObjectId());
                }else{
                    log.add(ref.getObjectId());
                }

            }
            RevCommit lastCommit = null;
            Iterable<RevCommit> call1 = log.call();
            for (RevCommit revCommit : call1) {
                if (lastCommit == null || revCommit.getCommitTime() > lastCommit.getCommitTime()){
                    lastCommit = revCommit;
                }
            }

            System.out.println(lastCommit.getFullMessage());

            Set<Ref> tagsForCommit = getTagsForCommit(repository, lastCommit);

            tagsForCommit.forEach(ref -> {
                System.out.println(ref.getName());
            } );

        } catch (GitAPIException e1) {
            e1.printStackTrace();
        } catch (MissingObjectException e1) {
            e1.printStackTrace();
        } catch (IncorrectObjectTypeException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


//        Messages.showInfoMessage(replace,"Current tags");


        System.out.println("INIT 2");
    }

    private static Set<Ref> getTagsForCommit(Repository repo,
                                             RevCommit commit) throws Exception {
        final Set<Ref> tags = new HashSet<Ref>();
        final RevWalk walk = new RevWalk(repo);
        walk.reset();
        for (final Ref ref : repo.getTags().values()) {
            final RevObject obj = walk.parseAny(ref.getObjectId());
            final RevCommit tagCommit;
            if (obj instanceof RevCommit) {
                tagCommit = (RevCommit) obj;
            } else if (obj instanceof RevTag) {
                tagCommit = walk.parseCommit(((RevTag) obj).getObject());
            } else {
                continue;
            }
            if (commit.equals(tagCommit)
                    || walk.isMergedInto(commit, tagCommit)) {
                tags.add(ref);
            }
        }
        return tags;
    }
}
