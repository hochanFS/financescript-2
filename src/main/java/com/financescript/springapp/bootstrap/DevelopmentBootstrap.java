package com.financescript.springapp.bootstrap;


import com.financescript.springapp.domains.Article;
import com.financescript.springapp.domains.Comment;
import com.financescript.springapp.domains.SubComment;
import com.financescript.springapp.domains.User;
import com.financescript.springapp.repositories.ArticleRepository;
import com.financescript.springapp.repositories.CommentRepository;
import com.financescript.springapp.repositories.SubCommentRepository;
import com.financescript.springapp.repositories.UserRepository;
import com.financescript.springapp.services.ArticleService;
import com.financescript.springapp.services.CommentService;
import com.financescript.springapp.services.SubCommentService;
import com.financescript.springapp.services.UserService;
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
    private final UserService userService;

    public DevelopmentBootstrap(ArticleService articleService, CommentService commentService, SubCommentService subCommentService, UserService userService) {
        this.articleService = articleService;
        this.commentService = commentService;
        this.subCommentService = subCommentService;
        this.userService = userService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.warn("FinanceScript >> Loading the bootstrap data for Development environment");
        loadData();
    }

    public void loadData() {

        // declare the users
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        user1.setUsername("user1");
        user1.setEmail("user1@financescript.com");
        user2.setUsername("user2");
        user2.setEmail("user2@financescript.com");
        user3.setUsername("user3");
        user3.setEmail("user3@financescript.com");

        Article article1 = new Article();
        article1.setTitle("GOOG is on the way to 2000");
        article1.setContents("Even ignoring the 'other bets', YouTube is poised to keep growing. "
                + "Especially, when the 5G is introduced, we expect more people will spend even "
                + "more time consuming the content.");
        article1.setUser(user1);
        user1.getArticles().add(article1);


        SubComment subComment1 = new SubComment();
        subComment1.setUser(user1);
        subComment1.setContents("Thanks!");
        Comment comment1 = new Comment();
        comment1.setContents("Interesting Thought");
        comment1.setUser(user2);
        comment1.setArticle(article1);
        comment1.getSubComments().add(subComment1);
        subComment1.setComment(comment1);

        userService.save(user1);
        userService.save(user2);
        userService.save(user3);

//        Article article2 = new Article();
//        article2.setTitle("M: high risk, high reward");
//        article2.setContents("Macy's has a great margin of safety due to its huge real estate. "
//                + "Even with this MoS and Macy's positive earnings, M's stock price fell sharply year to date. "
//                + "The negative sentiment may keep the stock price lower, but in the end patient investors will get the returns."
//                + "Another thing to keep an eye on is how much growth Macy's shows in their online business.");
//        article2.setUser(user1);
//        SubComment subComment2 = SubComment.builder().user(user1).contents("That is certainly how the market seems to believe.").build();
//        Comment comment2 = Comment.builder().contents("I disagree. I believe Retailers will be a dinosaur.").article(article2).user(user2).build();
//        subComment2.setComment(comment2);
//        comment2.getSubComments().add(subComment2);
//
//        Article article3 = new Article();
//        article3.setTitle("PAAS: Silver Story");
//        article3.setContents("PAAS is one of the biggest Silver miners in the world. "
//                + "The mining cost has been dropping substantially, while the demand of Silver has grown thanks to "
//                + "increased need of industry need as well as historically correlated hedge against loose monetary "
//                + "policies.");
//        article3.setUser(user2);
//        SubComment subComment3 = SubComment.builder().user(user3).contents("Sounds like gambling.").build();
//        Comment comment3 = Comment.builder().contents("I think the author has a point. Great diversification in portfolio at least..").article(article3).user(user1).build();
//        subComment3.setComment(comment3);
//        comment3.getSubComments().add(subComment3);
//
//        articleService.save(article1);
//        articleService.save(article2);
//        articleService.save(article3);
//
//        userService.save(user1);
//        userService.save(user2);
//        userService.save(user3);
//
//        commentService.save(comment1);
//        commentService.save(comment2);
//        commentService.save(comment3);

    }
}
