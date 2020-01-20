package com.financescript.springapp.dto.tools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArticleParserTest {

    ArticleParser articleParser;

    @BeforeEach
    void setUp() {
        articleParser = new ArticleParser();
    }

    @Test
    void parse__plain_md_text() {
        System.out.println(articleParser.parse("This is *Sparta*"));
        assertEquals("<p>This is <em>Sparta</em></p>", articleParser.parse("This is *Sparta*").trim());
    }

    @Test
    void parse__md_text_with_code() {
        System.out.println(articleParser.parse("This is *Sparta*"));
        assertEquals("<pre><code class=\"language-java\">import java.util.ArrayList;\n" +
                        "public class ArticleParser { }\n" +
                        "</code></pre>\n" +
                        "<p>this is <code>code snippet</code></p>",
                articleParser.parse(
                        "\n  ```java\nimport java.util.ArrayList;\npublic class ArticleParser { }\n```\nthis is ```code snippet```")
                        .trim());
    }
}