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
        if (this.RightChild == null && this.LeftChild == null) {
            return true;
        }
        return false;
    }

    public boolean isLeft() throws NullPointerException {
        if (this.Parent == null) {
            throw new NullPointerException("Parent is null!");
        }
        BSTNode<T> Parent = this.Parent;
        if (Parent.RightChild.NodeKey == this.NodeKey) {
            return false;
        }
        return true;
    }

    public boolean hasOneChild() {
        if (this.isLeaf()) {
            return false;
        }
        if (this.RightChild == null || this.LeftChild == null) {
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
        BSTNode<T> newNode = Node.RightChild;
        newNode = this.LastChild(newNode, true);

        if (newNode.isLeaf()) {
            newNode.Parent = Parent;
            newNode.RightChild = Node.RightChild;
            newNode.LeftChild = Node.LeftChild;

            if (!isRoot) {
                if (Node.isLeft()) {
                    Parent.LeftChild = newNode;
                } else {
                    Parent.RightChild = newNode;
                }
                return true;
            }
        }

        newNode.RightChild.Parent = newNode.Parent;
        if (newNode.isLeft()) {
            newNode.Parent.LeftChild = newNode.RightChild;
        } else {
            newNode.Parent.RightChild = newNode.RightChild;
        }
        newNode.Parent = Parent;
        newNode.RightChild = Node.RightChild;
        newNode.LeftChild = Node.LeftChild;

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
            this.DeleteRoot(Node);
        }

        BSTNode<T> Parent = Node.Parent;

        if (Node.isLeaf()) {
            if (Node.isLeft()) {
                Parent.LeftChild = null;
            } else {
                Parent.RightChild = null;
            }
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
        if (this.Root == null) {
            return 0;
        }

        int count = 0;
        if (this.Root.LeftChild != null) {
            count++;
            BST<T> leftSubTree = new BST<T>(this.Root.LeftChild);
            count += leftSubTree.Count();
        }
        if (this.Root.RightChild != null) {
            count++;
            BST<T> rightSubTree = new BST<T>(this.Root.RightChild);
            count += rightSubTree.Count();
        }
        return count; // количество узлов в дереве
    }

}
