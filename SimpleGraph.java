package com.company;

import java.util.*;

class Vertex
{
    public int Value;
    public boolean Hit;
    public Vertex(int val)
    {
        Value = val;
        Hit = false;
    }
}

class SimpleGraph
{
    Vertex [] vertex;
    int [][] m_adjacency;
    int max_vertex;

    public SimpleGraph(int size)
    {
        max_vertex = size;
        m_adjacency = new int [size][size];
        vertex = new Vertex[size];
    }

    public void AddVertex(int value)
    {
        // ваш код добавления новой вершины
        // с значением value
        // в незанятую позицию vertex

        for (int i = 0; i < max_vertex; i++) {
            if (vertex[i] == null) {
                vertex[i] = new Vertex(value);
                return;
            }
        }

    }

    // здесь и далее, параметры v -- индекс вершины
    // в списке  vertex
    public void RemoveVertex(int v)
    {
        // ваш код удаления вершины со всеми её рёбрами
        vertex[v] = null;
        for (int i = 0; i < max_vertex; i++) {
            m_adjacency[i][v] = 0;
            m_adjacency[v][i] = 0;
        }

    }

    public boolean IsEdge(int v1, int v2)
    {
        // true если есть ребро между вершинами v1 и v2

        return m_adjacency[v1][v2] == 1;
    }

    public void AddEdge(int v1, int v2)
    {
        // добавление ребра между вершинами v1 и v2
        m_adjacency[v1][v2] = 1;
        m_adjacency[v2][v1] = 1;
    }

    public void RemoveEdge(int v1, int v2)
    {
        // удаление ребра между вершинами v1 и v2
        m_adjacency[v1][v2] = 0;
        m_adjacency[v2][v1] = 0;
    }

    public int getVertexIndex(Vertex vertex) {
        for (int i = 0; i < max_vertex; i++) {
            if (this.vertex[i] == vertex) {
                return i;
            }
        }
        return -1;
    }

    private void DFS(int VFrom, int VTo, Stack<Vertex> path) {
        this.vertex[VFrom].Hit = true;
        path.push(this.vertex[VFrom]);

        if (this.IsEdge(VFrom,VTo)) {
            path.push(vertex[VTo]);
            return;
        }

        for (int i = 0; i < max_vertex; i++) {
            if (this.IsEdge(VFrom,i) && vertex[i].Hit == false) {
                this.DFS(i,VTo,path);
            }
        }

        path.pop();
        if (path.isEmpty()) {
            return;
        }

        this.DFS(getVertexIndex(path.pop()),VTo,path);
    }

    public ArrayList<Vertex> DepthFirstSearch(int VFrom, int VTo)
    {
        // Узлы задаются позициями в списке vertex.
        // Возвращается список узлов -- путь из VFrom в VTo.
        // Список пустой, если пути нету.

        for (int i = 0; i < max_vertex; i++) {
            this.vertex[i].Hit = false;
        }

        Stack<Vertex> path = new Stack<Vertex>();
        this.DFS(VFrom, VTo, path);
        return new ArrayList<Vertex>(path);

    }
}
