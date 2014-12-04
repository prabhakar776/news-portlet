package com.msci.webmasters.liferay.cl;

import java.io.IOException;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

public class NewsListController extends MVCPortlet
{
	private static Log	LOG	= LogFactoryUtil.getLog(NewsListController.class);
	private static String CONTENT_TYPE;
	private static int CATEGORY=0;
	
	@Override
	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);		
		try
		{
			PortletPreferences preferences = renderRequest.getPreferences();
			 
			String portletResource = ParamUtil.getString(renderRequest, "portletResource");
			if (Validator.isNotNull(portletResource)) {
			    preferences = PortletPreferencesFactoryUtil.getPortletSetup(renderRequest, portletResource);
			}
			
			CONTENT_TYPE = preferences.getValue("assettype", StringPool.BLANK);
			String category = preferences.getValue("category", StringPool.BLANK);
			if(category=="" || category==null)
			{
				
			}else{
				CATEGORY = Integer.parseInt(category.toString());
				List<JournalArticle> journalArticles = getArticleByType(themeDisplay.getScopeGroupId(), CONTENT_TYPE);
				System.out.println(journalArticles.size());
				renderRequest.setAttribute("journalArticles", journalArticles);
			}
			
		}
		catch (PortalException se)
		{
			LOG.error("Portal Exception thrown ", se);
		}catch(SystemException e){
			LOG.error("Portal Exception thrown ", e);
		}
		super.doView(renderRequest, renderResponse);
		
	}
	
	@SuppressWarnings("unchecked")
	private List<JournalArticle> getArticleByType(long scopeGroupId, String value) throws PortalException, SystemException
	{
		// maxVersionArticle : Query will find the article with its latest version
		DynamicQuery maxVersionArticle = DynamicQueryFactoryUtil.forClass(JournalArticle.class, "maxVersionArticle", PortalClassLoaderUtil.getClassLoader())
		.add(PropertyFactoryUtil.forName("articleId").eqProperty("articleByType.articleId"))		
		.setProjection(ProjectionFactoryUtil.max("id"));
	
		// articleByType : Query will find the articles by its type and appended the maxVersionArticle query so it will track only latest versions
		 DynamicQuery articleByType = DynamicQueryFactoryUtil.forClass(JournalArticle.class, "articleByType", PortalClassLoaderUtil.getClassLoader())
		.add(PropertyFactoryUtil.forName("id").eq(maxVersionArticle))
		
		.add(PropertyFactoryUtil.forName("groupId").eq(scopeGroupId));
		articleByType.setLimit(0, CATEGORY);
		
		if(!value.equalsIgnoreCase("all")){
			articleByType.add(PropertyFactoryUtil.forName("type").eq(value));
		}
		articleByType.addOrder(OrderFactoryUtil.desc("modifiedDate"));
		//LOG.info("Query -- " + articleByType.toString());
		List<JournalArticle> ja = (List<JournalArticle>) JournalArticleLocalServiceUtil.dynamicQuery(articleByType);
		return ja;

	}

}
