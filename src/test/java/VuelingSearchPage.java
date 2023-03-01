import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class VuelingSearchPage {
    private WebDriver driver;

    private WebDriverWait wait;

    public VuelingSearchPage(WebDriver driver, WebDriverWait wait) {
        // Set up the WebDriver
        this.wait = wait;
        this.driver = driver;
    }

    public void goTo() {
        // Navigate to the Vueling search page
        driver.get("https://www.vueling.com/");
    }

    public void acceptCookies() {
        WebElement acceptCookiesButton = wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("onetrust-accept-btn-handler"))));
        acceptCookiesButton.click();
    }

    public void enterOrigin(String origin) {
        // Set origin string on the origin input
        WebElement originInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("originInput")));
        originInput.sendKeys(origin);

        // Wait for the dropdown list to appear
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement firstOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#popup-list li.liStation")));

        // Select the first option from the dropdown list
        firstOption.click();
    }

    public void enterDestination(String destination) {
        WebElement destinationInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("destinationInput")));
        destinationInput.sendKeys(destination);

        // Wait for the dropdown list to appear
        WebElement firstOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#popup-list li.liStation")));

        // Select the first option from the dropdown list
        firstOption.click();
    }

    public void openOutboundDatePicker() {
        // Click on the outbound date input
        WebElement outboundDateInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("outboundDate")));
        outboundDateInput.click();
    }

    public void selectOneWayTripFromDatePicker() {
        WebElement oneWayRadioButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("onewayList")));
        oneWayRadioButton.click();
    }

    public void selectDateFromDatePicker(Date date) {

        // Format the date to as an integer for easier manipulation
        Integer intDate = this.formatDateIntoInteger(date);

        // Find the web element corresponding to the date and click on it
        WebElement dateElement = this.findDateElement(intDate);
        dateElement.click();
    }

    private Integer formatDateIntoInteger(Date date) {
        // Get the year, month, and day from the provided date
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        // Format the date as "YYYYMMDD" and parse it as an integer
        String formattedDate = String.format("%d%d%d", year, month, day);
        return Integer.parseInt(formattedDate);
    }

    private WebElement findDateElement(Integer date) {
        List<WebElement> displayingDateElements;
        WebElement targetDateElement = null;

        // Keep looping until the target date element is found
        while (targetDateElement == null) {
            // Find all web elements that start with "calendarDaysTable"
            displayingDateElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("table#calendarDaysTable td[id^='calendarDaysTable']")));

            // Initialize variables to keep track of the minimum and maximum dates in the current display
            int minDisplayingDate = Integer.MAX_VALUE;
            int maxDisplayingDate = Integer.MIN_VALUE;

            // Iterate over all the displaying date elements and look for the target date element
            for (WebElement displayingDateElement : displayingDateElements) {
                int displayingDate = Integer.parseInt(displayingDateElement.getAttribute("id").replaceAll("\\D+", ""));
                if (displayingDate == date) {
                    targetDateElement = displayingDateElement;
                    break;
                }
                minDisplayingDate = Math.min(minDisplayingDate, displayingDate);
                maxDisplayingDate = Math.max(maxDisplayingDate, displayingDate);
            }

            // If the target date element has not been found, click on the next or previous button
            if (targetDateElement == null) {
                String buttonId = date < minDisplayingDate ? "prevButtonCalendar" : "nextButtonCalendar";
                driver.findElement(By.id(buttonId)).click();
            }
        }

        return targetDateElement;
    }

    public void clickSearchButton() {
        WebElement searchButton = driver.findElement(By.id("btnSubmitHomeSearcher"));
        searchButton.click();
    }
}
