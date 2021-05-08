package class16;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * 最小生成树的P算法
 * <p>
 * 点--》边--》点--》边。。。。。
 * <p>
 * 边都被解锁之后，依次找小边直到都画X或者Y
 * <p>
 * 不需要并查集  因为每次都是一个点一个点的加入到result中的，不存在两个大集合的并
 * 所以不需要并查集
 */
// undirected graph only
public class Code05_Prim {

    public static class EdgeComparator implements Comparator<Edge> {
        /**
         * 小根堆
         *
         * @param o1
         * @param o2
         * @return
         */
        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }

    }

    /**
     * 没有连通性的问题
     *
     * @param graph
     * @return
     */
    public static Set<Edge> primMST(Graph graph) {
        // 解锁的边进入小根堆  实现边的从小到大
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(new EdgeComparator());

        // 哪些点被解锁出来了 点点集合
        HashSet<Node> nodeSet = new HashSet<>();

        // 依次挑选的的边在result里 最后要点边
        Set<Edge> result = new HashSet<>();


        // 随便挑了一个点，for循环防止森林
        for (Node node : graph.nodes.values()) {
            // node 是开始点
            if (!nodeSet.contains(node)) {
                nodeSet.add(node);

                // 由一个点，解锁所有相连的边，新解锁的边
                for (Edge edge : node.edges) {
                    //已经被放入点边也有可能会被放入进去，但不会影响结果
                    //可以优化
                    priorityQueue.add(edge);
                }
                while (!priorityQueue.isEmpty()) {
                    // 弹出解锁的边中，最小的边
                    Edge edge = priorityQueue.poll();
                    // 可能的一个新的点 to点（from点肯定在set中，点->边->点->边）
                    Node toNode = edge.to;
                    // 不含有的时候，就是新的点
                    if (!nodeSet.contains(toNode)) {
                        nodeSet.add(toNode);
                        result.add(edge);
                        //to点 解锁点一批边
                        for (Edge nextEdge : toNode.edges) {
                            priorityQueue.add(nextEdge);
                        }
                    }
                }
            }
            // break;
        }
        return result;
    }





    // 请保证graph是连通图
    // graph[i][j]表示点i到点j的距离，如果是系统最大值代表无路
    // 返回值是最小连通图的路径之和
    public static int prim(int[][] graph) {
        int size = graph.length;
        int[] distances = new int[size];
        boolean[] visit = new boolean[size];
        visit[0] = true;
        for (int i = 0; i < size; i++) {
            distances[i] = graph[0][i];
        }
        int sum = 0;
        for (int i = 1; i < size; i++) {
            int minPath = Integer.MAX_VALUE;
            int minIndex = -1;
            for (int j = 0; j < size; j++) {
                if (!visit[j] && distances[j] < minPath) {
                    minPath = distances[j];
                    minIndex = j;
                }
            }
            if (minIndex == -1) {
                return sum;
            }
            visit[minIndex] = true;
            sum += minPath;
            for (int j = 0; j < size; j++) {
                if (!visit[j] && distances[j] > graph[minIndex][j]) {
                    distances[j] = graph[minIndex][j];
                }
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println("hello world!");
    }

}
