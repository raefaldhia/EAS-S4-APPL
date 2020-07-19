package id.ac.polban.jtk._2018.eas.appl._1;

import java.util.*;

public class Library implements LibraryInfo {
	private ResourceManager resourceManager;

	private UserManager userManager;

	public ResourceManager getResourceManager() {
		return resourceManager;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	String libraryName;
	static Calendar calendar = new GregorianCalendar();
	
	
	/**
     * the main library object. There will be only one intantiation on this object.
     */
	static Library lib;
	

	Set<Fine> toBeFined;	//  keeps record of fines which are to be checked by the library
												//	They are related to those resources only which are ISSUED to userManager.getUsers()...
	

	/**
     * this will keep the record of all the resources (Books, CoursePacks, Magazines) in it
     */

    /**
     * The constructor of the Library Class
     */
    private Library(String name) {
		this.resourceManager = new ResourceManager(this);
		this.userManager = new UserManager(this);
				calendar = Calendar.getInstance();
				this.libraryName = name;
				toBeFined = new HashSet<Fine>();
				
		this.userManager.add("munshi:p", "lums123", User.Type.ADMIN);
    }
	
	/**
     * Get instance method. Makes sure there is only one instantiation of this class
	 * @param the name of the library
	 * @return returns the initiallized library object
     */
	 
	/**
	*Singleton class necessary for one instance of Library..This concept was explained in the lecture.
	*/
	public static Library getInstance(String name){
		if (lib == null){
			lib = new Library(name);
		}
		return lib;
	}
	
	public String getLibraryName(){
		return this.libraryName;
	}
	
	public void getLibraryStats(){
		
		int admin=0,faculty=0,students=0;
		
		//counts the number of admins, faculty and students in users ArrayList
		for(int i=0;i<userManager.getUsers().size();i++){
			if(userManager.getUsers().get(i) instanceof Admin)
				admin++;
			else if(userManager.getUsers().get(i) instanceof Faculty)
				faculty++;
			else
				students++;
		}

		int books=0,magazines=0,course_packs=0;
		
		int issued=0,overdue=0,request=0;
		Borrowable borrowable;
		
		Date date = calendar.getTime();
		
		// Counts the number of books,magazines and course packs. Also counts issued and over due this.getResourceManager().getResources().
		for(int i=0;i<this.getResourceManager().getResources().size();i++){
			if(this.getResourceManager().getResources().get(i) instanceof Book){
				books++;
				borrowable = (Borrowable)this.getResourceManager().getResources().get(i);
				if(borrowable.requests.size()>0)
					request++;
				if(borrowable.getReturnDate1() != null && date.compareTo(borrowable.getReturnDate1()) > 0)
					overdue++;
				if(!borrowable.checkStatus())
					issued++;
			}
			else if(this.getResourceManager().getResources().get(i) instanceof CoursePack){
				course_packs++;
				borrowable = (Borrowable)this.getResourceManager().getResources().get(i);
				if(borrowable.requests.size() > 0)
					request++;
				if(borrowable.getReturnDate1() != null && date.compareTo(borrowable.getReturnDate1()) > 0)
					overdue++;
				if(!borrowable.checkStatus())
					issued++;
			}
			else
				magazines++;
		}
		//output all the stats.
		System.out.println("\t\t\t\t*** Library System Stats ***\n\nNo. of Users:\t" + userManager.getUsers().size());
		System.out.println("\tAdministrators: " + admin + "\n\tFaculty: " + faculty + "\n\tStudents: " + students);
		
		System.out.println("\nNumber of Resources: " + this.getResourceManager().getResources().size() + "\n\tBooks: " + books + "\n\tCourse Packs: " +
											course_packs + "\n\tMagazines: " + magazines);
		
		System.out.println("\n\tItems Issued: " + issued + "\n\tItems Overdue: " + overdue + "\n\tTotal Requests: " + request);
	}
	
	
	
	/*****************************************/
	/************ To Update Fines ************/
	/*****************************************/

	void updateFines(){
	
		//Updates Fines by checking the list kept by Library (Set<Fine> toBeFined).
		//This collection contains the fines of the resources which are issued to some userManager.getUsers().
		//This function checks if the dueDate has passed or not and accordingly makes fines to userManager.getUsers().
		//IF A FINE IS IN THIS COLLECTION IS DOESN'T MEAN THAT THEY WILL BE FINED OBVIOUSLY. but 
		//only those users are fined who have resource(s) with due date passed...
	
	
		Date today = Library.calendar.getTime();
		int amount = 0,days = 0;
		Borrower borrower;
		Borrowable borrowable;
		
		for(Fine fine: toBeFined){
		
			Resource res = this.getResourceManager().get(fine.resourceID);
			if(res == null){
				removeToBeFined(fine);
				continue;
			}
			borrowable = (Borrowable)res;
			
			if(today.compareTo(borrowable.getReturnDate1()) > 0){
				borrower = (Borrower)this.getUserManager().get(fine.userID);
				days = today.getDate()- borrowable.getReturnDate1().getDate();
				if(borrowable instanceof Book){
					amount = days*100;
					fine.updateFine(amount);
				}
				else if(borrowable instanceof CoursePack){
					amount = days*500;
					fine.updateFine(amount);
				}
			}
		}
	}
	
	/*****************************************/

	
	boolean removeToBeFined(Fine fine){
	
	//removes Fine(s) from toBeFined if they are returned!
		if(toBeFined.contains(fine)){
			toBeFined.remove(fine);
			return true;
		}
		else
			return false;
	}
	
	boolean addToBeFined(Fine fine){
	
	//adds Fine to toBeChecked collection
		if(!toBeFined.contains(fine)){
			toBeFined.add(fine);
			return true;
		}
		else
			return false;
	}
}
