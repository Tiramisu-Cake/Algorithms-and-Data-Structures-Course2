package com.company;

import java.util.*;

class Heap
{
    public int [] HeapArray; // хранит неотрицательные числа-ключи

    public Heap() { HeapArray = null; }

    private int getMaxIndex(int firstIndex, int secondIndex) {
        if (this.HeapArray[firstIndex] > this.HeapArray[secondIndex]) {
            return firstIndex;
        }
        return secondIndex;
    }

    private void switchElements(int firstIndex, int secondIndex) {
        int x = this.HeapArray[firstIndex];
        this.HeapArray[firstIndex] = this.HeapArray[secondIndex];
        this.HeapArray[secondIndex] = x;

    }

    public void MakeHeap(int[] a, int depth)
    {
        // создаём массив кучи HeapArray из заданного
        // размер массива выбираем на основе глубины depth
        int size = (1 << depth + 1) - 1;
        HeapArray = new int[size];
        for (int i = 0; i<a.length; i++) {
            this.Add(a[i]);
        }
    }

    public int GetMax()
    {
        // вернуть значение корня и перестроить кучу
        if (this.HeapArray == null) {
            return -1; // если куча пуста
        }
        int result = this.HeapArray[0];
        int size = this.HeapArray.length;

        int newMax = 0;
        int lastIndex = 0;
        for (int i = size-1; i >= 0; i--) {
            if (this.HeapArray[i] != 0) {
                this.HeapArray[0] = this.HeapArray[i];
                this.HeapArray[i] = 0;
                lastIndex = i;
                break;
            }
        }

        for (int i = 0; i < (lastIndex - 1)/2; i++) {
            int max = Math.max(this.HeapArray[2*i + 1],this.HeapArray[2*i + 2]);
            int maxIndex = getMaxIndex(2*i + 1, 2*i + 2);

            if (max > this.HeapArray[i]) {
                this.switchElements(i,maxIndex);
            }

            i = maxIndex - 1;
        }

        return result;
    }

    public boolean Add(int key)
    {
        // добавляем новый элемент key в кучу и перестраиваем её
        if (this.HeapArray == null) {
            return false;
        }

        int size = HeapArray.length;
        if (this.HeapArray[size - 1] != 0) {
            return false; // если куча вся заполнена
        }

        int index = 0;
        for (int i = 0; i < size; i++) {
            if (this.HeapArray[i] == 0) {
                this.HeapArray[i] = key;
                index = i;
                break;
            }
        }

        while (index > 0) {
            int Parent = this.HeapArray[(index-1)/2];
            if (Parent > this.HeapArray[index]) {
                break;
            }
            switchElements(index,(index-1)/2);
            index = (index-1)/2;
        }

        return true;
    }

}
