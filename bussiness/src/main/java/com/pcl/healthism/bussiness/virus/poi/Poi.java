package com.pcl.healthism.bussiness.virus.poi;

import lombok.Data;

import java.util.List;

@Data
public class Poi {
    private List<VirusAffectCommunity> communities;
    private List<VirusAffectHospital> hospitals;
}
