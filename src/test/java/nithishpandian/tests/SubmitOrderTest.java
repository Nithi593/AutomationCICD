package nithishpandian.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import nithishpandian.TestComponents.BaseTest;
import nithishpandian.pageobjects.CartPage;
import nithishpandian.pageobjects.CheckoutPage;
import nithishpandian.pageobjects.ConfirmationPage;
import nithishpandian.pageobjects.LandingPage;
import nithishpandian.pageobjects.OrderPage;
import nithishpandian.pageobjects.ProductCatalogue;

public class SubmitOrderTest extends BaseTest {
	
	String productName = "ZARA COAT 3";	

	@Test(dataProvider = "getData", groups= {"Purchase"})
	public void submitOrder(String email, String password, String productName) throws IOException {
		
			
		//login
		ProductCatalogue productCatalogue = landingPage.loginApplication(email, password);
		
		//get products
		List<WebElement> products = productCatalogue.getProductsList();

		//add to cart 
		productCatalogue.addProductToCart(productName);

		//cart PAge
		CartPage cartPage = productCatalogue.goToCartPage();
		
		Boolean match = cartPage.VerifyProductDisplay(productName);		
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		
		checkoutPage.selectCountry("india");
		
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		
		String confirmMessage = confirmationPage.getConfirmationPage();

		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
	
	}
	
	//To verify ZARA COAT 3 is displaying in orders page
	@Test(dependsOnMethods = {"submitOrder"})
	public void orderHistoryTest() {
		
		ProductCatalogue productCatalogue = landingPage.loginApplication("scottmcall41@gmail.com", "Scottmcall41");
		OrderPage ordersPage = productCatalogue.goToOrdersPage();
		Assert.assertTrue(ordersPage.VerifyOrderDisplay(productName));
		driver.close();
	}
	
	
	
	@DataProvider
	public Object[][] getData() {
		return new Object[][] {{"scottmcall41@gmail.com", "Scottmcall41", "ZARA COAT 3"}, {"scottmcall41@gmail.com", "Scottmcall41" , "ADIDAS ORIGINAL"}};
	}
	
	@Test(dataProvider = "getData1", groups= {"Purchase1"})
	public void submitOrder1(HashMap<String, String> input) throws IOException {
		
			
		//login
		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
		
		//get products
		List<WebElement> products = productCatalogue.getProductsList();

		//add to cart 
		productCatalogue.addProductToCart(input.get("productName"));

		//cart PAge
		CartPage cartPage = productCatalogue.goToCartPage();
		
		Boolean match = cartPage.VerifyProductDisplay(input.get("productName"));		
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		
		checkoutPage.selectCountry("india");
		
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		
		String confirmMessage = confirmationPage.getConfirmationPage();

		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
	
	}
	
	@DataProvider
	public Object[][] getData1() throws IOException {
		/*
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("email", "scottmcall41@gmail.com");
		map.put("password", "Scottmcall41");
		map.put("productName", "ZARA COAT 3");
		
		HashMap<String, String> map1 = new HashMap<String, String>();
		map1.put("email", "scottmcall41@gmail.com");
		map1.put("password", "Scottmcall41");
		map1.put("productName", "ADIDAS ORIGINAL");
		*/
		
		
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir") + "/src/test/java/nithishpandian/data/PurchaseOrder.json");
		return new Object[][] {{data.get(0)}, {data.get(1)}};
	}

}
