package id.ac.polban.jtk._2018.eas.appl._1.common;

public abstract class Entity {
    private Integer id;

    protected Entity (final Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}