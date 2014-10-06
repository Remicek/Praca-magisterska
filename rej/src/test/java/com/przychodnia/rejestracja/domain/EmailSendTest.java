package com.przychodnia.rejestracja.domain;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/META-INF/spring/applicationContext*.xml")
public class EmailSendTest {
@Test
	public void testYourSendingCode() throws Exception {
	    GreenMail greenMail = new GreenMail(); //uses test ports by default
	    greenMail.start();
	    GreenMailUtil.sendTextEmailTest("remicek@gmail.com", "remiwawfly@gmail.com", "subject", "body");
	    Assert.assertEquals("subject", greenMail.getReceivedMessages()[0].getSubject());
	    greenMail.stop();
	    //That's it!
	}    
}
