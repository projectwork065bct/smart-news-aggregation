<%-- 
    Document   : RelateArticles1
    Created on : Oct 22, 2011, 11:02:32 AM
    Author     : ramsharan
--%>

<jsp:include page="header.jsp" />

<h5>Now it is time to group the related articles together.</h5></br>

<form action="RelateArticles.jsp">
    <table><tr>
            <th>Please specify the number of days to query the articles of 
            <th><input type="text" class="grid_1" name="days" />
            </th>
        </tr>
        <tr><th colspan="2"><input type="submit" onclick="return showMessage('Grouping related articles together......')" class="blueButton" value="Click on this button to group the related articles together."></tr>
    </table>
</form>
<form action="RelateArticles.jsp">
    <input type="checkbox" name="algorithm" value="">
    <input type="checkbox" name="algorithm" value="">
    <input type="checkbox" name="algorithm" value="">
    
</form>    
<br/><br/><hr/>
<a href="index.jsp">Return To The Home Page</a>


<jsp:include page="rightColumn.jsp" />
<jsp:include page="footer.jsp" />
