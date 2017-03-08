package com.gochinatv.cdn.api.service;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.gochinatv.cdn.api.service.impl.CdnEncryptionServiceImpl;

import static org.mockito.Mockito.*;

import java.util.List;

import org.mockito.InjectMocks;

public class CdnEncryptionServiceTest {
   
	@Mock
	private CdnEncryptionService cdnEncryptionService;
	
	@InjectMocks
	private CdnEncryptionServiceImpl cdnEncryptionService1;
	
	@BeforeClass
	public void initMocks() {  
	    MockitoAnnotations.initMocks(this);  
	}
	
	@Test
	public void testValidate(){
		try {
			when(cdnEncryptionService.getCdnKey("1")).thenReturn(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInit(){
		//创建mock对象，参数可以是类，也可以是接口  
        List<String> list = mock(List.class);  
        //设置方法的预期返回值  
        when(list.get(0)).thenReturn("helloworld");  
        //String result = list.get(0);  
        //验证方法调用(是否调用了get(0))  
        verify(list).get(0);  
        //junit测试  
        Assert.assertEquals("helloworld", "");  
		
	}
	
}
