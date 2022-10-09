package com.company;
import java.util.*;

public class AlgorithmsDataStructures2
{
    public static int getMiddle(int begin, int end) {
        return begin/2 + end/2;
    }
    
    public static int[] arrayLeft (int[] a) {
        int size = a.length/2;
        int[] aLeft = new int[size];
        for (int i = 0; i < size; i++) {
            aLeft[i] = a[i];
        }
        return aLeft;
    }

    public static int[] arrayRight (int[] a) {
        int size = a.length/2;
        int[] aRight = new int[size];
        for (int i = 0; i < size; i++) {
            aRight[i] = a[size + i + 1];
        }
        return aRight;

    }


    public static void putRightSubTree(int[] a, int [] result, int middleIndex, int IndexInTree) {
        int rightChildIndex = getMiddle(0, a.length);
        result[2*IndexInTree + 2] = a[rightChildIndex];
        if (middleIndex != a.length - 1) {
            putRightSubTree(arrayRight(a), result, rightChildIndex/2, 2*IndexInTree + 2);
            putLeftSubTree(arrayLeft(a), result, rightChildIndex, 2*IndexInTree + 2);
        }

    }

    public static void putLeftSubTree(int[] a, int [] result, int middleIndex, int IndexInTree) {
        int leftChildIndex = getMiddle(0, middleIndex - 1);
        result[2*IndexInTree + 1] = a[leftChildIndex];
        if (middleIndex != 1) {
            putLeftSubTree(arrayLeft(a), result, leftChildIndex, 2*IndexInTree + 1);
            putRightSubTree(arrayRight(a), result, leftChildIndex/2, 2*IndexInTree + 1);
        }
    }

    public static int[] GenerateBBSTArray(int[] a) {
        int[] aSorted = a.clone();
        Arrays.sort(aSorted);

        int[] result = new int[a.length];
        result[0] = aSorted[a.length/2];
        putLeftSubTree(arrayLeft(aSorted),result,a.length/2, 0);
        putRightSubTree(arrayRight(aSorted),result,a.length/2, 0);

        return result;
    }

}