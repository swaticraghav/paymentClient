package com.webservice.soap;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.wss4j.common.ext.WSPasswordCallback;

public class UTPasswordCallback implements CallbackHandler{

	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {

		for (int i = 0; i < callbacks.length; i++) {
			WSPasswordCallback callback = (WSPasswordCallback) callbacks[i];
			if(callback.getIdentifier().equals("admin")) {
				callback.setPassword("admin");
				return;
			}
		}
	}

}
