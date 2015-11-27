import com.finmechanics.fmcom.xlloopspring.XlServerBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;

/**
 * Created by rjain236 on 26/11/15.
 */
public class StandaloneExecuter {
    public static void main(String[] args){
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                "classpath*:applicationContextCombined.xml");
        System.out.println("Done");
    }
}
