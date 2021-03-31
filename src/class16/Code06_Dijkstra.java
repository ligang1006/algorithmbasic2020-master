package class16;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

/**
 * 迪克斯特拉 有权图最短路径问题
 * <p>
 * 迪杰斯特拉算法(Dijkstra)是由荷兰计算机科学家狄克斯特拉于1959 年提出的因此又叫狄克斯特拉算法。
 * 是从一个顶点到其余各顶点的最短路径算法，解决的是有权图中最短路径问题。
 * <p>
 * 迪杰斯特拉算法主要特点是从起始点开始，采用贪心算法的策略，
 * 每次遍历到始点距离最近且未访问过的顶点的邻接节点，直到扩展到终点为止
 */
// no negative weight
public class Code06_Dijkstra {

    public static HashMap<Node, Integer> dijkstra1(Node from) {
        /**
         * 从head出发到所有点的最小距离
         * key：从head出发到达Key
         * value：从head出发到达key的最小距离
         * 如果在表中。没有T的记录。含义是，从head出发到达T这个点的最近距离为  无穷  ∞*
         */
        HashMap<Node, Integer> distanceMap = new HashMap<>();
        distanceMap.put(from, 0);


        // 打过对号的点  已经求过距离的节点，存在selectedNode中，再也不碰  实现锁
        HashSet<Node> selectedNodes = new HashSet<>();

        /**minNode就是桥连点，下一跳的起始点*/
        //
        Node minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNodes);
        /**处理吓一跳*/
        while (minNode != null) {
            //  原始点  ->  minNode(跳转点)   最小距离distance
            //上一个节点到桥连点的最短距离
            int distance = distanceMap.get(minNode);
            for (Edge edge : minNode.edges) {
                //
                Node toNode = edge.to;
                if (!distanceMap.containsKey(toNode)) {
                    /**之前是∞  这里终于找到了一条路了*/              /*✨*/
                    distanceMap.put(toNode, distance + edge.weight);
                } else { // toNode
                    /**之前来到过  更新值*/
                    distanceMap.put(edge.to,
                            Math.min(distanceMap.get(toNode), distance + edge.weight));
                }
            }
            //这里自动pass
            selectedNodes.add(minNode);
            //更新下一个，这里当可以使  minNode为null
            minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNodes);
        }
        return distanceMap;
    }

    /**
     * 遍历为选中的节点并求出最短的距离
     *
     * @param distanceMap  最后生成的表
     * @param touchedNodes 选中过的节点
     * @return
     */
    public static Node getMinDistanceAndUnselectedNode(HashMap<Node, Integer> distanceMap, HashSet<Node> touchedNodes) {
        Node minNode = null;
        int minDistance = Integer.MAX_VALUE;
        for (Entry<Node, Integer> entry : distanceMap.entrySet()) {
            Node node = entry.getKey();
            int distance = entry.getValue();
            if (!touchedNodes.contains(node) && distance < minDistance) {
                minNode = node;
                minDistance = distance;
            }
        }
        return minNode;
    }

    public static class NodeRecord {
        public Node node;
        public int distance;

        public NodeRecord(Node node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }

    /**
     * *************通过堆进行优化***********
     */
    public static class NodeHeap {
        private Node[] nodes; // 实际的堆结构
        /**
         * key 某一个node， value 上面堆中的位置 Integer就是一个下标
         * -1说明曾经在堆上，ignore的
         */
        private HashMap<Node, Integer> heapIndexMap;
        // key 某一个节点， value 从源节点出发到该节点的目前最小距离
        private HashMap<Node, Integer> distanceMap;
        private int size; // 堆上有多少个点

        public NodeHeap(int size) {
            nodes = new Node[size];
            heapIndexMap = new HashMap<>();
            distanceMap = new HashMap<>();
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        // 有一个点叫node，现在发现了一个从源节点出发到达node的距离为distance
        // 判断要不要更新，如果需要的话，就更新，不存在就add 存在是否需要ignore和uodate
        public void addOrUpdateOrIgnore(Node node, int distance) {
            //在堆上
            if (inHeap(node)) {
                //老记录和新记录笔记进行更新操作
                distanceMap.put(node, Math.min(distanceMap.get(node), distance));

                insertHeapify(node, heapIndexMap.get(node));
            }
            if (!isEntered(node)) {
                nodes[size] = node;
                heapIndexMap.put(node, size);
                distanceMap.put(node, distance);
                insertHeapify(node, size++);
            }
        }

        public NodeRecord pop() {
            NodeRecord nodeRecord = new NodeRecord(nodes[0], distanceMap.get(nodes[0]));
            swap(0, size - 1);
            heapIndexMap.put(nodes[size - 1], -1);
            distanceMap.remove(nodes[size - 1]);
            // free C++同学还要把原本堆顶节点析构，对java同学不必
            nodes[size - 1] = null;
            heapify(0, --size);
            return nodeRecord;
        }

        private void insertHeapify(Node node, int index) {
            while (distanceMap.get(nodes[index]) < distanceMap.get(nodes[(index - 1) / 2])) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        private void heapify(int index, int size) {
            int left = index * 2 + 1;
            while (left < size) {
                int smallest = left + 1 < size && distanceMap.get(nodes[left + 1]) < distanceMap.get(nodes[left])
                        ? left + 1
                        : left;
                smallest = distanceMap.get(nodes[smallest]) < distanceMap.get(nodes[index]) ? smallest : index;
                if (smallest == index) {
                    break;
                }
                swap(smallest, index);
                index = smallest;
                left = index * 2 + 1;
            }
        }

        /**
         * 如何判断一个节点之前是否进来过
         * 某一个node， value 上面堆中的位置 在堆中的位置进来过
         *
         * @param node
         * @return
         */
        private boolean isEntered(Node node) {
            return heapIndexMap.containsKey(node);
        }

        /**
         * 是否在堆上
         * 之前进来过，并且记录不是-1说明在堆上
         *
         * @param node
         * @return
         */
        private boolean inHeap(Node node) {
            return isEntered(node) && heapIndexMap.get(node) != -1;
        }

        private void swap(int index1, int index2) {
            heapIndexMap.put(nodes[index1], index2);
            heapIndexMap.put(nodes[index2], index1);
            Node tmp = nodes[index1];
            nodes[index1] = nodes[index2];
            nodes[index2] = tmp;
        }
    }

    // 改进后的dijkstra算法
    // 从head出发，所有head能到达的节点，生成到达每个节点的最小路径记录并返回
    public static HashMap<Node, Integer> dijkstra2(Node head, int size) {
        NodeHeap nodeHeap = new NodeHeap(size);
        nodeHeap.addOrUpdateOrIgnore(head, 0);
        HashMap<Node, Integer> result = new HashMap<>();
        while (!nodeHeap.isEmpty()) {
            //从小根堆里弹出一个最小的记录，一定不是之前已经弄过的记录
            //以recode做桥连点，更新to的记录
            NodeRecord record = nodeHeap.pop();
            Node cur = record.node;
            int distance = record.distance;
            for (Edge edge : cur.edges) {
                /**edge.to
                 * 如果的记录没有--》add
                 * 如果有--》并且更短满足更新  update
                 * 如果之前已经处理过 弹出的记录--》ignore
                 * */
                nodeHeap.addOrUpdateOrIgnore(edge.to, edge.weight + distance);
            }
            result.put(cur, distance);
        }
        return result;
    }

}
