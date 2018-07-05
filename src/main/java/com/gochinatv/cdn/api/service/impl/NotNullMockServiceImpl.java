package com.gochinatv.cdn.api.service.impl;

import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import com.gochinatv.cdn.api.entity.CdnBean;
import com.gochinatv.cdn.api.service.NotNullMockService;


@Service
public class NotNullMockServiceImpl implements NotNullMockService{

	
	/**
	 * 测试NotNull
	 * @param bean
	 * @return
	 */
	public List<CdnBean> getList(@NotNull CdnBean bean){
		System.out.println(bean);
		return null;
	}
	
}
