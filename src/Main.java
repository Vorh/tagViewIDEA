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

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by yaroslav on 3/31/17.
 */
public class Main {

    public static void main(String[] args) {
        Git git = null;
        try {
            git = Git.open(new java.io.File("../docx-service/"));
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
