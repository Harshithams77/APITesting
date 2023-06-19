package files;
import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class JiraTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//REST API 3 principles-
				//given-all input details-queryParam,header,body 
				//when-Submit the API-http method,resource
				//Then- validate the response
		
		//add comment->    POST   /rest/api/2/issue/{issueIdOrKey}/comment
		
		RestAssured.baseURI="http://localhost:8080";
		
		//Login scenario
		SessionFilter session=new SessionFilter();
		
	String response = given().relaxedHTTPSValidation().header("Content-Type","application/json").body("{\r\n"
			+ "    \"username\": \"harshitha.murali15\", \r\n"
			+ "    \"password\":\"Ramyaharsh15\" \r\n"
			+ "}")
		.log().all().filter(session).when().post("/rest/auth/1/session").then().log().all().extract().response().asString();
		
	
	String expectedMessage="Hi How are you?";
		//Add comment
		String addCommentResponse=given().pathParam("key", "10106").log().all().header("Content-Type","application/json").body("{\r\n"
				+ "    \"body\": \""+expectedMessage+"\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"+
						" }\r\n"
				+ "}").filter(session).when().post("/rest/api/2/issue/{key}/comment").then().log().all().assertThat().statusCode(201).extract().response().asString();
		JsonPath js=new JsonPath(addCommentResponse);
		String commentId=js.getString("id");
		
		
		
        //Add attachment
		//POST /rest/api/2/issue/{issueIdOrKey}/attachments
		//curl -D- -u admin:admin -X POST -H "X-Atlassian-Token: no-check" -F "file=@myfile.txt" http://myhost/rest/api/2/issue/TEST-123/attachments
		given().header("X-Atlassian-Token","no-check").filter(session).pathParam("key","10106").header("Content-Type","multipart/form-data").multiPart("file",new File("jira.txt")).when().post("rest/api/2/issue/{key}/attachments").then().log().all().assertThat().statusCode(200);
		
		
		//Get Issue
		String issueDetails=given().filter(session).pathParam("key", "10106").queryParam("fields","comment").log().all().when().get("/rest/api/2/issue/{key}").then().log().all().extract().response().asString();	
		System.out.println(issueDetails);
		
		JsonPath js1=new JsonPath(issueDetails);
	    int commentsCount=js1.getInt("fields.comment.comments.size()");
		for(int i=0;i<commentsCount;i++)
		{
			String commentIdIssue=js1.get("fields.comment.comments["+i+"].id").toString();
			if(commentIdIssue.equalsIgnoreCase(commentId))
			{
				String message=js1.get("fields.comment.comments["+i+"].body").toString();
				System.out.println(message);
				Assert.assertEquals(message,expectedMessage);
			}
		}
			
		
		}

}