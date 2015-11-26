package com.finmechanics.fmcom.xlloopspring;

import org.springframework.stereotype.Component;
import org.threeten.bp.ZonedDateTime;

import com.finmechanics.fmcom.annotations.xlserver.XlService;

////@XlService
@Component
public class SampleXlBean {

	public String whoAmI(){
		return "How do I know";
	}
	

	public String whoAmIOneMore(){
		return "I am he nor man nor woman";
	}
}
