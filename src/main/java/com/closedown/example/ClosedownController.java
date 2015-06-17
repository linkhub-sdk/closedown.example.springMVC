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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
* 휴폐업조회 CloseDownService API 예재
*/
@Controller
public class ClosedownController {
	
	@Autowired
	private CloseDownChecker closedownChecker;
		
	@Value("#{EXAMPLE_CONFIG.SecretKey}")
	private String testUserID;
	@Value("#{EXAMPLE_CONFIG.LinkID}")
	private String testLinkID;
	
	//잔여포인트 확인
	@RequestMapping(value = "getBalance", method = RequestMethod.GET)
	public String getBalance(Model m) throws CloseDownException {
		try {
			double remainPoint = closedownChecker.getBalance();
			
			m.addAttribute("Result",remainPoint);
			
		} catch (CloseDownException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		return "result";
	}
	
	//조회단가 확인
	@RequestMapping(value = "getUnitCost", method = RequestMethod.GET)
	public String getPartnerBalance(Model m) throws CloseDownException {
		
		try {
			float unitCost = closedownChecker.getUnitCost();
			
			m.addAttribute("Result",unitCost);
			
		} catch (CloseDownException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
}
