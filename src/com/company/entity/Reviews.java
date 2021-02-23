package com.company.entity;

import java.util.Date;
import java.util.Objects;

public class Reviews extends AbstractEntity {

    private String reviews;
    private Date date;
    private User user;

    public Reviews(String reviews, Date date, User user) {
        this.reviews = reviews;
        this.date = date;
        this.user = user;
    }

    public Reviews(int id, String reviews, Date date, User user) {
        super(id);
        this.reviews = reviews;
        this.date = date;
        this.user = user;
    }

    public Reviews(String reviews, User user) {
        this.reviews = reviews;
        this.user = user;
        this.date = new Date();
    }

    public Reviews(int id, String reviews, User user) {
        super(id);
        this.reviews = reviews;
        this.user = user;
        this.date = new Date();
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reviews reviews1 = (Reviews) o;
        return Objects.equals(reviews, reviews1.reviews) && Objects.equals(date, reviews1.date) && Objects.equals(user, reviews1.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviews, date, user);
    }

    @Override
    public String toString() {
        return "Reviews{" +
                "reviews='" + reviews + '\'' +
                ", date=" + date +
                ", user=" + user +
                '}';
    }
}
