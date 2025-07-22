package OG_image;

import LinkedIn.LinkedINLandingPage;
import LinkedIn.LinkedInPostInceptorPage;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import AbstarctComponents.Log;

import org.testng.annotations.Test;

public class ScrappersLinkedIn extends BaseTest {

	LinkedInPostInceptorPage postInceptorPage;

	ExtentTest test;

	@Test
	public void LikedInLanding() {

		LinkedINLandingPage linkedINLandingPage = new LinkedINLandingPage(getDriver());
		linkedINLandingPage.goToLinkedInLoginPage();
		postInceptorPage = linkedINLandingPage.loginIntoLinkedIn(prop.getProperty("Lusername").toString(),
				prop.getProperty("Lpassword").toString());

		Log.info("Navigating to LinkedIn post incepator page...");
		postInceptorPage.goToLinkedInPostInceptorPage();
		Log.info("Loging in...");

		test = reports.createTest("Test Linkedin login", "Please refer below screen shots for LinkedIn OG image")
				.assignAuthor("Rupesh").assignDevice("Chrome");

		Log.info("Validating each URL and taking screen shots...");

	}

	@Test(dataProvider = "needTotestUrls", dataProviderClass = DBScrappers.class, dependsOnMethods = {
			"LikedInLanding" })
	public void LinkedInPostInception(String urls) throws Exception {
		
		String path = postInceptorPage.validateOGimageForURL(urls, "LinkedIN.png");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(path).build());
	}

	@Test(dependsOnMethods = {"LinkedInPostInception"})
	public void logLastLine() {
		Log.info("LinkedInPostInception scrappers validation has been completed..!");
	}
}
