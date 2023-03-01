import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Calendar;
import java.util.Date;


public class VuelingSearchTest {

    WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.firefoxdriver().setup();
    }

    @BeforeEach
    void setup() {
        driver = new FirefoxDriver();
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Severity(SeverityLevel.BLOCKER)
    @Test()
    @Description("Search valid flights from Barcelona to Madrid on 1st of June")
    public void testSearchFlights() {

        // Create an instance of the VuelingSearchPage and search for flights
        VuelingSearchPage searchPage = new VuelingSearchPage(driver, new WebDriverWait(driver, 10));
        searchPage.goTo();
        searchPage.acceptCookies();
        searchPage.enterOrigin("Madrid");
        searchPage.enterDestination("Barcelona");
        searchPage.selectOneWayTripFromDatePicker();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, Calendar.JUNE);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date juneFirst = cal.getTime();

        searchPage.selectDateFromDatePicker(juneFirst);
        searchPage.clickSearchButton();

        // Handle page switch
        VuelingFlightResultsPage vuelingFlightResultsPage = new VuelingFlightResultsPage(driver, new WebDriverWait(driver, 10));
        vuelingFlightResultsPage.switchTo();
        // Verify that search results are displayed
        Assertions.assertTrue(driver.getCurrentUrl().contains("https://tickets.vueling.com/"));

        Integer numResults = vuelingFlightResultsPage.getNumberOfResults();
        Assertions.assertTrue(numResults > 0);
        // Quit the WebDriver
        driver.quit();
    }
}
