package testrun;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class NTT_DATA {
	@Test(priority = 1)
	
	public void paymentdetailsExcel() throws IOException {

		// 1. Choose a folder where all Excel files will be saved

		String downloadPath = "D:\\SVU_DIS_Feb_2026\\Exam_application_August\\Transactions";
		File folder = new File(downloadPath);
		if (!folder.exists()) {
			boolean created = folder.mkdirs();
			if (created) {
				System.out.println("✅ Folder created: " + folder.getAbsolutePath());
			} else {
				System.out.println("❌ Failed to create folder!");
			}
		}
		// 2. Set Chrome preferences
		Map<String, Object> prefs = new HashMap<>();
		prefs.put("download.default_directory", downloadPath); // always save here
		prefs.put("download.prompt_for_download", false); // no popup
		prefs.put("profile.default_content_settings.popups", 0);

		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", prefs);

		ChromeDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.get("https://pgreports.atomtech.in/titan_merchant_console/home#no-back-button");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		FileInputStream fis = null;
		FileOutputStream fileOut = null;
		try {
//			FileInputStream fileinput = new FileInputStream(downloadPath);
//			XSSFWorkbook workbook = new XSSFWorkbook(fileinput);
//			Sheet sheet = workbook.getSheetAt(0);

			WebElement username = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("userName")));
			username.sendKeys("330350");

			WebElement password = wait
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='password']")));
			password.sendKeys("Dde@1972");

			WebElement login = wait
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@value='Login']")));
			login.click();

			WebElement transactions = wait.until(
					ExpectedConditions.presenceOfElementLocated(By.xpath("//p[contains(text(),'Transactions')]")));
			transactions.click();

			Thread.sleep(1000);
			WebElement viewtransactions = wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//span[contains(text(),'View Transaction New')]")));
			viewtransactions.click();
			System.out.println("clicked");

			for (int i = 1; i <= 1; i++) {
//				Row row1 = sheet.getRow(i);
//				if (row1 == null) {
//					continue;
//				}

				LocalDate today = LocalDate.now();
				DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);
				String livedate = today.format(formater);
				System.out.println(livedate);

				String fromdate = today.minusDays(10).format(formater);
				System.out.println(fromdate);
				Thread.sleep(1000);
				WebElement FromDate = wait
						.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='fromDate']")));

				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("document.getElementById('fromDate').value='" + fromdate + "';");
				// js.executeScript("document.getElementById('fromDate').value='19-08-2025';");
				// FromDate.sendKeys(Keys.TAB);
				Thread.sleep(1000);
				WebElement Todate = wait
						.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='toDate']")));
				js.executeScript("document.getElementById('toDate').value='" + livedate + "';");
				// js.executeScript("document.getElementById('toDate').value='"+todate+"';");
				Thread.sleep(1000);

				// 1. Click the visible dropdown button (not the <select>)
				WebElement dropdownBtn = wait.until(
						ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-id='transactionStatus']")));
				dropdownBtn.click();

				// 2. Wait and click the desired option (like ONLINE)
				WebElement option = wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("//span[@class='text' and text()='Success']")));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", option);
				Thread.sleep(300); // Optional
				option.click();

				Thread.sleep(1000);
				WebElement search = wait
						.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@id='search']")));
				wait.until(ExpectedConditions.elementToBeClickable(search));
				search.click();

				WebElement xlsx = wait.until(
						ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(text(), 'XLSX')]")));//// a[contains(text(),
				//// 'XLSX')]
				js.executeScript("arguments[0].scrollIntoView(true);", xlsx);
				wait.until(ExpectedConditions.elementToBeClickable(xlsx));
				xlsx.click();
//				Thread.sleep(1000);
//				WebElement Mydownload = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[contains(text(),'My Downloads')]")));
//				Mydownload.click();
//				Thread.sleep(1000);
//				WebElement download = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'Downloads')]")));
//				wait.until(ExpectedConditions.elementToBeClickable(download));
//				download.click();
//				Thread.sleep(1000);
//				WebElement Downloadexcel = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//a[contains(@href,'downloadReportFile')])[1]")));
//				wait.until(ExpectedConditions.elementToBeClickable(Downloadexcel));
//				Downloadexcel.click();
//				
				Thread.sleep(5000);
				System.out.println(i + " pass");
				System.out.println(folder.getAbsolutePath());
				Thread.sleep(3000);
			}

//			FileOutputStream fileout = new FileOutputStream(downloadPath);
//			workbook.write(fileout);
			System.out.println("SUCCESSFULLY GET THE PAYMENT DETAILS EXCEL");

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (fileOut != null) {
					fileOut.close();
				}
				if (fis != null) {
					fis.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (driver != null) {
			driver.quit(); // Close the WebDriver session

		}
	}

}
