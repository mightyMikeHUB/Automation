package com.dice;

import com.dice.base.BaseTest;
import com.dice.base.CsvDataProvider;
import com.dice.pages.LogInPage;
import com.dice.pages.ProfilePage;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.assertTrue;

public class LoginTest extends BaseTest {

    @Test
    public void positiveLogInTest() {
        LogInPage loginPage = new LogInPage(driver);
        String expectedPageTitle = "Seeker Dashboard - Profile";
        String correctProfileName = "Vadim Krivolanenko";

        // Open dice login page - https://www.dice.com/dashboard/login
        loginPage.openLogInPage();

        // Fill up email and password
        loginPage.fillUpEmailAndPassword("vadim.krivolanenko@gmail.com", "dtlhjbl1");

        // Push Sign IN button and wait for page profile to load
        ProfilePage profilePage = loginPage.pushSignInButton();
        profilePage.waitForProfilePageToLoad();

        // Verifications
        // - Virify title of the page is correct - Seeker Dashboard - Profile
        System.out.println("Verification");
        String actualTitle = profilePage.getTitle();
        assertTrue(expectedPageTitle.equals(actualTitle),
                "Page Title is not expected.\nExpected: " + expectedPageTitle + "\nActual: " + actualTitle + ".");

        // - Verify correct name on profile page - Vadim Krivolanenko
        assertTrue(profilePage.isCorrectProfileLoaded(correctProfileName), "Profile name is not expected.");
    }

    @Test(dataProvider = "CsvDataProvider", dataProviderClass = CsvDataProvider.class)
    public void negativeLogInTest(Map<String, String> testData) {
        String expectedErrorMessage = "Email and/or password incorrect.";
        String testNumber = testData.get("no");
        String email = testData.get("email");
        String password = testData.get("password");
        String description = testData.get("description");

        System.out.println("Test No #" + testNumber + " for " + description + " Where\nEmail: " + email + "\nPassword: " + password);

        LogInPage loginPage = new LogInPage(driver);

        // Open dice login page - https://www.dice.com/dashboard/login
        loginPage.openLogInPage();

        // Fill up email and password
        loginPage.fillUpEmailAndPassword(email, password);

        // Push Sign IN button
        loginPage.pushSignInButton();

        String errorMessage = loginPage.getLogInErrorMessage();

        assertTrue(errorMessage.contains(expectedErrorMessage),
                "Error messageis not expected.\nExpected: " + expectedErrorMessage + "\nActual: " + errorMessage + ".");
    }
}
