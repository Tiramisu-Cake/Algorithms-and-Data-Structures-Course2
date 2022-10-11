package com.company;
import java.util.*;

class BalancedBST
{
    public BSTNode Root; // корень дерева

    public BalancedBST()
    {
        Root = null;
    }
    
    public BSTNode putSubTrees(int[] a, int begin, int end, BSTNode Parent, int level) {
        if (begin > end) {
            return null;
        }
        int newNodeIndex = (begin+end)/2;
        BSTNode nextNode = new BSTNode(a[newNodeIndex], Parent);
        nextNode.Level = level;
        nextNode.LeftChild = putSubTrees(a, begin, newNodeIndex-1, nextNode, level+1);
        nextNode.RightChild = putSubTrees(a,newNodeIndex + 1, end, nextNode, level+1);

        return nextNode;
    }

    public void GenerateTree(int[] a)
    {
        // создаём дерево с нуля из неотсортированного массива a
        int[] aSorted = a.clone();
        Arrays.sort(aSorted);

        this.Root = this.putSubTrees(a,0,a.length - 1, null, 0);
    }

    public boolean IsBalanced(BSTNode root_node)
    {
        // сбалансировано ли дерево с корнем root_node
        if (root_node.isLeaf()) {
            return true;
        }

        if (root_node.hasOneChild()) {
            return IsBalanced(root_node.getTheOnlyChild());
        }

        if (root_node.LeftChild.isLeaf() && root_node.RightChild.isLeaf()) {
            return true;
        }

        if(!root_node.LeftChild.isLeaf() && !root_node.RightChild.isLeaf()) {
            return IsBalanced(root_node.LeftChild) && IsBalanced(root_node.RightChild);
        }

        if (root_node.LeftChild.isLeaf()) {
            return root_node.RightChild.isNotDeep();
        }

        return root_node.LeftChild.isNotDeep();

    }

}

class BSTNode
{
    public int NodeKey; // ключ узла
    public BSTNode Parent; // родитель или null для корня
    public BSTNode LeftChild; // левый потомок
    public BSTNode RightChild; // правый потомок
    public int     Level; // глубина узла

    public BSTNode(int key, BSTNode parent)
    {
        NodeKey = key;
        Parent = parent;
        LeftChild = null;
        RightChild = null;
    }

    public boolean isLeaf() {
        return this.LeftChild == null && this.RightChild == null;
    }

    public boolean hasOneChild() {
        return this.RightChild == null ^ this.LeftChild == null;
    }
    public BSTNode getTheOnlyChild() {

        if (!this.hasOneChild()) {
            return null;
        }
        if (this.RightChild == null) {
            return this.LeftChild;
        }
        return this.RightChild;
    }

    public boolean isNotDeep() {
        BSTNode Left = this.LeftChild;
        BSTNode Right = this.RightChild;

        if (Left != null && Right != null) {
            return Left.isLeaf() && Right.isLeaf();
        }
        if (Left != null) {
            return Left.isLeaf();
        }
        return Right.isLeaf();
    }
}