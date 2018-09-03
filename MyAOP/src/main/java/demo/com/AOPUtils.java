package demo.com;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

public class AOPUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(AOPUtils.class);

    public static void readXML(String fileName) {
        Document document = null;
        SAXReader saxReader = new SAXReader();

        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            document = saxReader.read(classLoader.getResourceAsStream(fileName));
            Element beans = document.getRootElement();

            //获取xml 文件中bean元素
            for (Iterator<Element> beanIterator = beans.elementIterator(); beanIterator.hasNext(); ) {
                Element element = beanIterator.next();

            }
        } catch (DocumentException e) {
            LOGGER.error("读取XML配置文件出错:{}", fileName);
        }
    }
}
