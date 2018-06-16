package org.okky.comment.domain.model;

import org.junit.Test;
import org.okky.comment.TestMother;
import org.okky.share.execption.BadArgument;

import static org.junit.Assert.assertNotNull;

public class ReplyCommentTest extends TestMother {
    @Test
    public void replyId는_필수() {
        expect(BadArgument.class, "답글 id는 필수입니다.");

        new ReplyComment(null, "잘 키운 딸 하나가 아주 큰일 해내네요...", "m", "c");
    }

    @Test
    public void body는_필수() {
        expect(BadArgument.class, "코멘트 내용은 필수입니다.");

        new ReplyComment("r-1", null, "m", "c");
    }

    @Test
    public void body가_1자일_때_예외() {
        expect(BadArgument.class, "코멘트는 2~150자까지 가능합니다.");

        new ReplyComment("r-1", "한", "m", "c");
    }

    @Test
    public void body가_151자일_때_예외() {
        expect(BadArgument.class, "코멘트는 2~150자까지 가능합니다.");

        String body = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s";
        new ReplyComment("r-1", body, "m", "c");
    }

    @Test
    public void body_trim_확인() {
        expect(BadArgument.class, "코멘트는 2~150자까지 가능합니다.");

        String body = "  코  ";
        new ReplyComment("r-1", body, "m", "c");
    }

    @Test
    public void commenterId는_필수() {
        expect(BadArgument.class, "코멘터 id는 필수입니다.");

        new ReplyComment("r-1", "바디", null, "c");
    }

    @Test
    public void commenterName은_필수() {
        expect(BadArgument.class, "코멘터 이름은 필수입니다.");

        new ReplyComment("r-1", "바디", "m", null);
    }

    @Test
    public void commentedOn_필수_확인() {
        ReplyComment comment = new ReplyComment("r-1", "바디", "m", "c");

        assertNotNull("코멘트 날짜는 필수다.", comment.getCommentedOn());
    }
}