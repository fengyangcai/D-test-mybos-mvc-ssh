package com.fyc.bos.utils;

import java.util.Random;

public class YanZhengMa {
	
	final static Random d =new Random();
	//4位
	public  static String  getCode4() {
		String code = "";
		while (code.length()<4) {
			int num =d.nextInt(10);
			code +=num+"";
		}
			return code;
	}
    //4位
    public  static String  getCode6() {
        String code = "";
        while (code.length()<6) {
            int num =d.nextInt(10);
            code +=num+"";
        }
        return code;
    }

}
