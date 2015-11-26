package com.finmechanics.fmcom.xlloopspring;

import org.boris.xlloop.util.XLoperObjectConverter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("singleton")
public class ConverterSpringBean {
	
	private static XLoperObjectConverter instance = new XLoperObjectConverter();
	
	public XLoperObjectConverter getInstance(){
		return instance;
	}
	
}
