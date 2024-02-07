package webcast;

import java.time.LocalDate;
import java.util.Date;

import contentItem.ContentItem;

public class Webcast {
    private ContentItem contentItem;
    private String titleWebcast;
    private int lengthWebcast;
    private LocalDate datePublication;
    private String URL;
    private String nameSpeaker;
    private String organisationSpeaker;
    private int contentItemID;

    public String getTitleWebcast() {
        return titleWebcast;
    }

    public void setTitleWebcast(String titleWebcast) {
        this.titleWebcast = titleWebcast;
    }

    public int getLengthWebcast() {
        return lengthWebcast;
    }

    public void setLengthWebcast(int lengthWebcast) {
        this.lengthWebcast = lengthWebcast;
    }

    public LocalDate getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(LocalDate date) {
        this.datePublication = date;
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
        return "Title: " + titleWebcast + "\n" + "Length: " + lengthWebcast + "\n" + "Date: " + datePublication + "\n"
                + "URL: " + URL + "\n"
                + "Speaker: " + nameSpeaker + "\n" + "Organisation: " + organisationSpeaker + "\n" + "ContentItemID: "
                + contentItemID;
    }
}
