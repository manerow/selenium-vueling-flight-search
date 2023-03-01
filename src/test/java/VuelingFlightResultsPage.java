import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class VuelingFlightResultsPage {
    private WebDriver driver;

    private WebDriverWait wait;

    public VuelingFlightResultsPage(WebDriver driver, WebDriverWait wait) {
        // Set up the WebDriver
        this.wait = wait;
        this.driver = driver;
    }

    public void switchTo() {
        String targetUrl = "https://tickets.vueling.com/ScheduleSelectNew.aspx";
        for (String windowHandle : driver.getWindowHandles()) {
            driver.switchTo().window(windowHandle);
            if (driver.getCurrentUrl().contains(targetUrl)) {
                break;
            }
        }
    }

    public Integer getNumberOfResults() {
        List<WebElement> displayingFlightResults = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("trip-selector_button")));
        return displayingFlightResults.size();
    }
}
