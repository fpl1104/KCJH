package kechuang.mr.com.kcjh.model;

import com.mr.utils.data.BeanUtils;

import org.json.JSONObject;

import java.util.Map;

import kechuang.mr.com.kcjh.base.BaseModel;

/**
 * 关于我们 aboutUs
 */
public class aboutUs extends BaseModel<aboutUs.Request, aboutUs.Response> {
	public String txnCd="";

	public aboutUs() {
		request = new Request();
		response = new Response();
	}

	public class Request {

		//原登录密码
		public String mobilelNo ="";



		public Map<String, String> toMap() {
			return BeanUtils.Bean2Map(this);
		}
	}

	public class Response {
		//版本号
		public String version;
		//版权所有
		public String jursdcTion;
		//客服电话
		public String custmerSive;
		//公司官网
		public String compyOficWebsite;
		//用户协议
		public String agremnt;


		public void parseResponseParams(JSONObject jsonObject) {
			version= jsonObject.optString("version");
			jursdcTion= jsonObject.optString("jursdcTion");
			custmerSive= jsonObject.optString("custmerSive");
			compyOficWebsite= jsonObject.optString("compyOficWebsite");
			agremnt= jsonObject.optString("agremnt");
		}
	}
}
