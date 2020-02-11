package test;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

public class Session5HwAddingNewAccount {

	WebDriver driver;

	@BeforeMethod
	public void launchBrowser() {
		System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("http://techfios.com/test/billing/?ng=admin/");
	}

	@Test
	public void addNewUserAccount() throws InterruptedException {
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys("techfiosdemo@gmail.com");
		driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("abc123");
		driver.findElement(By.xpath("//button[text()='Sign in']")).click();
		Thread.sleep(3000);
		String expectedTitle = "Dashboard- TechFios Test Application - Billing";

		Assert.assertEquals(driver.getTitle(), expectedTitle, "Dashboard Page did not display!");
		By BankAndCash_Menu_Locator = By.xpath("//ul[@id='side-menu']/descendant::span[text()='Bank & Cash']");
		By NewAccount_Page_Locator = By.linkText("New Account");
		driver.findElement(BankAndCash_Menu_Locator).click();
		driver.findElement(NewAccount_Page_Locator).click();

		// Selecting Choose an Account from DropDown
		// Select select = new
		// Select(driver.findElement(By.xpath("//input[@id='account']")));
//		select.selectByVisibleText(text);

		String ExpectedBankCashAddNewAccount = "AutomationTest" + new Random().nextInt(999);
		driver.findElement(By.xpath("//input[@id='account']")).sendKeys(ExpectedBankCashAddNewAccount);

		driver.findElement(By.xpath("//input[@id='description']")).sendKeys("My_Account");

		driver.findElement(By.xpath("//input[@id='balance']")).sendKeys(ExpectedBankCashAddNewAccount);

		driver.findElement(By.xpath("//button[@class='btn btn-primary']")).click();
		Thread.sleep(3000);
		// Validating the Presence of "Account Created Successfully"

		String AccountCreatedSuccesfully_xpath = "//div[@id='wrapper']//descendant::div[@class='alert alert-success fade in']";

		new WebDriverWait(driver, 60)
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(AccountCreatedSuccesfully_xpath)));

		// WebElement msgElem =
		// driver.findElement(By.xpath(AccountCreatedSuccesfully_xpath));

		// System.out.println("found text: " + msgElem.getText());

		Thread.sleep(2000);
		List<WebElement> accountElements = driver.findElements(By.xpath("//table/descendant::td"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("scroll(0,659)");
		Thread.sleep(3000);

	/*	Assert.assertTrue(isAccountMatch(expectedMatch, accountElements), "Did not show up Account");
	}

	private boolean isAccountMatch(String expectedMatch, List<WebElement> accountElements) {
		for (int i = 0; i < accountElements.size(); i++) {
			if (expectedMatch.equalsIgnoreCase(accountElements.get(i).getText())) {
				return true;
			}
		}
		return false;*/
	}

	@AfterMethod
	public void closeEverything() throws InterruptedException {
		Thread.sleep(2000);
		driver.close();
		driver.quit();
	}
}
