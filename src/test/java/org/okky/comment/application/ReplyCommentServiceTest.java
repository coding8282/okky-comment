package org.okky.comment.application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.okky.comment.TestMother;
import org.okky.comment.application.command.ModifyReplyCommentCommand;
import org.okky.comment.application.command.WriteReplyCommentCommand;
import org.okky.comment.domain.model.ReplyComment;
import org.okky.comment.domain.repository.ReplyCommentRepository;
import org.okky.comment.domain.service.ReplyCommentConstraint;
import org.okky.comment.domain.service.ReplyCommentProxy;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReplyCommentServiceTest extends TestMother {
    @InjectMocks
    ReplyCommentService service;
    @Mock
    ReplyCommentRepository repository;
    @Mock
    ReplyCommentConstraint constraint;
    @Mock
    ReplyCommentProxy proxy;
    @Mock
    ModelMapper mapper;

    @Test
    public void write() {
        WriteReplyCommentCommand cmd = new WriteReplyCommentCommand("r1", "c1", "name", "body");
        ReplyComment comment = mock(ReplyComment.class);
        when(mapper.toModel(cmd)).thenReturn(comment);

        ReplyComment returned = service.write(cmd);

        assertEquals("반환된 모델은 같아야 한다.", returned, comment);

        InOrder o = inOrder(repository, constraint, mapper, comment, proxy);
        o.verify(constraint).checkReplyExists("r1");
        o.verify(mapper).toModel(cmd);
        o.verify(repository).save(comment);
        o.verify(mapper).toEvent(comment);
        o.verify(proxy).sendEvent(any());
    }

    @Test
    public void modify() {
        ReplyComment comment = mock(ReplyComment.class);
        when(constraint.checkExistsAndGet("c")).thenReturn(comment);

        ModifyReplyCommentCommand cmd = new ModifyReplyCommentCommand("c", "b");
        service.modify(cmd);

        InOrder o = inOrder(repository, constraint, mapper, comment, proxy);
        o.verify(constraint).checkExistsAndGet("c");
        o.verify(comment).modify("b");
    }
}