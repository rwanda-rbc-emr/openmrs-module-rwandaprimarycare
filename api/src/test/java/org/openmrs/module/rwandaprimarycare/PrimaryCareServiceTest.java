package org.openmrs.module.rwandaprimarycare;

import org.junit.Assert;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.test.BaseModuleContextSensitiveTest;

public class PrimaryCareServiceTest extends BaseModuleContextSensitiveTest {

	@Test
	public void primaryCareServiceShouldBeInitialised() {
		Assert.assertNotNull(Context.getService(PrimaryCareService.class));
	}
	
	@Test
	public void testNationalIdentifierStuff() throws Exception {
		Context.authenticate("admin", "test");
		String nationalIdLong = "1 1974 8 0006220 0 690108042008THOMAS                   David                    1624";
		String nationalIdShort = "1 1974 8 0006220 0 69";

		Assert.assertTrue(nationalIdLong.length() == 85);
		System.out.println(PrimaryCareUtil.getFamilyNameFromNationalId(nationalIdLong));
		System.out.println(PrimaryCareUtil.getGivenNameFromNationalId(nationalIdLong));
		System.out.println(PrimaryCareUtil.getDOBYearFromNationalId(nationalIdLong));
		System.out.println(PrimaryCareUtil.getGenderFromNationalId(nationalIdLong));

		System.out.println(PrimaryCareUtil.getFamilyNameFromNationalId(nationalIdShort));
		System.out.println(PrimaryCareUtil.getGivenNameFromNationalId(nationalIdShort));
		System.out.println(PrimaryCareUtil.getDOBYearFromNationalId(nationalIdShort));
		System.out.println(PrimaryCareUtil.getGenderFromNationalId(nationalIdShort));

	}

}
