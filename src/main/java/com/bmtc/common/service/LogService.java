package com.bmtc.common.service;

import org.springframework.stereotype.Service;

import com.bmtc.common.domain.LogDO;
import com.bmtc.common.domain.PageDO;
import com.bmtc.common.utils.Query;
@Service
public interface LogService {
	PageDO<LogDO> queryList(Query query);
	int remove(Long id);
	int batchRemove(Long[] ids);
}
