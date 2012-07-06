<%-- 
    Document   : index
    Created on : Oct 14, 2011, 7:46:17 AM
    Author     : robik
--%>

<jsp:include page="header.jsp" />
<h2>WELCOME</h2>
<p> Our minor project is entitled "Smart News Aggregation". This web application collects 
    news articles from various Nepali news sites. It places those articles 
    under suitable categories. Then, it groups the related articles together and 
    presents them.
</p>

<br/>
<br/>
<hr/>
<table>
    <tr><th>
    <h5>Press this button to run the application. Doing so will run the entire application with default settings. The news articles will be collected, sub-categorized and indexed. Finally, the related articles will be grouped together.</h5></th>
</tr><tr><th><a href="defaultRun.jsp"><input type="button"  class="grid_10" value="RUN THE APPLICATION"></a>
    </th> </tr>
</table>
<h1>Things still left to be done</h1>
<table>
    <tr>
        <th>s.n</th><th>What to do?</th>

    </tr>
    <tr><td><td>Error handling And Log Report (To be tested)</tr>
    <tr><td><td>Re-factor codes used for database</tr>
    <tr><td><td>Showing statistics after relate</tr>
    <tr><td><td>Find out average length of documents</tr>
    <tr><td><td>Manual Testing</tr>
    <tr><td><td>Comparing Algorithms</tr>
    <tr><td><td>Report and Presentation</tr>
    <tr><td><td>Assign place</tr>

</table>
<jsp:include page="rightColumn.jsp" />
<jsp:include page="footer.jsp" />
