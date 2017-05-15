package com.mr.http.ssl;

import android.annotation.SuppressLint;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * ssl认证使用
 */
public class MR_HttpsTrustManager implements X509TrustManager {

	/**
	 * ssl 证书
	 */
	private final static String MAIN_TSL_CER_KEY = "MIIE/zCCA+egAwIBAgIQI1XkWQGGzuwhM5FrDmEwfDANBgkqhkiG9w0BAQUFADBPMQswCQYDVQQGEwJDTjEaMBgGA1UEChMRV29TaWduIENBIExpbWl0ZWQxJDAiBgNVBAMTG1dvU2lnbiBDbGFzcyAzIE9WIFNlcnZlciBDQTAeFw0xNTA0MjEwNzQ4NTJaFw0xNTA1MjEwODQ4NTJaMIGAMQswCQYDVQQGEwJDTjESMBAGA1UECAwJ5rmW5Y2X55yBMRIwEAYDVQQHDAnplb/mspnluIIxMDAuBgNVBAoMJ+m5sOeah+mHkeS9sOS7lee9kee7nOaKgOacr+aciemZkOWFrOWPuDEXMBUGA1UEAwwOamQua2luZ3Bhc3MuY24wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCUmEatfiQv63FF0Ygk0og+yo363lYmUrbON0s1KjgWxRpbZIKg27odVcCVDFGkl2fbd/V8z17Vz5MVl6hOtGLPJTqqazSiUsUvQ1mXjh+xMph2S96qtpIZiy63MjtNyQrMDPdo5dpLAR4KdYnDKsX1HQkJWqR6O2hC+g/c+Vju9pJRJLelBQsruAfZVFgwim5gHCiZC4qhgqVBeTjV+vCFeqD5iMyP+T0jyysFuRfP3S7kedQng8CyqUna2BSTntqaOvVBMLgEidDlIKcNq4pWAc4zXRbRvkuMS+MasOjRIc8TMQ8PdQv75yNRZOFU+VlU+nysaR7zI38CirEW61YXAgMBAAGjggGjMIIBnzALBgNVHQ8EBAMCBaAwHQYDVR0lBBYwFAYIKwYBBQUHAwIGCCsGAQUFBwMBMAkGA1UdEwQCMAAwHQYDVR0OBBYEFKSFPp9JGPGYT94/UI2LnYvxPLvFMB8GA1UdIwQYMBaAFGIugdnjQnkUo83ZVIpu+N6Vqo+YMH8GCCsGAQUFBwEBBHMwcTA1BggrBgEFBQcwAYYpaHR0cDovL29jc3AxLndvc2lnbi5jb20vY2xhc3MzL3NlcnZlci9jYTEwOAYIKwYBBQUHMAKGLGh0dHA6Ly9haWExLndvc2lnbi5jb20vY2xhc3MzLnNlcnZlci5jYTEuY2VyMDkGA1UdHwQyMDAwLqAsoCqGKGh0dHA6Ly9jcmxzMS53b3NpZ24uY29tL2NhMS1zZXJ2ZXItMy5jcmwwGQYDVR0RBBIwEIIOamQua2luZ3Bhc3MuY24wTwYDVR0gBEgwRjAIBgZngQwBAgIwOgYLKwYBBAGCm1EBAwIwKzApBggrBgEFBQcCARYdaHR0cDovL3d3dy53b3NpZ24uY29tL3BvbGljeS8wDQYJKoZIhvcNAQEFBQADggEBAIz6DslRQbGuj9MRrN+eXAvs4kUWPZVlkftkG14TWKYWquFvMvkoZc6kaQnUmN0YMPodUnY4Dr5EijtM9Oy3C+zmsYV/8sc5kVZwkBpEdbAD3n7bGqzt2DNOfjCaR9SFNxQylH07Su94f6FEIJVPin8H2TnARtBypbf7TweATsTsYzFWDp46dTXKClJEGqrQ0RILxMuuE4jeizsqN723ooC+upWvhlZiIAxu0BG92MMXJUyFejd30MMYQK4Pvyvg5JbdCZL99TICRuaMZaMNRk+Kc36d6PbOmkuNXSKH4cTTF8bdPvXDlZJaZ5MbKvcLCONbIzgOxaQkSc1h1Yrk7uU=";

	private static TrustManager[] trustManagers;
	private static final X509Certificate[] _AcceptedIssuers = new X509Certificate[] {};

	@Override
	public void checkClientTrusted(X509Certificate[] x509Certificates, String s)
			throws CertificateException {
		// To change body of implemented methods use File | Settings | File
		// Templates.
	}

	@Override
	public void checkServerTrusted(X509Certificate[] x509Certificates, String s)
			throws CertificateException {
		// To change body of implemented methods use File | Settings | File
		// Templates.
	}

	public boolean isClientTrusted(X509Certificate[] chain) {
		return true;
	}

	public boolean isServerTrusted(X509Certificate[] chain) {
		return true;
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		return _AcceptedIssuers;
	}

	/**
	 * 信任所有证书
	 */
	@SuppressLint("TrulyRandom")
	public static void allowAllSSL() {
		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

			@Override
			public boolean verify(String arg0, SSLSession arg1) {
				// TODO Auto-generated method stub
				return true;
			}

		});

		SSLContext context = null;
		if (trustManagers == null) {
			trustManagers = new TrustManager[] { new MR_HttpsTrustManager() };
		}

		try {
			context = SSLContext.getInstance("TLS");
			context.init(null, trustManagers, new SecureRandom());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
		HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
	}

	/**
	 * 信任有效指定cer证书
	 */
	public static void allowValidSSL() {
		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

			@Override
			public boolean verify(String arg0, SSLSession arg1) {
				// TODO Auto-generated method stub
				return true;
			}

		});
		SSLContext context = null;
		try {
			// SSL证书认证
			TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory
					.getDefaultAlgorithm());
			InputStream inputStream = new ByteArrayInputStream(Base64.decode(MAIN_TSL_CER_KEY.getBytes("UTF-8"),
					Base64.DEFAULT));
			CertificateFactory cerFactory = CertificateFactory.getInstance("X.509");
			Certificate cer = cerFactory.generateCertificate(inputStream);
			KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			keyStore.load(null, null);
			keyStore.setCertificateEntry("ca", cer);
			inputStream.close();
			trustManagerFactory.init(keyStore);
			context = SSLContext.getInstance("TLS");
			context.init(null, trustManagerFactory.getTrustManagers(), null);

		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
	}
}
