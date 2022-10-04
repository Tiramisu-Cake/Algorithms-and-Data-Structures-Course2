package com.company;

import java.io.*;
import java.util.*;


class BSTNode<T>
{
    public int NodeKey; // ключ узла
    public T NodeValue; // значение в узле
    public BSTNode<T> Parent; // родитель или null для корня
    public BSTNode<T> LeftChild; // левый потомок
    public BSTNode<T> RightChild; // правый потомок

    public BSTNode(int key, T val, BSTNode<T> parent)
    {
        NodeKey = key;
        NodeValue = val;
        Parent = parent;
        LeftChild = null;
        RightChild = null;
    }

    public boolean isLeaf() {
        return this.RightChild == null && this.LeftChild == null;
    }

    public boolean isLeft() throws NullPointerException {
        if (this.Parent == null) {
            throw new NullPointerException("Parent is null!");
        }
        BSTNode<T> Parent = this.Parent;
        if (Parent.RightChild.NodeKey == this.NodeKey) {
            return false;
        }
        return Parent.LeftChild.NodeKey == this.NodeKey;
    }

    public boolean hasOneChild() {
        if (this.RightChild == null ^ this.LeftChild == null) {
            return true;
        }
        return false;
    }

    public BSTNode<T> getTheOnlyChild() {

        if (!this.hasOneChild()) {
            return null;
        }
        if (this.RightChild == null) {
            return this.LeftChild;
        }
        return this.RightChild;
    }

    public boolean isTheOnlyChild() throws NullPointerException {
        if (this.Parent == null) {
            throw new NullPointerException("Parent is null!");
        }
        BSTNode<T> Parent = this.Parent;
        if (Parent.RightChild == null || Parent.LeftChild == null) {
            return true;
        }
        return false;
    }
}

// промежуточный результат поиска
class BSTFind<T>
{
    // null если в дереве вообще нету узлов
    public BSTNode<T> Node;

    // true если узел найден
    public boolean NodeHasKey;

    // true, если родительскому узлу надо добавить новый левым
    public boolean ToLeft;

    public BSTFind() { Node = null; }
}

class BST<T>
{
    BSTNode<T> Root; // корень дерева, или null

    public BST(BSTNode<T> node)
    {
        Root = node;
    }

    public BSTFind<T> FindNodeByKey(int key) {
        // ищем в дереве узел и сопутствующую информацию по ключу

        BSTFind<T> result = new BSTFind<>();

        if (this.Root == null) {
            result.Node = this.Root;
            result.NodeHasKey = false;
            result.ToLeft = false;
            return result;
        }

        if (this.Root.NodeKey == key) {
            result.Node = this.Root;
            result.NodeHasKey = true;
            result.ToLeft = false;
            return result;
        }

        if (key < this.Root.NodeKey) {
            if (this.Root.LeftChild == null) {
                result.Node = this.Root;
                result.NodeHasKey = false;
                result.ToLeft = true;
                return result;
            } else {
                BST<T> leftSubTree = new BST<T>(this.Root.LeftChild);
                return leftSubTree.FindNodeByKey(key);
            }
        }

        if (this.Root.RightChild == null) {
            result.Node = this.Root;
            result.NodeHasKey = false;
            result.ToLeft = false;
        } else {
            BST<T> rightSubTree = new BST<T>(this.Root.RightChild);
            return rightSubTree.FindNodeByKey(key);
        }

        return result;
    }

    public boolean AddKeyValue(int key, T val)
    {
        // добавляем ключ-значение в дерево

        BSTFind<T> Finder = this.FindNodeByKey(key);
        if (Finder.NodeHasKey == true) {
            return false; // если ключ уже есть
        }

        BSTNode<T> nodeToAdd = new BSTNode<>(key,val,Finder.Node);

        if (this.Root == null) {
            this.Root = nodeToAdd;
            return true;
        }

        if (Finder.ToLeft) {
            Finder.Node.LeftChild = nodeToAdd;
        } else {
            Finder.Node.RightChild = nodeToAdd;
        }

        return true;
    }

    public BSTNode<T> LastChild(BSTNode<T> FromNode, boolean Left) {
        BSTNode<T> Child = FromNode.RightChild;
        if (Left) {
            Child = FromNode.LeftChild;
        }
        if (Child == null) {
            return FromNode;
        }

        return LastChild(Child,Left);

    }
    public BSTNode<T> FinMinMax(BSTNode<T> FromNode, boolean FindMax)
    {
        // ищем максимальный/минимальный ключ в поддереве

        if (FindMax) {
            return LastChild(FromNode,false);
        }

        return LastChild(FromNode,true);
    }


    private boolean DeleteNodeWithChildren(BSTNode<T> Node, boolean isRoot) {
        BSTNode<T> Parent = Node.Parent;
        BSTNode<T> replacementNode = this.LastChild(Node.RightChild, true);
        int caseNum = 0;

        if (replacementNode.isLeft() && replacementNode.isLeaf()) {
            caseNum = 1;
        }

        if (!replacementNode.isLeft() && replacementNode.isLeaf()) {
            caseNum = 2;
        }

        if (replacementNode.hasOneChild()) {
            caseNum = 3;
        }

        switch (caseNum) {
            case 1:
                replacementNode.Parent.LeftChild = null;
                replacementNode.Parent = Parent;
                replacementNode.RightChild = Node.RightChild;
                replacementNode.LeftChild = Node.LeftChild;
                break;
            case 2:
                replacementNode.Parent = Parent;
                replacementNode.LeftChild = Node.LeftChild;
                break;
            case 3:
                replacementNode.Parent.RightChild = replacementNode.RightChild;
                replacementNode.RightChild.Parent = replacementNode.Parent;
                replacementNode.Parent = Parent;
        }

        if (!isRoot) {
            if (Node.isLeft()) {
                Parent.LeftChild = replacementNode;
            } else {
                Parent.RightChild = replacementNode;
            }
        }
        if (isRoot) {
            this.Root = replacementNode;
        }
        Node.Parent = null;
        Node.LeftChild = null;
        Node.RightChild = null;

        return true;
    }
    private boolean DeleteRoot(BSTNode<T> Node) {
        if (Node.isLeaf()) {
            this.Root = null;
            return true;
        }

        if (Node.hasOneChild()) {
            BSTNode<T> Child = Node.getTheOnlyChild();
            Child.Parent = null;
            this.Root = Child;
            return true;
        }
        return this.DeleteNodeWithChildren(Node, true);
    }


    public boolean DeleteNodeByKey(int key)
    {
        // удаляем узел по ключу
        BSTFind<T> Finder = FindNodeByKey(key);
        if (Finder.NodeHasKey == false) {
            return false; // если узел не найден
        }

        BSTNode<T> Node = Finder.Node;

        if (Node.Parent == null) {
            return this.DeleteRoot(Node);
        }

        BSTNode<T> Parent = Node.Parent;

        if (Node.isLeaf()) {
            if (Node.isLeft()) {
                Parent.LeftChild = null;
            } else {
                Parent.RightChild = null;
            }
            Node.Parent = null;
            return true;
        }

        if (Node.hasOneChild()) {
            BSTNode<T> Child = Node.getTheOnlyChild();
            Child.Parent = Parent;
            if (Node.isLeft()) {
                Parent.LeftChild = Child;
            } else {
                Parent.RightChild = Child;
            }
            return true;
        }

        return DeleteNodeWithChildren(Node, false);

    }

    public int Count()
    {
        // количество узлов в дереве
        if (this.Root == null) {
            return 0;
        }

        return 1 + new BST<T>(this.Root.LeftChild).Count() + new BST<T>(this.Root.RightChild).Count();
    }

    private void deepPreorder(ArrayList<BSTNode> result) {
        if (this.Root == null) {
            return;
        }

        result.add(this.Root);
        new BST(this.Root.LeftChild).deepPreorder(result);
        new BST(this.Root.RightChild).deepPreorder(result);

    }
    private void deepInorder(ArrayList<BSTNode> result) {
        if (this.Root == null) {
            return;
        }

        new BST(this.Root.LeftChild).deepInorder(result);
        result.add(this.Root);
        new BST(this.Root.RightChild).deepInorder(result);

    }
    private void deepPostorder(ArrayList<BSTNode> result) {
        if (this.Root == null) {
            return;
        }

        new BST(this.Root.LeftChild).deepPostorder(result);
        new BST(this.Root.RightChild).deepPostorder(result);
        result.add(this.Root);

    }

    public ArrayList<BSTNode> DeepAllNodes(int order) {
        ArrayList<BSTNode> result = new ArrayList<BSTNode>();

        switch (order) {
            case 0: this.deepInorder(result);
            break;
            case 1: this.deepPostorder(result);
            break;
            case 2: this.deepPreorder(result);
        }

        return result;
    }

    public ArrayList<BSTNode> WideAllNodes() {
        Queue<BSTNode> Nodes = new LinkedList<BSTNode>();
        if (this.Root != null) {
            Nodes.add(this.Root);
        }

        ArrayList<BSTNode> result = new ArrayList<BSTNode>();
        while (!Nodes.isEmpty()) {
            BSTNode currentNode = Nodes.poll();
            result.add(currentNode);
            if (currentNode.LeftChild != null) {
                Nodes.add(currentNode.LeftChild);
            }
            if (currentNode.RightChild != null) {
                Nodes.add(currentNode.RightChild);
            }
        }

        return result;
    }
}
