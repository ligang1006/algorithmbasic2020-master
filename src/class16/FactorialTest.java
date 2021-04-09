package class16;

import java.util.List;
import java.util.Stack;

/**
 * @author: lee
 * @create: 2021/4/9 3:58 下午
 **/
public class FactorialTest {

    public static void digui(Stack<Integer> nums) {
        Stack<Integer> numsReverse = new Stack<>();
        while (!nums.isEmpty()) {
            Integer pop = nums.pop();
            numsReverse.push(pop);
        }
    }

    /**
     * 计算阶乘
     *
     * @param nums
     */
    public static Integer factorial(Integer nums, Integer end) {
        if (nums <= 0) {
            return 0;
        }
        Integer result = 1;
        while (nums < end) {
            nums++;
            result = result * nums;
            factorial(nums, end);
        }
        return result;
    }

    public static void main(String[] args) {

        Integer factorial = factorial(8, 10);
        System.out.println(factorial);

    }
}
