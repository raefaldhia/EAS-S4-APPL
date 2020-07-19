package id.ac.polban.jtk._2018.eas.appl._1;

import java.util.ArrayList;
import java.util.List;

import id.ac.polban.jtk._2018.eas.appl._1.common.ValueObject;

public class UserManager extends ValueObject {
    private final Library library;

    private final List<User> users;

    UserManager (final Library library) {
        this.library = library;
        this.users = new ArrayList<>();
    }

    public Integer add (final String username,
                        final String password,
                        final User.Type type) {
        if (this.getByUsername(username) != null) {
            throw new IllegalArgumentException("This username already exists! Choose Another one.");
        }

        User user = null;

        switch (type) {
            case ADMIN:
                user = new Admin(username, password);
                break;
            case FACULTY:
                user = new Faculty(username, password);
                break;
            case STUDENT:
                user = new Student(username, password);
                break;
            default:
                throw new IllegalArgumentException("Unknown user type");
        }

        users.add(user);

        return user.getId();
    }
   
    public User get (Integer id) {
        for (int i = 0; i < users.size(); ++i) {
            if (users.get(i).getId().equals(id)) {
                return users.get(i);
            }
        }
        return null;
    }

    public User getByUsername (String username) {
        for (User user : this.users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }

        return null;
    }

    public Boolean remove (Integer id) {
        for (int i = 0; i < users.size(); ++i) {
            if (users.get(i).getId().equals(id)) {
                if (users.get(i) instanceof Borrower) {
                    Borrower borrower = (Borrower)users.get(i);
                    for(int j=0;j<borrower.issuedResources.size();j++){
                        library.getResourceManager().remove(borrower.issuedResources.get(j));
                    }        
                }

                users.remove(i);

                return true;
            }
        }

        return false;
    }

    public List<User> getUsers() {
        return users;
    }
}