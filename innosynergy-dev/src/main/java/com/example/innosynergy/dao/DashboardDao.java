package com.example.innosynergy.dao;

import com.example.innosynergy.model.Event;
import javafx.scene.chart.XYChart;

import java.util.List;

public interface DashboardDao {
    int getClientCount();
    int getPartnerCount();
    int getHelpRequestCount();
    List<XYChart.Data<Number, Number>> getLineChartData();
    List<Event> getEventTableData();}
