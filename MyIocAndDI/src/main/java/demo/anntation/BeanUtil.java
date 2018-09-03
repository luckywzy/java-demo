package demo.anntation;

import demo.DI.BeanDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanUtil {
    Logger LOG = LoggerFactory.getLogger(BeanUtil.class);

    public List<BeanDefine> beanList;
    public String basepackage;

    public BeanUtil(List<BeanDefine> beanList, String basepackage) {
        this.beanList = beanList;
        this.basepackage = basepackage.replace("*", "");
    }

    //
    public void initAnnotation() {
        String[] tmp = basepackage.split("\\.");
        //TODO  在进行test的时候会拿到classes-test 目录下的文件
        String s = this.getClass().getClassLoader().getResource("").getPath();
        for (String a : tmp) {
            s += a + File.separator;
        }
        String folder = s.substring(0, s.length() - 1); //去掉最后的\*\
        LOG.info("base:{}", folder);
        loopInitClass(folder);

    }

    //根据包路径拿到文件名，根据文件名初始化bean
    public void loopInitClass(String path) {
        File folder = new File(path);
        File[] files = folder.listFiles();
        LOG.info("size:{}", files.length);
        for (File file : files) {
            if (file.isDirectory()) {
                loopInitClass(path + "/" + file.getName());
            } else if (file.isFile()) {
                initClass(file.getName(), path);
            }
        }
    }

    //根据文件名初始化bean,
    public void initClass(String fileName, String path) {
        String clasName = fileName.split("\\.")[0];
        //TODO 多层嵌套的路径未处理
        String name = path + File.separator + clasName;
        try {
            Object obj = Class.forName(basepackage + clasName).newInstance();
            Class<?> objClass = obj.getClass();

            if (objClass.isAnnotationPresent(MyBean.class)) {
                //MyBean annotation = objClass.getAnnotation(MyBean.class);
                String id = name.replace(name.charAt(0), Character.toLowerCase(name.charAt(0)));
                beanList.add(new BeanDefine(id, clasName));
            }

        } catch (Exception e) {
            LOG.error("装配bean 出错：", e);
        }

    }


    public static void main(String[] args) throws IOException {
        BeanUtil beanUtil = new BeanUtil(new ArrayList<>(1), "demo.TestObject.*");
        beanUtil.initAnnotation();
    }

}
