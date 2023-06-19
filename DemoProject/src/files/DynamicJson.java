package files;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

//REST API 3 principles-
		//given-all input details-queryParam,header,body 
		//when-Submit the API-http method,resource
		//Then- validate the response
      
public class DynamicJson {
	
	@Test(dataProvider="BooksData")
	public void addBook(String isbn,String aisle)
	{
		RestAssured.baseURI="http://216.10.245.166";
		String response=given().header("Content-Type","application/json").
		body(payload.Addbook(isbn,aisle)).
		when().post("/Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200).
		extract().response().asString();      //then extract response in one string variable String resposne=given().
		JsonPath js1=ReUsableMethods.rawToJson(response);
		String id=js1.get("ID");
		System.out.println(id);
		
		/*//deleteBook
		pubic void delete(String isbn,String aisle) {
		
		String response=given().header("Content-Type","application/json").body(payload1.Deletebook(isbn,aisle)).
		when.delete("/Library/DeleteBook.php").
		then().log().all().assertThat().statusCode(200).
				extract(.response().asString();
				JsonPath js1=ReUsableMethods.rawToJson(response);
				
	}*/
	}
	
	@DataProvider(name="BooksData")
	public Object[][] getData()
	{
		//array-collection of elements
		//multidimensional array=collection of arrays
		return new Object[][] {{"ojfwty","9363"},{"cwetee","4253"},{"okmfet","533"}};
	}
	

}
