package class17;

import java.util.Stack;

/**
 * 视频动态规划 1：22：02
 * 汉诺塔问题
 * N层  2^N-1
 *
 * T(N) =T(N-1)（挪到中间）+1+T(N-1)  (挪回来 最右边)
 * <p>
 * 暴力递归
 * <p>
 * 思路：
 * <p>
 * 1、1～N-1 左--》中  大步
 * 2、N  左-》右
 * 3、1～N-1 中--》右  大步
 */
public class Code02_Hanoi {
    /**
     * 下面的6和方法就是滕路的过程
     *
     * @param n
     */
    public static void hanoi1(int n) {
        leftToRight(n);
    }

    // 请把1~N层圆盘 从左 -> 右
    public static void leftToRight(int n) {
        if (n == 1) { // base case
            System.out.println("Move 1 from left to right");
            return;
        }
        /**
         * 下面两个方法领域理解成黑盒
         */
        leftToMid(n - 1);
        System.out.println("Move " + n + " from left to right");
        midToRight(n - 1);
    }

    // 请把1~N层圆盘 从左 -> 中
    public static void leftToMid(int n) {
        if (n == 1) {
            System.out.println("Move 1 from left to mid");
            return;
        }
        leftToRight(n - 1);
        System.out.println("Move " + n + " from left to mid");
        rightToMid(n - 1);
    }

    public static void rightToMid(int n) {
        if (n == 1) {
            System.out.println("Move 1 from right to mid");
            return;
        }
        rightToLeft(n - 1);
        System.out.println("Move " + n + " from right to mid");
        leftToMid(n - 1);
    }

    public static void midToRight(int n) {
        if (n == 1) {
            System.out.println("Move 1 from mid to right");
            return;
        }
        midToLeft(n - 1);
        System.out.println("Move " + n + " from mid to right");
        leftToRight(n - 1);
    }

    public static void midToLeft(int n) {
        if (n == 1) {
            System.out.println("Move 1 from mid to left");
            return;
        }
        midToRight(n - 1);
        System.out.println("Move " + n + " from mid to left");
        rightToLeft(n - 1);
    }

    public static void rightToLeft(int n) {
        if (n == 1) {
            System.out.println("Move 1 from right to left");
            return;
        }
        rightToMid(n - 1);
        System.out.println("Move " + n + " from right to left");
        midToLeft(n - 1);
    }


    /**
     * from--》to  other    忘掉左中右
     * <p>
     * 1、1～N-1  from-->other
     * 2、N       from-->to
     * 3、1～N-1  other-->to
     * <p>
     * <p>
     * 整个问题  N  左--》右
     * <p>
     * 拆分后的一个子问题   N-1 左--》中
     * <p>
     * 左--右
     * 左--中
     * <p>
     * 中--右
     * 中--左
     * <p>
     * 右--左
     * 右--中
     * 当N非常大的时候，上面的这六个步骤肯定都会出现
     * 所以第一种方法考虑这6中嵌套
     * <p>
     * 方法2
     * 就是加了几个参数，代表了6个过程
     */
    public static void hanoi2(int n) {
        if (n > 0) {

            func(n, "left", "right", "mid");
        }
    }

    public static void func(int N, String from, String to, String other) {
        int i = 0;
        //这里N是停止打印的标志
        if (N == 1) { // base
            System.out.println("Move 1 from " + from + " to " + to);
        } else {
            //N-1 从from --》to  目的是把N-1从from移动到 other，，，所以to变成了other

            func(N - 1, from, other, to);
            System.out.println("Move " + N + " from " + from + " to " + to);
            func(N - 1, other, to, from);
            i++;
            System.out.println(i);
        }
    }

    /**
     * 非递归
     *
     * 系统压站  不让他压站
     * 自己进行压栈，模拟递归栈怎么去做，，
     *
     *
     */

    public static class Record {
        public boolean finish1;
        public int base;
        public String from;
        public String to;
        public String other;

        public Record(boolean f1, int b, String f, String t, String o) {
            finish1 = false;
            base = b;
            from = f;
            to = t;
            other = o;
        }
    }


    public static void hanoi3(int N) {
        if (N < 1) {
            return;
        }
        Stack<Record> stack = new Stack<>();
        //自己进行压栈 Record(false, N, "left", "right", "mid") false标示步骤还未完成

        stack.add(new Record(false, N, "left", "right", "mid"));
        while (!stack.isEmpty()) {
            Record cur = stack.pop();
            if (cur.base == 1) {
                System.out.println("Move 1 from " + cur.from + " to " + cur.to);
                if (!stack.isEmpty()) {

                    stack.peek().finish1 = true;
                }
            } else {
                if (!cur.finish1) {
                    stack.push(cur);
                    stack.push(new Record(false, cur.base - 1, cur.from, cur.other, cur.to));
                } else {
                    System.out.println("Move " + cur.base + " from " + cur.from + " to " + cur.to);
                    stack.push(new Record(false, cur.base - 1, cur.other, cur.to, cur.from));
                }
            }
        }
    }

    public static void main(String[] args) {
        int n = 4;
//        hanoi1(n);
        System.out.println("============");
        hanoi2(n);
        System.out.println("============");
//		hanoi3(n);
    }

}
