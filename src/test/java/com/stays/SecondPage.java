package com.stays;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.utils.PropertyReader;

public class SecondPage {
	WebDriver driver = null;
	Properties prop = null;

	public SecondPage(WebDriver driver) {
		this.driver = driver;
		prop = PropertyReader.getInstance();
	}

	public void dismiss(ExtentTest log) throws Exception {
		
		log.info("Clicking on dismiss button");
		WebDriverWait wait = new WebDriverWait(driver, 20);
		WebElement dis = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("dismiss"))));
		dis.click();
		
	}

	public void hotelSelect() throws Exception {	
		List<WebElement> list = driver.findElements(By.xpath(prop.getProperty("hotels")));
		System.out.println("Number of hotels present in this city are " + list.size());
		System.out.println();
		for (int i = 0; i < list.size(); i++) {
			String l = list.get(i).getText();
			System.out.println(i + 1 + " " + l);
		}
			driver.findElement(By.xpath("//input[@name='hotelName']//following-sibling::button")).click();		
			WebElement inp_jw = driver.findElement(By.xpath("//input[@placeholder='e.g. Best Western']"));
			inp_jw.sendKeys("JW Mar");
			inp_jw.sendKeys(Keys.ENTER);
			Thread.sleep(2000);
			driver.findElement(By.xpath(prop.getProperty("hotel"))).click();
			Thread.sleep(3000);
			ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs2.get(1));
	}

	/* Test Case Scenarios */

	// Choose hotel Without Filter
	public void chooseHotel(ExtentTest log) throws Exception {
		dismiss(log);
		log.info("Selecting hotel");
		hotelSelect();
	}

	// Choose Hotel Sorted By price
	public void chooseHotelSort(ExtentTest log) throws Exception {
		dismiss(log);
		log.info("Sorting Hotel based on price ");
		WebElement drop = driver.findElement(By.id("sort"));
		Select s = new Select(drop);
		s.selectByVisibleText("Price");
		Thread.sleep(3000);
		hotelSelect();

	}

}
