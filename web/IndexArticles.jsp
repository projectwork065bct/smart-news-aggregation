<%-- 
    Document   : IndexArticles
    Created on : Oct 18, 2011, 2:28:26 PM
    Author     : robik
--%>
<jsp:include page="header.jsp" />
<jsp:include page="leftMenu.jsp" />
<div class="right_content" />
<%@page import="java.util.Date" %>
<h1>Index Articles</h1>
<jsp:useBean class="ArticleCollection.Controller" id="Controller" />
<%
    long startTime = new Date().getTime();
    int days = 0;
    String message = "";
    String error = "<font color=\"red\">Please specify valid integer for the number of days!! Anyway, an index has been created.</font><br/><br/><hr/>";

    if (!request.getParameter("days").equals("")) {

        try {
            days = Integer.parseInt(request.getParameter("days"));
            if (days < 0) {
                days = 0;
            } else {
                message += "and the articles since " + days + " days before ";
            }

        } catch (Exception e) {
            days = 0;
            out.println(error);

        }
    }

    Controller.automaticIndexing(days);
    long endTime = new Date().getTime();
    
    //print the time taken to do indexing
    out.println("<h4>The time taken to create the index for articles is: </h4><font color='red'>" + ((endTime - startTime)) + "</font><h4> milliseconds</h4>" + " i.e. <font color='red'>" + ((float) (endTime - startTime)) / 1000 + "</font><h4> seconds</h4><br/><hr/>");
    
    
    out.println("<h4>The articles collected today " + message + "have been indexed.</h4>");
%>
<br/><br/><a href="index.jsp">Return To The Home Page</a>
<jsp:include page="footer.jsp" />