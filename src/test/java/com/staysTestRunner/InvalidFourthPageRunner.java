package com.staysTestRunner;

import org.testng.annotations.Test;
import com.stays.FourthPage;
import com.stays.SecondPage;
import com.stays.StaysSearchPage;
import com.stays.ThirdPage;
import com.utils.Base;

public class InvalidFourthPageRunner extends Base {
	@Test(priority = 2, description = "All Invalid Test Cases of fourth Page")
	public void goesTillFourthPage() throws Exception {
		driver.get(prop.getProperty("url"));
		StaysSearchPage st = new StaysSearchPage(driver);
		st.hotelSearch(testLog);

		SecondPage sc = new SecondPage(driver);
		sc.chooseHotel(testLog);

		ThirdPage tp = new ThirdPage(driver);
		tp.selectRoom(testLog);
	}

	@Test(dependsOnMethods = "goesTillFourthPage")
	public void emptyField() throws Exception {
		FourthPage fp = new FourthPage(driver);
		fp.emptyField(testLog);		
		Thread.sleep(2000);
	}

	@Test(dependsOnMethods = "goesTillFourthPage")
	public void invalidMobile() throws Exception {
		FourthPage fp = new FourthPage(driver);
		fp.invalidMobile(testLog);
		Thread.sleep(2000);
	}
	

	@Test(dependsOnMethods = "goesTillFourthPage")
	public void onlyfName() throws Exception {
		FourthPage fp = new FourthPage(driver);
		fp.onlyFirstName(testLog);
	}

}
