package Login;

import Main.CustomListener;
import Main.Util;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.time.Duration;

@Listeners (CustomListener.class)
public class VerifyTheLoginSection {
    static WebDriver Driver1 ;
    static String ActualResult ;
    Alert WrongUserAlert ;
    SoftAssert SoftAssert_Login = new SoftAssert();

    @BeforeTest (groups = "Login")
    public void Launch_Browser()
    {
        System.out.println("launching chrome browser");
        Driver1 = new ChromeDriver();
        Driver1.get(Util.BaseUrl) ;
        Driver1.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }
    @AfterTest (groups = "Login")
    public void Close_Browser()
    {
        Driver1.close();
    }
    /**
    * 
    * @author Omar Medhat
    * @details Test Script 01 - Test Case 1
    *        ************** 
    * @Steps        
    *       1)  Go to http://www.demo.guru99.com/V4/
            2) Enter valid UserId
            3) Enter valid Password
            4) Click Login
    */
    @Test (priority = 9 , groups = "Login")
    public void Verify_Succeful_Login() {
        Driver1.findElement(By.name("uid")).clear();
        Driver1.findElement(By.name("uid")).sendKeys(Util.UserID);

        Driver1.findElement(By.name("password")).clear();
        Driver1.findElement(By.name("password")).sendKeys(Util.Password);

        Driver1.findElement(By.name("btnLogin")).click();
        ActualResult =Driver1.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[2]/td/marquee")).getText();
        SoftAssert_Login.assertEquals(ActualResult,Util.Expected_Result_Title);
        SoftAssert_Login.assertAll();
    }

    /**
    * 
    * @author Omar Medhat
    * @details Test Script 01 - Test Case 2
    *        ************** 
    * @Steps        
    *       1)  Go to http://www.demo.guru99.com/V4/
            2) Enter empty UserId
            3) Enter empty Password
            4) Click Login
    */    
    @Test (priority = 1)
    public void Verify_BlankData_For_UserID() {

        Driver1.findElement(By.name("uid")).click();
        Driver1.findElement(By.name("password")).click();
        ActualResult = Driver1.findElement(By.id("message23")).getText();
        SoftAssert_Login.assertEquals(ActualResult,Util.Expected_Result_BlankUserID);
        SoftAssert_Login.assertAll();

    }

    /**
    * 
    * @author Omar Medhat
    * @details Test Script 01 - Test Case 3
    *        ************** 
    * @Steps        
    *       1)  Go to http://www.demo.guru99.com/V4/
            2) Enter empty UserId
            3) Enter empty Password
            4) Click Login
    */    
    @Test (priority = 2)
    public void Verify_BlankData_For_Password() {

        Driver1.findElement(By.name("password")).click();
        Driver1.findElement(By.name("uid")).click();
        ActualResult = Driver1.findElement(By.id("message18")).getText();
        SoftAssert_Login.assertEquals(ActualResult,Util.Expected_Result_BlankPassword);
        SoftAssert_Login.assertAll();

    }

    /**
    * 
    * @author Omar Medhat
    * @details Test Script 01 - Test Case 4
    *        ************** 
    * @Steps        
    *       1)  Go to http://www.demo.guru99.com/V4/
            2) Enter valid wrong UserId
            3) Enter valid Password
            4) Click Login
    */
    @Test (priority = 3)
    public void Verify_Enter_Wrong_UserId() {
    
        Driver1.findElement(By.name("uid")).clear();
        Driver1.findElement(By.name("uid")).sendKeys(Util.RandomUserID);

        Driver1.findElement(By.name("password")).clear();
        Driver1.findElement(By.name("password")).sendKeys(Util.Password);

        Driver1.findElement(By.name("btnLogin")).click();

        WrongUserAlert = Driver1.switchTo().alert();
        ActualResult = WrongUserAlert.getText();
        WrongUserAlert.accept();
        SoftAssert_Login.assertEquals(ActualResult,Util.Expected_Result_WrongUserOrPass);
        SoftAssert_Login.assertAll();

    }

    /**
    * 
    * @author Omar Medhat
    * @details Test Script 01 - Test Case 5
    *        ************** 
    * @Steps        
    *       1)  Go to http://www.demo.guru99.com/V4/
            2) Enter valid UserId
            3) Enter valid wrong Password
            4) Click Login
    */
    @Test (priority = 4)
    public void Verify_Enter_Wrong_Password() {

        Driver1.findElement(By.name("uid")).clear();
        Driver1.findElement(By.name("uid")).sendKeys(Util.UserID);

        Driver1.findElement(By.name("password")).clear();
        Driver1.findElement(By.name("password")).sendKeys(Util.RandomPassword);

        Driver1.findElement(By.name("btnLogin")).click();

        WrongUserAlert = Driver1.switchTo().alert();
        ActualResult = WrongUserAlert.getText();
        WrongUserAlert.accept();
        SoftAssert_Login.assertEquals(ActualResult,Util.Expected_Result_WrongUserOrPass);
        SoftAssert_Login.assertAll();

    }
}