package org.yugh.test.mvc.common;

import org.crazycake.jdbcTemplateTool.JdbcTemplateProxy;
import org.crazycake.jdbcTemplateTool.JdbcTemplateTool;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
public class Running {

	public static final ApplicationContext CONTEXT = new ClassPathXmlApplicationContext("applicationContext.xml");
	//事务获取
	static final PlatformTransactionManager MANAGER = (PlatformTransactionManager)CONTEXT.getBean("transactionManager");
	public static final TransactionTemplate TEMPLATE  = new TransactionTemplate(MANAGER);
	public static final JdbcTemplate JDBC_TEMPLATE = (JdbcTemplate)CONTEXT.getBean("jdbcTemplate");
	public static final JdbcTemplateTool TEMPLATE_TOOL = (JdbcTemplateTool)CONTEXT.getBean("jdbcTemplateTool");
	public static final JdbcTemplateProxy TEMPLATE_PROXY = (JdbcTemplateProxy)CONTEXT.getBean("jdbcTemplateProxy");
	
	public static void main(String[] args) {

	}
}
