package com.przychodnia.rejestracja.domain;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AppointmentIntegrationTest.class, BhoursIntegrationTest.class,
		DoctorIntegrationTest.class, PersonIntegrationTest.class })

public class AllTests {
	public static int NUMBER_OF_OBJECTS_CREATED = 100;

//	 @AfterClass
//	 public static void createTests(){
//				NUMBER_OF_OBJECTS_CREATED+=100;
//}
}