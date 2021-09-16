package com.example.studentmanagement;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.PropertiesRealm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.security.sasl.AuthenticationException;

@ControllerAdvice
@SpringBootApplication
public class StudentManagementApplication {

	//方便debut
	private static Logger log = LoggerFactory.getLogger(StudentManagementApplication.class);

	@ExceptionHandler(AuthenticationException.class) //捕获没有权限的异常反馈给客户，instead of 500 error
	@ResponseStatus(HttpStatus.FORBIDDEN) //反馈状态给前端
	public void handlerException(AuthenticationException exception){
		log.debug("{} was thrown",exception.getClass(),exception);

	}
	@Bean
	public Realm realm(){
		//uses 'classpath:shiro-users.properties' by default
		PropertiesRealm realm = new PropertiesRealm();
		return realm;
	}
	@Bean
	ShiroFilterChainDefinition shiroFilterChainDefinition(){
		DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
		chainDefinition.addPathDefinition("/**","authcBasic");
		return chainDefinition;
	}
	@Bean //解决shiro导致requestmapping404失效
	public static DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
		DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		defaultAdvisorAutoProxyCreator.setUsePrefix(true);
		return defaultAdvisorAutoProxyCreator;
	}



	public static void main(String[] args) {
		SpringApplication.run(StudentManagementApplication.class, args);
	}

}
