package com.dr.navigationapplication.dao.daoimpl;

import com.dr.navigationapplication.dao.ActivityTable;
import com.dr.navigationapplication.dao.CityTable;
import com.dr.navigationapplication.dao.FloorPlanTable;
import com.dr.navigationapplication.dao.InfoBaseTable;
import com.dr.navigationapplication.dao.NodesContact;
import com.dr.navigationapplication.dao.NodesTable;
import com.dr.navigationapplication.dao.PlaceTable;
import com.dr.navigationapplication.dao.ViewsTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 董神 on 2015/9/30.
 * I love programming
 * all data collection
 */
public class Data {

    public static List<ActivityTable> activityTableList = new ArrayList<>();
    public static List<CityTable> cityTableList = new ArrayList<>();
    public static List<FloorPlanTable> floorPlanTableList = new ArrayList<>();
    public static List<InfoBaseTable> infoBaseTableList = new ArrayList<>();
    public static List<NodesContact> nodesContactList = new ArrayList<>();
    public static List<NodesTable> nodesTableList = new ArrayList<>();
    public static List<PlaceTable> placeTableList = new ArrayList<>();
    public static List<ViewsTable> viewTableList = new ArrayList<>();
    private static List<String> strings;

    public static List<String> getOnlyCity() {
        if (strings == null) {
            strings = new ArrayList<>();
            for (int i = 0; i < cityTableList.size(); i++) {
                String s = cityTableList.get(i).getName();
                strings.add(s);
            }
        }
        return strings;
    }
}
