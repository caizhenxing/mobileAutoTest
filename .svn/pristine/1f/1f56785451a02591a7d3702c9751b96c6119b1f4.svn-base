package com.bmtc.system.service.impl;

import java.util.*;

import com.bmtc.system.dao.ConfigDao;
import com.bmtc.system.domain.ConfigDO;
import com.bmtc.system.domain.ConfigInfoDO;
import com.bmtc.system.service.ConfigService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统配置service实现类
 * @author lpf7161
 */
@Transactional
@Service
public class ConfigServiceImpl implements ConfigService {
	
	private static final Logger logger = LoggerFactory
			.getLogger(ConfigServiceImpl.class);
	
	@Autowired
	ConfigDao configDao;
	
	/**
	 * 保存系统配置数据
	 * @param configInfo
	 * @return count
	 */
	@Override
	public int save(ConfigDO configInfo) {
		logger.info("ConfigServiceImpl.save() start");
		int count = configDao.save(configInfo);
		logger.info("ConfigServiceImpl.save() end");
		return count;
	}

	/**
	 * 更新系统配置数据
	 * @param configInfo
	 * @return count
	 */
	@Override
	public int update(ConfigDO config) {
		logger.info("ConfigServiceImpl.update() start");
		int count = configDao.update(config);
		logger.info("ConfigServiceImpl.update() end");
		return count;
	}

	/**
	 * 通过svn服务器IP地址查询svn相关的信息
	 * @param svnServer
	 * @return configInfo
	 */
	@Override
	public ConfigInfoDO getConfigInfo(String svnServer) {		
		logger.info("ConfigServiceImpl.getConfigInfo() start");
		ConfigInfoDO configInfo = configDao.getConfigInfo(svnServer);
		logger.info("ConfigServiceImpl.getConfigInfo() end");
		return configInfo;
	}

	/**
	 * 通过系统配置id批量移除系统配置信息
	 * @param configIds
	 * @return count
	 */
	@Override
	public int batchRemove(Long[] configIds) {
		logger.info("ConfigServiceImpl.batchremove() start");
		int count = configDao.batchRemove(configIds);
		logger.info("ConfigServiceImpl.batchremove() end");
		return count;
	}

	/**
	 * 通过系统配置id获取系统配置对象
	 * @param id
	 * @return configInfo
	 */
	@Override
	public ConfigInfoDO get(Long id) {
		logger.info("ConfigServiceImpl.get() start");
		ConfigInfoDO configInfo = configDao.get(id);
		logger.info("ConfigServiceImpl.get() end");
		return configInfo;
	}

	/**
	 * 查询系统配置信息
	 * @param map
	 * @return List<ConfigDO>
	 */
	@Override
	public List<ConfigInfoDO> list(Map<String, Object> map) {
		logger.info("ConfigServiceImpl.list() start");
		logger.info("ConfigServiceImpl.list() end");
		return configDao.list(map);
	}

	/**
	 * 统计系统配置信息总数
	 * @param map
	 * @return count
	 */
	@Override
	public int count(Map<String, Object> map) {
		logger.info("ConfigServiceImpl.count() start");
		logger.info("ConfigServiceImpl.count() end");
		return configDao.count(map);
	}

	/**
	 * 通过系统配置id移除系统配置信息
	 * @param configId
	 * @return count
	 */
	@Override
	public int remove(Long configId) {
		logger.info("ConfigServiceImpl.remove() start");
		logger.info("ConfigServiceImpl.remove() end");
		return configDao.remove(configId);
	}

}
