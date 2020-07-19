package id.ac.polban.jtk._2018.eas.appl._1;

public class Admin extends User {

    Admin(final String username,
          final String password){
        super(username, password);
    }

    @Override
    public Type getType() {
      return Type.ADMIN;
    }
}