<%-- 
    Document   : CollectArticles
    Created on : Oct 16, 2011, 8:21:57 AM
    Author     : robik
--%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="header.jsp" />
<jsp:useBean class="ArticleCollection.Controller" id="Controller" />
<jsp:useBean class="DbInformation.StatisticsProvider"  id="Statistics" />
<% 
long startTime=new Date().getTime();
Controller.automaticallyInsertArticles();
long endTime=new Date().getTime();
    String a;
    
long milliSeconds=endTime-startTime;
long seconds=milliSeconds/1000;

out.println("It took <font color='red'>"+milliSeconds+"</font> milliseconds OR <font color='red'>"+seconds+"</font> seconds to collect the articles");

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