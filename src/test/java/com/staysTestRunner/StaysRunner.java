package com.staysTestRunner;

import org.testng.annotations.Test;

import com.stays.FourthPage;
import com.stays.SecondPage;
import com.stays.StaysSearchPage;
import com.stays.ThirdPage;
import com.utils.Base;

public class StaysRunner extends Base {
	StaysSearchPage st;
	SecondPage sc;
	ThirdPage tp;
	FourthPage fp;

	@Test(priority = 0)
	public void staysFirstPage() throws Exception {
		driver.get(prop.getProperty("url"));
		st = new StaysSearchPage(driver);
		st.hotelSearchWithChild(testLog);

	}

	@Test(dependsOnMethods = { "staysFirstPage" })
	public void staysSecondPage() throws Exception {
		sc = new SecondPage(driver);
		sc.chooseHotel(testLog);
	}

	@Test(dependsOnMethods = { "staysFirstPage", "staysSecondPage" })
	public void staysThirdPage() throws Exception {
		tp = new ThirdPage(driver);
		tp.selectRoom(testLog);
	}

	@Test(dependsOnMethods = { "staysFirstPage", "staysSecondPage", "staysThirdPage" })
	public void staysFourthPage() throws Exception {
		fp = new FourthPage(driver);
		fp.doPayment(testLog);

	}

}
