package class16;

import java.util.HashSet;
import java.util.Stack;

/**
 * 深度优先遍历：
 * 一条路走到底，再往上返回
 * <p>
 * 1、栈实现
 * 2、从源节点开始，把节点按照深度放入栈，然后弹出
 * 3、每弹出一个节点，把该节点下一个没有进过栈的邻接点放入栈
 * 4、直到栈变空
 */
public class Code02_DFS {

    public static void dfs(Node node) {
        if (node == null) {
            return;
        }
        //栈记录的是深度优先遍历的路径
        Stack<Node> stack = new Stack<>();
        //防止重复，来回走
        HashSet<Node> set = new HashSet<>();
        stack.add(node);
        set.add(node);
        //进栈的时候打印
        System.out.println(node.value);

        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            //枚举当前节点的后代
            for (Node next : cur.nexts) {
                if (!set.contains(next)) {
                    //把当前的和下一个压进去
                    stack.push(cur);
                    stack.push(next);
                    set.add(next);
                    System.out.println(next.value);
                    //a 的直接有bcd ，当把b和a再压入栈之后，break后就不会打印了

                    //之后就不在走了，其他的邻居在以后的时间在进入
                    break;
                }
            }
        }
    }


}
