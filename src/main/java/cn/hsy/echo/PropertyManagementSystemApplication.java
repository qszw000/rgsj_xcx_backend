package cn.hsy.echo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.multipart.MultipartFile;

@SpringBootApplication
@MapperScan("cn.hsy.echo.dao")
@EnableAutoConfiguration
public class PropertyManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(PropertyManagementSystemApplication.class, args);
	}

}
