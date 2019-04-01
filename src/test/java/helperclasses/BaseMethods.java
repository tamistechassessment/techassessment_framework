package helperclasses;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseMethods {
	private static Properties ER = null;
    protected static Properties TESTDATA = null;
    private static WebDriver dr = null;
    protected static EventFiringWebDriver driver = null;


    //<editor-fold desc="Main Methods">
    //Purpose: Create Selenium driver, create connections to properties files, create connection to specified browser
    //This method must be run at the start of any automated script, therefore I call it from the startTest Method (Before method) of all tests
    protected void driverSetup() throws Exception {
        try {

            ClassLoader classLoader = getClass().getClassLoader();
            if (driver != null) {
                driver.quit();
            }
            if (driver == null) {
                setup();

                System.setProperty("webdriver.chrome.driver", classLoader.getResource("browsers/chromedriver.exe").getFile());
                ChromeOptions options = new ChromeOptions();
                options.addArguments("disable-extensions");
                options.addArguments("start-maximized");
                dr = new ChromeDriver(options);                                  //Launch Chrome

                driver = new EventFiringWebDriver(dr);                                  //Establish Selenium's connection with the browser
                driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            }
        } catch (Throwable t) {
            Assert.fail("Null pointer exception encounter in driverSetup() method. Files may not be accessible .");    //Fails test cases
        }
    }
    //Purpose: Create Selenium driver, create connections to properties files, create connection to specified browser
    //This method must be run at the start of any automated script, therefore I call it from the startTest Method (Before method) of all tests
    protected void setup() throws Exception {
        try {
            ClassLoader classLoader = getClass().getClassLoader();

            TESTDATA = new Properties();
            FileInputStream td = new FileInputStream(classLoader.getResource("test_data.properties").getFile());             //Connect to test_data.properties file to retrieve the test data
            TESTDATA.load(td);
            ER = new Properties();
            FileInputStream ip = new FileInputStream(classLoader.getResource("element_repository.properties").getFile());    //Connect to element_repository.properties file to retrieve the identifying information about each object in the application
            ER.load(ip);
        } catch (Throwable t) {
            Assert.fail("Null pointer exception encounter in setup() method. Files may not be accessible.");    //Fails test cases
            }
    }
	//Purpose: Navigate to the specified URL
	protected static void navigateToUrl(String url){
		driver.get(TESTDATA.getProperty(url));                  //URL retrieved from test_data.properties file
	}
	//Purpose: Uses Selenium method to create the WebElement object that allows the actions to be performed on the web page elements.
    //Returns: WebElement object that actions are performed on the web page.
	static WebElement getElement(String xpath) throws Exception {
		try{
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);        //Waits up to 20 seconds for the element to become available
			return driver.findElement(By.xpath(ER.getProperty(xpath)));             //Element Xpath retrieved from the element_repository.properties file
		}catch(Throwable t){
			Assert.fail("Exception encounter in getElement() method. Files may not be accessible");    //Fails test cases
		    return null;
		}	
	}
    //Purpose: Create a list of elements for elements like radio buttons, using the elements name value, that have multiple values
    //Returns a list of elements
    static List<WebElement> getListOfElementsByClassName(String classname) throws Exception {
        String name = ER.getProperty(classname);
        try{
            return driver.findElements(By.className(name));
        }catch (Throwable t){
            Assert.fail("Exception encounter in getElement() method. Files may not be accessible");    //Fails test cases
            return null;
        }
    }
    static void waitForElement(String element){
        WebDriverWait wait = new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ER.getProperty(element))));
    }
    //</editor-fold>
    }
