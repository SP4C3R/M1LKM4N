package sms;

public class smsDone_new {

    public static void main(String[] args) 
    {        
    	String m="Banglore Computer Edu.: * Registration Open for DEC-2017 batches * Learn: ANDROID, JAVA, C Plus Plus.";
    	
    	String resp=SST_SMS.bceSunSoftSend("9465208070", m);
    	System.out.println(resp);
    	
    	if(resp.indexOf("Exception")!=-1)
    		System.out.println("Check Internet Connection");
    	
    	else
    		if(resp.indexOf("successfully")!=-1)
        		System.out.println("Sent");
    		else
    		System.out.println( "Invalid Number");
    }
}