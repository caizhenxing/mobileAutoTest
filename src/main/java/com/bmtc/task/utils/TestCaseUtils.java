package com.bmtc.task.utils;

import java.util.ArrayList;
import java.util.List;

import com.bmtc.device.domain.TestCase;
import com.bmtc.device.domain.TestCaseTable;

/**
 * 解析前段接收的路径集并封装为testcase的工具类
 * @author Administrator
 *
 */
public class TestCaseUtils {

	/**
	 * 通过前端参数BMTCTask bMTCTask获得List<TestCase>的方法
	 * 
	 * @param BMTCTask bMTCTask
	 * @return List<TestCase>
	 */
	public static List<TestCase> getTestCases(String testSuiteCaseNames) {
		// 获得测试任务关联的脚本的测试套路径，替换路径中的“\”为“/”
		testSuiteCaseNames = testSuiteCaseNames.replace("\\", "/");
		// 切分
		String[] testSuitePaths = testSuiteCaseNames.split(",");
		// 创建集合存储TestCase
		List<TestCase> testCases = new ArrayList<TestCase>();
		// 遍历获得关联的脚本信息
		for (String testSuitePath : testSuitePaths) {
			
			/**
			 * 判断:不包含“.txt”,下次循环
			 */
			if(!testSuitePath.contains(".txt")){
				continue;
			}
			/**
			 * 判断:包含“.txt”
			 * 1.以".txt"结尾
			 * 2.不是以".txt"结尾
			 */
			if(testSuitePath.endsWith(".txt")){// 以".txt"结尾
				// 判断是否已经存储到List中
				boolean flag = false;
				for (TestCase testCase : testCases) {
					// 判断这条测试套是否已经存入List<TestCase> testCases
					if (testCase.getTestSuite().equals(testSuitePath)) {
						flag = true;
					}
				}
				// 如果没有,存储
				if(!flag) {
					// 封装testCase
					TestCase testCase = getTestCase(testSuitePaths,testSuitePath);
					testCases.add(testCase);
				}
			} else {// 不是以".txt"结尾
				// 截取所属测试套路径
				String tp = testSuitePath.substring(0,testSuitePath.lastIndexOf("/"));
				if(!tp.endsWith(".txt")){
					tp = tp.substring(0,tp.lastIndexOf("/"));
				}
				// 判断是否已经存储到List中
				boolean flag = false;
				for (TestCase testCase : testCases) {
					// 判断这条测试套是否已经存入List<TestCase> testCases
					if (testCase.getTestSuite().equals(tp)) {
						flag = true;
					}
				}
				// 如果没有,存储
				if(!flag) {
					// 封装testCase
					TestCase testCase = getTestCase(testSuitePaths,tp);
					testCases.add(testCase);
				}
			}
		}
		return testCases;
	}
	/**
	 * 封装testCase
	 * @param String[] testSuitePaths,String testSuitePath
	 * @return TestCase
	 */
	public static TestCase getTestCase(String[] testSuitePaths,String testSuitePath) {
		// 创建testCase对象
		TestCase testCase = new TestCase();
		// 赋值testSuitePath
		testCase.setTestSuite(testSuitePath);
		// 获取caseName
		List<TestCaseTable> caseName = new ArrayList<TestCaseTable>();
		// 遍历路径
		for (String path : testSuitePaths) {
			// 如果路径不包含测试套路径，继续下个循环
			if(!path.contains(testSuitePath) || path.equals(testSuitePath)){
				continue;
			} else {// 如果路径包含测试套路径,组装TestCaseTable
				// 截取caseName/caseNum或者caseName
				String testCaseName = path.replace(testSuitePath+"/", "");
				// 判断区分是caseName/caseNum还是caseName
				if(!testCaseName.contains("/")){// 是caseName
					// 创建TestCaseTable
					TestCaseTable testCaseTable = new TestCaseTable();
					// 赋值caseName
					testCaseTable.setCaseName(testCaseName);
					// 创建List<String> testCaseNum
					List<String> testCaseNum = new ArrayList<String>();
					// 遍历获取CaseNum
					for (String p : testSuitePaths) {
						// 判断p是否包含path
						if(p.contains(path) && !p.equals(path)){
							// 截取caseNum
							String caseNum = p.replace(path+"/", "");
							// 赋值caseNum
							testCaseNum.add(caseNum);
						}
					}
					testCaseTable.setTestCaseNum(testCaseNum);
					caseName.add(testCaseTable);
				}
			}
		}
		testCase.setCaseName(caseName);
		return testCase;
	}
	
}
