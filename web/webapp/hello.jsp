<%@ page contentType="text/html; charset=utf-8" %>
<%@ page language="java" import="java.util.*;" pageEncoding="utf-8"%>
<%
response.setContentType("text/html;charset=gbk");
request.setCharacterEncoding("utf-8");
%>
<!doctype html>
<head>
<title>Welcome Iseer</title>
<meta charset="utf-8">
</head>
<body>
<%!
public String revertWord(String word) {
    StringBuffer newWord = new StringBuffer();
    for (int l = word.length() - 1; l > -1; l--) {
        newWord.append(String.valueOf(word.charAt(l)));
    }
    return newWord.toString();
}
/*
 * @author: jarryli@gmail.com
 **/
/*
public String revertWord(String word) {
String newWord = new String();
for (int l = word.length() - 1; l > -1; l--) {
    newWord += (String.valueOf(word.charAt(l)));
}
return newWord;
} */
public String revertText(String[] text) {
    String[] p = new String[text.length];
    StringBuffer newText = new StringBuffer();
    try {
        for (int l = text.length - 1; l > -1; l--) {
            p[l] = String.valueOf(text[l]);
            newText.append(revertWord(p[l]));
            newText.append(" ");
        }
    } catch (Exception ex) {
        System.out.print(ex.getMessage());
    }
    return newText.toString();
} 

%>
<%
String greeting = "Hello, Welcome to Iseer";
out.print("<h1>" + greeting + "</h1>");
String[] words = greeting.split(" ");
out.println(revertText(words));
/* String   word = "";
for (int i = words.length - 1; i > -1; i--) {
    word = words[i];
    out.println(revertWord(word));
    for (int l = word.length() - 1; l > -1; l--) {
        out.print(word.charAt(l));
    }
    out.print(" ");
} */
%>
<%
// out.print("<h3>I am Jarry, I am very pleasure to introduce this system to you.</h3>");
out.print("<h2>");
String introText = ("Iseer is Base on Content Similar Image Recognition and Retrieval System.");
out.println(introText);
out.print("</h2>");
String[] introTextList = introText.split(" ");
out.println(revertText(introTextList));
%>
<div>
<h4>相似图像识别与检索系统设计与实现</h4>
<ul>
    <li>查询列表</li>
    <li>索引排序</li>
    <li>特征提取</li>
    <li>相似性度量</li>
</ul>
</div>
<p>
</p>
<address>
    CopyRight@Lichunping mail:<a href="mailto:jarryli@gmail.com">jarryli@gmail.com</a>
</address>
</body>
</html>
