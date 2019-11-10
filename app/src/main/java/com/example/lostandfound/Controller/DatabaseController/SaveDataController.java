package com.example.lostandfound.Controller.DatabaseController;

import android.content.Intent;

import com.example.lostandfound.Model.DatabaseModel.RealtimeDatabaseDemoModel;
import com.example.lostandfound.View.SecondUi.HomeFragment;

import java.util.Map;

public class SaveDataController
{
    Map map;
    RealtimeDatabaseDemoModel databaseDemo;


    public SaveDataController()
    {
        databaseDemo = new RealtimeDatabaseDemoModel(map);

    }

    public void setMap(Map map)
    {
        this.map = map;
    }

    public void saveData()
    {
        databaseDemo.saveData(map);
    }

//    public void setImage(Intent data)
////    {
////        databaseDemo.load(data);
////    }
}