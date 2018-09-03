package demo.DI;


import demo.TestObject.Obj5;
import demo.TestObject.TestObject1;
import demo.TestObject.TestObject2;
import demo.TestObject.TestObjectThree;
import org.junit.Test;

public class ClassPathXMLApplicationContextTest {

    @Test
    public void loadBeans() {
        ClassPathXMLApplicationContext applicationContext = new ClassPathXMLApplicationContext("ApplicationContext.xml");
        TestObject1 testObject1 = (TestObject1) applicationContext.getBean("testObject1");
        TestObject2 testObject2 = (TestObject2) applicationContext.getBean("testObject2");
        TestObjectThree testObjectThree = (TestObjectThree) applicationContext.getBean(TestObjectThree.class);
        testObject1.print();
        testObject2.print();
        testObjectThree.print();

    }

    @Test
    public void loadAnnotation() {
        ClassPathXMLApplicationContext applicationContext = new ClassPathXMLApplicationContext("ApplicationContext.xml");
        Obj5 bean = applicationContext.getBean(Obj5.class);

        bean.print();

    }
}