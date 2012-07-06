<%-- 
    Document   : RelateArticles
    Created on : Oct 18, 2011, 2:28:36 PM
    Author     : robik
--%>

<%@page import="java.util.Date"%>
<jsp:include page="header.jsp" />

<h5>Group the related articles together</h5>
<jsp:useBean class="ArticleCollection.Controller" id="c" />
<jsp:useBean class="DbInformation.QueriesForOutput" id="qfo" />
<jsp:useBean class="TextAnalysis.ScoreAndSave" id="ss"/>
<%
    long startTime = new Date().getTime();
    String message = "";
    int days = 0;
    if (!request.getParameter("days").equals("")) {
        try {
            days = Integer.parseInt(request.getParameter("days"));
            if (days < 0) {
                days = 0;
            }
            message = " and the articles collected since " + days + " days before";

        } catch (Exception e) {
            out.println("<font color=\"red\">The number of days could not be read correctly. Anyway, the related articles have been grouped together.</font><br/><br/><hr/>");
            days = 0;
        }

    }
    c.automaticallyFindBM25Score(days);
    out.println("The articles collected today " + message + " were analyzed and grouped together.<br/><br/><hr/>");

    long endTime = new Date().getTime();

    out.println("<h4>The time taken to group together the related articles is </h4><font color='red'>" + (endTime - startTime) + "</font><h4> milliseconds</h4> i.e. <font color='red'>" + (((float) (endTime - startTime)) / 1000) + "</font><h4> seconds</h4>");

    out.println(qfo.getRelatedArticles());

    out.println("<br/><br/><hr/>");


%>



<% ss.AutoCalculateCosineSimilarity();
    ss.AutoCalculateIDF();
    ss.AutoCalculateMCS();
%>

<a href="index.jsp">Return To The Home Page</a>
<jsp:include page="rightColumn.jsp" />
<jsp:include page="footer.jsp" />