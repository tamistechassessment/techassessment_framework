package testcases;

import helperclasses.testutil;
import org.junit.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class saucedemo_uitests extends testutil {


    @BeforeMethod
    public void startTest() throws Exception
    {
        driverSetup();
    }
    //Automation Engineer: Tamera Eaton
    //Testcase ID:
    //Description: On Saucedemo webpage, login, select products and verify products are added to cart
    @Test
    public void saucedemo()throws Exception
    {
        String productOnesie = (TESTDATA.getProperty("productName1"));
        String productBikeLight = (TESTDATA.getProperty("productName2"));

        //Launch Saucedemo and login
        navigateToUrl("Saucedemo_URL");
        saucedemoLogin();

        //Add Onesie and Bike Light products to cart
        addProductToCart("AllItemsPage_Title_SauceLabsOnesie");  //Note: Element xpath defined in element_repository.properties file
        navigateToAllItemsPage();
        addProductToCart("AllItemsPage_Title_BikeLight");
        navigateToCartPage();

        //Verify correct products are in the cart
        boolean itemOneIsInCart = verifyItemsInCart(productOnesie);
        boolean itemtwoIsInCart = verifyItemsInCart(productBikeLight);
        Assert.assertTrue(itemOneIsInCart && itemtwoIsInCart);
    }
    @AfterMethod
    public void wrapup ()
    {
        driver.quit();
    }
}
