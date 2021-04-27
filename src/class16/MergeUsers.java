package class16;

import class14.Code05_UnionFind;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * 并查集
 * 1）有若干个样本 a b c d...类型假设是V
 * 2）在并查集中一开始认为每个样本都在单独的集合中（小的集合当中）
 * 3）用户可以任何时候，调用以下两个方法
 * boolean isSameSet（V x，V y）：查询样本x和样本y是否是属于一个集合
 * void union（V x，V y）：把x和y各自所在的集合所有样本进行合并，合并成一个集合
 * <p>
 * 4)isSameSet和union方法的代价越低越好
 * <p>
 * <p>
 * 一群样本 x x x xx  xxx x xxx
 * 把样本分成 多个小的集合
 * 每次调用 3）中的方法之后合并
 * <p>
 * 高效实现就是并查集实现 O（1）的复杂度  hash表的并查集
 *
 * @author: lee
 * @create: 2021/4/26 9:38 上午
 **/
public class MergeUsers {

    public static class User {
        public String a;
        public String b;
        public String c;

        public User(String a, String b, String c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }

    /**
     * 如果两个user a字段一样 或者b字段一样或者c字段一样，就认为是一个人
     * 请合并users，返回合并之后的用户数量
     * <p>
     * （1，10，13）用户a
     * （1，20，37）用户b
     * （400，500，37）用户c
     * <p>
     * a b c其实是一个用户
     * <p>
     * mapA （1，1，400）
     * mapB（10，20，500）
     * mapC（13，37，37）
     *
     * @param users
     * @return
     */
    public static int mergeUsers(List<User> users) {

        UnionSet<User> unionFind = new UnionSet<>(users);
        HashMap<String, User> mapA = new HashMap<>();
        HashMap<String, User> mapB = new HashMap<>();
        HashMap<String, User> mapC = new HashMap<>();
        for (User user : users) {
            //如果mapA中包含
            if (mapA.containsKey(user.a)) {
                //当前的user和mapA中之前有a字段的拥有者user和在一起
                unionFind.union(user, mapA.get(user.a));
                //新字段
            } else {
                mapA.put(user.a, user);
            }
            //如果mapA中包含
            if (mapB.containsKey(user.b)) {
                //当前的user和mapA中之前有a字段的拥有者user和在一起
                unionFind.union(user, mapB.get(user.b));
                //新字段
            } else {
                mapA.put(user.b, user);
            }
            //如果mapA中包含
            if (mapC.containsKey(user.c)) {
                //当前的user和mapA中之前有a字段的拥有者user和在一起
                unionFind.union(user, mapC.get(user.c));
                //新字段
            } else {
                mapA.put(user.c, user);
            }
        }
        //向并查集询问，合并之后还有多少集合
        return unionFind.sets();

    }

    /**
     * 并查集
     *
     * @param <V>
     */
    public static class UnionSet<V> {
        public HashMap<V, Code05_UnionFind.Node<V>> nodes;
        public HashMap<Code05_UnionFind.Node<V>, Code05_UnionFind.Node<V>> parents;
        public HashMap<Code05_UnionFind.Node<V>, Integer> sizeMap;

        /**
         * 初始化一个并查集
         *
         * @param values
         */
        public UnionSet(List<V> values) {
            nodes = new HashMap<>();
            parents = new HashMap<>();
            sizeMap = new HashMap<>();
            for (V cur : values) {
                Code05_UnionFind.Node<V> node = new Code05_UnionFind.Node<>(cur);
                nodes.put(cur, node);
                parents.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        // 给你一个节点，请你往上到不能再往上，把代表返回
        public Code05_UnionFind.Node<V> findFather(Code05_UnionFind.Node<V> cur) {
            Stack<Code05_UnionFind.Node<V>> path = new Stack<>();
            while (cur != parents.get(cur)) {
                path.push(cur);
                cur = parents.get(cur);
            }
            while (!path.isEmpty()) {
                parents.put(path.pop(), cur);
            }
            return cur;
        }

        public boolean isSameSet(V a, V b) {

            return findFather(nodes.get(a)) == findFather(nodes.get(b));
        }

        public void union(V a, V b) {
            Code05_UnionFind.Node<V> aHead = findFather(nodes.get(a));
            Code05_UnionFind.Node<V> bHead = findFather(nodes.get(b));
            if (aHead != bHead) {
                int aSetSize = sizeMap.get(aHead);
                int bSetSize = sizeMap.get(bHead);
                Code05_UnionFind.Node<V> big = aSetSize >= bSetSize ? aHead : bHead;
                Code05_UnionFind.Node<V> small = big == aHead ? bHead : aHead;
                parents.put(small, big);
                sizeMap.put(big, aSetSize + bSetSize);
                sizeMap.remove(small);
            }
        }

        public int sets() {
            return sizeMap.size();
        }

    }


}
