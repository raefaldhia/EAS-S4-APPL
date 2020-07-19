package id.ac.polban.jtk._2018.eas.appl._1;

import java.util.*;

public class Student extends Borrower {
	
	Student(final String username,
	        final String password){
		super(username, password);
		this.fines = new HashSet<Fine>();
	}

	@Override
	public Integer getBorrowLimit() {
		return 3;
	}

	@Override
	public Type getType() {
		return Type.STUDENT;
	}
}