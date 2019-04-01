package helperclasses;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import java.util.List;

//These methods are used to perform actions needed to support function performed on the TMDB APIs
public class TestUtil extends BaseMethods {
    private RequestSpecification request= RestAssured.given();

    protected String constructMovieDetailsEndpointURL(String movieId, String baseURL, String apikey)
    {
        String movieDetailsEndpoint = "movie/"+ movieId;
        return baseURL + movieDetailsEndpoint + apikey;
    }
    private String constructAuthenticationEndpointURL(String baseURL, String apikey)
    {
        String authenticationEndpoint = "authentication/guest_session/new";
        return baseURL + authenticationEndpoint + apikey;
    }
    private String constructRateMovieEndpointURL(String movieId, String baseURL, String apikey, String guestSessionId)
    {
        String rateMovieEndpoint = "movie/"+ movieId + "/rating";
        return baseURL + rateMovieEndpoint + apikey +"&guest_session_id="+guestSessionId;
    }
    protected String getGuestSessionId(String baseURL, String apikey) {
        String authenticationEndpoint_URL = constructAuthenticationEndpointURL(baseURL, apikey);
        return request.get(authenticationEndpoint_URL).then().extract().path("guest_session_id");

    }
    protected int buildRateMovieRequestAndPost(int rating, String movieId, String guestSessionId, String baseURL, String apikey)
    {
        //Required header for Rate Movie endpoint
        request.header("Content-Type", "application/json");

        //build JSON Request Body
        JSONObject json=new JSONObject();
        json.put("value", rating);             //movie rating
        request.body(json.toJSONString());

        //construct url
        String rateMovieURL = constructRateMovieEndpointURL(movieId, baseURL, apikey, guestSessionId);

        //post rating request and get status code
        return request.post(rateMovieURL).getStatusCode();
    }
    protected String buildRateMovieRequestAndDeleteRating(String movieId, String guestSessionId, String baseURL, String apikey)
    {
        //build request
        request.header("Content-Type", "application/json");

        //construct url
        String deleteRateMovieURL = constructRateMovieEndpointURL(movieId, baseURL, apikey, guestSessionId);

        //Post movie rating and get response
        return request.delete(deleteRateMovieURL).then().extract().path("status_message");
    }

    //***********************************Saucedemo Methods************************************


    protected void saucedemoLogin()throws Exception
    {
        try {
            getElement("Saucedemo_Editfield_Username").sendKeys(TESTDATA.getProperty("Loginpage_user"));    //Note: Element xpath defined in element_repository.properties file
            getElement("Saucedemo_Editfield_Password").sendKeys(TESTDATA.getProperty("Loginpage_password"));
            getElement("Saucedemo_Button_Login").click();
        }catch(Throwable t){
            Assert.fail("Null pointer exception encounter in saucedemoLogin() method. Test data or objects may not be accessible .");    //Fails test cases
        }
    }
    protected void addProductToCart(String productName) throws Exception
    {
        try{
            Thread.sleep(1000);
            getElement(productName).click();
            waitForElement("ItemPage_Button_AddToCart");
            getElement("ItemPage_Button_AddToCart").click();
        }catch(Throwable t){
            Assert.fail("Null pointer exception encounter in addProductToCart() method. Objects may not be accessible .");    //Fails test cases
        }
    }
    protected void navigateToAllItemsPage() throws Exception
    {
        try{
            getElement("Menu_Button").click();
            waitForElement("Menu_Link_AllItems");
            getElement("Menu_Link_AllItems").click();
        }catch(Throwable t){
            Assert.fail("Null pointer exception encounter in navigateToAllItemsPage() method. Objects may not be accessible .");    //Fails test cases
        }
    }
    protected void navigateToCartPage() throws Exception
    {
        try{
            waitForElement("Cart_link");
            getElement("Cart_link").click();
        }catch(Throwable t){
            Assert.fail("Null pointer exception encounter in navigateToCartPage() method. Objects may not be accessible .");    //Fails test cases
        }
    }
    protected boolean verifyItemsInCart(String productName) throws Exception
    {
        try{
            List<WebElement> items = getListOfElementsByClassName("Cart_list");
            for (WebElement item: items)
            {
                String itemName = item.getText();
                if(itemName.equals(productName))
                {
                    return true;
                }
            }
            return false;
        }catch(Throwable t){
            Assert.fail("Null pointer exception encounter in navigateToCartPage() method. Objects may not be accessible .");    //Fails test cases
            return false;
        }
    }
}
