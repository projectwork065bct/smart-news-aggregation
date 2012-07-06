<%-- 
    Document   : RelateArticles1
    Created on : Oct 22, 2011, 11:02:32 AM
    Author     : ramsharan
--%>

<jsp:include page="header.jsp" />
<jsp:include page="leftMenu.jsp" />
<div class="right_content" />

<form action="RelateArticles.jsp">
    <table>
        <tr>
            <th>Please specify the number of days to query the articles of 
            <th><input type="text" class="grid_1" value="2" name="days" />
            </th>
        </tr>
    </table>
    <br/><br/>
    <table>
        <tr><th>Specify the algorithms to be used</tr>
        <tr><th>Check Box<th>Algorithm</tr>
        <tr><td><input type="checkbox" name="bm25" value="dm25" checked="true"><td>BM25 </tr>   
        <tr><td><input type="checkbox" name="cosine" value="cosine"><td>Cosine Similarity</tr>
        <tr><td><input type="checkbox" name="mcs" value="mcs"><td>Modified Cosine Similarity</tr>
        <tr><td><input type="checkbox" name="idf" value="idf"><td>Inverse Document</tr>
      </table>
        <input type="submit" onclick="return showMessage('Grouping related articles together......')" value="CLICK HERE TO GROUP THE RELATED ARTICLES TOGETHER." class="NFButton">
</form>

<jsp:include page="footer.jsp" />
