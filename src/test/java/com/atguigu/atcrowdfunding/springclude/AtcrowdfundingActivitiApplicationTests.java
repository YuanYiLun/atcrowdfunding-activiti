package com.atguigu.atcrowdfunding.springclude;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AtcrowdfundingActivitiApplicationTests {
	
	
	@Autowired
	private ProcessEngine processEngine ;
	@Autowired
	private RepositoryService repositoryService ;
	
	
	@Test
	public void test1() {
		Deployment deploy = repositoryService.createDeployment().addClasspathResource("MyProcess.bpmn").deploy();
		System.out.println(deploy);
	}
	
	@Test
	public void test2() {
		
		Deployment deploy = repositoryService.createDeployment().addClasspathResource("WorkTime.bpmn").deploy();
		
		System.out.println(deploy);
	}
	
	 
	@Test
	public void contextLoads() {
		System.out.println( processEngine );
		System.out.println(repositoryService);
	}
	
	@Test
	public void getPage() {
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		repositoryService.createDeploymentQuery();
		Integer pagesize = 5;
		Integer pageno = 1;
		Integer pageindex = (pageno-1)*pagesize;
		
		List<ProcessDefinition> list = query
				.orderByProcessDefinitionVersion().desc().listPage(pageindex, pagesize);
		
		for (ProcessDefinition dp : list) {
			System.out.println(dp.getId()+"-"+dp.getName()+"-"+dp.getKey()+"-"+dp.getVersion());
		}
		
	}
	
	@Test
	public void latestVersion() {
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		Integer pagesize = 5;
		Integer pageno = 1;
		Integer pageindex = (pageno-1)*pagesize;
		
		ProcessDefinition dp = query.processDefinitionKey("workTime").latestVersion().singleResult();
		System.out.println(dp.getId()+"-"+dp.getName()+"-"+dp.getKey()+"-"+dp.getVersion());
		
		/*for (ProcessDefinition dp : list) {
			System.out.println(dp.getId()+"-"+dp.getName()+"-"+dp.getKey()+"-"+dp.getVersion());
		}*/
		
	}
	
	@Test
	public void definition() {
		DeploymentBuilder builder = repositoryService.createDeployment().addClasspathResource("third.bpmn");
		Deployment deployment = builder.deploy();
		System.out.println(deployment);
		
		
	}
	@Autowired
	private TaskService taskService;
	@Autowired
	private RuntimeService runtimeService;
	@Test
	public void start() {
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey("third").latestVersion().singleResult();
		ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());
		
	}
	
	@Test
	public void complete() {
		TaskQuery query = taskService.createTaskQuery();
		List<Task> list = query.taskAssignee("zhangsan").list();
		System.out.println(list);
		for (Task task : list) {
			taskService.complete(task.getId());
		}
	}




}
