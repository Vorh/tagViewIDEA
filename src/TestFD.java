import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.IOException;
import java.util.List;

/**
 * Created by yaroslav on 3/31/17.
 */
public class TestFD extends AnAction {


    public TestFD() {
        super("fsdfsd");
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here

        Git git = null;
        try {
            git = Git.open(new java.io.File("../../docx-service/"));
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

        } catch (GitAPIException e1) {
            e1.printStackTrace();
        } catch (MissingObjectException e1) {
            e1.printStackTrace();
        } catch (IncorrectObjectTypeException e1) {
            e1.printStackTrace();
        }


//        Messages.showInfoMessage(replace,"Current tags");


        System.out.println("INIT 2");
    }
}
