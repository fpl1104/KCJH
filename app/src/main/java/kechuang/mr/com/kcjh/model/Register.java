package kechuang.mr.com.kcjh.model;

import com.mr.utils.data.BeanUtils;

import org.json.JSONObject;

import java.util.Map;

import kechuang.mr.com.kcjh.base.BaseModel;

/**
 * 用户注册 register
 */
public class Register extends BaseModel<Register.Request, Register.Response> {
		public String txnCd="APP0010012";

	public Register() {
		request = new Request();
		response = new Response();
	}

	public class Request {
		public String mobilelNo="";
		public String logPwd="";
		public String smsCode="";
		public String remdPerson="";

		public Map<String, String> toMap() {
			return BeanUtils.Bean2Map(this);
		}
	}

	public class Response {



		public void parseResponseParams(JSONObject jsonObject) {

		}
	}
}
