package com.manuel.ApiResfulSpoify.confi;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

public class APIKeyAuthFilter extends AbstractPreAuthenticatedProcessingFilter {
	
	private String principalRequestHeader;

    public APIKeyAuthFilter(String principalRequestHeader) {
        this.principalRequestHeader = principalRequestHeader;
    }
	
	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return request.getHeader(principalRequestHeader);
	}

	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return "N/A";
	}

}
