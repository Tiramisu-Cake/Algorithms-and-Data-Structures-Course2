package com.company;
import java.util.*;

public class aBST
{
    public Integer Tree []; // массив ключей

    public aBST(int depth)
    {
        // правильно рассчитайте размер массива для дерева глубины depth:
        int tree_size = (1 << depth + 1) - 1;
        Tree = new Integer[ tree_size ];
        for(int i=0; i<tree_size; i++) Tree[i] = null;
    }

    public Integer FindKeyIndex(int key)
    {
        // ищем в массиве индекс ключа
        int tree_size = this.Tree.length;

        if (Tree[0] == null) { // if tree is empty
            return null;
        }

        for (int i = 0; i < tree_size; i++) {
            if (Tree[i] == null) {
                return -i;
            }
            if (Tree[i] == key) {
                return i;
            }

            if (key < Tree[i]) { // goes to left child
                i = 2*i;
            } else { // goes to right child
                i = 2*i + 1;
            }
        }
        return null; // не найден
    }

    public int AddKey(int key)
    {
        // добавляем ключ в массив
        if (Tree[0] == null) {
            Tree[0] = key;
            return 0;
        }
        Integer index = this.FindKeyIndex(key);
        if (index != null) {
            if (index < 0) {
                Tree[-index] = key;
                return -index;
            }
        }

        return -1;
        // индекс добавленного/существующего ключа или -1 если не удалось
    }

}
