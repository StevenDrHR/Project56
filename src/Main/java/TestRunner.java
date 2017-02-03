import junit.framework.TestCase;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner extends TestCase {
    public static void main(String[] args) {
        //Result result = JUnitCore.runClasses(TestJUnit.class, TestButton.class, TestButton2.class); //Enter Unit Test
        Result result = JUnitCore.runClasses(TestButton2.class);
        //Result result = JUnitCore.runClasses(TestAddProduct.class);

        for (Failure failure : result.getFailures()) {
           System.out.println(failure.toString());
      }

       System.out.println(result.wasSuccessful());
    }
}