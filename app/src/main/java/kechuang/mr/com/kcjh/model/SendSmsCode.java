package kechuang.mr.com.kcjh.model;

import com.mr.utils.data.BeanUtils;

import org.json.JSONObject;

import java.util.Map;

import kechuang.mr.com.kcjh.base.BaseModel;

/**
 * 短信验证码 SendSmsCode
 */
public class SendSmsCode extends BaseModel<SendSmsCode.Request, SendSmsCode.Response> {
		public String txnCd="APP0010010";

	public SendSmsCode() {
		request = new Request();
		response = new Response();
	}

	public class Request {
		//手机号
		public String mobilelNo="";
//		1用户注册
//		2重置登录密码
//		3重置支付密码
//		4绑定银行卡
//		5关联邮箱帐号
//		7 商户绑定
		public String codeType="";


		public Map<String, String> toMap() {
			return BeanUtils.Bean2Map(this);
		}
	}

	public class Response {
		public String smsCode;


		public void parseResponseParams(JSONObject jsonObject) {
			smsCode= jsonObject.optString("smsCode");
		}
	}
}
