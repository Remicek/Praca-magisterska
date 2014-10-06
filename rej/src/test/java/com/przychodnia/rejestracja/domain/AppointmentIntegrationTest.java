package com.przychodnia.rejestracja.domain;
import java.util.Iterator;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.carrotsearch.junitbenchmarks.AbstractBenchmark;
import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.annotation.AxisRange;
import com.carrotsearch.junitbenchmarks.annotation.BenchmarkHistoryChart;
import com.carrotsearch.junitbenchmarks.annotation.BenchmarkMethodChart;
import com.przychodnia.rejestracja.jsf.util.Literals;
@AxisRange(min = 0, max = 1)

@BenchmarkMethodChart(filePrefix = "appointmentMethodChart")
@BenchmarkHistoryChart( maxRuns = 10, filePrefix = "appointmentHistoryChart")
@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/META-INF/spring/applicationContext*.xml")
@Transactional
@BenchmarkOptions(benchmarkRounds = 3, warmupRounds = 1)
public class AppointmentIntegrationTest extends AbstractBenchmark {


	@Autowired
    AppointmentDataOnDemand dod;

	@Test
    public void testCountAppointments() {
        Assert.assertNotNull("Data on demand for 'Appointment' failed to initialize correctly", dod.getRandomAppointment());
        long count = Appointment.countAppointments();
        Assert.assertTrue("Counter for 'Appointment' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindAppointment() {
        Appointment obj = dod.getRandomAppointment();
        Assert.assertNotNull("Data on demand for 'Appointment' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Appointment' failed to provide an identifier", id);
        obj = Appointment.findAppointment(id);
        Assert.assertNotNull("Find method for 'Appointment' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Appointment' returned the incorrect identifier", id, obj.getId());
    }


	@Test
    public void testFindAllAppointments() {
        Assert.assertNotNull("Data on demand for 'Appointment' failed to initialize correctly", dod.getRandomAppointment());
        long count = Appointment.countAppointments();
//        Assert.assertTrue("Too expensive to perform a find all test for 'Appointment', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < Literals.NUMBER_OF_OBJECTS_CREATED + 50);
        List<Appointment> result = Appointment.findAllAppointments();
        Assert.assertNotNull("Find all method for 'Appointment' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Appointment' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindAppointmentEntries() {
        Assert.assertNotNull("Data on demand for 'Appointment' failed to initialize correctly", dod.getRandomAppointment());
        long count = Appointment.countAppointments();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Appointment> result = Appointment.findAppointmentEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Appointment' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Appointment' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        Appointment obj = dod.getRandomAppointment();
        Assert.assertNotNull("Data on demand for 'Appointment' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Appointment' failed to provide an identifier", id);
        obj = Appointment.findAppointment(id);
        Assert.assertNotNull("Find method for 'Appointment' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyAppointment(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'Appointment' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testMergeUpdate() {
        Appointment obj = dod.getRandomAppointment();
        Assert.assertNotNull("Data on demand for 'Appointment' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Appointment' failed to provide an identifier", id);
        obj = Appointment.findAppointment(id);
        boolean modified =  dod.modifyAppointment(obj);
        Integer currentVersion = obj.getVersion();
        Appointment merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Appointment' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testPersist() {
        Assert.assertNotNull("Data on demand for 'Appointment' failed to initialize correctly", dod.getRandomAppointment());
        Appointment obj = dod.getNewTransientAppointment(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Appointment' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Appointment' identifier to be null", obj.getId());
        try {
            obj.persist();
        } catch (final ConstraintViolationException e) {
            final StringBuilder msg = new StringBuilder();
            for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                final ConstraintViolation<?> cv = iter.next();
                msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
            }
            throw new IllegalStateException(msg.toString(), e);
        }
        obj.flush();
        Assert.assertNotNull("Expected 'Appointment' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testRemove() {
        Appointment obj = dod.getRandomAppointment();
        Assert.assertNotNull("Data on demand for 'Appointment' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Appointment' failed to provide an identifier", id);
        obj = Appointment.findAppointment(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'Appointment' with identifier '" + id + "'", Appointment.findAppointment(id));
    }
}
