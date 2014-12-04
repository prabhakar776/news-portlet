<%@include file="init.jsp" %>
 
<form action="<liferay-portlet:actionURL portletConfiguration="true" />"
method="post" name="<portlet:namespace />fm">
 
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
 
<table class="lfr-table">
<tr>
    <td>category</td>
        <td>
        	<aui:select label="web-content-type" name="contentType">
				<c:choose>
					<c:when test="${contentType eq 'all'}">
							<aui:option selected="true" label="All Types" value="all"/>
					</c:when>
				</c:choose>
					<%
					for (int i = 0; i < TYPES.length; i++) {
					%>
						<%if(TYPES[i].equals(contentType)) {%>
							<aui:option selected="true" label="<%= TYPES[i] %>" />
						<%}else{ %>
							<aui:option label="<%= TYPES[i] %>" />
						<%} %>
					<%
					}
					%>
				</aui:select>
    </td>
</tr>

<tr>
       <td colspan="2">
            <input type="button" value="<liferay-ui:message key="save" />"
onClick="submitForm(document.<portlet:namespace />fm);" />
       </td>
</tr>
</table>
</form>