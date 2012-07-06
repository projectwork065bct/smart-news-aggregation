<%-- 
    Document   : Statistics
    Created on : Oct 22, 2011, 11:08:36 AM
    Author     : ramsharan
--%>

<jsp:include page="header.jsp" />
<jsp:include page="leftMenu.jsp" />
<div class="right_content" />
<jsp:useBean class="DbInformation.StatisticsProvider" id="statistics"/>
<h5>The following is the statistical report.</h5>
<%
    //statistics.connect();
    out.println("<table><tr><td><h2>Date:<td><h2> " + statistics.getDate() + "</h2></tr>");
    out.println("<tr><td><h4>The number of articles collected today is: <td><h4><center>" + statistics.numberOfArticles() + "</center></h2></tr>");
    out.println(statistics.category());
    out.println(statistics.subCategory());
    out.println(statistics.statusOfChannel());
    out.println(statistics.channelAndArticle());
%>


<jsp:include page="footer.jsp" />