<%@page import="java.util.List"%>
<%@include file="/init.jsp" %>
<%@page import="com.liferay.portlet.journal.model.JournalArticle" %>
<%@page import="com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil" %>
<%@page import="com.liferay.portlet.journal.model.JournalArticleDisplay" %>
<%@page import="com.liferay.portlet.journalcontent.util.JournalContentUtil" %>

<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationActionURL" />
<liferay-portlet:renderURL portletConfiguration="true" var="configurationRenderURL" />

<html>
<head>

</head>
<body>
			<form action="<liferay-portlet:actionURL portletConfiguration="true" />" method="post" name="<portlet:namespace />fm" enctype="multipart/form-data">
		    <table>
		    <tr>
		    	<td>
		    	Asset Type<select  name='<portlet:namespace/>assettype'>
		    		<option>select</option>
		    		<option value="announcements">Announcements</option>
		    		<option value="blogs">Blogs</option>
		    		<option value="news">News</option>
		    		<option value="press-release">Press Release</option>
		    	</select>
		    	</td>
		    </tr>
		    <tr>
		    	<td>Category<input type="text" name='<portlet:namespace/>category' />
		    </tr>
		    <tr>
		    	<td><br><input type="submit" value="Submit"></td>
		    </tr>
			</table>
					
			</form>
		
</body>


</html>


