package com.microsoft.order.common.tools.reflect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.web.context.support.StandardServletEnvironment;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

public class ToolClass {

    private static final Logger log= LoggerFactory.getLogger(ToolClass.class);

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static List<Class> getSonClass(Class fatherClass,Class annotion) {
        List<Class> sonClassList = new ArrayList<Class>();
        String packageName = fatherClass.getPackage().getName();

        Class<?>[] packageClassList = findAllConfigurationClassesInPackage(packageName, annotion);
        for (Class clazz : packageClassList) {
            if (fatherClass.isAssignableFrom(clazz) && !fatherClass.equals(clazz)) {
                sonClassList.add(clazz);
            }
        }
        return sonClassList;
    }

//    @SuppressWarnings({ "rawtypes" })
//    private static List<Class> getPackageClass(String packageName) {
//        ClassLoader loader = ToolClass.class.getClassLoader();
//        String path = packageName.replace(".", "/");
//        log.error("getPackageClass1:"+path);
//        Enumeration<URL> resources = null;
//        try {
//            resources = loader.getResources(path);
//        } catch (IOException e) {
//            e.printStackTrace();
//            log.error(e.getMessage());
//        }
//        log.error("getPackageClass2:"+(resources==null?"null":resources.toString()));
//        List<File> fileList = new ArrayList<File>();
//        while (resources.hasMoreElements()) {
//            URL resource = resources.nextElement();
//            log.error("getPackageClass3:"+ (resource==null?"null":resource.toString()));
//            fileList.add(new File(resource.getFile()));
//        }
//        ArrayList<Class> classList = new ArrayList<Class>();
//        for (File file : fileList) {
//            log.error("getPackageClass4:"+(file==null?"null":file.getPath()));
//            List<Class> aClass = findClass(file, packageName);
//
//            log.error("getPackageClass5:{}",aClass);
//            classList.addAll(aClass);
//        }
//        return classList;
//    }
//
//    @SuppressWarnings({ "rawtypes" })
//    private static List<Class> findClass(File file, String packageName) {
//        List<Class> classList = new ArrayList<Class>();
//        boolean a = !file.exists();
//        log.error("findClass1:" + a);
//        if (a) {
//            return classList;
//        }
//        File[] fileArray = file.listFiles();
//        log.error("findClass2:" + fileArray.length);
//        for (File subFile : fileArray) {
//            log.error("findClass3:" + subFile.getPath());
//            boolean isDir  = subFile.isDirectory();
//            log.error("findClass4:" + isDir);
//            if (subFile.isDirectory()) {
//                assert !file.getName().contains(".");
//                classList.addAll(findClass(subFile, packageName + "." + subFile.getName()));
//            } else if (subFile.getName().endsWith(".class")) {
//                try {
//                    Class<?> aClass = Class.forName(packageName + "." + subFile.getName().split(".class")[0]);
//                    log.error("findClass5:" + aClass);
//                    classList.add(aClass);
//                } catch (ClassNotFoundException e) {
//                    log.error("{}",e);
//                }
//            }
//            log.error("findClass6:");
//        }
//        log.error("findClass7:" + classList.size());
//        return classList;
//    }

    public static Class<?>[] findAllConfigurationClassesInPackage(String packageName,Class clazz) {
        final List<Class<?>> result = new LinkedList<Class<?>>();
        final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(
                true, new StandardServletEnvironment());
        provider.addIncludeFilter(new AnnotationTypeFilter(clazz));
        for (BeanDefinition beanDefinition : provider
                .findCandidateComponents(packageName)) {
            try {
                result.add(Class.forName(beanDefinition.getBeanClassName()));
            } catch (ClassNotFoundException e) {
                log.warn("Could not resolve class object for bean definition", e);
            }
        }
        return result.toArray(new Class<?>[result.size()]);
    }
}
