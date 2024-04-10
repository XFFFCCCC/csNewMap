package com.pcl.healthism.bussiness.virus.poi;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.pcl.healthism.bussiness.common.Pair;
import com.pcl.healthism.bussiness.common.tools.StringTool;
import com.pcl.healthism.bussiness.virus.common.GpsConvertApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class Parser {

    private String configDir;
    private String hospitalConfigPath;
    private String communityConfigPath;
    private static final Set<String> SPECIFIC_HOSPITAL = Sets.newHashSet("深圳市第三人民医院");
    private final GpsConvertApi gpsConvertApi;

    @PostConstruct
    private void init() {
        final String userDir = System.getProperty("user.dir").replace("\\","/");
        configDir = userDir + "/config";
        hospitalConfigPath = configDir + "/hospital.data";
        communityConfigPath = configDir + "/virus.data";
    }

    public List<VirusAffectHospital> loadHospitals() {
        File file = new File(hospitalConfigPath);
        if (!file.exists()) {
            log.error("not found hospital config file");
            return Collections.EMPTY_LIST;
        }
        List<String> lines = readFile(file);
        List<VirusAffectHospital> hospitals = lines.stream().map(this::parseHospital).collect(Collectors.toList());

        return hospitals;
    }

    public static List<String> readFile(File file)  {
        List<String> list = new ArrayList<String>();
        try {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line = "";
            while ((line = br.readLine()) != null) {
                    list.add(line);
            }
            br.close();
            isr.close();
            fis.close();
        } catch (Exception e) {
            log.error("error read file", e);
        }
        return list;
    }

    public List<VirusAffectCommunity> loadCommunity() {
        File file = new File(communityConfigPath);
        if (!file.exists()) {
            log.error("not found community config file");
            return Collections.EMPTY_LIST;
        }
        List<String> lines = readFile(file);
        List<VirusAffectCommunity> communities = lines.stream().map(this::parseCommunity).collect(Collectors.toList());
//        for ( List<VirusAffectCommunity> patch: Lists.partition(communities, 40) ) {
//            List<Pair<Double, Double>> pairs = gpsConvertApi.convertTo(
//                    patch.stream().map(l -> new Pair<>(l.getLongitude(), l.getLatitude())).collect(Collectors.toList()),
//                    "baidu");
//            for (int i=0; i < patch.size(); i++) {
//                patch.get(i).setLongitude(pairs.get(i).getLeft());
//                patch.get(i).setLatitude(pairs.get(i).getRight());
//            }
//        }
        return communities;
    }

    private VirusAffectHospital parseHospital(String line) {
        String[] data = line.split("\t");
        List<String> cleanData = Stream.of(data).filter( l -> !Strings.isBlank(l)).collect(Collectors.toList());
        if (cleanData.size() != 5) {
            throw new IllegalStateException("not found legal hospital data in file");
        }
        List<Double> gps = StringTool.splitToD(cleanData.get(4), ",");
        VirusAffectHospital hospital = new VirusAffectHospital();
        hospital.setLongitude(gps.get(0));
        hospital.setLatitude(gps.get(1));
        hospital.setCity(cleanData.get(0));
        hospital.setHospitalName(cleanData.get(1));
        hospital.setAddress(cleanData.get(2));
        hospital.setTelephone(cleanData.get(3));
        if (SPECIFIC_HOSPITAL.contains(hospital.getHospitalName())) {
            hospital.setSpecific(true);
        }
        return hospital;
    }

    private List<Double> parseGPS(String line) {
        int index = line.indexOf("(");
        if( index < 0) {
            throw new IllegalStateException("not found legal gps data in file");
        }
        String gps  = line.substring(index).trim()
                .replace("(", "")
                .replace(")", "");
        return StringTool.splitToD(gps, ",");
    }

    private VirusAffectCommunity parseCommunity(String line) {
        String[] data = line.split("\t");
        List<String> cleanData = Stream.of(data).filter( l -> !Strings.isBlank(l)).collect(Collectors.toList());
        if (cleanData.size() != 4) {
            throw new IllegalStateException("not found legal community data in file");
        }
        List<Double> gps = StringTool.splitToD(cleanData.get(3), ",");
        VirusAffectCommunity community = new VirusAffectCommunity();
        community.setLongitude(gps.get(0));
        community.setLatitude(gps.get(1));
        community.setCity(cleanData.get(0));
        community.setDistrict(cleanData.get(1));
        community.setCommunity(cleanData.get(2));
        return community;
    }

    /*
    function bMapTransQQMap(lng, lat) {
      let x_pi = 3.14159265358979324 * 3000.0 / 180.0;
      let x = lng - 0.0065;
      let y = lat - 0.006;
      let z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
      let theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
      let lngs = z * Math.cos(theta);
      let lats = z * Math.sin(theta);

      return {
          lng: lngs,
          lat: lats
      }
    }
    * */

    private static Pair<Double,Double> baiduGps2QQGps(double lng, double lat) {
        return new Pair<>(lng, lat);
//        double pi = Math.PI * 3000.0 / 180.0;
//        double x = lng - 0.0065;
//        double y = lat - 0.006;
//        double z = Math.sqrt(x*x + y*y) - 0.00002 * Math.sin(y * pi);
//        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * pi);
//        return new Pair<>(z * Math.cos(theta), z * Math.sin(theta));
    }

}
