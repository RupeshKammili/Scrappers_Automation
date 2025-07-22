package AbstarctComponents;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

public class AbstractClass {

    WebDriver driver;

    public AbstractClass(WebDriver driver){
        this.driver = driver;
    }


    public void waitfortheVisbilityOfElement(By b){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));
        wait.until(ExpectedConditions.visibilityOfElementLocated(b));
    }

    public String takeScreeShot(String fileName) throws Exception{
        TakesScreenshot ts = (TakesScreenshot) driver;
        File fs = ts.getScreenshotAs(OutputType.FILE);
        File fd = new File("./Scrapper/"+fileName);
        FileUtils.copyFile(fs, fd);
        System.out.println("Screen shot taken successfully..");
        return fd.getPath();
    }

    public void executeJSScript(String key){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(key);
    }
}
