package com.lifeline.nyinst.avinash;

public class PostViewModalClass {

    private String profileUrl, name, city, dayUploaded, postDescription, postImgUrl;
    private int id,totalLike,totalComment;

    public PostViewModalClass(int id,String profileUrl, String name, String city, String dayUploaded, String postDescription, String postImgUrl, int totalLike, int totalComment) {
        this.id = id;
        this.profileUrl = profileUrl;
        this.name = name;
        this.city = city;
        this.dayUploaded = dayUploaded;
        this.postDescription = postDescription;
        this.postImgUrl = postImgUrl;
        this.totalLike = totalLike;
        this.totalComment = totalComment;


    }

    public int getId() {
        return id;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getDayUploaded() {
        return dayUploaded;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public String getPostImgUrl() {
        return postImgUrl;
    }

    public int getTotalLike() {
        return totalLike;
    }

    public int getTotalComment() {
        return totalComment;
    }
}
