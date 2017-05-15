package kechuang.mr.com.kcjh.model;

import com.mr.utils.data.BeanUtils;

import org.json.JSONObject;

import java.util.Map;

import kechuang.mr.com.kcjh.base.BaseModel;

/**
 * 用户登录
 */
public class Login extends BaseModel<Login.Request, Login.Response> {
		public String txnCd="APP0010014";

	public Login() {
		request = new Request();
		response = new Response();
	}

	public class Request {
		//手机号
		public String mobilelNo="";
		//登录密码
		public String logPwd="";
		//0-	手势密码登录
		//1-	正常登录
		public String gestureFlg="1";


		public Map<String, String> toMap() {
			return BeanUtils.Bean2Map(this);
		}
	}

	public class Response {



		public void parseResponseParams(JSONObject jsonObject) {

		}
	}
}
