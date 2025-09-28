package numberrangesummarizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests cover basic functionality, edge cases, and error conditions.
 * 
 */
public class NumberRangeTest {
    
    private final NumberRange summarizer = new NumberRange();

    @Test
    void testCollectParsesOneNumberCorrectly() {
        // test that a single number input is parsed correctly
        Collection<Integer> result = summarizer.collect("1");
        
        assertEquals(1, result.size());
        assertTrue(result.contains(1));
    }
    
    @Test
    void testCollectParsesEmptyInputCorrectly() {
        // test that various forms of empty input return empty collections
        Collection<Integer> result1 = summarizer.collect("");
        Collection<Integer> result2 = summarizer.collect("   ");
        Collection<Integer> result3 = summarizer.collect(null);

        assertNotNull(result1);
        assertTrue(result1.isEmpty());
        assertNotNull(result2);
        assertTrue(result2.isEmpty());
        assertNotNull(result3);
        assertTrue(result3.isEmpty());
    }
    
    @Test
    void testCollectParsesInputCorrectly() {
        // Test basic parsing functionality with valid comma-separated integers
        Collection<Integer> result1 = summarizer.collect("1,3");
        assertEquals(2, result1.size());
        
        // Test with the sample input provided in the requirements
        Collection<Integer> result2 = summarizer.collect("1,3,6,7,8,12,13,14,15,21,22,23,24,31");
        assertEquals(14, result2.size());
    }
    
    @Test
    void testCollectParsesNonNumber() {
        // test that invalid/non-numeric values are ignored gracefully
        Collection<Integer> result1 = summarizer.collect("1,3,a");
        assertEquals(2, result1.size());
        
        Collection<Integer> result2 = summarizer.collect("1,3,6,7,8,12,13,14,a,21,22,23,24,31");
        assertEquals(13, result2.size());
    }
    
    @Test
    void testCollectIgnoresEmptyAndInvalidStrings() {
        // Test handling of mixed valid/invalid input with empty elements and whitespace
        Collection<Integer> result = summarizer.collect(" , 5, a, 10,, ,3");
        
        assertEquals(3, result.size());
        assertTrue(result.contains(3));
        assertTrue(result.contains(5));
        assertTrue(result.contains(10));
    }
    
    @Test
    void testCollectRemovesDuplicates() {
        // test that duplicate numbers are removed and result is sorted
        Collection<Integer> result = summarizer.collect("1,2,2,3,3,3,4");
        
        assertEquals(4, result.size()); // Should have 4 unique numbers
        assertEquals(Arrays.asList(1,2,3,4), new ArrayList<>(result)); // Should be sorted
    }

    @Test
    void testSummarizeCollectionWithEmptyOrNullInput() {
        // test that empty and null inputs return empty strings
        Collection<Integer> empty = summarizer.collect("");
        assertEquals("", summarizer.summarizeCollection(empty));
        assertEquals("", summarizer.summarizeCollection(null));
    }
    
    @Test
    void testGeneralSequence() {
        // test that a complete range is summarized correctly
        Collection<Integer> input = summarizer.collect("1,2,3,4,5,6");
        String summary = summarizer.summarizeCollection(input);
        
        assertEquals("1-6", summary);
    }
    
    @Test
    void testSummarizeCollectionProducesExpectedOutput() {
        // test the main sample case from the requirements
        Collection<Integer> input = summarizer.collect("1,3,6,7,8,12,13,14,15,21,22,23,24,31");
        String summary = summarizer.summarizeCollection(input);
        
        assertEquals("1, 3, 6-8, 12-15, 21-24, 31", summary);
    }
    
    @Test
    void testEmptyInputReturnsEmptyString() {
        // test that empty collection input returns empty string
        Collection<Integer> input = summarizer.collect("");
        String summary = summarizer.summarizeCollection(input);
        
        assertEquals("", summary);
    }
    
    @Test
    void testSingleNumber() {
        // test that a single number doesn't create a range
        Collection<Integer> input = summarizer.collect("42");
        String summary = summarizer.summarizeCollection(input);
        
        assertEquals("42", summary);
    }
    
    @Test
    void testNonSequentialNumbers() {
        // test that non-consecutive numbers are listed separately
        Collection<Integer> input = summarizer.collect("5,10,15");
        String summary = summarizer.summarizeCollection(input);
        
        assertEquals("5, 10, 15", summary);
    }
    
    @Test
    void testSequentialNumbersAtStartAndEnd() {
        // test multiple separate ranges in the same input
        Collection<Integer> input = summarizer.collect("1,2,3,7,8,9");
        String summary = summarizer.summarizeCollection(input);
        
        assertEquals("1-3, 7-9", summary);
    }
}