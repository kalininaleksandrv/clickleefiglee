package com.github.kalininaleksandrv.clickleefiglee.dao;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Article {

    private long Id;
    @NonNull
    private String title;
    private String content;
    private States state;
    private String link;
    private String mediatitle;
    private String medialogo;
    private String date;
    private String author;
    private boolean isClickBait;
    private boolean isFakeNews;


    public Article(long id, @NonNull String title) {
        Id = id;
        this.title = title;
        this.isClickBait = false;
        this.isFakeNews = false;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Id == article.Id &&
                Objects.equals(title, article.title);
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = (int) (prime * result + (this.Id) * Objects.hash(this.title));
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "Movie{" +
                "Id=" + Id +
                ", title='" + title + '\'' +
                '}';
    }

    public String getIdAsString(){
        if(Id != 0){
            return String.valueOf(Id);
        } else return "0";
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public States getState() {
        return state;
    }

    public void setState(States state) {
        this.state = state;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMediatitle() {
        return mediatitle;
    }

    public void setMediatitle(String mediatitle) {
        this.mediatitle = mediatitle;
    }

    public String getMedialogo() {
        return medialogo;
    }

    public void setMedialogo(String medialogo) {
        this.medialogo = medialogo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isClickBait() {
        return isClickBait;
    }

    public void setClickBait(boolean clickBait) {
        isClickBait = clickBait;
    }

    public boolean isFakeNews() {
        return isFakeNews;
    }

    public void setFakeNews(boolean fakeNews) {
        isFakeNews = fakeNews;
    }
}
