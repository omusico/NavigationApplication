package com.onlylemi.map.utils.math;

import java.util.ArrayList;
import java.util.List;

public class FloydMath {

	private static final int INF = Integer.MAX_VALUE; // 无边
	private int[][] dist;

	// 顶点i 到 j的最短路径长度，初值是i到j的边的权重
	private int[][] path;
	private List<Integer> result = new ArrayList<Integer>();

	public void init(int[][] matrix) {
		this.dist = new int[matrix.length][matrix.length];
		this.path = new int[matrix.length][matrix.length];
	}

	/**
	 * 求两点间的最短路径
	 * 
	 * @param begin
	 * @param end
	 * @param matrix
	 */
	public List<Integer> findCheapestPath(int begin, int end, int[][] matrix) {
		init(matrix);

		floyd(matrix);
		result.add(begin);
		findPath(begin, end);
		result.add(end);

		return result;
	}

	public void findPath(int i, int j) {
		int k = path[i][j];
		if (k == -1)
			return;
		findPath(i, k); // 递归
		result.add(k);
		findPath(k, j);
	}

	public void floyd(int[][] matrix) {
		int size = matrix.length;
		// initialize dist and path
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				path[i][j] = -1;
				dist[i][j] = matrix[i][j];
			}
		}
		for (int k = 0; k < size; k++) {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (dist[i][k] != INF && dist[k][j] != INF
							&& dist[i][k] + dist[k][j] < dist[i][j]) {
						dist[i][j] = dist[i][k] + dist[k][j];
						path[i][j] = k;
					}
				}
			}
		}

	}

}
