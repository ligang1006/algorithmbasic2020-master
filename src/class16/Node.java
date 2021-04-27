package class16;

import java.util.ArrayList;

// 点结构的描述
public class Node {
    /**
     * String 类型也可
     * 编号（Id）
     */
    public int value;
    /**
     * 入度 有多少点是接收的
     */
    public int in;
    /**
     * 出度 有多少点是从这出去的
     * 直接出
     */
    public int out;
    /**
     * 直接邻居（由自己出发的边连接谁）
     */
    public ArrayList<Node> nexts;
    /**
     * 与他连接的边
     */
    public ArrayList<Edge> edges;

    public Node(int value) {
        this.value = value;
        in = 0;
        out = 0;
        nexts = new ArrayList<>();
        edges = new ArrayList<>();
    }
}
