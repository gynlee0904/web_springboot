package kr.co.boot;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration  //환경설정@ => config.xml와 같은것 
@PropertySource("classpath:/application.properties")  //@PropertySource : properties파일을 로드할수 있게 하는 @.
public class dbinfos {
	
//	@ConfigurationProperties(prefix="spring.second-datasource")
//	@ConfigurationProperties(prefix="spring.datasource.hikari")
//	@Bean
//	public DataSource mysqldb() {
//		return DataSourceBuilder.create().build();
//	}
}
