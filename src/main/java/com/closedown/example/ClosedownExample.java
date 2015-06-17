/*
 * Copyright 2006-2014 innopost.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0.txt
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.closedown.example;

import kr.co.linkhub.closedown.api.CloseDownChecker;
import kr.co.linkhub.closedown.api.CloseDownException;
import kr.co.linkhub.closedown.api.CorpState;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 휴폐업조회 CloseDownService API 예제
 */

@Controller
@RequestMapping("CloseDownService")
public class ClosedownExample {
	@Autowired
	private CloseDownChecker closedownChecker;
		
	@Value("#{EXAMPLE_CONFIG.SecretKey}")
	private String testUserID;
	@Value("#{EXAMPLE_CONFIG.LinkID}")
	private String testLinkID;
	
	@RequestMapping(value="checkCorpNum", method = RequestMethod.GET)
	public String checkCorpNum(@RequestParam(required=false) String CorpNum, Model m) throws CloseDownException { 
		
		if(CorpNum !=null && CorpNum != ""){
			
			try {
				CorpState corpState = closedownChecker.CheckCorpNum(CorpNum);
				
				m.addAttribute("CorpState", corpState);
				
			} catch(CloseDownException e){
				m.addAttribute("Exception", e);
				return "exception";
			}
			
		}
		return "checkCorpNum";
	}
	
	@RequestMapping(value="checkCorpNums", method = RequestMethod.GET)
	public String checkCorpNums(Model m) throws CloseDownException {
		
		//사업자번호 리스트, 최대 1000건
		String[] CorpNumList = new String[] {"1234567890", "4352343543", "4108600477", "1111111111"};
		
		try {
			
			CorpState[] corpStates = closedownChecker.CheckCorpNum(CorpNumList);
			
			m.addAttribute("CorpStates", corpStates);
			
		} catch(CloseDownException e){
			m.addAttribute("Exception", e);
			return "exception";
		}
			
		return "checkCorpNums";
	}
}



