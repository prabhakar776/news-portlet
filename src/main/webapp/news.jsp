<%@ include file="init.jsp" %>

<%@page import="com.liferay.portlet.journalcontent.util.JournalContentUtil"%>

<% 

if (Validator.isNotNull(portletResource)) {
  preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}

System.out.println("Gategory is $$$$$$$$$$$$$$$$$$$$$$ "+contentType);

Calendar cal = Calendar.getInstance();

if(Validator.isNotNull(renderRequest.getAttribute("journalArticles"))) {
	List<JournalArticle> articles =(List<JournalArticle>) renderRequest.getAttribute("journalArticles");
%>
	<h2>Latest News from MSCI</h2>
	<% int count = 0;
		for(JournalArticle ja:articles){
			cal.setTime(ja.getCreateDate());
			int day = cal.get(Calendar.DATE);
		    int month = cal.get(Calendar.MONTH);
		    int year = cal.get(Calendar.YEAR);
						
			String months[] = {"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
			String monthName = months[month];
			String yearPostFix = (""+year).substring(2, 4);
	%>	
		
		<div class="news-brief">
			<img class="news-icon" src="/image/image_gallery?img_id=<%=ja.getSmallImageId()%>" alt="Placeholder alt text" />
			<p class="news-date corner-10-white"><%=monthName%>&nbsp; <%=day%>, <%=year %></p>
			<liferay-ui:journal-article articleId="<%=ja.getArticleId()%>" groupId="<%=themeDisplay.getScopeGroupId()%>" />
		</div>
		
	<%
		}
	%>
	
<%
}
%>
