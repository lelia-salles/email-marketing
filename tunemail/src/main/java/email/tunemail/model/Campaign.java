package email.tunemail.model;


import jakarta.persistence.*;
import java.time.LocalDateTime;

// JPA entity for email campaigns
// A/B test

@Entity
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String recipientList;
    private boolean sent;

    private int openCountA = 0;
    private int openCountB = 0;
    private int clickCountA = 0;
    private int clickCountB = 0;

    // New fields for tracking
    private int openCount = 0;
    private int clickCount = 0;

    private String winningVersion; // Stores which version (A or B) is the winner for future campaigns

    @Column(nullable = false)
    public Long getId() {
        return id;
    }

    @Column(nullable = false)
    public String getRecipientList() {
        return recipientList;
    }

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String subjectA;

    @Column(nullable = false)
    private String subjectB;

    @Column(nullable = false, length = 10000)
    private String contentA;

    @Column(nullable = false, length = 10000)
    private String contentB;

    @Column(nullable = true)
    private LocalDateTime scheduledTime;

    @Enumerated(EnumType.STRING)
    private CampaignStatus status;

    public Campaign() {
        // Default constructor for JPA
    }

    private Campaign(Builder builder) {
        this.name = builder.name;
        this.subjectA = builder.subject;
        this.subjectB = builder.subject;
        this.contentA = builder.content;
        this.contentB = builder.content;
        this.scheduledTime = builder.scheduledTime;
        this.status = builder.status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRecipientList(String recipientList) {
        this.recipientList = recipientList;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public int getOpenCountA() {
        return openCountA;
    }

    public void setOpenCountA(int openCountA) {
        this.openCountA = openCountA;
    }

    public int getOpenCountB() {
        return openCountB;
    }

    public void setOpenCountB(int openCountB) {
        this.openCountB = openCountB;
    }

    public int getClickCountA() {
        return clickCountA;
    }

    public void setClickCountA(int clickCountA) {
        this.clickCountA = clickCountA;
    }

    public int getClickCountB() {
        return clickCountB;
    }

    public void setClickCountB(int clickCountB) {
        this.clickCountB = clickCountB;
    }

    public int getOpenCount() {
        return openCount;
    }

    public void setOpenCount(int openCount) {
        this.openCount = openCount;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public String getWinningVersion() {
        return winningVersion;
    }

    public void setWinningVersion(String winningVersion) {
        this.winningVersion = winningVersion;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSubjectA(String subjectA) {
        this.subjectA = subjectA;
    }

    public void setSubjectB(String subjectB) {
        this.subjectB = subjectB;
    }

    public String getContentA() {
        return contentA;
    }

    public void setContentA(String contentA) {
        this.contentA = contentA;
    }

    public void setContentB(String contentB) {
        this.contentB = contentB;
    }

    public void setScheduledTime(LocalDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public void setStatus(CampaignStatus status) {
        this.status = status;
    }

    public void setSubject(String subjectB) {
    }

    public void setContent(String contentB) {

    }

    public String getSubject() {
        return null;
    }

    public String getContent() {
        return null;
    }

    public static class Builder {
        private String name;
        private String subject;
        private String content;
        private LocalDateTime scheduledTime;
        private CampaignStatus status;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder scheduledTime(LocalDateTime scheduledTime) {
            this.scheduledTime = scheduledTime;
            return this;
        }

        public Builder status(CampaignStatus status) {
            this.status = status;
            return this;
        }

        public Campaign build() {
            return new Campaign(this);
        }
    }


    public String getName() {
        return name;
    }

    public String getcontentA() {
        return contentA;
    }

    public String getContentB() {
        return contentB;
    }

    public String getSubjectA() {
        return subjectA;
    }

    public String getSubjectB() {
        return subjectB;
    }

    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public CampaignStatus getStatus() {
        return status;
    }
}

enum CampaignStatus {
    DRAFT, SCHEDULED, SENT, FAILED
}

