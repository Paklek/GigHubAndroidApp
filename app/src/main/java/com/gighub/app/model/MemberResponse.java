package com.gighub.app.model;

/**
 * Created by user on 06/02/2017.
 */
public class MemberResponse extends Response {
    private Member member;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
