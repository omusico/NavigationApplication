package com.onlylemi.map.utils;

import android.graphics.PointF;
import android.nfc.Tag;
import android.util.Log;

import com.onlylemi.map.utils.AssistMath;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by only乐秘 on 2015-08-25.
 */
public class Assist {

    private static final String TAG = "Assist: ";

    private static final int INF = Integer.MAX_VALUE;
    private static int nodesSize;
    private static int nodesContactSize;

    public static void init(List<PointF> nodes, List<PointF> nodesContact) {
        if (nodes != null) {
            nodesSize = nodes.size();
        }
        if (nodesContact != null) {
            nodesContactSize = nodesContact.size();
        }
    }

    /**
     * 得到list点集间的距离
     *
     * @param nodes
     * @param list
     * @return
     */
    public static float getDistanceBetweenList(List<PointF> nodes,
                                               List<Integer> list) {
        float distance = 0;
        for (int i = 0; i < list.size() - 1; i++) {
            distance += AssistMath.getDistanceBetweenTwoPoints(nodes.get(list.get(i)),
                    nodes.get(list.get(i + 1)));
        }
        return distance;
    }

    /**
     * 得到地图中两点间的最短路径点集list
     *
     * @param start        起点
     * @param end          终点
     * @param nodes        点集list
     * @param nodesContact 点集之间的关系list
     * @return
     */
    public static List<Integer> getShortestPathBetweenTwoPoints(int start,
                                                                int end, List<PointF> nodes, List<PointF> nodesContact) {
        int[][] matrix = getMatrixBetweenFloorPlanNodes(nodes, nodesContact);

        return AssistMath.getShortestPathBetweenTwoPoints(start, end, matrix);
    }

    /**
     * 得到任意多个点集间的最优路径
     *
     * @param points
     * @param nodes
     * @param nodesContact
     * @return
     */
    public static List<Integer> getShortestPathBetweenPoints(int[] points,
                                                             List<PointF> nodes, List<PointF> nodesContact) {
        // 关系矩阵
        int[][] matrix = new int[points.length][points.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i; j < matrix[i].length; j++) {
                if (i == j) {
                    matrix[i][j] = INF;
                } else {
                    matrix[i][j] = (int) getDistanceBetweenList(
                            nodes,
                            getShortestPathBetweenTwoPoints(points[i],
                                    points[j], nodes, nodesContact));
                    matrix[j][i] = matrix[i][j];
                }
            }
        }

        // TSP 计算最优路线
        List<Integer> routeList = new ArrayList<>();
        List<Integer> result = AssistMath.getShortestPathBetweenPoints(matrix);
        System.out.println(result);
        for (int i = 0; i < result.size() - 1; i++) {
            int size = routeList.size();
            routeList.addAll(getShortestPathBetweenTwoPoints(
                    points[result.get(i)], points[result.get(i + 1)], nodes,
                    nodesContact));
            if (i != 0) {
                routeList.remove(size);
            }
        }
        return routeList;
    }


    /**
     * 得到地图上任意多个点集间的最优路径
     *
     * @param pointList
     * @param nodes
     * @param nodesContact
     * @return
     */
    public static List<Integer> getShortestPathBetweenPoints(List<PointF> pointList,
                                                             List<PointF> nodes, List<PointF> nodesContact) {
        if (nodesSize != nodes.size()) {
            int value = nodes.size() - nodesSize;
            for (int i = 0; i < value; i++) {
                nodes.remove(nodes.size() - 1);
            }
            value = nodesContact.size() - nodesContactSize;
            for (int i = 0; i < value; i++) {
                nodesContact.remove(nodesContact.size() - 1);
            }
        }

        //找到目标点 距离 最近的路线上的点
        int[] points = new int[pointList.size()];
        for (int i = 0; i < pointList.size(); i++) {
            addPointToList(pointList.get(i), nodes, nodesContact);
            points[i] = nodes.size() - 1;
        }


        return getShortestPathBetweenPoints(points, nodes, nodesContact);
    }

    /**
     * 得到两点间的最短距离
     *
     * @param start
     * @param end
     * @param nodes
     * @param nodesContact
     * @return
     */
    public static float getShortestDistanceBetweenTwoPoints(int start, int end,
                                                            List<PointF> nodes, List<PointF> nodesContact) {
        List<Integer> list = getShortestPathBetweenTwoPoints(start, end, nodes,
                nodesContact);
        return getDistanceBetweenList(nodes, list);
    }

    /**
     * 得到节点集之间的关系矩阵
     *
     * @param nodes
     * @param nodesContact
     * @return
     */
    public static int[][] getMatrixBetweenFloorPlanNodes(List<PointF> nodes, List<PointF> nodesContact) {
        // 设初始值为无穷大
        int[][] matrix = new int[nodes.size()][nodes.size()];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = INF;
            }
        }

        // 给矩阵赋路径权值
        for (int i = 0; i < nodesContact.size(); i++) {
            matrix[(int) nodesContact.get(i).x][(int) nodesContact.get(i).y] = (int) AssistMath.getDistanceBetweenTwoPoints(
                    nodes.get((int) nodesContact.get(i).x),
                    nodes.get((int) nodesContact.get(i).y));

            matrix[(int) nodesContact.get(i).y][(int) nodesContact.get(i).x] = matrix[(int) nodesContact
                    .get(i).x][(int) nodesContact.get(i).y];
        }

        return matrix;
    }

    /**
     * 得到地图中任意两点间的最短路径
     *
     * @param start
     * @param end
     * @param nodes
     * @param nodesContact
     * @return
     */
    public static List<Integer> getShortestDistanceBetweenTwoPoints(PointF start, PointF end,
                                                                    List<PointF> nodes, List<PointF> nodesContact) {
        Log.i(TAG, "getShortestDistanceBetweenTwoPoints");

        if (nodesSize != nodes.size()) {
            int value = nodes.size() - nodesSize;
            for (int i = 0; i < value; i++) {
                nodes.remove(nodes.size() - 1);
            }
            value = nodesContact.size() - nodesContactSize;
            for (int i = 0; i < value; i++) {
                nodesContact.remove(nodesContact.size() - 1);
            }
        }

        addPointToList(start, nodes, nodesContact);
        addPointToList(end, nodes, nodesContact);

        return getShortestPathBetweenTwoPoints(nodes.size() - 2, nodes.size() - 1, nodes, nodesContact);
    }

    /**
     * 得到地图中目标点到定位点的最短路径
     *
     * @param position
     * @param target
     * @param nodes
     * @param nodesContact
     * @return
     */
    public static List<Integer> getShortestDistanceBetweenTwoPoints(PointF position, int target,
                                                                    List<PointF> nodes, List<PointF> nodesContact) {
        if (nodesSize != nodes.size()) {
            int value = nodes.size() - nodesSize;
            for (int i = 0; i < value; i++) {
                nodes.remove(nodes.size() - 1);
            }
            value = nodesContact.size() - nodesContactSize;
            for (int i = 0; i < value; i++) {
                nodesContact.remove(nodesContact.size() - 1);
            }
        }

        addPointToList(position, nodes, nodesContact);

        return getShortestPathBetweenTwoPoints(nodes.size() - 1, target, nodes, nodesContact);
    }

    /**
     * 添加点到list中去
     *
     * @param point
     * @param nodes
     * @param nodesContact
     */
    private static void addPointToList(PointF point, List<PointF> nodes, List<PointF> nodesContact) {
        if (point != null) {
            PointF pV = null;
            int po1 = 0, po2 = 0;
            float min1 = INF;
            for (int i = 0; i < nodesContact.size() - 1; i++) {
                PointF p1 = nodes.get((int) nodesContact.get(i).x);
                PointF p2 = nodes.get((int) nodesContact.get(i).y);
                if (!AssistMath.isObtuseAngleBetweenPointToLine(point, p1, p2)) {
                    float minDis = AssistMath.getDistancePointToLine(point, p1, p2);
                    if (min1 > minDis) {
                        pV = AssistMath.getIntersectionPointToLine(point, p1, p2);
                        min1 = minDis;
                        po1 = (int) nodesContact.get(i).x;
                        po2 = (int) nodesContact.get(i).y;
                    }
                }
            }
            //得到交点
            nodes.add(pV);
            Log.i(TAG, "node=" + (nodes.size() - 1) + ", po1=" + po1 + ", po2=" + po2);
            nodesContact.add(new PointF(po1, nodes.size() - 1));
            nodesContact.add(new PointF(po2, nodes.size() - 1));
        }
    }
}
