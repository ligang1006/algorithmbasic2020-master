package class06;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 堆：
 * 1、首先必须是完全二叉树
 * 2、不是大根堆就是小根堆
 *
 * 从0开始  i的父节点  位置  （i-1）/2  1/2=0
 *         i的左孩子        2*i+1
 *         i右孩子          2*i+2
 *
 * 从1开始  i的父节点位置   i/2
 *         i的左孩子      2*i
 *  *      i右孩子        2*i+1
 *
 *
 *
 * N节点  每次新增节点的时候，，添加进来调整成大根堆操作复杂度---->logN
 *
 *
 *
 * 小根堆
 *
 *               0
 *              / \
 *             1   2
 *            / \ / \
 *           3  4 5  6
 * 大根堆
 *                  6
 *  *              / \
 *  *             5   4
 *  *            / \ / \
 *  *           3  4 1  2
 */
public class Code02_Heap {

    public static class MyMaxHeap {
        private int[] heap;
        private final int limit;
        private int heapSize;

        public MyMaxHeap(int limit) {
            heap = new int[limit];
            this.limit = limit;
            heapSize = 0;
        }

        public boolean isEmpty() {
            return heapSize == 0;
        }

        public boolean isFull() {
            return heapSize == limit;
        }

        /**
         * @param value
         */
        public void push(int value) {
            if (heapSize == limit) {
                throw new RuntimeException("heap is full");
            }
            heap[heapSize] = value;
            // value  heapSize  往上看
            heapInsert(heap, heapSize++);
        }

        // 用户此时，让你返回最大值，并且在大根堆中，把最大值删掉
        // 剩下的数，依然保持大根堆组织
        public int pop() {
            int ans = heap[0];
            swap(heap, 0, --heapSize);
            heapify(heap, 0, heapSize);
            return ans;
        }


        // 新加进来的数，现在停在了index位置，请依次往上移动，

        /**
         * 两种停止的条件
         * 移动到0位置  不会大于父节点（都是arr[0]位置的 所以也满足条件）
         * 比父节点位置大就交换
         *
         * @param arr
         * @param index
         */
        // 移动到0位置，或者干不掉自己的父亲了，停！
        private void heapInsert(int[] arr, int index) {
            // [index]    [index-1]/2（父位置）
            // index == 0  只要比父节点位置大就交换
            while (arr[index] > arr[(index - 1) / 2]) {
                swap(arr, index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        /**
         *  *                  6
         *  *  *              / \
         *  *  *             5   4
         *  *  *            / \ / \
         *  *  *           3  4 1  2
         *
         *  此时用户需要返回最大值
         *  并将最大值删掉，剩下的数，依然保持大根堆的结构
         * @param arr
         * @param index    下标
         * @param heapSize 堆大小
         */
        // 从index位置，往下看，不断的下沉 logN
        // 停：case1、较大的孩子都不再比index位置的数大；case2 已经没孩子了
        private void heapify(int[] arr, int index, int heapSize) {
            int left = index * 2 + 1;
            //左孩子没越界
            while (left < heapSize) { // 如果有左孩子，有没有右孩子，可能有可能没有！
                // 把较大孩子的下标，给largest  left + 1 < heapSize 右不越界
//                1)有右孩子
//                2）右孩子》左孩子的值
                //最大值给 largest
                int largest = left + 1 < heapSize
                        && arr[left + 1] > arr[left] ? left + 1 : left;

                largest = arr[largest] > arr[index] ? largest : index;
                //不需要下沉
                if (largest == index) {
                    break;
                }
                // index和较大孩子，要互换
                swap(arr, largest, index);
                index = largest;
                left = index * 2 + 1;
            }
        }

        private void swap(int[] arr, int i, int j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }

    }

    public static class RightMaxHeap {
        private int[] arr;
        private final int limit;
        private int size;

        public RightMaxHeap(int limit) {
            arr = new int[limit];
            this.limit = limit;
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean isFull() {
            return size == limit;
        }

        public void push(int value) {
            if (size == limit) {
                throw new RuntimeException("heap is full");
            }
            arr[size++] = value;
        }

        public int pop() {
            int maxIndex = 0;
            for (int i = 1; i < size; i++) {
                if (arr[i] > arr[maxIndex]) {
                    maxIndex = i;
                }
            }
            int ans = arr[maxIndex];
            arr[maxIndex] = arr[--size];
            return ans;
        }

    }


    public static class MyComparator implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;
        }

    }

    public static void main(String[] args) {
        // 小根堆
        PriorityQueue<Integer> heap = new PriorityQueue<>(new MyComparator());
        heap.add(5);
        heap.add(5);
        heap.add(5);
        heap.add(3);
        //  5 , 3
        System.out.println(heap.peek());
        heap.add(7);
        heap.add(0);
        heap.add(7);
        heap.add(0);
        heap.add(7);
        heap.add(0);
        System.out.println(heap.peek());
        while (!heap.isEmpty()) {
            System.out.println(heap.poll());
        }


        int value = 1000;
        int limit = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            int curLimit = (int) (Math.random() * limit) + 1;
            MyMaxHeap my = new MyMaxHeap(curLimit);
            RightMaxHeap test = new RightMaxHeap(curLimit);
            int curOpTimes = (int) (Math.random() * limit);
            for (int j = 0; j < curOpTimes; j++) {
                if (my.isEmpty() != test.isEmpty()) {
                    System.out.println("Oops!");
                }
                if (my.isFull() != test.isFull()) {
                    System.out.println("Oops!");
                }
                if (my.isEmpty()) {
                    int curValue = (int) (Math.random() * value);
                    my.push(curValue);
                    test.push(curValue);
                } else if (my.isFull()) {
                    if (my.pop() != test.pop()) {
                        System.out.println("Oops!");
                    }
                } else {
                    if (Math.random() < 0.5) {
                        int curValue = (int) (Math.random() * value);
                        my.push(curValue);
                        test.push(curValue);
                    } else {
                        if (my.pop() != test.pop()) {
                            System.out.println("Oops!");
                        }
                    }
                }
            }
        }
        System.out.println("finish!");

    }

}
