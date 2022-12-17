package com.stays;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.utils.PropertyReader;

public class FourthPage {
	WebDriver driver = null;
	Properties prop = null;
	private String name = null;
	private String mobile = null;
	private String emailID = null;
	private String suggestions = "Roll away matress ...";
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement ptmt = null;

	public FourthPage(WebDriver driver) {
		this.driver = driver;
		prop = PropertyReader.getInstance();
	}	

	public void extra() throws Exception {
		driver.findElement(By.name(prop.getProperty("checkbox"))).click();
		driver.findElement(By.id(prop.getProperty("special"))).click();
		WebElement ef = driver.findElement(By.xpath(prop.getProperty("write")));
		ef.sendKeys(suggestions);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,900)");
	}

	public void completeBook(){
//		driver.findElement(By.id(prop.getProperty("confirm"))).click();
		WebElement c = driver.findElement(By.id(prop.getProperty("confirm")));
		Actions ac = new Actions(driver);
		ac.moveToElement(c);
		ac.click().build().perform();
		
		
	}

	public void enterName() {
		WebElement n = driver.findElement(By.id(prop.getProperty("contactName")));
		n.clear();
		n.sendKeys(name);
	}

	public void enterMobile() {
		WebElement m = driver.findElement(By.xpath(prop.getProperty("mobile")));
		m.clear();
		m.sendKeys(mobile);
	}

	public void enterEmail() {
		WebElement em = driver.findElement(By.xpath("//div[@class='email-input ']//input"));
		em.clear();
		em.sendKeys(emailID);
	}

	public void invalidMsgName() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		WebElement surname = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("nameInput"))));
		String okname = surname.getText();
		System.out.println();
		System.out.println("!!! ERROR -> " + okname);

	}

//	public void invalidMsgBoth() {
//		invalidMsgName();
//		WebDriverWait wait = new WebDriverWait(driver, 30);
//		WebElement mob = wait
//				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("mobileInput"))));
//		String okmob = mob.getText();
//		System.out.println();
//		System.out.println("!!! ERROR -> " + okmob);
//	}

	public void database() throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:127.0.0.1:1521/ORCL", "system", "Oracle123");
		try {
			String query = "insert into expedia(contactName,mobile,email) values(?,?,?)";
			ptmt = conn.prepareStatement(query);
			ptmt.setString(1, name);
			ptmt.setString(2, mobile);
			ptmt.setString(3, emailID);
			ptmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ptmt != null)
					ptmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void displayTraveler() {
		System.out.println();
		System.out.println("Traveler details are ");
		System.out.println("Name  - > " + name);
		System.out.println("Mobile is -> " + mobile);
		System.out.println("Email id is -> " + emailID);
		System.out.println();
		System.out.println("Your special request before check-in -> " + suggestions);
	}

	public void display() {

		System.out.println();
		System.out.println("Thankyou for booking a room via Expedia.co.in ");
		System.out.println("---------------------------------------------");
		System.out.println();
		System.out.println("Price summary title are below");
		System.out.println("---------------------------");
		String p = driver.findElement(By.xpath(prop.getProperty("priceDetails"))).getText();
		System.out.println(p + ":-");
		System.out.println();
		List<WebElement> total = driver.findElements(By.xpath(prop.getProperty("totalPrice")));
		System.out.println("   ----------------------------------------");
		for (int i = 0; i < total.size(); i++) {
			String t = total.get(i).getText();
			System.out.print(" |" + t + "        ");
		}
		System.out.println();
		List<WebElement> total1 = driver.findElements(By.xpath(prop.getProperty("roomSummary")));
		for (int i = 0; i < total1.size(); i++) {
			String t = total1.get(i).getText();
			System.out.print(" |" + t + "      ");
		}

		System.out.println();
		List<WebElement> total2 = driver.findElements(By.xpath(prop.getProperty("checkIn")));
		for (int i = 0; i < total2.size(); i++) {
			String q = total2.get(i).getText();
			System.out.print(" |" + q + "    ");
		}
		System.out.println();
		List<WebElement> total3 = driver.findElements(By.xpath(prop.getProperty("checkOut")));
		for (int i = 0; i < total3.size(); i++) {
			String r = total3.get(i).getText();
			System.out.print(" |" + r + "   ");
		}
		System.out.println();
		System.out.println("   -----------------------------------------");
		System.out.println();
		System.out.println();
		String summ = driver.findElement(By.xpath(prop.getProperty("summary"))).getText();
		System.out.println(summ);
		System.out.println();
		System.out.println(
				"------------------------------------------      END OF TEST CASES    ---------------------------------------------");
		System.out.println();

	}

	/* Test Case Scenarios */

	// Valid Traveler Details
	public void doPayment(ExtentTest log) throws Exception {
		log.info("Came to payment page");
		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(prop.getProperty("excelpath")));
		XSSFSheet s = wb.getSheet(prop.getProperty("sheet"));
		name = s.getRow(1).getCell(0).toString();
		mobile = s.getRow(1).getCell(1).toString();
		emailID = s.getRow(1).getCell(2).toString();
		log.info("Enter the contact name");
		enterName();
		log.info(" Enter The mobile number");
		enterMobile();
		log.info("Checkbox deselected");
		extra();
		log.info("Entering Emial id");
		enterEmail();
		log.info("Click on Complete Booking");
		completeBook();
		database();
		log.info("Displaying Traveler Details in Console");
		displayTraveler();
		log.info("Displaying Final Details in console");
		display();
		
	}

	// Invalid Test Case
	public void emptyField(ExtentTest log) throws Exception{
		Thread.sleep(3000);
		completeBook();
		log.info("Came to payment page");
		log.info("Enter the contact name");
		log.info(" Enter The mobile number");
		log.info("Checkbox deselected");
		log.info("Click on Complete Booking");
		display();
		System.out.println();
		System.out.println("Error msg for empty field");
		System.out.println("----------------------------------------------");
		System.out.println();
		
	}

	public void invalidMobile(ExtentTest log) throws Exception {
		log.info("Came to payment page");
		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(prop.getProperty("excelpath")));
		XSSFSheet s = wb.getSheet(prop.getProperty("sheet"));
		name = s.getRow(3).getCell(0).toString();
		mobile = s.getRow(3).getCell(1).toString();
		emailID = s.getRow(3).getCell(2).toString();
		log.info("Enter the contact name");
		enterName();
		log.info(" Enter The mobile number");
		enterMobile();
		log.info("Checkbox deselected");
		log.info("Entering Emial id");
		enterEmail();
		log.info("Click on Complete Booking");
		completeBook();
	}

	public void onlyFirstName(ExtentTest log) throws Exception {
		log.info("Came to payment page");
		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(prop.getProperty("excelpath")));
		XSSFSheet s = wb.getSheet(prop.getProperty("sheet"));
		name = s.getRow(4).getCell(0).toString();
		mobile = s.getRow(4).getCell(1).toString();
		emailID = s.getRow(4).getCell(2).toString();
		log.info("Enter the contact name");
		enterName();
		log.info(" Enter The mobile number");
		enterMobile();
		log.info("Checkbox deselected");
//		extra();
		log.info("Entering Emial id");
		enterEmail();
		log.info("Click on Complete Booking");
		completeBook();
		log.info("Displaying Final Details in console");
		System.out.println("Error msg for only first name field");
		invalidMsgName();
		System.out.println();
	}

}
