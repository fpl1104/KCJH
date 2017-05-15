package kechuang.mr.com.kcjh.model;

import com.mr.utils.data.BeanUtils;

import org.json.JSONObject;

import java.util.Map;

import kechuang.mr.com.kcjh.base.BaseModel;

/**
 * 重置	登录密码 ResetLogPwd
 */
public class ResetLogPwd extends BaseModel<ResetLogPwd.Request, ResetLogPwd.Response> {
		public String txnCd="APP0010016";

	public ResetLogPwd() {
		request = new Request();
		response = new Response();
	}

	public class Request {
		//新登录密码
		public String newLogPwd ="";
		//验证码
		public String chkCode="";
		//用户登录账号
		public String mobilelNo="";


		public Map<String, String> toMap() {
			return BeanUtils.Bean2Map(this);
		}
	}

	public class Response {



		public void parseResponseParams(JSONObject jsonObject) {

		}
	}
}
