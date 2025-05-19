package com.example.jeeHamlaoui.Sattelites_Service.model.util;

import java.util.List;

public class OrbitalTrajectoryResponse {

    private List<Double> cartesianValues;
    private List<Point3D> points3D;
    private double periodSeconds;

    public OrbitalTrajectoryResponse(List<Double> cartesianValues, List<Point3D> points3D, double periodSeconds) {
        this.cartesianValues = cartesianValues;
        this.points3D = points3D;
        this.periodSeconds = periodSeconds;
    }

    public List<Double> getCartesianValues() {
        return cartesianValues;
    }

    public List<Point3D> getPoints3D() {
        return points3D;
    }

    public double getPeriodSeconds() {
        return periodSeconds;
    }
}
