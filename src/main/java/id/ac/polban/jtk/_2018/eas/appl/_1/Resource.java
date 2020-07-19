package id.ac.polban.jtk._2018.eas.appl._1;

import id.ac.polban.jtk._2018.eas.appl._1.common.AggregateRoot;

public class Resource extends AggregateRoot {
    private static Integer LAST_USED_ID = 0;

    public enum Type {
        BOOK,
        COURSEPACK,
        MAGAZINE
    }

    private String name;

    Resource (final String name) {
        super(GetNextId());

        this.name = name;
    }

    public String getName () {
        return name;
    }

    private static Integer GetNextId () {
        Resource.LAST_USED_ID += 1;
        return Resource.LAST_USED_ID;
    }
}
