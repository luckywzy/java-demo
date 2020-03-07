package com.demo.refactor.v2;

import com.demo.refactor.v2.AccountType;
import lombok.Getter;

/**
 * @Author: wangzongyu
 * @Date: 2019/11/27 23:11
 */
public class Account {
	@Getter
	private AccountType type;
	@Getter
	private int daysOverDraw;

	double bankCharge(){
		double result = 4.5;
		if(daysOverDraw > 0){
			//result += type.overDraftCharge(this);
			result += type.overDraftCharge(daysOverDraw);
		}
		return result;
	}
}
