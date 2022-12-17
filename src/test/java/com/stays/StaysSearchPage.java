package com.stays;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.utils.PropertyReader;

public class StaysSearchPage {
	WebDriver driver = null;
	Properties prop = null;
	private String from = null;
	private int inv = 8;

	public StaysSearchPage(WebDriver driver) {
		this.driver = driver;
		prop = PropertyReader.getInstance();
	}

	/* Test Cases Scenario */

	// City Input Function
	public void cityInput(ExtentTest log) {
		log.info("Clicking on Going to section");
		driver.findElement(By.xpath(prop.getProperty("click"))).click();
		log.info("Select a city");
		WebElement a = driver.findElement(By.xpath(prop.getProperty("cityName")));
		a.sendKeys(from);
		a.sendKeys(Keys.ENTER);
	}

	// Click Search Function
	public void clickSearch() {
		driver.findElement(By.xpath(prop.getProperty("search"))).click();
	}

	public void thanks() {
		System.out.println();
		System.out.println("Thankyou for visiting " + from + " :-)");
		System.out.println("!!!! WE HOPE FOR YOUR SAFE AND PROSPEROUS JOURNEY  !!!!");
		System.out.println();
	}

	// Check-in Function
	public void calendarIn() {
		driver.findElement(By.xpath(prop.getProperty("calIn"))).click();
		WebDriverWait wait = new WebDriverWait(driver, 20);
		WebElement dateIn = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("date1"))));
		dateIn.click();
		driver.findElement(By.xpath(prop.getProperty("okDone"))).click();
	}

	// Check-out Function
	public void calendarOut() {
		driver.findElement(By.id(prop.getProperty("calOut"))).click();
		WebDriverWait wait = new WebDriverWait(driver, 20);
		WebElement dateOut = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("date2"))));
		dateOut.click();
		driver.findElement(By.xpath(prop.getProperty("okDone"))).click();
	}

	// Traveler function with child
	public void travelerChild() {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		WebElement trav = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("traveler"))));
		trav.click();
		WebElement ch = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("child"))));
		ch.click();
		WebElement dr = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id(prop.getProperty("sel"))));
		Select sc = new Select(dr);
		sc.selectByVisibleText("6");
	}

	// Valid Test Case without child input
	public void hotelSearch(ExtentTest log) throws Exception {
		@SuppressWarnings("resource")
		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(prop.getProperty("excelpath")));
		XSSFSheet s = wb.getSheet(prop.getProperty("sheetName"));
		from = s.getRow(1).getCell(0).toString();
		cityInput(log);
		calendarIn();
		calendarOut();
		log.info("Click on search button");
		clickSearch();
		log.info("Went to next page");
		thanks();
	}

	// Valid Test Case with child input
	public void hotelSearchWithChild(ExtentTest log) throws Exception {
		@SuppressWarnings("resource")
		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(prop.getProperty("excelpath")));
		XSSFSheet s = wb.getSheet(prop.getProperty("sheetName"));
		from = s.getRow(1).getCell(0).toString();
		cityInput(log);
		calendarIn();
		calendarOut();
		travelerChild();
		log.info("Click on search button");
		clickSearch();
		clickSearch();
		log.info("Went to next page");
		thanks();

	}

	// Invalid Test Case (Empty City)
	public void emptyCity(ExtentTest log) {
		log.info("Entering City");
		clickSearch();
		String invalid = driver.findElement(By.id(prop.getProperty("inv"))).getText();
		System.out.println();
		System.out.println("!!! ERROR ->  " + invalid);
	}

	// Invalid Test Case (Invalid City)
	public void invalidCity(ExtentTest log) throws Exception {

		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(prop.getProperty("excelpath")));
		XSSFSheet s = wb.getSheet(prop.getProperty("sheetName"));
		from = s.getRow(inv).getCell(0).toString();
		cityInput(log);
		clickSearch();
		String sor = driver.findElement(By.xpath(prop.getProperty("sorry"))).getText();
		System.out.println();
		System.out.println("!!! ERROR ->  " + sor);
	}

}
