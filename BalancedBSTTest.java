package com.company;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BalancedBSTTest {
    void printTree(BSTNode node) {
        if (node == null) {
            return;
        }
        System.out.print(node.NodeKey + " ");
        printTree(node.LeftChild);
        printTree(node.RightChild);
    }

    @Test
    void generateTree() {
        int n = 15;
        int [] A = new int[n];
        for (int i = 0; i < n; i++) {
            A[i] = i+1;
        }

        BalancedBST Tree = new BalancedBST();
        Tree.GenerateTree(A);
        printTree(Tree.Root);
    }
}