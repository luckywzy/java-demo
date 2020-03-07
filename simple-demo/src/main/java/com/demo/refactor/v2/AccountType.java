package com.demo.refactor.v2;

/**
 * @Author: wangzongyu
 * @Date: 2019/11/27 23:12
 */
public class AccountType {
	public boolean isPremium() {
		//logic
		return false;
	}

	public double overDraftCharge(int daysOverDraw){
		if(isPremium()){
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
}
