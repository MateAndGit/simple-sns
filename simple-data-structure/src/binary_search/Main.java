package binary_search;

public class Main {

    // binary search = Search algorithm that finds the position
    //				   of a target value within a sorted array.
    //				   Half of the array is eliminated during each "step"

    public static void main(String[] args) {
        int array[] = new int[1_000_000];
        int target = 777777;

        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }

        int index = binarySearch(array, target);

        if (index == -1) {
            System.out.println(target + "not found");
        } else {
            System.out.println("Element found at: " + index);
        }
    }

    private static int binarySearch(int[] array, int target) {
        int start = 0;
        int last = array.length - 1;

        while (start <= last) {
            int mid = start + (last - start) / 2;
            int value = array[mid];

            System.out.println("middle: " + value);

            if (value < target) start = mid + 1;
            else if (value > target) last = mid - 1;
            else return mid;
        }

        return -1;
    }
}
