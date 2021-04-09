package class17;

import java.util.Stack;

/**
 * 给一个栈，请逆序这个栈   不可申请额外的数据结构  只能用递归函数？
 * <p>
 * 思路：把最底层的元素去掉并返回，之后让上面的数盖下去
 * <p>
 * |1|			|3|
 * |2| -函数->  	|2|
 * |3|			|1|
 * <p>
 * 递归栈能记录数据
 */

public class Code05_ReverseStackUsingRecursive {
    /**
     * 每一次都返回最底层的
     * <p>
     * 这个返回的也是相当于逆序了，，把底层的记录下来了
     * 之后在返回的时候，压栈，，达到了逆序的目的
     *
     * @param stack
     */
    public static void reverse(Stack<Integer> stack) {
        if (stack.isEmpty()) {
            return;
        }
        int i = f(stack);
        reverse(stack);
        stack.push(i);
    }

    /**
     * 栈底元素移除掉
     * 上面的元素盖下来
     * 返回移除掉的栈底元素
     * <p>
     * 每一次返回的都是last
     * result是暂时存储的数据
     * |1|
     * |2| -函数->  |1|
     * |3|			|2|
     * <p>
     * 每次执行之后，最底层值去掉，并返回
     * result是临时存储的信息（每次递归保存）
     * 等下次调用结束之后，在压栈
     */
    public static int f(Stack<Integer> stack) {
        int result = stack.pop();
        if (stack.isEmpty()) {
            return result;
        } else {
            int last = f(stack);
            stack.push(result);
            return last;
        }
    }

    public static void main(String[] args) {
        Stack<Integer> test = new Stack<Integer>();
        test.push(3);
        test.push(2);
        test.push(1);

//        test.push(4);
//        test.push(5);
        reverse(test);
        while (!test.isEmpty()) {
            System.out.println(test.pop());
        }

    }

}
