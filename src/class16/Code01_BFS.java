package class16;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 图的宽度和深度优先遍历
 * <p>
 * 宽度：
 * 1、利用队列实现
 * 2、从源节点开始按照宽度进队列，然后弹出
 * 3、每弹出一个节点，把该节点所有没有进队列的邻节点放入队列
 * 4、直到队列变空
 * 在二叉树中用的队列
 *
 */
public class Code01_BFS {

    // 从node出发，进行宽度优先遍历
    public static void bfs(Node start) {
        if (start == null) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        //二叉树遍历没有环的问题，不会多次进入队列，set的作用是防止产生环，使程序执行完成
        HashSet<Node> set = new HashSet<>();
        queue.add(start);
        set.add(start);
        //只要有节点，就把他的邻节点加进去
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            System.out.println(cur.value);
            for (Node next : cur.nexts) {
                if (!set.contains(next)) {
                    set.add(next);
                    queue.add(next);
                }
            }
        }
    }

}
