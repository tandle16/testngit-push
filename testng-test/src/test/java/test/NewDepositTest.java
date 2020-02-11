package test;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class NewDepositTest {

	WebDriver driver;

	@BeforeMethod
	public void launchBrowser() {
		System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("http://techfios.com/test/billing/?ng=admin/");
	}

	@Test
	public void userShouldBeAbleToDeposite1() throws InterruptedException {
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys("techfiosdemo@gmail.com");
		driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("abc123");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		// validating particular title through Assertion
		String expectedTitle = "Dashboard- TechFios Test Application - Billing";
		Assert.assertEquals(driver.getTitle(), expectedTitle, "Dashboard Page did not display!");
		// Thread.sleep(2000);
		By TRANSACTIONS_MENU_LOCATOR = By.xpath("//ul[@id='side-menu']/descendant::span[text()='Transactions']");
		By NEW_DEPOSITE_PAGE_LOCATOR = By.linkText("New Deposit");
		driver.findElement(TRANSACTIONS_MENU_LOCATOR).click();
		// using WaitForElement Method at New deposit to wait
		waitForElement(NEW_DEPOSITE_PAGE_LOCATOR, driver, 30);
		driver.findElement(NEW_DEPOSITE_PAGE_LOCATOR).click();
		// Select an account
		Select select = new Select(driver.findElement(By.cssSelector("select#account")));
		select.selectByVisibleText("AutoTest858");
		
//		Random rnd = new Random();
		// =rnd.nextInt(999);
		String expectedDescription = "AutomationTest" + new Random().nextInt(999);
		System.out.println(expectedDescription);
		driver.findElement(By.id("description")).sendKeys(expectedDescription);
		driver.findElement(By.id("amount")).sendKeys("3500,500");
		driver.findElement(By.id("submit")).click();
		//validating with explicit wait
		new WebDriverWait(driver , 60).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(@class,'blockUI')]")));
		waitForElement(By.linkText(expectedDescription),driver,60);
		//Thread.sleep(5000);
		//to get whole table x-path
		List<WebElement> descriptionElements = driver.findElements(By.xpath("//table/descendant::a"));
        //
	//	System.out.println(descriptionElements.get(0).getText());
		//validating with Assertion
		Assert.assertTrue(isDescriptionMatch(expectedDescription , descriptionElements), "Deposit unsucessfull!");
		Thread.sleep(10000);

	}
	//looping to compare every element
	private boolean isDescriptionMatch(String expectedDescription, List<WebElement> descriptionElements) {
	for(int i=0; i <descriptionElements.size(); i++) {
		if(expectedDescription.equalsIgnoreCase(descriptionElements.get(i).getText())) { 
			return true;
		}
	}
		
		return false;
	}

	private void waitForElement(By LOCATOR, WebDriver driver1, int time) {
		new WebDriverWait(driver1, time).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(LOCATOR));
		
	}
	@AfterMethod
	public void closeEverything() throws InterruptedException {
		Thread.sleep(2000);
		driver.close();
		driver.quit();
	}
}