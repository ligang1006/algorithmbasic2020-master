package class16;

/**
 * 属性点图结构
 * <p>
 * 图
 * 数据结构
 * 图比较复杂，
 * 生成图（图的转化）
 * <p>
 * 这里将[边权重  from to]
 * <p>
 * <p>
 * 图算法的思路
 * 1）先用自己最熟悉的方式，实现图结构的表达
 * 2）在自己属性的结构上，实现所有常用的图算法为模板
 * 3）把面试题提供的图结构，转化成自己熟悉的图结构，再调用模板即可
 */
public class GraphGenerator {

    /**
     * matrix 所有的边
     * N*3 的矩阵
     * [weight, from节点上面的值，to节点上面的值]
     * [ 5 , 0 , 7]
     * [ 3 , 0,  1]
     * <p>
     * <p>
     * 每个图 的组成
     * 1、点
     * 2、线
     * 3、点连线，加上权重
     * <p>
     * 这里给的方式是int[][] matrix
     * <p>
     * 还有邻接表点方法
     * <p>
     * <p>
     * 邻接矩阵转成这种方法，，遍历邻接矩阵  看到正数就建立边
     *
     * @param matrix
     * @return
     */
    public static Graph createGraph(int[][] matrix) {
        Graph graph = new Graph();
        for (int i = 0; i < matrix.length; i++) {
            // 拿到每一条边， matrix[i]
            int weight = matrix[i][0];
            int from = matrix[i][1];
            int to = matrix[i][2];

            /**建立点*/
            //如果没有该点，就放进去
            if (!graph.nodes.containsKey(from)) {
                graph.nodes.put(from, new Node(from));
            }
            if (!graph.nodes.containsKey(to)) {
                graph.nodes.put(to, new Node(to));
            }
            /**建立边*/
            Node fromNode = graph.nodes.get(from);
            Node toNode = graph.nodes.get(to);
            Edge newEdge = new Edge(weight, fromNode, toNode);

            fromNode.nexts.add(toNode);
            fromNode.out++;
            toNode.in++;
            //from点的直接边
            fromNode.edges.add(newEdge);
            graph.edges.add(newEdge);
        }
        return graph;
    }

}
