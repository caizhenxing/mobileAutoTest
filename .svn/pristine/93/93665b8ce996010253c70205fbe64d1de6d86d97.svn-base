package com.bmtc.system.service.impl;

import java.net.MalformedURLException;
import java.util.*;

import com.bmtc.common.domain.Tree;
import com.bmtc.common.utils.BuildTree;
import com.bmtc.common.utils.MD5Utils;
import com.bmtc.system.dao.DeptDao;
import com.bmtc.system.dao.UserDao;
import com.bmtc.system.dao.UserProductDao;
import com.bmtc.system.dao.UserRoleDao;
import com.bmtc.system.domain.DeptDO;
import com.bmtc.system.domain.UserDO;
import com.bmtc.system.domain.UserProductDO;
import com.bmtc.system.domain.UserRoleDO;
import com.bmtc.system.service.DeptService;
import com.bmtc.system.service.UserService;
import com.bmtc.system.utils.GetDataByATP;
import com.bmtc.system.utils.BMTC.ArrayOfInt;
import com.bmtc.system.utils.BMTC.ArrayOfOrganization;
import com.bmtc.system.utils.BMTC.ArrayOfUserInfo;
import com.bmtc.system.utils.BMTC.BMTCSoap;
import com.bmtc.system.utils.BMTC.Organization;
import com.bmtc.system.utils.BMTC.UserInfo;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户管理service实现类
 * 
 * @author nienannan
 *
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory
			.getLogger(UserServiceImpl.class);

	@Autowired
	UserDao userMapper;
	@Autowired
	UserRoleDao userRoleMapper;
	@Autowired
	DeptDao deptMapper;
	@Autowired
	UserProductDao userProductMapper;

	/**
	 * 通过用户id获取用户对象
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public UserDO get(Long id) {
		logger.info("UserServiceImpl.get() start");
		List<Long> roleIds = userRoleMapper.listRoleId(id);
		UserDO user = userMapper.get(id);
		// user.setDeptName(deptMapper.get(user.getDeptId()).getName());
		user.setRoleIds(roleIds);
		logger.info("UserServiceImpl.get() start");
		return user;
	}

	/**
	 * 查询用户数据
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public List<UserDO> list(Map<String, Object> map) {
		logger.info("UserServiceImpl.list() start");
		logger.info("UserServiceImpl.list() end");
		return userMapper.list(map);
	}

	/**
	 * 统计用户数
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public int count(Map<String, Object> map) {
		logger.info("UserServiceImpl.count() start");
		logger.info("UserServiceImpl.count() end");
		return userMapper.count(map);
	}

	/**
	 * 保存用户数据
	 * 
	 * @param user
	 * @return
	 * @throws MalformedURLException
	 */
	@Transactional
	@Override
	public int save(UserDO user) throws MalformedURLException {
		logger.info("UserServiceImpl.save() start");
		// 将用户设置为待审批状态
		user.setStatus(2);
		//从页面获取产品id并将其转化为list
		user.getDeptIds();
		String[] deptIds = user.getDeptIds().split(",");
		int[] deptIDs = new int[deptIds.length];
		for (int i = 0; i < deptIds.length; i++) {
			deptIDs[i] = Integer.parseInt(deptIds[i]);
		}
		List<Integer> deptIdList = Ints.asList(deptIDs);
		ArrayOfInt arrayOfInt = new ArrayOfInt();
		//判断所选产品是否是同一部门下的同一团队的产品
		if (deptIdList != null) {
			DeptDO deptDO = deptMapper.get(Long.valueOf(deptIdList.get(0)));
			Integer firstTeamId = Integer.valueOf(deptDO.getParentId()
					.toString());
			DeptDO firstDepartment = deptMapper.get(Long.valueOf(firstTeamId));
			Integer firstDepartmentId = Integer.valueOf(firstDepartment
					.getParentId().toString());
			for (Integer deptId : deptIdList) {
				arrayOfInt.getInt().add(deptId);
				DeptDO team = deptMapper.get(Long.valueOf(deptId));
				Integer teamId = Integer.valueOf(team.getParentId().toString());
				DeptDO department = deptMapper.get(Long.valueOf(teamId));
				Integer departmentId = Integer.valueOf(department.getParentId()
						.toString());
				if (!teamId.equals(firstTeamId) || !departmentId.equals(firstDepartmentId)) {
					return -1;
				}
			}
			//调取接口在ATP平台注册用户
			BMTCSoap soap = GetDataByATP.getData();
			String res = null;
			res = soap.createATPUser(user.getName(), user.getUsername(),
					user.getPassword(), firstDepartmentId, firstTeamId,
					arrayOfInt);
			logger.info("================>" + res);
			if ("新增失败".equals(res)) {
				return -2;
			}
		}
		//将用户注册信息保存到本地数据库
		int count = userMapper.save(user);
		Long userId = user.getUserId();
		userProductMapper.removeByUserId(userId);
		List<UserProductDO> userProductlist = new ArrayList<>();
		for (Integer deptId : deptIdList) {
			UserProductDO userProduct = new UserProductDO();
			userProduct.setUserId(userId);
			userProduct.setDeptId(Long.valueOf(deptId));
			userProductlist.add(userProduct);
		}
		if (userProductlist.size() > 0) {
			userProductMapper.batchSave(userProductlist);
		}
		logger.info("UserServiceImpl.save() end");
		return count;
	}

	/**
	 * 更新用户数据
	 * 
	 * @param user
	 * @return
	 */
	@Override
	public int update(UserDO user) {
		logger.info("UserServiceImpl.update() start");
		int r = userMapper.update(user);
		//将用户角色信息进行保存
		Long userId = user.getUserId();
		List<Long> roles = user.getRoleIds();
		userRoleMapper.removeByUserId(userId);
		List<UserRoleDO> list = new ArrayList<>();
		for (Long roleId : roles) {
			UserRoleDO ur = new UserRoleDO();
			ur.setUserId(userId);
			ur.setRoleId(roleId);
			list.add(ur);
		}
		if (list.size() > 0) {
			userRoleMapper.batchSave(list);
		}
		//从页面获取用户所属产品信息并将其转化为list
		String[] deptIds = user.getDeptIds().split(",");
		int[] deptIDs = new int[deptIds.length];
		for (int i = 0; i < deptIds.length; i++) {
			deptIDs[i] = Integer.parseInt(deptIds[i]);
		}
		List<Integer> deptIdList = Ints.asList(deptIDs);
		//判断是否是同一部门下的同一团队的产品
		if (deptIdList != null) {
			DeptDO deptDO = deptMapper.get(Long.valueOf(deptIdList.get(0)));
			Integer firstTeamId = Integer.valueOf(deptDO.getParentId()
					.toString());
			DeptDO firstDepartment = deptMapper.get(Long.valueOf(firstTeamId));
			Integer firstDepartmentId = Integer.valueOf(firstDepartment
					.getParentId().toString());
			for (Integer deptId : deptIdList) {
				DeptDO team = deptMapper.get(Long.valueOf(deptId));
				Integer teamId = Integer.valueOf(team.getParentId().toString());
				DeptDO department = deptMapper.get(Long.valueOf(teamId));
				Integer departmentId = Integer.valueOf(department.getParentId()
						.toString());
				if (!teamId.equals(firstTeamId)
						|| !departmentId.equals(firstDepartmentId)) {
					return -1;
				}
			}
		}
		//将产品信息保存到本地数据库
		userProductMapper.removeByUserId(userId);
		List<UserProductDO> userProductlist = new ArrayList<>();
		for (Integer deptId : deptIdList) {
			UserProductDO userProduct = new UserProductDO();
			userProduct.setUserId(userId);
			userProduct.setDeptId(Long.valueOf(deptId));
			userProductlist.add(userProduct);
		}
		if (userProductlist.size() > 0) {
			userProductMapper.batchSave(userProductlist);
		}
		logger.info("UserServiceImpl.update() end");
		return r;
	}

	/**
	 * 通过用户id移除用户
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	public int remove(Long userId) {
		logger.info("UserServiceImpl.remove() start");
		userRoleMapper.removeByUserId(userId);
		logger.info("UserServiceImpl.remove() end");
		return userMapper.remove(userId);
	}

	/**
	 * 判断用户是否存在
	 * 
	 * @param params
	 * @return
	 * @throws MalformedURLException
	 */
	@Override
	public boolean exist(Map<String, Object> params)
			throws MalformedURLException {
		logger.info("UserServiceImpl.exist() start");
		//从ATP获取用户信息
		BMTCSoap soap = GetDataByATP.getData();
		ArrayOfUserInfo userLists = soap.getUserLists();
		List<UserInfo> userInfos = userLists.getUserInfo();
		//判断该用户在ATP中存在
		for (UserInfo userInfo : userInfos) {
			//判断该用户在ATP中存在
			if (userInfo.getLoginName().equals(params.get("username"))
					//判断该用户在本地数据库中存在
					&& userMapper.list(params).size() <= 0) {
				//将用户信息封装存入本地数据库
				UserDO user = new UserDO();
				user.setUsername(userInfo.getLoginName());
				user.setName(userInfo.getUserName());
				user.setPassword(userInfo.getPassword());
				user.setStatus(2);
				userMapper.save(user);
				//从ATP获取用户所属产品信息并将其存入本地
				ArrayOfOrganization userProducts = soap
						.getUserProducts(userInfo.getID());
				List<Organization> organizations = userProducts
						.getOrganization();
				List<UserProductDO> list = new ArrayList<>();
				for (Organization organization : organizations) {
					UserProductDO upd = new UserProductDO();
					upd.setDeptId((long) organization.getID());
					upd.setUserId(user.getUserId());
					list.add(upd);
				}
				if (list.size() > 0) {
					userProductMapper.batchSave(list);
				}
			}
		}
		//判断该用户在本地数据库中是否存在
		boolean exist;
		exist = userMapper.list(params).size() > 0;
		logger.info("UserServiceImpl.exist() end");
		return exist;
	}

	/**
	 * 重置密码
	 * 
	 * @param userVO
	 * @param userDO
	 * @return
	 * @throws Exception
	 */
	@Override
	public int resetPwd(UserDO user) throws Exception {
		logger.info("UserServiceImpl.resetPwd() start");
		user.setUsername(get(user.getUserId()).getUsername());
		user.setPassword(MD5Utils.encryptPureMD5(user.getPassword()));
		logger.info("UserServiceImpl.resetPwd() end");
		return userMapper.update(user);
	}

	/**
	 * 通过用户id批量移除用户
	 * 
	 * @param userIds
	 * @return
	 */
	@Transactional
	@Override
	public int batchremove(Long[] userIds) {
		logger.info("UserServiceImpl.batchremove() start");
		int count = userMapper.batchRemove(userIds);
		userRoleMapper.batchRemoveByUserId(userIds);
		logger.info("UserServiceImpl.batchremove() end");
		return count;
	}

	/**
	 * 产品机构树形图和用户列表相关联展示的数据组装
	 * 
	 * @return
	 */
	@Override
	public Tree<DeptDO> getTree() {
		logger.info("UserServiceImpl.getTree() start");
		List<Tree<DeptDO>> trees = new ArrayList<Tree<DeptDO>>();
		List<DeptDO> depts = deptMapper.list(new HashMap<String, Object>(16));
		Long[] pDepts = deptMapper.listParentDept();
		Long[] uDepts = listAllDept();
		Long[] allDepts = (Long[]) ArrayUtils.addAll(pDepts, uDepts);
		for (DeptDO dept : depts) {
			if (!ArrayUtils.contains(allDepts, dept.getDeptId())) {
				continue;
			}
			Tree<DeptDO> tree = new Tree<DeptDO>();
			tree.setId(dept.getDeptId().toString());
			tree.setParentId(dept.getParentId().toString());
			tree.setText(dept.getName());
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", true);
			state.put("mType", "dept");
			tree.setState(state);
			trees.add(tree);
		}
		List<UserDO> users = userMapper.list(new HashMap<String, Object>(16));
		for (UserDO user : users) {
			Tree<DeptDO> tree = new Tree<DeptDO>();
			tree.setId(user.getUserId().toString());
			tree.setParentId(user.getDeptIds().toString());
			tree.setText(user.getName());
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", true);
			state.put("mType", "user");
			tree.setState(state);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<DeptDO> t = BuildTree.build(trees);
		logger.info("UserServiceImpl.getTree() end");
		return t;
	}

	/**
	 * 更新个人资料
	 * 
	 * @param userDO
	 * @return
	 */
	@Override
	public int updatePersonal(UserDO userDO) {
		logger.info("UserServiceImpl.updatePersonal() start");
		logger.info("UserServiceImpl.updatePersonal() end");
		return userMapper.update(userDO);
	}

	/**
	 * 通过用户id获取所关联的所有产品对象
	 * 
	 * @param userId
	 */
	@Override
	public List<DeptDO> getProductsByUserId(Long userId) {
		logger.info("UserServiceImpl.getProductsByUserId() start");
		List<Long> deptIds = getDeptIdByUserId(userId);
		List<DeptDO> products = new ArrayList<DeptDO>();
		for (Long deptId : deptIds) {
			DeptDO deptDO = deptMapper.get(deptId);
			products.add(deptDO);
		}
		logger.info("UserServiceImpl.getProductsByUserId() end");
		return products;
	}

	/**
	 * 通过用户id获取所关联的所有产品id
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	public List<Long> getDeptIdByUserId(Long userId) {
		logger.info("UserServiceImpl.getDeptIdByUserId() start");
		List<Long> deptIds = userProductMapper.getDeptIdByUserId(userId);
		logger.info("UserServiceImpl.getDeptIdByUserId() end");
		return deptIds;
	}

	/**
	 * 查询用户对应的所有产品id
	 */
	@Override
	public Long[] listAllDept() {
		logger.info("UserServiceImpl.listAllDept() start");
		Long[] listAllDept = userProductMapper.listAllDept();
		logger.info("UserServiceImpl.listAllDept() end");
		return listAllDept;
	}

}
