package class16;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 拓扑排序
 * <p>
 * 1、找到入度为0的点
 * 2、去掉该点的影响
 */
public class Code03_TopologySort {

    // directed graph and no loop
    public static List<Node> sortedTopology(Graph graph) {
        // key 某个节点   value 剩余的入度
        HashMap<Node, Integer> inMap = new HashMap<>();
        /** 只有剩余入度为0的点，才进入这个队列*/
        Queue<Node> zeroInQueue = new LinkedList<>();
        for (Node node : graph.nodes.values()) {
            inMap.put(node, node.in);
            if (node.in == 0) {
                zeroInQueue.add(node);
            }
        }
        //拓扑排序的结果，依次加入到result
        List<Node> result = new ArrayList<>();
        while (!zeroInQueue.isEmpty()) {
            //弹出的顺序，就是拓扑排序
            Node cur = zeroInQueue.poll();
            result.add(cur);
            for (Node next : cur.nexts) {
                //消除弹出的节点的影响，影响-1，邻居的入度-1
                inMap.put(next, inMap.get(next) - 1);
                //新出现的入度为0的点
                if (inMap.get(next) == 0) {
                    zeroInQueue.add(next);
                }
            }
        }
        return result;
    }
}
