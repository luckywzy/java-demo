package com.demo.refactor.v1;

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

	double overDraftCharge(){
		if(type.isPremium()){
			double result = 10;
			if(daysOverDraw > 7){
				result += (daysOverDraw - 7) * 0.85;
				return result;
			}
		}else {
			return daysOverDraw * 1.75;
		}
		//add
		return 0.0;
	}

	double bankCharge(){
		double result = 4.5;
		if(daysOverDraw > 0){
			result += overDraftCharge();
		}
		return result;
	}
}
