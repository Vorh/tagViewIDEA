package ru.vorh.services;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

import java.util.Set;

/**
 * Created by yaroslav on 3/31/17.
 */
public interface GitService {

    Set<Ref> getTagCommit(Repository repository, RevCommit commit);

    RevCommit getLastCommit(Repository repository);

    Set<Ref> getTagCommit(RevCommit commit);

    RevCommit getLastCommit();

    Git getGit();

    void addTag(String tag);
}
