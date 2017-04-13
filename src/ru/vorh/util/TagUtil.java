package ru.vorh.util;

import org.eclipse.jgit.lib.Ref;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by yaroslav on 4/13/17.
 */
public class TagUtil {

    public static List<String> cutNameTag(Set<Ref> set){
        List<String> list = new ArrayList<>();
        set.forEach(ref -> {
            String replace = ref.getName().replace("refs/tags/", "");
            list.add(replace);
        });
    return list;
    }
}
