package kakao2019;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FindRoad {

    private List<Node> nodeList;

    public class Node implements Comparable<Node> {
        int num;
        int x;
        int y;

        Node left = null;
        Node right = null;

        @Override
        public int compareTo(Node o) {
            return this.x - o.x;
        }

        public Node(int num, int x, int y) {
            this.num = num;
            this.x = x;
            this.y = y;
        }
    }

    public int[][] solution(int[][] nodeinfo) {
        nodeList = parseNodeInfo(nodeinfo);
        Node root = getHighestNode(0, nodeList.size());
        getChildren(root, 0, nodeList.size());

        int[][] answer = new int[2][];
        answer[0] = getPreOrder(root);
        answer[1] = getPostOrder(root);
        return answer;
    }

    public List<Node> parseNodeInfo(int[][] nodeinfo) {
        List<Node> nodeList = new ArrayList<>();
        for (int i = 0; i < nodeinfo.length; i++) {
            nodeList.add(new Node(i+1, nodeinfo[i][0], nodeinfo[i][1]));
        }

        Collections.sort(nodeList);
        return nodeList;
    }

    public Node getHighestNode(int from, int to) {
        if (from >= to) {
            return null;
        }

        Node highestNode = new Node(0, Integer.MIN_VALUE, Integer.MIN_VALUE);
        for (int i = from; i < to; i++) {
            if (highestNode.y < nodeList.get(i).y) {
                highestNode = nodeList.get(i);
            }
        }

        return highestNode;
    }

    public Node getChildren(Node parent, int from, int to) {
        int parentIndex = nodeList.indexOf(parent);
        Node leftChild = getHighestNode(from, parentIndex);
        Node rightChild = getHighestNode(parentIndex + 1, to);

        if (leftChild != null) {
            parent.left = getChildren(leftChild, from, parentIndex);
        }

        if (rightChild != null) {
            parent.right = getChildren(rightChild, parentIndex+1, to);
        }

        return parent;
    }

    public int[] getPreOrder(Node root) {
        List<Integer> sequence = new ArrayList<>();
        preOrder(root, sequence);
        return sequence.stream().mapToInt(Integer::intValue).toArray();
    }

    public void preOrder(Node node, List<Integer> sequence) {
        sequence.add(node.num);
        if (node.left != null) {
            preOrder(node.left, sequence);
        }
        if (node.right != null) {
            preOrder(node.right, sequence);
        }
    }

    public int[] getPostOrder(Node root) {
        List<Integer> sequence = new ArrayList<>();
        postOrder(root, sequence);
        return sequence.stream().mapToInt(Integer::intValue).toArray();
    }


    public void postOrder(Node node, List<Integer> sequence) {
        if (node.left != null) {
            postOrder(node.left, sequence);
        }
        if (node.right != null) {
            postOrder(node.right, sequence);
        }
        sequence.add(node.num);
    }

}
