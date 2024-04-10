package com.pcl.healthism.bussiness.common.tools;


import com.google.common.base.Strings;

import javax.servlet.http.HttpServletRequest;

public class IPTool {

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (!Strings.isNullOrEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!Strings.isNullOrEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }

    public static boolean isInnerNetwork(HttpServletRequest request) {
        String ip = getIpAddr(request);
        return ip != null &&  (ip.startsWith("192")
                || ip.startsWith("210.22.22")
                || ip.startsWith("127")
                || ip.startsWith("10")
                || ip.startsWith("172")
        );
    }
}
