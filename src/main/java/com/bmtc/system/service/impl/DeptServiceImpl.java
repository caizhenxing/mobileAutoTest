package com.bmtc.system.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bmtc.common.domain.Tree;
import com.bmtc.common.utils.BuildTree;
import com.bmtc.system.dao.DeptDao;
import com.bmtc.system.dao.UserDao;
import com.bmtc.system.domain.DeptDO;
import com.bmtc.system.service.DeptService;
import com.bmtc.system.utils.GetDataByATP;
import com.bmtc.system.utils.BMTC.ArrayOfString;
import com.bmtc.system.utils.BMTC.BMTCSoap;


/**
 * 部门管理service实现类
 * @author Administrator
 *
 */
@Service
@Component
public class DeptServiceImpl implements DeptService {
	
	
	private static final Logger logger = LoggerFactory
			.getLogger(DeptServiceImpl.class);
	
	@Autowired
	private DeptDao sysDeptMapper;
	
	@Autowired
	private UserDao userMapper;
	
	/**
	 * 通过产品机构id获取产品机构对象
	 */
	@Override
	public DeptDO get(Long deptId){
		logger.info("DeptServiceImpl.get() start");
		logger.info("DeptServiceImpl.get() end");
		return sysDeptMapper.get(deptId);
	}
	
	/**
	 * 查询产品机构数据
	 */
	@Override
	public List<DeptDO> list(Map<String, Object> map){
		logger.info("DeptServiceImpl.list() start");
		logger.info("DeptServiceImpl.list() end");
		return sysDeptMapper.list(map);
	}
	
	/**
	 * 查询产品数
	 */
	@Override
	public int count(Map<String, Object> map){
		logger.info("DeptServiceImpl.count() start");
		logger.info("DeptServiceImpl.count() end");
		return sysDeptMapper.count(map);
	}
	
	/**
	 * 保存产品
	 * @throws MalformedURLException 
	 */
	@Override
	@Transactional
	@Scheduled(cron="0 0 0,12 * * ?")
	public void save() throws MalformedURLException{
		logger.info("DeptServiceImpl.save() start");
		//清除产品机构临时数据表信息
		sysDeptMapper.removeTemp();
		//从ATP获取产品机构信息
		BMTCSoap soap = GetDataByATP.getData();
		ArrayOfString allProducts = soap.getAllProducts();
		List<String> products = allProducts.getString();
		for (String obj :products) {
			//解析获取信息
			String[] organization = obj.split("\\|");
			DeptDO sysDept = new DeptDO();
			Long id = Long.parseLong(organization[0]);
			DeptDO deptDO = get(id);
			//判断数据库表中是否存在该信息
			if (deptDO == null || deptDO.getDeptId() != id || !organization[1].equals(deptDO.getName())) {
				sysDept.setDeptId(id);
				sysDept.setName(organization[1]);
				sysDept.setParentId(Long.parseLong(organization[2]));
				sysDept.setOrganLevel(Integer.parseInt(organization[3]));
				sysDeptMapper.saveTemp(sysDept);
			}else{
				sysDeptMapper.saveTemp(deptDO);
			}
		}
		//清除产品机构数据库表
		synchronized (this) {
			sysDeptMapper.remove();
		}
		//将临时表信息copy到产品机构表
		sysDeptMapper.copyDate();
		
		logger.info("DeptServiceImpl.save() end");
	}
	
	
	/**
	 * 更新产品机构
	 */
	@Override
	public int update(DeptDO sysDept){
		logger.info("DeptServiceImpl.update() start");
		logger.info("DeptServiceImpl.update() end");
		return sysDeptMapper.update(sysDept);
	}
	
	/**
	 * //封装产品机构树形图数据(不包含产品)
	 */
	@Override
	public Tree<DeptDO> getTrees() {
		logger.info("DeptServiceImpl.getTrees() start");
		List<Tree<DeptDO>> trees = new ArrayList<Tree<DeptDO>>();
		List<DeptDO> sysDepts = sysDeptMapper.list(new HashMap<String,Object>(16));
		for (DeptDO sysDept : sysDepts) {
			if (sysDept.getOrganLevel() != 3 && sysDept.getOrganLevel() != null) {
				Tree<DeptDO> tree = new Tree<DeptDO>();
				tree.setId(sysDept.getDeptId().toString());
				tree.setParentId(sysDept.getParentId().toString());
				tree.setText(sysDept.getName());
				Map<String, Object> state = new HashMap<>(16);
				state.put("closed", true);
				tree.setState(state);
				trees.add(tree);
			}
			
		}
		// 默认顶级菜单为0，根据数据库实际情况调整
		Tree<DeptDO> t = BuildTree.build(trees);
		t.setText("中国银行软件中心");
		logger.info("DeptServiceImpl.getTrees() end");
		return t;
	}
	
	/**
	 * 查询部门以及此部门的下级部门
	 */
	@Override
	public boolean checkDeptHasUser(Integer deptId) {
		logger.info("DeptServiceImpl.checkDeptHasUser() start");
		int result = sysDeptMapper.getDeptUserNumber(deptId);
		logger.info("DeptServiceImpl.checkDeptHasUser() end");
		return result==0?true:false;
	}
	
	/**
	 * 封装产品机构树形图数据(包含产品)
	 */
	@Override
	public Tree<DeptDO> getTree() {
		logger.info("DeptServiceImpl.getTree() start");
		List<Tree<DeptDO>> trees = new ArrayList<Tree<DeptDO>>();
		List<DeptDO> sysDepts = sysDeptMapper.list(new HashMap<String,Object>(16));
		for (DeptDO sysDept : sysDepts) {
				Tree<DeptDO> tree = new Tree<DeptDO>();
				tree.setId(sysDept.getDeptId().toString());
				tree.setParentId(sysDept.getParentId().toString());
				tree.setText(sysDept.getName());
				Map<String, Object> state = new HashMap<>(16);
				state.put("closed", true);
				tree.setState(state);
				trees.add(tree);
		}
		// 默认顶级菜单为0，根据数据库实际情况调整
		Tree<DeptDO> t = BuildTree.build(trees);
		t.setText("中国银行软件中心");
		logger.info("DeptServiceImpl.getTree() end");
		return t;
	}
	
	/**
	 * 通过产品机构名称获取产品机构对象
	 */
	@Override
	public DeptDO getName(String name) {
		logger.info("DeptServiceImpl.getName() start");
		logger.info("DeptServiceImpl.getName() end");
		return sysDeptMapper.getName(name);
	}


}
