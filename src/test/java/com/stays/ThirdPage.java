package com.stays;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.utils.Base;
import com.utils.PropertyReader;

public class ThirdPage extends Base {

	WebDriver driver = null;
	Properties prop = null;

	public ThirdPage(WebDriver driver) {
		this.driver = driver;
		prop = PropertyReader.getInstance();
	}

	public void selectRoom(ExtentTest log) throws Exception {
		System.out.println("-----------------------------------------");
		System.out.println();
		String heading = driver.findElement(By.tagName("h1")).getText();
		System.out.println("Hotel Selected -> " + heading);
		log.info("Selecting room in a hotel");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement res = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("reserve"))));
		res.click();
		log.info("Scrolling down to see rooms");
		Thread.sleep(1500);
		WebElement priceD = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("room"))));
		priceD.click();

		WebElement rooms = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("room1"))));
		rooms.click();
		log.info("Room selected");
		String room = driver.findElement(By.xpath(prop.getProperty("option"))).getText();
		String price = driver.findElement(By.xpath(prop.getProperty("price"))).getText();
		System.out.println("Room selected -> " + room.toUpperCase());
		System.out.println();
		System.out.println("The price of the room is " + price + " per night ");
		log.info("Redirecting to paymnent pop-up");
		driver.findElement(By.xpath(prop.getProperty("pay"))).click();
		System.out.println();

	}

}
