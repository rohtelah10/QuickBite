package com.QuickBite.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private Long feedbackId;

    @Column(name = "user_name") // Optional, if you want to store the user's name
    private String userName;

    @Column(name = "feedback_text", nullable = false)
    private String feedbackText;

    @Column(name = "feedback_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date feedbackDate;

    public Feedback() {
        // Default constructor
    }

    public Feedback(String userName, String feedbackText, Date feedbackDate) {
        this.userName = userName;
        this.feedbackText = feedbackText;
        this.feedbackDate = feedbackDate;
    }

    // Getters and Setters
    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    public Date getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
    }
}
