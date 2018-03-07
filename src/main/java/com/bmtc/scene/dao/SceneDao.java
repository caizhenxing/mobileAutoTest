package com.bmtc.scene.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.bmtc.common.utils.Query;
import com.bmtc.scene.domain.Scene;

/**
 * 场景管理的Dao接口
 * @author Administrator
 *
 */
@Mapper
public interface SceneDao {

	// 查询场景数据
	List<Scene> list(Map<String,Object> map);
	// 查询总记录数
	int count(Query query);
	// 通过ID查询场景
	Scene get(Long sceneId);
	// 查询指定条件的场景总记录数
	int count(Map<String,Object> map);
	// 保存场景
	int save(Scene scene);
	// 修改场景
	int update(Scene scene);
	// 删除场景
	int remove(Long sceneId);
	// 批量删除场景
	int batchRemove(Long[] sceneIds);
	
}
