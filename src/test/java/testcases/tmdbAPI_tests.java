package testcases;

import helperclasses.testutil;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;


public class tmdbAPI_tests extends testutil {
    RequestSpecification request= RestAssured.given();
    private String apikey = null;
    private String baseURL = null;

    @BeforeMethod
    public void startTest() throws Exception
    {
        setup();
        apikey = "?api_key=" + TESTDATA.getProperty("tmdbApiKey");
        baseURL = TESTDATA.getProperty("tmdb_baseURL");
    }
    //Automation Engineer: Tamera Eaton
    //Testcase ID:
    //Description: Verify 200 status code for Search Movies endpoint
    @Test
    public void get_ResponseCode200()
    {
        String movieId = TESTDATA.getProperty("tmdb_movieId");
        String movieDetailsEndpointURL = constructMovieDetailsEndpointURL(movieId, baseURL, apikey);
        int code = get(movieDetailsEndpointURL).getStatusCode();
        Assert.assertEquals(code, 200);

        //ToDo: Add reporting of test case results to test case tool (i.e. JIRA, Rally)
    }
    //Automation Engineer: Tamera Eaton
    //Testcase ID:
    //Description: Verify Authentication > Negative test > Provide invalid API Key
    @Test
    public void get_InvalidAuthentication()
    {
        String movieId = TESTDATA.getProperty("tmdb_movieId");
        String invalidApikey = TESTDATA.getProperty("invalidAPIKey");
        String movieDetailsEndpointURL = constructMovieDetailsEndpointURL(movieId, baseURL, invalidApikey);
        int code = get(movieDetailsEndpointURL).getStatusCode();
        Assert.assertEquals(code, 401);

        //ToDo: Add reporting of test case results to test case tool (i.e. JIRA, Rally)
    }
    //Automation Engineer: Tamera Eaton
    //Testcase ID:
    //Description: Verify a successful movie rating post
    @Test
    public void post_RateMovie()
    {
        String movieId = TESTDATA.getProperty("tmdb_movieId");
        int rating = Integer.parseInt(TESTDATA.getProperty("rating"));

        //get guest session id
        String guestSessionId = getGuestSessionId(baseURL, apikey);

        //build request and post rating
        int code = buildRateMovieRequestAndPost(rating, movieId, guestSessionId, baseURL, apikey);
        Assert.assertEquals(code, 201);

        //ToDo: Add reporting of test case results to test case tool (i.e. JIRA, Rally)
    }
    //Automation Engineer: Tamera Eaton
    //Testcase ID:
    //Description: Delete movie rating
    @Test
    public void delete_Rating()
    {
        String movieId = TESTDATA.getProperty("tmdb_movieId");

        //Post a rating first so it can be deleted
        post_RateMovie();

        //get guest session id
        String guestSessionId = getGuestSessionId(baseURL, apikey);

        //build request and delete rating
        String statusMessage = buildRateMovieRequestAndDeleteRating(movieId, guestSessionId, baseURL, apikey);

        //validate response
        Assert.assertEquals(statusMessage,"The item/record was deleted successfully.");

        //ToDo: Add reporting of test case results to test case tool (i.e. JIRA, Rally)
    }

    //Automation Engineer: Tamera Eaton
    //Testcase ID:
    //Description: Verify movie rating can be changed
    @Test
    public void post_UpdateMovieRating_Code201()
    {
        //ToDo: For Rate Movie endpoint, after posting a rating, update the rating. Verify the new rating value.
    }
    //Automation Engineer:
    //Testcase ID:
    //Description: Verify valid authentication.
    @Test
    public void get_ValidateAuthentication()
    {
        //ToDo: Provide a valid API Key. Verify a successful connection
    }

    //Automation Engineer:
    //Testcase ID:
    //Description: For Rate Movie endpoint, verify a 401 unauthorized status code for an invalid guest session id.
    @Test
    public void get_GuestSessionID_NegativeTest_Code401()
    {
        //ToDo: For Rate Movie endpoint, verify a 401 unauthorized status code for an invalid guest session id.
    }

    //Automation Engineer:
    //Testcase ID:
    //Description: For Search Movies end point, verify search results return correct movie title for a specified movie
    @Test
    public void get_SearchMovieTitle()
    {
        //ToDo: Search for a movie using the endpoint for Search Movies. Verify the title in the response (i.e. Captain Marvel)
    }

    //Automation Engineer:
    //Testcase ID:
    //Description: For Search Movies end point, verify response contains multiple results
    @Test
    public void get_SearchMovieMultipleTitles()
    {
        //ToDo: Search for a movie using the endpoint for Search Movies that will return multiple results. Verify multiple results are returned in the response (i.e. Captain Marvel)
    }

    //Automation Engineer:
    //Testcase ID:
    //Description: For Search Movies end point, verify all results returned in the response have the year supplied
    @Test
    public void get_SearchMovieTitleAndYear_PostiiveTest()
    {
        //ToDo: Search for a movie using based on title and a year that will return no movies found, in the endpoint for Search Movies. Verify all titles found have the correct year supplied in the search criteria, in the response (i.e. Captain Marvel)
    }

    //Automation Engineer:
    //Testcase ID:
    //Description: For Search Movies end point, verify response message that communicates no results were found
    @Test
    public void get_SearchMovieTitleAndYear_NegativeTest()
    {
        //ToDo: Search for a movie using based on title and a year that will return no movies found, in the endpoint for Search Movies. Verify the search results return no titles found (i.e. Captain Marvel)
    }

    //Automation Engineer:
    //Testcase ID:
    //Description: For Create Guest Session endpoint, verify the successful retrieval of a guest session id.
    @Test
    public void get_GuestSessionID()
    {
        //ToDo: For Create Guest Session endpoint, verify the successful retrieval of a guest session id.
    }
}