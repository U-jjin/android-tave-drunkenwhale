package org.androidtown.alcohol.Community;

public class CommentInfo {

    private  String nickname;
    private String comment;
    private String date;

    public CommentInfo(String nickname, String comment, String date) {
        this.nickname = nickname;
        this.comment = comment;
        this.date = date;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
