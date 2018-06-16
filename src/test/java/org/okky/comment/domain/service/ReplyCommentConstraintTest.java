package org.okky.comment.domain.service;

import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.okky.comment.TestMother;
import org.okky.comment.domain.repository.ReplyCommentRepository;
import org.okky.share.execption.ModelNotExists;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@FieldDefaults(level = PRIVATE)
public class ReplyCommentConstraintTest extends TestMother {
    @InjectMocks
    ReplyCommentConstraint constraint;
    @Mock
    ReplyCommentRepository repository;

    @Test
    public void checkExistsAndGet_없는_아이디인_경우_예외() {
        expect(ModelNotExists.class, "해당 코멘트는 존재하지 않습니다: 'c1'");

        when(repository.findById("c1")).thenReturn(Optional.empty());

        constraint.checkExistsAndGet("c1");
    }
}