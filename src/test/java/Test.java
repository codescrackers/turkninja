import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.yazilimokulu.config.DataConfiguration;

public class Test {

	BeanFactory factory= new AnnotationConfigApplicationContext(DataConfiguration.class);
	
	
	
	
}
