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

        // CI-friendly download location
        String downloadPath = System.getProperty("user.dir") + File.separator + "Downloads";

        File folder = new File(downloadPath);

        if (!folder.exists()) {
            boolean created = folder.mkdirs();

            if (created) {
                System.out.println("Folder created: " + folder.getAbsolutePath());
            } else {
                System.out.println("Failed to create folder!");
            }
        }

        // Chrome download preferences
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", downloadPath);
        prefs.put("download.prompt_for_download", false);
        prefs.put("profile.default_content_settings.popups", 0);

        ChromeOptions options = new ChromeOptions();

        options.setExperimentalOption("prefs", prefs);

        // Headless mode for GitHub Actions / CI
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");

        ChromeDriver driver = new ChromeDriver(options);

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        FileInputStream fis = null;
        FileOutputStream fileOut = null;

        try {

            driver.get(
                "https://pgreports.atomtech.in/titan_merchant_console/home#no-back-button"
            );

            WebElement username = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                    By.id("userName")
                )
            );

            username.sendKeys("330350");

            WebElement password = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//input[@id='password']")
                )
            );

            password.sendKeys("Dde@1972");

            WebElement login = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//button[@value='Login']")
                )
            );

            login.click();

            WebElement transactions = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//p[contains(text(),'Transactions')]")
                )
            );

            transactions.click();

            Thread.sleep(1000);

            WebElement viewtransactions = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//span[contains(text(),'View Transaction New')]")
                )
            );

            viewtransactions.click();

            System.out.println("clicked");

            for (int i = 1; i <= 1; i++) {

                LocalDate today = LocalDate.now();

                DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern(
                        "dd/MM/yyyy",
                        Locale.ENGLISH
                    );

                String livedate = today.format(formatter);
                String fromdate =
                    today.minusDays(10).format(formatter);

                System.out.println(livedate);
                System.out.println(fromdate);

                Thread.sleep(1000);

                WebElement FromDate = wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//input[@id='fromDate']")
                    )
                );

                JavascriptExecutor js =
                    (JavascriptExecutor) driver;

                js.executeScript(
                    "document.getElementById('fromDate').value='"
                    + fromdate + "';"
                );

                Thread.sleep(1000);

                WebElement Todate = wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//input[@id='toDate']")
                    )
                );

                js.executeScript(
                    "document.getElementById('toDate').value='"
                    + livedate + "';"
                );

                Thread.sleep(1000);

                WebElement dropdownBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[@data-id='transactionStatus']")
                    )
                );

                dropdownBtn.click();

                WebElement option = wait.until(
                    ExpectedConditions.elementToBeClickable(
                        By.xpath("//span[@class='text' and text()='Success']")
                    )
                );

                js.executeScript(
                    "arguments[0].scrollIntoView(true);",
                    option
                );

                Thread.sleep(300);

                option.click();

                Thread.sleep(1000);

                WebElement search = wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//button[@id='search']")
                    )
                );

                wait.until(
                    ExpectedConditions.elementToBeClickable(search)
                );

                search.click();

                WebElement xlsx = wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//button[contains(text(), 'XLSX')]")
                    )
                );

                js.executeScript(
                    "arguments[0].scrollIntoView(true);",
                    xlsx
                );

                wait.until(
                    ExpectedConditions.elementToBeClickable(xlsx)
                );

                xlsx.click();

                Thread.sleep(5000);

                System.out.println(i + " pass");
                System.out.println(
                    "Download path: " + folder.getAbsolutePath()
                );

                Thread.sleep(3000);
            }

            System.out.println(
                "SUCCESSFULLY GET THE PAYMENT DETAILS EXCEL"
            );

        } catch (Exception e) {

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

            if (driver != null) {
                driver.quit();
            }
        }
    }
}