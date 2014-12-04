package com.msci.webmasters.liferay;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

public class NewsConfigurationAction implements ConfigurationAction {
	
	private static Log	LOG	= LogFactoryUtil.getLog(NewsConfigurationAction.class);
	@Override
	public void processAction(PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionRespone) throws Exception {

			LOG.info("reading values from config page");
			//reading values from config file
	        String assettype = ParamUtil.getString(actionRequest, "assettype");
	        String category = ParamUtil.getString(actionRequest, "category");
	       
	        String portletResource = ParamUtil.getString(actionRequest,"portletResource");
	        PortletPreferences preferences = PortletPreferencesFactoryUtil.getPortletSetup(actionRequest, portletResource);
	        // set the config values to preferences
	        preferences.setValue("assettype", assettype);
	        preferences.setValue("category", category);

	        preferences.store();
	 
	        PortletSession portletSession = actionRequest.getPortletSession();
	        SessionMessages.add(actionRequest, portletConfig.getPortletName()+ ".doConfigure");
		
	}

	@Override
	public String render(PortletConfig arg0, RenderRequest arg1,
			RenderResponse arg2) throws Exception {
		// TODO Auto-generated method stub
		return "/configuration.jsp";
	}

}
