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
import com.przychodnia.rejestracja.domain.Person;

@AxisRange(min = 0, max = 1)
@BenchmarkMethodChart(filePrefix = "personMethodChart")
@BenchmarkHistoryChart( maxRuns = 10, filePrefix = "personHistoryChart")
@BenchmarkOptions(benchmarkRounds = 3, warmupRounds = 1)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/META-INF/spring/applicationContext*.xml")
@Transactional
@Configurable
public class PersonIntegrationTest extends AbstractBenchmark {

	@Autowired
    PersonDataOnDemand dod;

	@Test
    public void testCountPeople() {
        Assert.assertNotNull("Data on demand for 'Person' failed to initialize correctly", dod.getRandomPerson());
        long count = Person.countPeople();
        Assert.assertTrue("Counter for 'Person' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindPerson() {
        Person obj = dod.getRandomPerson();
        Assert.assertNotNull("Data on demand for 'Person' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Person' failed to provide an identifier", id);
        obj = Person.findPerson(id);
        Assert.assertNotNull("Find method for 'Person' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Person' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllPeople() {
        Assert.assertNotNull("Data on demand for 'Person' failed to initialize correctly", dod.getRandomPerson());
        long count = Person.countPeople();
//        Assert.assertTrue("Too expensive to perform a find all test for 'Person', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Person> result = Person.findAllPeople();
        Assert.assertNotNull("Find all method for 'Person' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Person' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindPersonEntries() {
        Assert.assertNotNull("Data on demand for 'Person' failed to initialize correctly", dod.getRandomPerson());
        long count = Person.countPeople();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Person> result = Person.findPersonEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Person' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Person' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        Person obj = dod.getRandomPerson();
        Assert.assertNotNull("Data on demand for 'Person' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Person' failed to provide an identifier", id);
        obj = Person.findPerson(id);
        Assert.assertNotNull("Find method for 'Person' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyPerson(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'Person' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testMergeUpdate() {
        Person obj = dod.getRandomPerson();
        Assert.assertNotNull("Data on demand for 'Person' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Person' failed to provide an identifier", id);
        obj = Person.findPerson(id);
        boolean modified =  dod.modifyPerson(obj);
        Integer currentVersion = obj.getVersion();
        Person merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Person' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testPersist() {
        Assert.assertNotNull("Data on demand for 'Person' failed to initialize correctly", dod.getRandomPerson());
        Person obj = dod.getNewTransientPerson(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Person' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Person' identifier to be null", obj.getId());
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
        Assert.assertNotNull("Expected 'Person' identifier to no longer be null", obj.getId());
    }

//	@Test
//    public void testRemove() {
//        Person obj = dod.getRandomPerson();
//        Assert.assertNotNull("Data on demand for 'Person' failed to initialize correctly", obj);
//        Long id = obj.getId();
//        Assert.assertNotNull("Data on demand for 'Person' failed to provide an identifier", id);
//        obj = Person.findPerson(id);
//        obj.remove();
//        obj.flush();
//        Assert.assertNull("Failed to remove 'Person' with identifier '" + id + "'", Person.findPerson(id));
//    }
}
