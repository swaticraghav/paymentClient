package com.webservice.soap;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;

import com.bharath.ws.soap.MyWebServiceName;
import com.bharath.ws.soap.PaymentProcessorImplService;
import com.bharath.ws.soap.PaymentProcessorRequest;
import com.bharath.ws.soap.PaymentProcessorResponse;

public class PaymentWsClient {

	public static void main(String[] args) throws MalformedURLException {

		PaymentProcessorImplService service = new PaymentProcessorImplService(
				new URL("http://localhost:7071/myAppName/paymentProcessor?wsdl"));
		MyWebServiceName port = service.getPaymentProcessorImplPort();
		Client client = ClientProxy.getClient(port);
		Endpoint endpoint = client.getEndpoint();
		
		Map<String, Object> outProps = new HashMap<String, Object>();
		outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
		outProps.put(WSHandlerConstants.USER, "admin");
		outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
		outProps.put(WSHandlerConstants.PW_CALLBACK_CLASS, UTPasswordCallback.class.getName());	
		WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(outProps);
		endpoint.getOutInterceptors().add(wssOut);
		
		PaymentProcessorResponse response = port.processPayment(new PaymentProcessorRequest());

		System.out.println(response.isResult());
	}

}
