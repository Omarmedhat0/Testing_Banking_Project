import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.time.Duration;

@Listeners (CustomListener.class)
public class VerifyTheLoginSection extends  Main{

    @BeforeTest (groups = "Login")
    public void Launch_Browser()
    {
        System.out.println("launching chrome browser");
        Driver1 = new ChromeDriver();
        Driver1.get(BaseUrl);
        Driver1.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }
    @AfterTest (groups = "Login")
    public void Close_Browser()
    {
        Driver1.close();
    }
    @Test (priority = 9 , groups = "Login")
    public void Verify_Succeful_Login() {
        String UserID = "mngr597258" ;
        String Password = "ebEhUjy" ;
        String ActualResult ;
        String ExpectedResult = "Welcome To Manager's Page of Guru99 Bank" ;
        SoftAssert LoginSucced = new SoftAssert();
        Driver1.findElement(By.name("uid")).sendKeys(UserID);
        Driver1.findElement(By.name("password")).sendKeys(Password);
        Driver1.findElement(By.name("btnLogin")).click();
        ActualResult =Driver1.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[2]/td/marquee")).getText();
        LoginSucced.assertEquals(ActualResult,ExpectedResult);
        LoginSucced.assertAll();
    }
    @Test (priority = 1)
    public void Verify_BlankData_For_UserID_Or_Password() {
        String UserIDActualResult ;
        String UserIDExpectedResult = "User-ID must not be blank" ;
        String PasswordIDActualResult ;
        String PasswordExpectedResult = "Password must not be blank" ;
        SoftAssert BlankUserID = new SoftAssert();
        SoftAssert BlankPassword = new SoftAssert();
        Driver1.findElement(By.name("uid")).click();
        Driver1.findElement(By.name("password")).click();
        Driver1.findElement(By.name("uid")).click();
        UserIDActualResult = Driver1.findElement(By.id("message23")).getText();
        PasswordIDActualResult = Driver1.findElement(By.id("message18")).getText();
        BlankUserID.assertEquals(UserIDActualResult,UserIDExpectedResult);
        BlankPassword.assertEquals(PasswordIDActualResult,PasswordExpectedResult);
        BlankPassword.assertAll();
        BlankUserID.assertAll();
    }
    @Test (priority = 2)
    public void Verify_Enter_Wrong_UserId() {
        String UserID = "mngr597259" ;
        String Password = "ebEhUjy" ;
        Alert WrongUserAlert ;
        String ActualResult ;
        String ExpectedResult = "User or Password is not valid" ;
        SoftAssert LoginFailed = new SoftAssert();
        Driver1.findElement(By.name("uid")).sendKeys(UserID);
        Driver1.findElement(By.name("password")).sendKeys(Password);
        Driver1.findElement(By.name("btnLogin")).click();
        WrongUserAlert = Driver1.switchTo().alert();
        ActualResult = WrongUserAlert.getText();
        WrongUserAlert.accept();
        LoginFailed.assertEquals(ActualResult,ExpectedResult);
        LoginFailed.assertAll();

    }
    @Test (priority = 3)
    public void Verify_Enter_Wrong_Password() {
        String UserID = "mngr597258" ;
        String Password = "ebEhUjq" ;
        Alert WrongUserAlert ;
        String ActualResult ;
        String ExpectedResult = "User or Password is not valid" ;
        SoftAssert LoginFailed = new SoftAssert();
        Driver1.findElement(By.name("uid")).sendKeys(UserID);
        Driver1.findElement(By.name("password")).sendKeys(Password);
        Driver1.findElement(By.name("btnLogin")).click();
        WrongUserAlert = Driver1.switchTo().alert();
        ActualResult = WrongUserAlert.getText();
        WrongUserAlert.accept();
        LoginFailed.assertEquals(ActualResult,ExpectedResult);
        LoginFailed.assertAll();

    }
}