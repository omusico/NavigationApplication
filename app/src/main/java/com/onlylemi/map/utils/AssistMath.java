package com.onlylemi.map.utils;

import java.util.ArrayList;
import java.util.List;

import android.graphics.PointF;
import android.util.Log;

import com.onlylemi.map.utils.math.FloydMath;
import com.onlylemi.map.utils.math.TSPNearestNeighbour;

/**
 * @author only乐秘
 */
public class AssistMath {

    private static final String TAG = "TestActivity";

    /**
     * 得到两点之间的距离
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static float getDistanceBetweenTwoPoints(float x1, float y1,
                                                    float x2, float y2) {
        return (float) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    /**
     * 得到两点之间的距离
     *
     * @param start
     * @param end
     * @return
     */
    public static float getDistanceBetweenTwoPoints(PointF start, PointF end) {
        return (float) Math.sqrt(Math.pow(end.x - start.x, 2)
                + Math.pow(end.y - start.y, 2));
    }


    /**
     * 得到地图中两点间的最短路径点集list (FloyedMath)
     *
     * @param begin  起点
     * @param end    终点
     * @param matrix 点集之间的关系矩阵
     * @return
     */
    public static List<Integer> getShortestPathBetweenTwoPoints(int begin,
                                                                int end, int[][] matrix) {

        return new FloydMath().findCheapestPath(begin, end, matrix);
    }

    /**
     * 任意点间最优路径list (邻近点)
     *
     * @param matrix
     * @return
     */
    public static List<Integer> getShortestPathBetweenPoints(int[][] matrix) {
        // TSP 计算最优路线
        List<Integer> result = new TSPNearestNeighbour().tsp(matrix);
        return result;
    }


    /**
     * 得到两点连线与水平面所成夹角
     *
     * @param start
     * @param end
     * @return
     */
    public static float getAngleBetweenTwoPointsWithLevel(PointF start, PointF end) {
        float angle = 90.0f;
        if (start.x != end.x) {
            angle = (float) Math.toDegrees(Math.atan((end.y - start.y)
                    / (end.x - start.x)));
        }
        return angle;
    }

    /**
     * 得到两点间中点的坐标
     *
     * @param start
     * @param end
     * @return
     */
    public static PointF getMidPointBetweenTwoPoints(PointF start, PointF end) {
        return getEveryPointBetweenTwoPoints(start, end, 0.5f);
    }

    /**
     * 得到两点间任意一点的坐标
     *
     * @param start
     * @param end
     * @param value
     * @return
     */
    public static PointF getEveryPointBetweenTwoPoints(PointF start, PointF end, float value) {
        //坐标系 y=kx+b
        float x, y;
        //有斜率
        if (start.x != end.x) {
            float k = (end.y - start.y) / (end.x - start.x);
            float b = end.y - k * end.x;

            if (end.x > start.x) {
                x = Math.min(end.x, start.x) + (end.x - start.x) * value;
            } else {
                x = Math.max(end.x, start.x) + (end.x - start.x) * value;
            }
            y = k * x + b;
        } else { //无斜率
            x = start.x;
            if (end.y > start.y) {
                y = Math.min(end.y, start.y) + (end.y - start.y) * value;
            } else {
                y = Math.max(end.y, start.y) + (end.y - start.y) * value;
            }
        }
        return new PointF(x, y);
    }


    /**
     * 得到一个点到直线最短距离
     *
     * @param point      已知点
     * @param linePoint1 确定直线的1点
     * @param linePoint2 确定直线的2点
     * @return
     */
    public static float getDistancePointToLine(PointF point, PointF linePoint1, PointF linePoint2) {
        // y = kx + b;
        // d = |kx-y+b| / √(k^2+1)
        float d;
        if (linePoint1.x != linePoint2.x) { //有斜率
            float k = (linePoint2.y - linePoint1.y) / (linePoint2.x - linePoint1.x);
            float b = linePoint2.y - k * linePoint2.x;
            d = Math.abs(k * point.x - point.y + b) / (float) Math.sqrt(k * k + 1);
        } else { //无斜率
            d = Math.abs(point.x - linePoint1.x);
        }
        return d;
    }

    /**
     * 得到一个点到直线最短距离的交点坐标
     *
     * @param point
     * @param linePoint1
     * @param linePoint2
     * @return
     */
    public static PointF getIntersectionPointToLine(PointF point, PointF linePoint1, PointF linePoint2) {

        // y = kx + b;
        float x, y;
        if (linePoint1.x != linePoint2.x) { //有斜率
            float k = (linePoint2.y - linePoint1.y) / (linePoint2.x - linePoint1.x);
            float b = linePoint2.y - k * linePoint2.x;
            //过point的垂线方程
            if (k != 0) {
                float kV = -1 / k;
                float bV = point.y - kV * point.x;
                x = (b - bV) / (kV - k);
                y = kV * x + bV;
            } else {
                x = point.x;
                y = linePoint1.y;
            }
        } else { //无斜率
            x = linePoint1.x;
            y = point.y;
        }
        return new PointF(x, y);
    }

    /**
     * 判断一个点与一条线段顶点连线的夹角
     *
     * @param point
     * @param linePoint1
     * @param linePoint2
     * @return
     */
    public static boolean isObtuseAngleBetweenPointToLine(PointF point, PointF linePoint1, PointF linePoint2) {
        // A*A + B*B < C*C
        float p_l1, p_l2, l1_l2;
        p_l1 = getDistanceBetweenTwoPoints(point, linePoint1);
        p_l2 = getDistanceBetweenTwoPoints(point, linePoint2);
        l1_l2 = getDistanceBetweenTwoPoints(linePoint1, linePoint2);

        return ((p_l1 * p_l1 + l1_l2 * l1_l2) < p_l2 * p_l2)
                || ((p_l2 * p_l2 + l1_l2 * l1_l2) < p_l1 * p_l1);
    }

}
