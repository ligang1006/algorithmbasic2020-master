package class14;

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
 * 一群样本 x x x xx  xxx x xxx
 * 把样本分成 多个小的集合
 * 每次调用 3）中的方法之后合并
 * <p>
 * 高效实现就是并查集实现 O（1）的复杂度  hash表的并查集
 *
 * @author: lee
 * @create: 2021/4/26 9:38 上午
 **/
public class Code05_UnionFind {

	public static class Node<V> {
		V value;

		public Node(V v) {
			value = v;
		}
	}

	public static class UnionFind<V> {
		public HashMap<V, Node<V>> nodes;
		public HashMap<Node<V>, Node<V>> parents;
		public HashMap<Node<V>, Integer> sizeMap;

		public UnionFind(List<V> values) {
			nodes = new HashMap<>();
			parents = new HashMap<>();
			sizeMap = new HashMap<>();
			for (V cur : values) {
				Node<V> node = new Node<>(cur);
				nodes.put(cur, node);
				parents.put(node, node);
				sizeMap.put(node, 1);
			}
		}

		// 给你一个节点，请你往上到不能再往上，把代表返回
		public Node<V> findFather(Node<V> cur) {
			Stack<Node<V>> path = new Stack<>();
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
			Node<V> aHead = findFather(nodes.get(a));
			Node<V> bHead = findFather(nodes.get(b));
			if (aHead != bHead) {
				int aSetSize = sizeMap.get(aHead);
				int bSetSize = sizeMap.get(bHead);
				Node<V> big = aSetSize >= bSetSize ? aHead : bHead;
				Node<V> small = big == aHead ? bHead : aHead;
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
