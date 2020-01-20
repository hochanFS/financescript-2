package com.financescript.springapp.dto.tools;

import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;

@Component
public class ArticleParser {
    public String parse(String source) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(source);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }

    public static void main(String[] args) {
        ArticleParser articleParser = new ArticleParser();
        //System.out.println(articleParser.sanitize("<div><a href='https://www.google.com'><h1>bye</h1></a><p><a>hi</a></p><form>aaa</form></div>"));
        System.out.println(articleParser.parse("This is *Sparta*"));
        System.out.println(articleParser.parse("\n  ```java\nimport java.util.ArrayList;\npublic class ArticleParser { }\n```\nthis is ```code snippet```"));
    }
}
