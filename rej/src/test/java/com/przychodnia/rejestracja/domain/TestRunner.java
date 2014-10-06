package com.przychodnia.rejestracja.domain;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;

public class TestRunner {
   public static void main(String[] args) {
  
      for (int i=0; i<1;i++) {
    	  JUnitCore.runClasses(AllTests.class);
      }
   }
}  	