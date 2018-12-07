package com.microsoft.order.common.tools.log.logback;//package com.fang.order.common.tools.log.logback;
//
//import ch.qos.logback.classic.pattern.ClassicConverter;
//import ch.qos.logback.classic.spi.ILoggingEvent;
//
//import java.net.InetAddress;
//import java.net.NetworkInterface;
//import java.net.SocketException;
//import java.util.Enumeration;
//
///**
// * @author: 陈偲
// * @date: 2017/11/29 17:36
// * @version: 1.0
// */
//public class IpLogConfig extends ClassicConverter{
//    private static final ThreadLocal<String> IP_ADDRESS=new ThreadLocal<>();
//    @Override
//    public String convert(ILoggingEvent event) {
//        if (IP_ADDRESS.get()==null) {
//            String ip = "";
//            try {
//                for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
//                    NetworkInterface intf = en.nextElement();
//                    String name = intf.getName();
//                    if (!name.contains("docker") && !name.contains("lo")) {
//                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
//                            InetAddress inetAddress = enumIpAddr.nextElement();
//                            if (!inetAddress.isLoopbackAddress()) {
//                                String ipaddress = inetAddress.getHostAddress().toString();
//                                if (!ipaddress.contains("::") && !ipaddress.contains("0:0:") && !ipaddress.contains("fe80")) {
//                                    ip = ipaddress;
//                                }
//                            }
//                        }
//                    }
//                }
//            } catch (SocketException ex) {
//                ip = "127.0.0.1";
//                ex.printStackTrace();
//            }
//            IP_ADDRESS.set(ip);
//        }
//        return IP_ADDRESS.get();
//    }
//}
