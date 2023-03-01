## Project Description

This is a Java project that tests the search functionality of the Vueling website using Selenium WebDriver and JUnit 5. The test script navigates to the website, accepts cookies, enters the origin and destination cities, selects a one-way trip on a specific date, and clicks the search button. It then verifies that the search results are displayed and that there is at least one flight available. The test is annotated with the Allure Framework to report the test results.

## Execution Instructions

To execute the project, follow these steps:

1. Navigate to the project root folder in the command line.
2. Run the following command to clean the project and run the test script:

```shell
./gradlew clean test allureServe
```
This command will generate an Allure report, which can be viewed in the browser.