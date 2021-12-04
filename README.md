# INFO6250_final_project
Final project

# Getting Started

### Dependency Installation

**Download ICU4j**
```
curl https://github.com/unicode-org/icu/releases/download/release-70-1/icu4j-70_1.jar --output icu4j-70_1.jar
curl https://repo1.maven.org/maven2/org/ini4j/ini4j/0.5.4/ini4j-0.5.4.jar --output ini4j-0.5.4.jar
```
**Add the jar to the project dependency**

**Add JUnit to project**
1. Right-click your project and choose "Open Module Settings" or hit F4.
2. Go to the "Libraries" group, click the little green plus (look up), and choose "From Maven...".
3. Search for "junit" -- choose "junit:junit:4.13.2".

### Entry

The SortBenchmark.java is the entry to benchmark all the sorting algorithms.

There are six files with shuffled Chinese names as the input:
* shuffledChinese250k.txt
* shuffledChinese500k.txt
* shuffledChinese1M.txt
* shuffledChinese2M.txt
* shuffledChinese4M.txt

The target file can changed by modifying the variable "SHUFFLED_CHINESE_FILENAME"

We recommend to commenting out the Dual-Pivot Quicksort because it will take a long time to complete.

The writeToFile flag can be turned on to see the results.

# Troubleshooting

* Dual-Pivot Quicksort Stackoverflow issue
The default stack size is 1M and it is not enough for Dual-Pivot Quicksort when benchmarking a 1M-sized array. After some experiments, the stack size should be increased to 48M to make the Dual-Pivot Quicksort run sucessfully.

The steps is the following:
1. Right-click the "SortBenchmark.java" and choose "Modify Run Configuration"
2. Go to the top-right corner and click "Modify options", then click "Add VM options"
3. Input "-Xss48m" in the text field right besides the java version

# References:

* [icu4j Alphabetic Index](https://icu.unicode.org/design/alphabetic-index)
* [install and download jUnit](https://stackoverflow.com/questions/19330832/setting-up-junit-with-intellij-idea)
