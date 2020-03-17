package springboot_system.jiaoyu_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackages = "com.jiaoyu")
@MapperScan(basePackages = "com.jiaoyu.dao")
public class JiaoyuSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(JiaoyuSystemApplication.class, args);
	}

}
