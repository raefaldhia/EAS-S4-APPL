package id.ac.polban.jtk._2018.eas.appl._1;

import java.util.*;

import id.ac.polban.jtk._2018.eas.appl._1.User.Type;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class App implements Runnable {
    private final Scanner scanner;

    private final Library library;

    App (final String libName){
        this.scanner = new Scanner(System.in);
        library = Library.getInstance(libName);
    }

    /*****************************************/
    /******** USER Login IO Interface ********/
    /*****************************************/
    void loginIO(){
        System.out.println("Enter the username:");
        final String username = scanner.nextLine();

        System.out.println("Enter the password:");
        final String password = scanner.nextLine();

        User user = library.getUserManager()
                           .getByUsername(username);

        if(user == null) {
            System.out.println("The username or password was not correct!\n");
            return;
        }
        
        if(!user.authenticate(username, password)) {
            System.out.println("The username or password entered was not correct!");
            return;
        }        

        final String template_1 = "\n\nChoose from the Following options:\n\n" +
                                "- For adding an administrator account to the system, press 1\n" + "- For adding a faculty account to the system, press 2\n" +
                                "- For adding a student account to the system, press 3\n" + "- For adding a book to the system, press 4\n" +
                                "- For adding a course pack to the system, press 5\n" + "- For adding a magazine to the system, press 6\n" +
                                "- For removing a user account from the system, press 7\n" + "- For removing a resource from the system, press 8\n" +
                                "- For logging out of the system, press 9";

        final String template_2 = "\n\nChoose from the following options:\n\n"+
                                  "- For borrowing a resource, press 1\n"+
                                  "- For returning a resource, press 2\n"+
                                  "- For deleting a request, press 3\n"+
                                  "- For viewing issued books, press 4\n"+
                                  "- For viewing pending requests, press 5\n"+
                                  "- For viewing you fines, press 6\n"+
                                  "- For logging out of the system, press 7\n"+
                                  "- For renewing a resource, press 8\n";

        boolean done = false;

        

        //For Admin Login IO Interface!
        while(!done) {     
            if(user.getType() == Type.ADMIN) {         
                System.out.println("\n***************************************\n\nWelcome "+ username + "!" + ((user.getType() == Type.ADMIN) ? template_1 : template_2));
  
                switch (this.scanner.nextInt()) {
                    case 1:
                        addUserIO((Admin)user,User.Type.ADMIN);		//transfers control to add user IO.
                        break;
                    case 2:
                        addUserIO((Admin)user,User.Type.FACULTY);	//transfers control to add user IO.
                        break;
                    case 3:
                        addUserIO((Admin)user,User.Type.STUDENT);	//transfers control to add user IO.
                        break;
                    case 4:
                        addResourceIO((Admin)user,Resource.Type.BOOK);	//transfers control to add resource Interface
                        break;
                    case 5:
                        addResourceIO((Admin)user,Resource.Type.COURSEPACK);	//transfers control to add resource Interface
                        break;
                    case 6:
                        addResourceIO((Admin)user,Resource.Type.MAGAZINE);	//transfers control to add resource Interface
                        break;
                    case 7:
                        removeUserIO((Admin)user);		// transfers control to remove user Interface
                        break;
                    case 8:
                        removeResourceIO((Admin)user);	// transfers control to remove resource Interface
                        break;
                    case 9:
                        System.out.println("Do you really want to log out? y/n");		// asks user if wants to log out
                        
                        final String option = this.scanner.next();
                        if((option.equals("y") || option.equals("Y"))){
                            System.out.println("Thanks... logging out!");
                            user.logout();
                            done = true;
                        }
                        else{
                            System.out.println(user.getUsername() + " still Logged in...");
                        }
                    break;
                    default:
                        System.out.println("Give the correct input");
                        break;
                }
            }
            else {
                switch(this.scanner.nextInt()) {
                    case 1:
                        borrowIO((Borrower)user);		// transfers control to borrow Interface
                        break;
                    case 2:
                        returnResourceIO((Borrower)user);		// transfers control to return resource Interface
                        break;
                    case 3:
                        deleteRequestIO((Borrower)user);		// transfers control to delete request Interface
                        break;
                    case 4:
                        ((Borrower)user).viewIssued();			// views issued Resources by calling viewIssued function
                        break;
                    case 5:
                        ((Borrower)user).viewRequests();		// views requests of user
                        break;
                    case 6:
                        Set<Fine> fines = ((Borrower)user).getFines();

                        if (fines.isEmpty()) {
                            System.out.println("There are no fines.");
                        }
                        else {
                            int totalFine = 0,i=1;
                            
                            System.out.println("Following is the list of fines:\n\nNo. ---- resourceID ---- Fine");
                            for(Fine fine : fines){
                                if(fine.fine == 0)
                                    continue;
                                System.out.println((i) + ". ---- "+ fine.resourceID + " ---- " + fine.fine);
                                totalFine += fine.fine;
                                i++;
                            }
                            
                            System.out.println("Total fine = Rs. " + totalFine);    
                        }
                        break;
                    case 8:
                        renewResourceIO((Borrower)user);		// transfers control to renew Resource IO Interface
                        break;
                    case 7:
                        System.out.println("Do you really want to log out? y/n");

                        final String option = this.scanner.next();
                        if((option.equals("y") || option.equals("Y"))){
                            System.out.println("Thanks... logging out!");
                            user.logout();
                            done = true;
                        }
                        else {
                            System.out.println(user.getUsername() + " Still Logged in...");
                        }
                        break;
                    default:
                        System.out.println("Give the correct input");
                        break;
                }
            }
        }
    }

    /*****************************************/
    /********* Add User IO Interface *********/
    /*****************************************/
    void addUserIO(Admin admin, User.Type type) {
        System.out.println("Enter the new username:");
        final String username = scanner.nextLine();
        
        System.out.println("Enter the password:");
        final String password = scanner.nextLine();
        
        try {
            Integer id = library.getUserManager()
                                .add(username, password, type);

            System.out.println("The new userID is: " + id + "\n");
        } catch (Exception e) {
            System.out.println("New user could not be created. Please try again with different username.\n");
        }
    }

    /*****************************************/
    /******* Remove User IO Interface ********/
    /*****************************************/
    void removeUserIO(Admin admin){
        int id;
        String userInput;
        System.out.println("Enter the userID:");

        while(!scanner.hasNextInt()){
            System.out.println("Give the correct integer input...");
            System.out.println("Enter the userID:");
            userInput = scanner.nextLine();
            continue;
        }
        id = scanner.nextInt();
        userInput = scanner.nextLine();
        
        if (library.getUserManager().remove(id)) {
            System.out.println("User " + id + " has been successfully removed!");
        }
        else{
            System.out.println("User " + id + "was not removed!");
        }
    }
    /*****************************************/


    /*****************************************/
    /******** IO For Adding Resource *********/
    /*****************************************/

    void addResourceIO(Admin admin, Resource.Type type){

        //This function asks for the Name of the Resource to be Added and calls addResource function of Admin.Java...
    
        String name;
        System.out.println("Enter the name of Resource:");
        name = scanner.nextLine();
        try {
            library.getResourceManager().add(name, type);
            System.out.println("The resource has been added successfully");
        }
        catch (Exception e) {
            System.out.println("The resource could not be added.\nPlease try with another name!");
        }
    }
    /*****************************************/


    /*****************************************/
    /******* IO For Removing Resource ********/
    /*****************************************/
    void removeResourceIO(Admin admin){
    
        // This function assks for the Name or ID of the Resource to be deleted and calls removeResource function of ADMIN...
        List<Resource> resources = new ArrayList<Resource>();
        String name,userInput;
        int id = -1;
        System.out.println("Enter the Resource Name or ID:");
        if(scanner.hasNextInt()){
            id = scanner.nextInt();
            userInput = scanner.nextLine();
        }
        else{
            name = scanner.nextLine();
            if(library.getResourceManager().getByName(name)!=null){
                resources = library.getResourceManager().getByName(name);
            }
        }
        for(int i =0;i<resources.size();i++){
            if(library.getResourceManager().remove(resources.get(i).getId())){
                System.out.println("The resource is successfully removed!");
            }
            else{
                System.out.println("The resource is not found/removed");
            }
        }
    }
    /*****************************************/


    /*****************************************/
    /************** Borrow IO ****************/
    /*****************************************/
    void borrowIO(Borrower borrower){
    
        // This function asks for the name or ID of the resource to be borrowed and calls tryIssue function of the Borrower.java
        List<Resource> resources = new ArrayList<Resource>();
        String name;
        int id = -1;
        System.out.println("Enter the name or ID of resource:");
        if(scanner.hasNextInt()){
            id = scanner.nextInt();
            name =  scanner.nextLine();
        }
        else{
            name = scanner.nextLine();
            if(library.getResourceManager().getByName(name)!=null){
                resources = library.getResourceManager().getByName(name);
            }
        }
        borrower.tryIssue(resources);
    }
    /*****************************************/


    /*****************************************/
    /******** IO for Resource Return *********/
    /*****************************************/

    void returnResourceIO(Borrower borrower){
    
        // This function asks for the name or ID of the resource to be returned and calls tryReturn function of the Borrower.java 
        List<Resource> resources = new ArrayList<Resource>();
        String name;
        int id = -1;
        System.out.println("Enter the name or ID of resource:");
        if(scanner.hasNextInt()){
            id = scanner.nextInt();
            name = scanner.nextLine();
        }
        else{
            name = scanner.nextLine();
            if(library.getResourceManager().getByName(name)!=null){
                resources = library.getResourceManager().getByName(name);
            }
        }
        
        for(int i =0;i<resources.size();i++){
            id=resources.get(i).getId();
            if(borrower.findIssued(resources.get(i).getId())){
                if(borrower.tryReturn(resources.get(i).getId())){
                    System.out.println("The requested resource has been successfully returned!");
                    return;
                }
                
            }
        }
        if(borrower.findIssued(id)){
            if(borrower.tryReturn(id)){
                System.out.println("The requested resource has been successfully returned!");
            }
        }
        
        
        
        System.out.println("The resource was not returned! or it was not found!");
        
    }
    /*****************************************/


    /*****************************************/
    /******** IO for Request Deletion ********/
    /*****************************************/
    void deleteRequestIO(Borrower borrower){
    
        // This function asks for the name or ID of resource of which request is to be deleted and calls withdrawRequest of Borrower.Java...
        List<Resource> resources = new ArrayList<Resource>();
        int id = -1;;
        String name;
        borrower.viewRequests();
        System.out.println("\nEnter the name or ID of the Resource:");
        if(scanner.hasNextInt()){
            id = scanner.nextInt();
            name = scanner.nextLine();
        }
        else{
            name = scanner.nextLine();
            if(!library.getResourceManager().getByName(name).isEmpty()){
                resources = library.getResourceManager().getByName(name);
                for(int i =0;i<resources.size(); i++){
                    if(borrower.withdrawRequest(resources.get(i).getId())){
                        System.out.println("The request was withdrawn successfully!");
                        return;
                    }
                }
            }
            else if(borrower.withdrawRequest(id)){
                System.out.println("The request was withdrawn successfully!");
                return;
            }
        }
        
        System.out.println("The transaction was not completed!");
    }

    /*****************************************/

    /*****************************************/
    /********* IO for Renew Resource *********/
    /*****************************************/	
    
    void renewResourceIO(Borrower borrower){
    
        // This function asks for the name or ID of resource to be renewed and calls tryRenew of Borrower.Java...
        ArrayList<Resource> resources = new ArrayList<Resource>();
        int id = -1;;
        String name;
        System.out.println("\nEnter the name or ID of the Resource to renew: ");
        if(scanner.hasNextInt()){
            id = scanner.nextInt();
            name = scanner.nextLine();
        }
        else {
            name = scanner.nextLine();
            for(int i =0;i<resources.size(); i++){
                if(borrower.tryRenew(resources.get(i).getId())){
                    System.out.println("The request was withdrawn successfully!");
                    return;
                }
            }
        }
        
        if(borrower.tryRenew(id)){
            System.out.println("The resource was renewed successfully!");
        }
        else{
            System.out.println("The resource was not renewed!");
        }
        
        
    }

    @Override
    public void run() {		
        boolean done = false;
        
        while(!done){
            System.out.println("\n\n***Welcome to Library Management System***\n\nSelect from the following options:\n\n" +
                               "-  For viewing system stats, press 1\n" + "-  For logging into the system press 2\n" +
                               "-  For exit, press 3\n");

            switch(this.scanner.nextInt()){
                case 1:
                    this.library.getLibraryStats();
                    break;
                case 2:
                    this.loginIO();
                    break;
                case 5:
                    System.out.println("Do you really want to lexit? y/n");

                    final String option = this.scanner.next();
                    if (option.equals("y") || option.equals("Y")) {
                        System.out.println("Thanks... exiting!");
                        done = true;
                    }
                    break;
                default:
                    System.out.println("Give the correct input");
                    break;
            }
            this.library.updateFines();
        }
        
        this.scanner.close();

        //Printing date...
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Library.calendar;
        System.out.println(dateFormat.format(cal.getTime()));
    }

    public static void main (String[] argv){
        new App("LUMS Library").run();
    }
}