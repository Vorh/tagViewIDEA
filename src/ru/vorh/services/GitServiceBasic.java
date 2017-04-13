package ru.vorh.services;

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
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by yaroslav on 3/31/17.
 */
public class GitServiceBasic implements GitService {

    private Git git;

    public GitServiceBasic(Git git) {
        this.git = git;
    }


    @Nullable
    @Override
    public Set<Ref> getTagCommit(Repository repository, RevCommit commit) {
        Set<Ref> tags = new HashSet<>();

        RevWalk revWalk = new RevWalk(repository);
        revWalk.reset();

        for (Ref ref : repository.getTags().values()) {
            try {
                RevObject revObject = revWalk.parseAny(ref.getObjectId());
                RevCommit tagCommit = null;

                if (revObject instanceof RevCommit){
                    tagCommit = (RevCommit) revObject;
                }else if (revObject instanceof RevTag){
                    tagCommit = revWalk.parseCommit(((RevTag) revObject).getObject());
                }

                if (commit.equals(tagCommit)||revWalk.isMergedInto(commit,tagCommit)){
                    tags.add(ref);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return tags;
    }

    @Override
    public Set<Ref> getTagCommit(RevCommit commit) {
        return getTagCommit(git.getRepository(),commit);
    }



    @Nullable
    @Override
    public RevCommit getLastCommit(Repository repository) {
        try {
            List<Ref> tags = git.tagList().call();

            LogCommand log = git.log();

            for (Ref ref : tags) {
                Ref peel = repository.peel(ref);
                if (peel.getPeeledObjectId() != null) {
                    log.add(peel.getPeeledObjectId());
                } else {
                    log.add(ref.getObjectId());
                }

            }
            RevCommit lastCommit = null;
            Iterable<RevCommit> iterableLog = null;
            iterableLog = log.call();
            for (RevCommit revCommit : iterableLog) {
                if (lastCommit == null || revCommit.getCommitTime() > lastCommit.getCommitTime()) {
                    lastCommit = revCommit;
                }
            }
            return lastCommit;
        } catch (IncorrectObjectTypeException | MissingObjectException | GitAPIException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public RevCommit getLastCommit() {
        return getLastCommit(git.getRepository());
    }

    @Override
    public Git getGit() {
        return git;
    }

    @Override
    public void addTag(String tag) {
        try {
            git.tag().setName(tag).call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

}
