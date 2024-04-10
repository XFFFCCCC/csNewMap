package com.pcl.healthism.bussiness.virus.poi;

import lombok.Data;

@Data
public class VirusAffectHospital {
    private String city;
    private String hospitalName;
    private boolean specific;
    private String address;
    private double longitude;
    private double latitude;
    private String telephone;
}
