package id.ac.polban.jtk._2018.eas.appl._1;

import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class CoursePack extends Borrowable {
	CoursePack(final String name){
		super(name);
		this.available = true;
		this.issuedTo = -1;
	}
	
	boolean issueResource(int userID){
			issuedTo = userID;
			available = false;
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_YEAR,-1);
			dueDate = cal.getTime();
			cal.add(Calendar.DAY_OF_YEAR,-2);
			issueDate = cal.getTime();
			System.out.println(issueDate);
			return true;
	}	
}