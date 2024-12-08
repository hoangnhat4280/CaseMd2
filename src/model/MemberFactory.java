package model;

public class MemberFactory {
    public static Member createMember(String memberId, String name) {
        return new Member(memberId, name);
    }
}
