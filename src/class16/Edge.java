package class16;

public class Edge {
	/**
	 * 权重
	 */
	public int weight;
	public Node from;
	/**
	 * 到哪
	 */
	public Node to;

	public Edge(int weight, Node from, Node to) {
		this.weight = weight;
		this.from = from;
		this.to = to;
	}

}
