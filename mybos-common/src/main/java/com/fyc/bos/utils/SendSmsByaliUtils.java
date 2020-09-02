package com.fyc.bos.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

public class SendSmsByaliUtils {
	
	public static Boolean sendSms(String phoneNum,String signName,String templateCode, String code) {
		DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4FrejYL6gM9mTjN2qcRT", "wWOIr97OPNVWXQEioShSfldYYcs7uz");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNum);
       // request.putQueryParameter("SignName", "德馨人力");
        request.putQueryParameter("SignName", signName);
       // request.putQueryParameter("TemplateCode", "SMS_176531295");
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", "{\"code\":"+code+"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            String data = response.getData();
            System.out.println(data);
            JSONObject dataObeject = JSON.parseObject(data);
            
            System.out.println(dataObeject);
            if ( dataObeject.getString("Code")!=null&& dataObeject.getString("Code").equals("OK")) {
				return true;
			}
            System.out.println(data);
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        
        return false;
    }

    public static void main(String[] args) {
        sendSms("13750015150", "德馨人力","SMS_176531295","1234");
    }

	
}
