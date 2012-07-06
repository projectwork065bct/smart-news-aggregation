<%-- 
    Document   : CollectArticles
    Created on : Oct 16, 2011, 8:21:57 AM
    Author     : robik
--%>
<%@page import="java.util.Date" %>
<jsp:include page="header.jsp" />
<jsp:include page="leftMenu.jsp" />
<div class="right_content" />
<jsp:useBean class="ArticleCollection.Controller" id="Controller" />
<jsp:useBean class="DbInformation.StatisticsProvider"  id="Statistics" />
<%
    long startTime = new Date().getTime();
    Controller.automaticallyInsertArticles();
    long endTime = new Date().getTime();

    long milliSeconds = endTime - startTime;
    long seconds = milliSeconds / 1000;
    out.println("It took " + milliSeconds + " milliseconds i.e. " + seconds + " seconds to fetch the articles");
    String a;

%>
<br>

The articles have been retrieved.<br><br>
<% //Statistics.connect();
    out.println(Statistics.linkAndError());
%> 

<a href="index.jsp">Return To The Home Page</a>

<jsp:include page="footer.jsp" />