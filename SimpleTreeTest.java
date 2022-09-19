package com.company;

import org.junit.Assert;
import java.util.Random;

class SimpleTreeTest {


    @org.junit.jupiter.api.Test
    void leafCount() {
        Random rand = new Random();
        // количество узлов
        int n = 10;

        // заводим узлы
        SimpleTreeNode<Integer> [] Nodes = new SimpleTreeNode[n];
        for (int i = 0; i < n; i++) {
            Nodes[i] = new SimpleTreeNode<>(i,null);
        }

        // формируем дерево с 1 листом
        int Tree1leaves = 1;
        SimpleTree<Integer> Tree1 = new SimpleTree<>(Nodes[0]);
        Tree1.AddChild(Tree1.Root, Nodes[1]);
        for (int i = 2; i < n; i++) {
            Tree1.AddChild(Nodes[i-1], Nodes[i]);
        }

        // снова заводим узлы
        Nodes = new SimpleTreeNode[n];
        for (int i = 0; i < n; i++) {
            Nodes[i] = new SimpleTreeNode<>(i,null);
        }

        // Дерево с 2 листами
        int Tree2leaves = 2;
        SimpleTree<Integer> Tree2 = new SimpleTree<>(Nodes[0]);
        Tree2.AddChild(Tree2.Root, Nodes[1]);
        Tree2.AddChild(Tree2.Root, Nodes[2]);
        for (int i = 3; i < n; i++) {
            Tree2.AddChild(Nodes[i-1], Nodes[i]);
        }

        // снова заводим узлы
        Nodes = new SimpleTreeNode[n];
        for (int i = 0; i < n; i++) {
            Nodes[i] = new SimpleTreeNode<>(i,null);
        }
        // Дерево с 9 листами
        int Tree3leaves = 9;
        SimpleTree<Integer> Tree3 = new SimpleTree<>(Nodes[0]);
        for (int i = 1; i < n; i++) {
            Tree3.AddChild(Tree3.Root, Nodes[i]);
        }

        Assert.assertTrue(Tree1leaves == Tree1.LeafCount());
        Assert.assertTrue(Tree2leaves == Tree2.LeafCount());
        Assert.assertTrue(Tree3leaves == Tree3.LeafCount());

    }
}