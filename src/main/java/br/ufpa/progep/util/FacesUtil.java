package br.ufpa.progep.util;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/*
 * Class utilized to flag info and error messages in the web page
 * and to pass the web parameters to the Controller class.
 * */
public class FacesUtil {

	/*
	 * Method utilized to flag info messages in the web page.
	 * */
	public static void addInfoMessage(String sumary, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, sumary, detail);

		FacesContext context = FacesContext.getCurrentInstance();

		/*
		 * Utilized to maintain the growl when the web page is redirected
		 * after it has persisted, edited or removed an object.
		 * */
		context.getExternalContext().getFlash().setKeepMessages(true);
		
		context.addMessage(null, message);
	}

	/*
	 * Method utilized to flag error messages in the web page.
	 * */
	public static void addErrorMessage(String sumary, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, sumary, detail);

		FacesContext context = FacesContext.getCurrentInstance();

		/*
		 * Utilized to maintain the growl when the web page is redirected
		 * after it has persisted, edited or removed an object.
		 * */
		context.getExternalContext().getFlash().setKeepMessages(true);
		
		context.addMessage(null, message);
	}
	
	/*
	 * Method utilized to pass a parameter from 'param' flag to the
	 * Controller class.
	 * */
	public static String getParam(String paramName) {
		FacesContext context = FacesContext.getCurrentInstance();
		
		ExternalContext externalContext = context.getExternalContext();
		
		Map<String, String> parameters = externalContext.getRequestParameterMap();
		
		String param = parameters.get(paramName);
		
		return param;
	}
}
