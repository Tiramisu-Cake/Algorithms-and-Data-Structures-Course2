package com.company;

import java.util.*;

public class SimpleTreeNode<T>
{
    public T NodeValue; // значение в узле
    public SimpleTreeNode<T> Parent; // родитель или null для корня
    public List<SimpleTreeNode<T>> Children; // список дочерних узлов или null
    public int Level;

    public SimpleTreeNode(T val, SimpleTreeNode<T> parent)
    {
        NodeValue = val;
        Parent = parent;
        Children = null;
        Level = 0;
    }
}

class SimpleTree<T>
{
    public SimpleTreeNode<T> Root; // корень, может быть null

    public SimpleTree(SimpleTreeNode<T> root)
    {
        Root = root;
    }

    public void AddChild(SimpleTreeNode<T> ParentNode, SimpleTreeNode<T> NewChild)
    {
        if (ParentNode.Children == null) {
            ParentNode.Children = new ArrayList<>();
        }
        ParentNode.Children.add(NewChild);
        NewChild.Parent = ParentNode;
        // ваш код добавления нового дочернего узла существующему ParentNode
    }

    public void DeleteNode(SimpleTreeNode<T> NodeToDelete)
    {
        NodeToDelete.Parent.Children.remove(NodeToDelete);
        if (NodeToDelete.Parent.Children.isEmpty()) {
            NodeToDelete.Parent.Children = null;
        }
        NodeToDelete.Parent = null;
        // ваш код удаления существующего узла NodeToDelete
    }

    public List<SimpleTreeNode<T>> GetAllNodes()
    {
        List<SimpleTreeNode<T>> AllNodes = new ArrayList<SimpleTreeNode<T>>();
        AllNodes.add(this.Root);
        if (this.Root.Children != null) {
            for (SimpleTreeNode Child : this.Root.Children) {
                SimpleTree<T> childSubTree = new SimpleTree(Child);
                AllNodes.addAll(childSubTree.GetAllNodes());
            }
        }
        // ваш код выдачи всех узлов дерева в определённом порядке
        return AllNodes;
    }

    public List<SimpleTreeNode<T>> FindNodesByValue(T val)
    {
        List<SimpleTreeNode<T>> Nodes = new ArrayList<SimpleTreeNode<T>>();
        if (this.Root.NodeValue == val) {
            Nodes.add(this.Root);
        }
        if (this.Root.Children != null) {
            for (SimpleTreeNode Child : this.Root.Children) {
                SimpleTree<T> childSubTree = new SimpleTree(Child);
                Nodes.addAll(childSubTree.FindNodesByValue(val));
            }
        }
        // ваш код поиска узлов по значению
        return Nodes;
    }

    public void MoveNode(SimpleTreeNode<T> OriginalNode, SimpleTreeNode<T> NewParent)
    {
        this.AddChild(NewParent,OriginalNode);
        this.DeleteNode(OriginalNode);
        // ваш код перемещения узла вместе с его поддеревом --
        // в качестве дочернего для узла NewParent
    }

    public int Count()
    {
        int nodesCount = 0;
        if (this.Root == null) {
            return 0;
        }
        nodesCount++;
        if (this.Root.Children != null) {
            for (SimpleTreeNode Child : this.Root.Children) {
                SimpleTree<T> childSubTree = new SimpleTree(Child);
                nodesCount += childSubTree.Count();
            }
        }
        // количество всех узлов в дереве
        return nodesCount;
    }

    public int LeafCount()
    {
        int leaves = 0;
        if (this.Root == null) {
            return 0;
        }
        if (this.Root.Children == null) {
            return 1;
        }
        if (this.Root.Children.isEmpty()) {
            return 1;
        }
        for (SimpleTreeNode Child : this.Root.Children) {
            SimpleTree<T> childSubTree = new SimpleTree(Child);
            leaves += childSubTree.LeafCount();
        }
        // количество листьев в дереве
        return leaves;
    }

    public void putLevels() {
        this.Levels(0);
    }
    private void Levels(int currentLevel) {
        if (this.Root.Children == null) {
            return;
        }
        for (SimpleTreeNode Child : this.Root.Children) {
            Child.Level = currentLevel + 1;

            SimpleTree<T> childSubTree = new SimpleTree(Child);
            childSubTree.Levels(currentLevel + 1);
        }
    }
}