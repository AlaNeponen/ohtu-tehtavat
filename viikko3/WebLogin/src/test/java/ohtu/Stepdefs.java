package ohtu;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.Assert.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class Stepdefs {
    //WebDriver driver = new ChromeDriver();signupsignupsignupsignup
    WebDriver driver = new HtmlUnitDriver();
    String baseUrl = "http://localhost:4567";
    @Given("user with username {string} with password {string} is successfully created")
    public void userWithUsernameWithPasswordIsSuccessfullyCreated(String username, String password) {
        NewIsSelected();
        createNewUser(username, password, password);
    }
    @Given("user with username {string} and password {string} is tried to be created")
    public void userWithUsernameAndPasswordIsTriedToBeCreated(String username, String password) {
        NewIsSelected();
        createNewUser(username, password, password);
    }
    @Given("login is selected")
    public void loginIsSelected() {
        driver.get(baseUrl);
        WebElement element = driver.findElement(By.linkText("login"));       
        element.click();   
    }    
    @Given("command new user is selected")
    public void NewIsSelected() {
        driver.get(baseUrl);
        WebElement element = driver.findElement(By.linkText("register new user"));       
        element.click();   
    }    
    @When("correct username {string} and password {string} are given")
    public void correctUsernameAndPasswordAreGiven(String username, String password) {
        logInWith(username, password);
    }    
    
    @Then("user is logged in")
    public void userIsLoggedIn() {
        pageHasContent("Ohtu Application main page");
    }    
 
    @When("correct username {string} and incorrect password {string} are given")
    public void correctUsernameAndIncorrectPasswordAreGiven(String username, String password) {
        logInWith(username, password);
    }    
    @When("a valid username {string} and password {string} are entered but the password confirmation {string} doesnt match")
    public void aValidUsernameAndPasswordAreEnteredButThePasswordConfirmationDoesntMatch(String username, String password, String passwordConf) {
        createNewUser(username, password, passwordConf);
    }
    @Then("user is not logged in and error message is given")
    public void userIsNotLoggedInAndErrorMessageIsGiven() {
        pageHasContent("invalid username or password");
        pageHasContent("Give your credentials to login");
    }    
    
    @When("nonexistent username {string} and password {string} are given")
    public void usernameAndPasswordAreGiven(String username, String password) throws Throwable {
        logInWith(username, password);
    }   
    @When("valid username {string} and too short password {string} and matching password confirmation are entered")
    public void validUsernameAndTooShortPasswordAndMatchingPasswordConfirmationAreEntered(String username, String password) {
        createNewUser(username, password, password);
    }

    @Then("system will respond {string}")
    public void systemWillRespond(String pageContent) throws Throwable {
        assertTrue(driver.getPageSource().contains(pageContent));
    }
    @When("too short username {string} and valid password {string} and matching password confirmation are entered")
    public void tooShortUsernameAndValidPasswordAndMatchingPasswordConfirmationAreEntered(String username, String password) {
        createNewUser(username, password, password);
    } 
    @Then("a new user is created")
    public void newUserCreated() {
        pageHasContent("Welcome to Ohtu Application!");
    }
    @Then("user is not created and error {string} is reported")
    public void userNotCreated(String error) {
        pageHasContent(error);    
    }
    
    @When("a valid username {string} and password {string} and matching password confirmation are entered")
    public void aValidUsernameAndPasswordAndMatchingPasswordConfirmationAreEntered(String username, String password) {
        createNewUser(username, password, password);
    }
    @After
    public void tearDown(){
        driver.quit();
    }
        
    /* helper methods */
 
    private void pageHasContent(String content) {
        assertTrue(driver.getPageSource().contains(content));
    }
        
    private void logInWith(String username, String password) {
        assertTrue(driver.getPageSource().contains("Give your credentials to login"));
        WebElement element = driver.findElement(By.name("username"));
        element.sendKeys(username);
        element = driver.findElement(By.name("password"));
        element.sendKeys(password);
        element = driver.findElement(By.name("login"));
        element.submit();  
    }
    private void createNewUser(String username, String password, String passwordConf) {
        assertTrue(driver.getPageSource().contains("Create username and give password"));
        WebElement element = driver.findElement(By.name("username"));
        element.sendKeys(username);
        element = driver.findElement(By.name("password"));
        element.sendKeys(password);
        element = driver.findElement(By.name("passwordConfirmation"));
        element.sendKeys(passwordConf);
        element = driver.findElement(By.name("signup"));
        element.submit();



    } 
}
 