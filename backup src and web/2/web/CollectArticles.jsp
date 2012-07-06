<%-- 
    Document   : CollectArticles
    Created on : Oct 16, 2011, 8:21:57 AM
    Author     : robik
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:include page="header.jsp" />
<jsp:useBean class="ArticleCollection.Controller" id="Controller" />
<jsp:useBean class="DbInformation.StatisticsProvider"  id="Statistics" />
<% Controller.automaticallyInsertArticles();
    String a;

%>
<br>
The articles have been retrieved.<br><br>
<% //Statistics.connect();
    out.println(Statistics.linkAndError());
%> 

<a href="index.jsp">Return To The Home Page</a>

<%--    which which channel
        which which category
        which link is not working notify
        which which sub category, for certain channels,
        
--%>
<jsp:include page="rightColumn.jsp" />
<jsp:include page="footer.jsp" />