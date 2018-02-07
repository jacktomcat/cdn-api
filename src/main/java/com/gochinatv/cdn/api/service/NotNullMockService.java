package com.gochinatv.cdn.api.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.gochinatv.cdn.api.entity.CdnBean;

public interface NotNullMockService {

	/**
	 * 测试NotNull
	 * @param bean
	 * @return
	 */
	public List<CdnBean> getList(@NotNull CdnBean bean);
	
}
