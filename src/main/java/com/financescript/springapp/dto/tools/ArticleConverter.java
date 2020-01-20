package com.financescript.springapp.dto.tools;

import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArticleConverter {

    public String convert(String source) {
        return sanitize(codeToDivIfLanguageSpecified(parse(source)));
    }

    public String parse(String source) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(source);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }

    /**
     * Defines the allowed items before rendering it to html
     * Intends to prevent most of the XSS attacks
     */
    public String sanitize(String source) {
        PolicyFactory policy = new HtmlPolicyBuilder()
                .allowElements("p", "a", "div", "span", "em", "br", "pre")
                .allowElements(
                        (String elementName, List<String> attrs) -> {
                            // Add a class attribute.
                            attrs.add("class");
                            attrs.add("article-" + elementName);
                            // Return elementName to include, null to drop.
                            return "div";
                        }, "h1", "h2", "h3", "h4", "h5", "h6", "table", "tr", "td", "code")
                .allowUrlProtocols("https")
                .allowAttributes("href").onElements("a")
                .allowAttributes("class").onElements("code", "div")
                .toFactory();
        return policy.sanitize(source);
    }

    public String codeToDivIfLanguageSpecified(String source) {
        KmpSearch kmpSearch = new KmpSearch();
        List<Integer> openCodes = kmpSearch.substringSearch("<code class=\"language-", source);
        List<Integer> doubleQuotes = kmpSearch.substringSearch("\"", source);  // the second double quotes after <code class=\"language-" block will be used to identify the target language
        List<Integer> closedCodes = kmpSearch.substringSearch("</code>", source); // the closest index after openCode will need to be edited. "<" signs within the code block will be replaced by "&lt;"

        int len_source = source.length();
        if (openCodes.size() != 0 && closedCodes.size() != 0) {
            final int LEN_OPEN_CODE_STRING = "<code class=\"language-".length();
            final int LEN_CLOSED_CODE_STRING = "</code>".length();
            StringBuilder sb = new StringBuilder();
            int i = 0; int j = 0; int k = 0;
            int lenOpenCodes = openCodes.size();
            int lenDoubleQuotes = doubleQuotes.size();
            int lenClosedQuotes = closedCodes.size();
            copyToStringBuilder(source, 0, openCodes.get(0), sb);
            while (i < lenOpenCodes) {
                int index = openCodes.get(i);
                if (i != 0)
                    copyToStringBuilder(source, closedCodes.get(k) + LEN_CLOSED_CODE_STRING, index, sb);
                while(doubleQuotes.get(j) < index + LEN_OPEN_CODE_STRING && j < lenDoubleQuotes)
                    j++;
                while(closedCodes.get(k) < index + LEN_OPEN_CODE_STRING && k < lenClosedQuotes)
                    k++;

                sb.append("<div class=\"language-");
                sb.append(source.substring(index + LEN_OPEN_CODE_STRING, doubleQuotes.get(j)).toLowerCase());
                copyToStringBuilder(source, doubleQuotes.get(j), closedCodes.get(k), sb);
                sb.append("</div>");
                i++;
            }
            copyToStringBuilder(source, closedCodes.get(k) + LEN_CLOSED_CODE_STRING, len_source, sb);
            return sb.toString();
        } else {
            return source;
        }
    }

    private void copyToStringBuilder(String s, int startIndex, int endIndex, StringBuilder sb) {
        for (int i = startIndex; i < endIndex; i++) {
            sb.append(s.charAt(i));
        }
    }
}
