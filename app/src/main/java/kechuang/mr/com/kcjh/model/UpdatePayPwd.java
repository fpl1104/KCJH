package kechuang.mr.com.kcjh.model;

import com.mr.utils.data.BeanUtils;

import org.json.JSONObject;

import java.util.Map;

import kechuang.mr.com.kcjh.base.BaseModel;

/**
 * 修改登录密码 UpdatePayPwd
 */
public class UpdatePayPwd extends BaseModel<UpdatePayPwd.Request, UpdatePayPwd.Response> {
	public String txnCd="APP0010015";

	public UpdatePayPwd() {
		request = new Request();
		response = new Response();
	}

	public class Request {

		//原登录密码
		public String oldPayPwd ="";
		//新登录密码
		public String newPayPwd ="";
		//短信验证码
		public String smsCode="";
		//手机号
		public String mobilelNo="";
		//操作类型
		public String oprTyp="";//1:修改密码2:忘记密码


		public Map<String, String> toMap() {
			return BeanUtils.Bean2Map(this);
		}
	}

	public class Response {



		public void parseResponseParams(JSONObject jsonObject) {

		}
	}
}
