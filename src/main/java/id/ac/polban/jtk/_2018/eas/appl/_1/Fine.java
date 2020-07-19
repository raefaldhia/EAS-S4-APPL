package id.ac.polban.jtk._2018.eas.appl._1;

import id.ac.polban.jtk._2018.eas.appl._1.common.Entity;

public class Fine extends Entity {
	private static Integer LAST_USED_ID = 0;

	int resourceID;
	int userID;
	int fine;
	
	//Constructor for Fine class...
	Fine(int _resID,int userID,int _fine){
		super(GetNextId());

		this.resourceID = _resID;
		this.fine = _fine;
		this.userID = userID;
	}
	
	//to update fine.
	void updateFine(int fine){
		this.fine = fine;
	}

	private static Integer GetNextId () {
        Fine.LAST_USED_ID += 1;
        return Fine.LAST_USED_ID;
    }
}