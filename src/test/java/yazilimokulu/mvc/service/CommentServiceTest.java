package yazilimokulu.mvc.service;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.yazilimokulu.mvc.controllers.ForbiddenException;
import com.yazilimokulu.mvc.daos.CommentRatingRepository;
import com.yazilimokulu.mvc.daos.CommentRepository;
import com.yazilimokulu.mvc.entities.Comment;
import com.yazilimokulu.mvc.entities.CommentRating;
import com.yazilimokulu.mvc.entities.Post;
import com.yazilimokulu.mvc.entities.User;
import com.yazilimokulu.mvc.services.AlreadyVotedException;
import com.yazilimokulu.mvc.services.CommentServiceImpl;
import com.yazilimokulu.mvc.services.UserService;


public class CommentServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentRatingRepository commentRatingRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Spy
    private Post post;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldAddNewComment() {
        User user = new User();

        when(userService.currentUser()).thenReturn(user);

        Comment comment = new Comment();

        commentService.saveNewComment(comment, post, null);

        assertThat(comment.getPost(), is(equalTo(post)));

        assertThat(comment.getUser(), is(equalTo(user)));

        assertThat(comment.getDateTime().toLocalDate().equals(LocalDate.now()), is(equalTo(true)));

        verify(commentRepository, times(1)).saveAndFlush(Matchers.any(Comment.class));
    }

    @Test
    public void shouldGetComment() {
        final long commentId = 1L;

        Comment comment = new Comment();

        when(commentRepository.findOne(commentId)).thenReturn(comment);

        Comment retrievedComment = commentService.getComment(commentId);

        assertThat(retrievedComment, is(equalTo(comment)));

        verify(commentRepository, times(1)).findOne(commentId);
    }

    @Test
    public void shouldReturnNullWhenCommentDoesNotExist() {
        when(commentRepository.findOne(Matchers.anyLong())).thenReturn(null);

        assertThat(commentService.getComment(1L), is(equalTo(null)));

        verify(commentRepository, times(1)).findOne(Matchers.anyLong());
    }

    @Test
    public void shouldVote() throws Exception {
        Long commentId = 1L;

        User user = new User();
        user.setId(10L);

        User anotherUser = new User();
        anotherUser.setId(8L);

        Comment comment = new Comment();
        comment.setId(commentId);
        comment.setUser(anotherUser);

        when(commentRepository.findOne(commentId)).thenReturn(comment);

        when(userService.currentUser()).thenReturn(user);

        when(commentRatingRepository.findUserRating(commentId, user.getId())).thenReturn(null);

        commentService.vote(commentId, true);

        verify(commentRatingRepository, times(1)).findUserRating(commentId, user.getId());
        verify(commentRatingRepository, times(1)).saveAndFlush(Matchers.any());
    }

    @Test(expected = AlreadyVotedException.class)
    public void shouldThrowExceptionWhenAlreadyVoted() throws Exception {
        Long commentId = 1L;

        User user = new User();
        user.setId(10L);

        User anotherUser = new User();
        anotherUser.setId(8L);

        Comment comment = new Comment();
        comment.setId(commentId);
        comment.setUser(anotherUser);

        when(commentRepository.findOne(commentId)).thenReturn(comment);

        when(userService.currentUser()).thenReturn(user);

        when(commentRatingRepository.findUserRating(commentId, user.getId())).thenReturn(new CommentRating());

        commentService.vote(commentId, true);
    }

    @Test(expected = ForbiddenException.class)
    public void shouldThrowExceptionWhenVoteForOwnComment() throws Exception {
        Long commentId = 1L;

        User user = new User();
        user.setId(10L);

        Comment comment = new Comment();
        comment.setId(commentId);
        comment.setUser(user);

        when(commentRepository.findOne(commentId)).thenReturn(comment);

        when(userService.currentUser()).thenReturn(user);

        when(commentRatingRepository.findUserRating(commentId, user.getId())).thenReturn(new CommentRating());

        commentService.vote(commentId, true);
    }
}