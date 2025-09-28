# Impact-test
This repository contains my implementation of Impact's technical test. A comma delimited number range summarizer that groups sequential numbers into ranges.

- Invalid/non-numeric input should be ignored
- Negative numbers are supported
- Input collections may contain duplicates
- Input collections may be unsorted
- Null values in collections should throw an exception

# How to run

## Requires
- Java 8+
- Maven 3.6

```
# Clone the repository
git clone <repository-url>
cd Impact-test
cd NumberRangeSummarizer

# Run tests
mvn test
```
