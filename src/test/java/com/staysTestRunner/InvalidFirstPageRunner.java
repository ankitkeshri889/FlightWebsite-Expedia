package com.staysTestRunner;

import org.testng.annotations.Test;

import com.stays.StaysSearchPage;
import com.utils.Base;

public class InvalidFirstPageRunner extends Base {

	// FirstPage Invalid Test Case
	@Test(priority = 0, description = "Empty City Invalid Test Case")
	public void emptyCityInvalid() {
		driver.get(prop.getProperty("url"));
		StaysSearchPage sc = new StaysSearchPage(driver);
		sc.emptyCity(testLog);
	}

	@Test(priority = 1, description = "Invalid City Invalid Test Case")
	public void inputCityInvalid() throws Exception {
		StaysSearchPage sc = new StaysSearchPage(driver);
		sc.invalidCity(testLog);
		Thread.sleep(3000);
	}

}
