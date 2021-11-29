package edu.neu.coe.info6205.elementary;/*
  (c) Copyright 2018, 2019 Phasmid Software
 */
import edu.neu.coe.info6205.BaseHelper;
import edu.neu.coe.info6205.Helper;
import edu.neu.coe.info6205.util.Config;
import edu.neu.coe.info6205.SortWithHelper;

public class InsertionSort<X extends Comparable<X>> extends SortWithHelper<X> {

    /**
     * Constructor for any sub-classes to use.
     *
     * @param description the description.
     * @param N           the number of elements expected.
     * @param config      the configuration.
     */
    protected InsertionSort(String description, int N, Config config) {
        super(description, N, config);
    }

    /**
     * Constructor for edu.neu.coe.info6205.sort.elementary.InsertionSort
     *
     * @param N      the number elements we expect to sort.
     * @param config the configuration.
     */
    public InsertionSort(int N, Config config) {
        this(DESCRIPTION, N, config);
    }

    public InsertionSort(Config config) {
        this(new BaseHelper<>(DESCRIPTION, config));
    }

    /**
     * Constructor for edu.neu.coe.info6205.sort.elementary.InsertionSort
     *
     * @param helper an explicit instance of Helper to be used.
     */
    public InsertionSort(Helper<X> helper) {
        super(helper);
    }

    public InsertionSort() {
        this(BaseHelper.getHelper(InsertionSort.class));
    }

    /**
     * Sort the sub-array xs:from:to using insertion sort.
     *
     * @param xs   sort the array xs from "from" to "to".
     * @param from the index of the first element to sort
     * @param to   the index of the first element not to sort
     */
    public void sort(X[] xs, int from, int to) {
        final Helper<X> helper = getHelper();

        // TO BE IMPLEMENTED
        int target = from + 1; // target will move toward the end of the array
        while (target < to) {
            // The for loop will swap the target from the index of itself to the start of the array until sorted
            for (int i = target; i > from; i--) {
                boolean swapped = helper.swapConditional(xs, i - 1, i);
                if (!swapped) break;
            }
            target += 1;
        }
    }

    public static final String DESCRIPTION = "Insertion sort";

    public static <T extends Comparable<T>> void sort(T[] ts) {
        new InsertionSort<T>().mutatingSort(ts);
    }
}
