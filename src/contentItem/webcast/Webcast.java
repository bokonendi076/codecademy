package webcast;

import java.time.LocalDate;
import java.util.Date;

import contentItem.ContentItem;

public class Webcast {
    private ContentItem contentItem;
    private String title;
    private int duration;
    private LocalDate publicationDate;
    private String URL;
    private String nameSpeaker;
    private String organisationSpeaker;
    private int contentItemID;

    public String getTitleWebcast() {
        return title;
    }

    public void setTitleWebcast(String title) {
        this.title = title;
    }

    public int getLengthWebcast() {
        return duration;
    }

    public void setLengthWebcast(int lengthWebcast) {
        this.duration = lengthWebcast;
    }

    public LocalDate getDatePublication() {
        return publicationDate;
    }

    public void setDatePublication(LocalDate date) {
        this.publicationDate = date;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String uRL) {
        URL = uRL;
    }

    public String getNameSpeaker() {
        return nameSpeaker;
    }

    public void setNameSpeaker(String nameSpeaker) {
        this.nameSpeaker = nameSpeaker;
    }

    public String getOrganisationSpeaker() {
        return organisationSpeaker;
    }

    public void setOrganisationSpeaker(String organisationSpeaker) {
        this.organisationSpeaker = organisationSpeaker;
    }

    public int getContentItemID() {
        return contentItemID;
    }

    public void setContentItemID(int contentItemID) {
        this.contentItemID = contentItemID;
    }

    public String toString() {
        return "Title: " + title + "\n" + "Length: " + duration + "\n" + "Date: " + publicationDate + "\n"
                + "URL: " + URL + "\n"
                + "Speaker: " + nameSpeaker + "\n" + "Organisation: " + organisationSpeaker + "\n" + "ContentItemID: "
                + contentItemID;
    }
}
