package com.atguigu.atcrowdfunding.springclude;

import static org.mockito.Matchers.intThat;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyTest {
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private ProcessEngine processEngine;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private TaskService taskService;
	
	
	@Test//部署
	public void test() {
		System.out.println(processEngine);
		
		Deployment deploy = repositoryService.createDeployment().addClasspathResource("five.bpmn").deploy();
		System.out.println(deploy);
		
		
	}
	@Test//开启
	public void test1() {
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionKey("five").latestVersion().singleResult();
		Map<String, Object> map = new HashMap<>();
		map.put("day", 4);
		taskService.claim(pd.getId(), "zhangsan");
		map.put("pay", 3000);
		ProcessInstance instance = runtimeService.startProcessInstanceById(pd.getId(),map);
		
		
		
		System.out.println(instance);
	}
	
	@Test
	public void sendMail() {
	repositoryService.createDeployment().addClasspathResource("myEmail.bpmn").deploy(); 
	// 获取流程定义ID
	ProcessDefinition pd =  repositoryService
	.createProcessDefinitionQuery().processDefinitionKey("myEmail")
	                  .latestVersion().singleResult();
	    // 启动
	    ProcessInstance pi = runtimeService.startProcessInstanceById(pd.getId());
	}

	
}
