package com.pcl.healthism.bussiness.virus.poi;

import lombok.Data;

@Data
public class VirusAffectCommunity {
    private String city;
    private String district;
    private String community;
    private double longitude;
    private double latitude;
}
