package com.company;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BalancedBSTTest {
    void checkTree(int[] a, BSTNode node, BSTNode Parent, int begin, int end, int level) {
        if (node == null) {
            return;
        }
        int NodeIndex = (begin + end)/2;
        Assert.assertTrue(a[NodeIndex] == node.NodeKey);
        Assert.assertTrue(node.Parent == Parent);
        Assert.assertTrue(level == node.Level);
        checkTree(a, node.LeftChild, node, begin, NodeIndex - 1, level + 1);
        checkTree(a, node.RightChild, node,NodeIndex + 1, end, level + 1);
    }

    @Test
    void generateTree() {
        int n = 7;
        int [] A = new int[n];
        for (int i = 0; i < n; i++) {
            A[i] = i+1;
        }

        BalancedBST Tree = new BalancedBST();
        Tree.GenerateTree(A);
        checkTree(A, Tree.Root, null, 0, A.length - 1, 0);
    }
}