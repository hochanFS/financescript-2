package com.financescript.springapp.dto.tools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArticleConverterTest {

    ArticleConverter articleConverter;

    @BeforeEach
    void setUp() {
        articleConverter = new ArticleConverter();
    }

    @Test
    void parse__plain_md_text() {
        System.out.println(articleConverter.parse("This is *Sparta*"));
        assertEquals("<p>This is <em>Sparta</em></p>", articleConverter.parse("This is *Sparta*").trim());
    }

    @Test
    void parse__md_text_with_code() {
        System.out.println(articleConverter.parse("This is *Sparta*"));
        assertEquals("<pre><code class=\"language-java\">import java.util.ArrayList;\n" +
                        "public class ArticleParser { }\n" +
                        "</code></pre>\n" +
                        "<p>this is <code>code snippet</code></p>",
                articleConverter.parse(
                        "\n  ```java\nimport java.util.ArrayList;\npublic class ArticleParser { }\n```\nthis is ```code snippet```")
                        .trim());
    }

    @Test
    void codeToDivIfLanguageSpecified__canSkipCorrectly() {
        assertEquals("</code></pre>\n" +
                "<p>this is <code>code snippet</code></p>",
                articleConverter.codeToDivIfLanguageSpecified("</code></pre>\n" +
                        "<p>this is <code>code snippet</code></p>"));
    }

    @Test
    void codeToDivIfLanguageSpecified__convert() {
        assertEquals("<pre><div class=\"language-java\">import java.util.ArrayList;\n" +
                        "public class ArticleParser { }\n" +
                        "</div></pre>\n" +
                        "<p>this is <code>code snippet</code></p>",
                articleConverter.codeToDivIfLanguageSpecified("<pre><code class=\"language-JAVA\">import java.util.ArrayList;\n" +
                        "public class ArticleParser { }\n" +
                        "</code></pre>\n" +
                        "<p>this is <code>code snippet</code></p>"));
    }

    @Test
    void codeToDivIfLanguageSpecified__multipleConvert() {
        assertEquals("<pre><div class=\"language-java\">import java.util.ArrayList;\n" +
                        "public class ArticleParser { }\n" +
                        "</div></pre>\n" +
                        "<p>this is <code>code snippet</code></p>" +
                        "<pre><div class=\"language-java\">import java.util.ArrayList;\n" +
                        "public class ArticleParser { }\n" +
                        "</div></pre>\n",
                articleConverter.codeToDivIfLanguageSpecified("<pre><code class=\"language-JAVA\">import java.util.ArrayList;\n" +
                        "public class ArticleParser { }\n" +
                        "</code></pre>\n" +
                        "<p>this is <code>code snippet</code></p>" +
                        "<pre><code class=\"language-JAVA\">import java.util.ArrayList;\n" +
                        "public class ArticleParser { }\n" +
                        "</code></pre>\n"));
    }

    @Test
    void sanitize() {
        assertEquals("<pre><div class=\"language-java\">import java.util.ArrayList;\n" +
                        "public class ArticleParser { }\n" +
                        "</div></pre>\n" +
                        "something here\n" +
                        "<p>this is <div class=\"article-code\">code snippet</div></p>",
                articleConverter.sanitize("<pre><div class=\"language-java\">import java.util.ArrayList;\n" +
                        "public class ArticleParser { }\n" +
                        "</div></pre>\n" +
                        "<form>something here</form>\n" +
                        "<p>this is <code>code snippet</code></p>"));
    }

    @Test
    void convert__null() {
        assertNull(articleConverter.convert(null));
    }

}