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

    public boolean isLeaf() {
        if (this.Children == null) {
            return true;
        }
        if (this.Children.isEmpty()) {
            return true;
        }
        return false;
    }
    public boolean hasOneChild() {
        if (this.Children == null) {
            return false;
        }
        if (this.Children.size() == 1) {
            return true;
        }
        return false;
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
        // ваш код добавления нового дочернего узла существующему ParentNode
        if (ParentNode.Children == null) {
            ParentNode.Children = new ArrayList<>();
        }
        ParentNode.Children.add(NewChild);
        NewChild.Parent = ParentNode;

    }

    public void DeleteNode(SimpleTreeNode<T> NodeToDelete) {
        // ваш код удаления существующего узла NodeToDelete
        NodeToDelete.Parent.Children.remove(NodeToDelete);
        if (NodeToDelete.Parent.Children.isEmpty()) {
            NodeToDelete.Parent.Children = null;
        }
        NodeToDelete.Parent = null;

    }

    public List<SimpleTreeNode<T>> GetAllNodes()
    {
        // ваш код выдачи всех узлов дерева в определённом порядке
        List<SimpleTreeNode<T>> AllNodes = new ArrayList<SimpleTreeNode<T>>();
        AllNodes.add(this.Root);

        if (this.Root.Children == null) {
            return AllNodes;
        }
        for (SimpleTreeNode Child : this.Root.Children) {
            SimpleTree<T> childSubTree = new SimpleTree(Child);
            AllNodes.addAll(childSubTree.GetAllNodes());
        }

        return AllNodes;
    }

    public List<SimpleTreeNode<T>> FindNodesByValue(T val)
    {
        // ваш код поиска узлов по значению
        List<SimpleTreeNode<T>> Nodes = new ArrayList<SimpleTreeNode<T>>();
        if (this.Root.NodeValue == val) {
            Nodes.add(this.Root);
        }
        if (this.Root.Children == null) {
            return Nodes;
        }
        for (SimpleTreeNode Child : this.Root.Children) {
            SimpleTree<T> childSubTree = new SimpleTree(Child);
            Nodes.addAll(childSubTree.FindNodesByValue(val));
        }

        return Nodes;
    }

    public void MoveNode(SimpleTreeNode<T> OriginalNode, SimpleTreeNode<T> NewParent)
    {
        // ваш код перемещения узла вместе с его поддеревом --
        // в качестве дочернего для узла NewParent
        this.DeleteNode(OriginalNode);
        this.AddChild(NewParent,OriginalNode);
    }

    public int Count()
    {
        // количество всех узлов в дереве
        int nodesCount = 0;
        if (this.Root == null) {
            return 0;
        }

        nodesCount++;

        if (this.Root.Children == null) {
            return nodesCount;
        }
        if (this.Root.Children.isEmpty()) {
            return nodesCount;
        }
        for (SimpleTreeNode Child : this.Root.Children) {
            SimpleTree<T> childSubTree = new SimpleTree(Child);
            nodesCount += childSubTree.Count();
        }

        return nodesCount;
    }

    public int LeafCount()
    {
        // количество листьев в дереве
        if (this.Root == null) {
            return 0;
        }
        if (this.Root.Children == null) {
            return 1;
        }
        if (this.Root.Children.isEmpty()) {
            return 1;
        }
        int leaves = 0;
        for (SimpleTreeNode Child : this.Root.Children) {
            SimpleTree<T> childSubTree = new SimpleTree(Child);
            leaves += childSubTree.LeafCount();
        }

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

    private void cloneChildren(SimpleTreeNode<T> Original, SimpleTreeNode<T> Clone) {
        if (Original.Children == null) {
            return;
        }
        Clone.Children = new ArrayList<SimpleTreeNode<T>>();

        for (SimpleTreeNode<T> Child : Original.Children) {
            SimpleTreeNode<T> childCopy = new SimpleTreeNode<T>(Child.NodeValue,Clone);
            Clone.Children.add(childCopy);
            cloneChildren(Child,childCopy);
        }
    }

    public SimpleTree<T> clone() {
        SimpleTreeNode<T> rootCopy = new SimpleTreeNode<T>(this.Root.NodeValue,this.Root.Parent);
        SimpleTree<T> treeClone = new SimpleTree<T>(rootCopy);
        treeClone.cloneChildren(this.Root,rootCopy);
        return treeClone;
    }

    private ArrayList<T> getCutsForEvenTrees() {
        if (this.Root == null) {
            return new ArrayList<T>();
        }

        if (this.Root.Children == null) {
            return new ArrayList<T>();
        }

        SimpleTree<T> TreeCopy = this.clone();
        ArrayList<T> result = new ArrayList<T>();

        for (SimpleTreeNode<T> Child : TreeCopy.Root.Children) {
            result.addAll(new SimpleTree<T>(Child).getCutsForEvenTrees());
        }

        int leavesNum = TreeCopy.LeafCount();
        boolean firstConditionToAdd = leavesNum == TreeCopy.Count()-1 && leavesNum % 2 == 1;
        boolean secondConditionToAdd = TreeCopy.Count() % leavesNum == 0 && leavesNum % 2 == 0;
        boolean thirdConditionToAdd = TreeCopy.Count() % 2 == 0 && leavesNum % 2 == 0;

        if (firstConditionToAdd || secondConditionToAdd || thirdConditionToAdd) {
            if (TreeCopy.Root.Parent != null) {
                result.add(TreeCopy.Root.Parent.NodeValue);
                result.add(TreeCopy.Root.NodeValue);
                TreeCopy.Root.Parent.Children.remove(TreeCopy.Root);
                TreeCopy.Root.Parent = null;
            }
            return result;
        }

        return result;
    }

    public ArrayList<T> EvenTrees() {
        if (this.Count() % 2 == 0) {
            return this.getCutsForEvenTrees();
        }
        return new ArrayList<>();

    }
}
