package harsh;

import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		JsonPath js=new JsonPath(payload.CoursePrice());
		
		//1. Print No of courses returned by API
        int count=js.getInt("courses.size()");
		System.out.println(count);
		//output-3

	
        //2.Print Purchase Amount
	    int totalAmount=js.getInt("dashboard.purchaseAmount");
	    System.out.println(totalAmount);
	    //output-910
	    
	    //3.Print Title of the first course
	    String titleFirstCourse=js.get("courses[0].title");
	    System.out.println(titleFirstCourse);
	    
	    //2nd course
	    String titleSecondCourse=js.get("courses[2].title");
	    System.out.println(titleSecondCourse);
	    
	    //Print all courses and respective prices
	    
	    for(int i=0;i<count;i++)
	    {
	    	String courseTitles=js.get("courses["+i+"].title");
	    	
	    	System.out.println(js.get("courses["+i+"].price").toString());
	    	
	    	
	    	System.out.println(courseTitles);
	    }
	    System.out.println("Print no of copies sold by RPA Course");
	    
	    for(int i=0;i<count;i++)
	    {
	        String courseTitles=js.get("courses["+i+"].title");
	        if(courseTitles.equalsIgnoreCase("RPA"))
	        {
	        	int copies=js.get("courses["+i+"].copies");
	        	System.out.println(copies);
	        	break;
	        }
	    }
	        System.out.println("Print no of copies sold by Appium Course");
		    
		    for(int i=0;i<count;i++)
		    {
		        String courseTitles=js.get("courses["+i+"].title");
		        if(courseTitles.equalsIgnoreCase("Appium"))
		        {
		        	int copies=js.get("courses["+i+"].copies");
		        	System.out.println(copies);
		        	break;
		        }
	    }
	    
}
}
