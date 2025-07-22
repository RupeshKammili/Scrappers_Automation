package OG_image;

import Twitter.TwitterLandingPage;
import Twitter.TwitterValidatorPage;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import AbstarctComponents.Log;

import org.testng.annotations.Test;

import java.time.Duration;

public class ScrappersTwitter extends BaseTest {

	TwitterValidatorPage twitterValidatorPage;

	ExtentTest test;

	@Test
	public void twitterLanding() {

		TwitterLandingPage lpage = new TwitterLandingPage(getDriver());
		lpage.goToTwitter();

		Log.info("Navigating to Twitter post incepator page...");
		twitterValidatorPage = lpage.loginIntoTwitter(prop.getProperty("Tusername").toString(),
				prop.getProperty("Tpassword").toString());
		Log.info("Loging in...");

		test = reports.createTest("Test Twitter login", "Please refer below screen shots for Twitter OG image")
				.assignAuthor("Rupesh").assignDevice("Chrome");

		Log.info("Validating each URL and taking screen shots...");
	}

	@Test(dataProvider = "needTotestUrls", dataProviderClass = DBScrappers.class, dependsOnMethods = {
			"twitterLanding" })
	public void twitterInspectPost(String urls) throws Exception {

		String path = twitterValidatorPage.twitterPageurlsValidation(urls);
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(path).build());
	}

	@Test(dependsOnMethods = {"twitterInspectPost"})
	public void logLastLine() {
		Log.info("twitter scrappers validation has been completed..!");
	}
}
