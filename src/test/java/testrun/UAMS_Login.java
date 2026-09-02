package testrun;

import java.time.Duration;

import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UAMS_Login {

	@Test
	
	public void UAMS_login() {
		ChromeOptions optins = new ChromeOptions();
		optins.addArguments("--headless=new");
		optins.addArguments("--no-sandbox");
		optins.addArguments("--disable-dev-shm-usage");
		optins.addArguments("--window-size=1920,1080");
		
		ChromeDriver driver = new ChromeDriver(optins);
	
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		driver.get("https://uamsadmin.epen.co.in/");
		WebElement username = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='username']")));
		username.sendKeys("Iyyappan");
		WebElement password = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='password']")));
		password.sendKeys("admin@1234");
		
		WebElement signin = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(text(),'Sign In')]")));
		signin.click();
		
		WebElement dashboard = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(),'Dashboard')]")));
		
		System.out.println("Dashboard is displayed: " + dashboard.isDisplayed());
		System.out.println("logged in successfully");
		driver.quit();
	}
}
