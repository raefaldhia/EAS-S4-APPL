package id.ac.polban.jtk._2018.eas.appl._1;

import id.ac.polban.jtk._2018.eas.appl._1.common.AggregateRoot;

public abstract class User extends AggregateRoot {
    private static Integer LAST_USED_ID = 0;

    public enum Type {
        ADMIN,
        FACULTY,
        STUDENT
    }

    private String username;

    private String password;

    User (final String username,
          final String password) {
        super(GetNextId());

        this.username = username;
        this.password = password;
    }

    public String getUsername () {
        return username;
    }

    public String getPassword () {
        return password;
    }

    public Boolean authenticate (final String username, final String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    private static Integer GetNextId () {
        User.LAST_USED_ID += 1;
        return User.LAST_USED_ID;
    }

    public abstract Type getType();

    /**
     * logs out the currently logged in user. Note that the system cannot have multiple users logged in at the same time
     * @return return true if logout successful else return false
     */
    boolean logout() {

        /**
         * Write your code here
         */
        return true;
    }
}