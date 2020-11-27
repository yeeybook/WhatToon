package com.example.yeeybook.whattoon.Model;

public class CommentModel {
    public String comment, userId;
    public int webtoonId;
    public float rate;
}
//Users - Comments - webtoonId -> webtoonId, comment
//Webtoons - webtoonId - Comments - uid -> userId, comment, rate