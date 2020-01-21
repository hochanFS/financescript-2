package com.financescript.springapp.bootstrap;


import com.financescript.springapp.domains.*;
import com.financescript.springapp.dto.MemberDto;
import com.financescript.springapp.services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Profile("development")
@Component
@Slf4j
public class DevelopmentBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final ArticleService articleService;
    private final CommentService commentService;
    private final SubCommentService subCommentService;
    private final MemberService memberService;
    private final RoleService roleService;

    public DevelopmentBootstrap(ArticleService articleService, CommentService commentService, SubCommentService subCommentService, MemberService memberService, RoleService roleService) {
        this.articleService = articleService;
        this.commentService = commentService;
        this.subCommentService = subCommentService;
        this.memberService = memberService;
        this.roleService = roleService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        // If there is no data, load the dummy data
        if (memberService.findAll().size() == 1) {
            log.warn("FinanceScript::DevelopmentBootstrap >> Loading the bootstrap data for Development environment");
            loadDummyData();
        }
    }

    public void loadDummyData() {

        // add users
        // declare the users; used MemberDto instead of Member in order to test password encryption
        MemberDto member1Dto = new MemberDto();
        MemberDto member2Dto = new MemberDto();
        MemberDto member3Dto = new MemberDto();
        member1Dto.setUsername("USER1");
        member1Dto.setEmail("user1@financescript.com");
        member1Dto.setPassword("test123");
        member2Dto.setUsername("USER2");
        member2Dto.setEmail("user2@financescript.com");
        member2Dto.setPassword("test123");
        member3Dto.setUsername("USER3");
        member3Dto.setEmail("user3@financescript.com");
        member3Dto.setPassword("test123");

        // save the users
        Member member1 = memberService.save(member1Dto);
        Member member2 = memberService.save(member2Dto);
        Member member3 = memberService.save(member3Dto);
        log.info("FinanceScript::DevelopmentBootstrap >> Successfully saved the members");

        // add articles
        Article article1 = new Article();
        article1.setTitle("GOOG is on the way to 2000");
        article1.setOriginalText("Even ignoring the 'other bets', YouTube is poised to keep growing. " +
                "Especially, when the 5G is introduced, we expect more people will spend even " +
                "more time consuming the content.\n\n" +
                "```java\npublic class Main() {\n" +
                "import java.util.ArrayList;\n" +
                "System.out.println(\"Hi\")\n" +
                "}\n```");
        Article article2 = new Article();
        article2.setTitle("M: high risk, high reward");
        article2.setOriginalText("Macy's has a great margin of safety due to its huge real estate. "
                + "Even with this MoS and Macy's positive earnings, M's stock price fell sharply year to date. "
                + "The negative sentiment may keep the stock price lower, but in the end patient investors will get the returns."
                + "Another thing to keep an eye on is how much growth Macy's shows in their online business.");

        Article article3 = new Article();
        article3.setTitle("PAAS: Silver Story");
        article3.setOriginalText("PAAS is one of the biggest Silver miners in the world. "
                + "The mining cost has been dropping substantially, while the demand of Silver has grown thanks to "
                + "increased need of industry need as well as historically correlated hedge against loose monetary "
                + "policies.");

        // save the article
        member1.addArticle(article1);
        member2.addArticle(article2);
        member1.addArticle(article3);
        log.info("FinanceScript::DevelopmentBootstrap >> Adding the articles");

        // add comments
        Comment comment1 = new Comment();
        comment1.setContents("Interesting Thought");
        comment1.setMember(member2);
        Comment comment2 = new Comment();
        comment2.setContents("I disagree. I believe Retailers will be a dinosaur.");
        comment2.setMember(member1);
        Comment comment3 = new Comment();
        comment3.setContents("I think the author has a point. Great diversification in portfolio at least..");
        comment3.setMember(member3);

        // save comments
        article1.addComment(comment1);
        article2.addComment(comment2);
        article3.addComment(comment3);
        log.info("FinanceScript::DevelopmentBootstrap >> Adding the comments");

        // add sub-comments
        SubComment subComment1 = new SubComment();
        subComment1.setContents("Thanks!");
        subComment1.setMember(member1);
        SubComment subComment2 = new SubComment();
        subComment2.setMember(member2);
        subComment2.setContents("That is certainly how the market seems to believe.");
        SubComment subComment3 = new SubComment();
        subComment3.setMember(member1);
        subComment3.setContents("Sounds like gambling.");

        // save the sub-comments
        comment1.addSubComment(subComment1);
        comment2.addSubComment(subComment2);
        comment3.addSubComment(subComment3);
        log.info("FinanceScript::DevelopmentBootstrap >> Adding the sub-comments");

        subCommentService.save(subComment1);
        subCommentService.save(subComment2);
        subCommentService.save(subComment3);

        commentService.save(comment1);
        commentService.save(comment2);
        commentService.save(comment3);

        articleService.save(article1);
        articleService.save(article2);
        articleService.save(article3);
    }
}
