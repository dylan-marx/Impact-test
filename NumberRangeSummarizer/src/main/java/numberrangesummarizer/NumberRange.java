package numberrangesummarizer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * Implementation of NumberRangeSummarizer that creates a comma delimeted ranges
 * from a collection of integers
 * 
 * @author Dylan Marx
 */
public class NumberRange implements NumberRangeSummarizer {

    /**
     * Parses a string of integers and uses comma as delimiter
     *  returns a sorted collection of unique valid integers
     * 
     * @param input comma delimited string of integers
     * @return sorted collection of unique integers
     */
    @Override
    public Collection<Integer> collect(String input) {
        if (input == null || input.trim().isEmpty()) {
            return Collections.emptyList();
        }

        String[] parts = input.split(",");
        List<Integer> numbers = new ArrayList<>();

        for (String part : parts) {
            part = part.trim();
            if (!part.isEmpty()) {
                try {
                    numbers.add(Integer.parseInt(part));
                } catch (NumberFormatException e) {
                    // ignore invalid numbers
                }
            }
        }

        // sort numbers and remove duplicates
        List<Integer> distinctNumbers = new ArrayList<>();
        Collections.sort(numbers);
        Integer last = null;
        for (Integer n : numbers) {
            if (!n.equals(last)) {
                distinctNumbers.add(n);
                last = n;
            }
        }

        return distinctNumbers;
    }

    /**
     * Helper method to append a range to the result string
     * @param result current result string
     * @param start start of the range
     * @param end end of the range
     * @return updated result string
     */
    private String appendRange(String result, int start, int end) {
        if (!result.isEmpty()) {
            result += ", ";
        }
        if (start == end) {
            result += start;
        } else {
            result += start + "-" + end;
        }
        return result;
    }

    /**
     * Converts a collection of integers to a comma delimited string with ranges
     * 
     * @param input collection of integers
     * @return formatted string with ranges
     */
    @Override
    public String summarizeCollection(Collection<Integer> input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        List<Integer> numbers = new ArrayList<>(input);
        String result = "";
        int start = numbers.get(0);
        int prev = start;

        for (int i = 1; i < numbers.size(); i++) {
            int current = numbers.get(i);
            if (current == prev + 1) {
                prev = current;
            } else {
                result = appendRange(result, start, prev);
                start = current;
                prev = current;
            }
        }
        result = appendRange(result, start, prev);
        return result;
    }
}
