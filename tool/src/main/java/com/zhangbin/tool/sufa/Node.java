package com.zhangbin.tool.sufa;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Objects;

/**
 * 二叉树翻转  后续遍历就可以了 先打印左节点，在打印又节点，最后打印本身
 *
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Node {

    int value;

    Node left;

    Node right;

    public Node(int value, Node left, Node right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public static void main(String[] args) {

        Node data = getData();

//        print(data);

        Node turn = turn(data);
        System.out.println("turn = " + turn);
    }




    public static void print(Node node) {
        if (Objects.isNull(node)) {
            return;
        }
        Node left = node.getLeft();
        Node right = node.getRight();
        print(left);
        print(right);
        System.out.println("   " + node.getValue());
    }


    public static Node getData() {
//        Node n1 = new Node(1, null, null);
        Node n3 = new Node(3, null, null);
        Node n6 = new Node(6, null, null);
        Node n9 = new Node(9, null, null);
        Node n2 = new Node(2, null, n3);
        Node n7 = new Node(7, n6, n9);
        return new Node(4, n2, n7);
    }


    private static Node turn(Node node) {
        if (node == null) {
            return null;
        }
        Node left = node.getLeft();
        Node right = node.getRight();
        node.setRight(left);
        node.setLeft(right);
        turn(left);
        turn(right);
        return node;
    }

}
