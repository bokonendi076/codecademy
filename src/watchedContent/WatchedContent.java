package watchedContent;

public class WatchedContent {
    private int contentItemID;
    private int cursistID;
    private double percentageWatched;

    public int getContentItemID() {
        return contentItemID;
    }

    public void setContentItemID(int contentItemID) {
        this.contentItemID = contentItemID;
    }

    public int getCursistID() {
        return cursistID;
    }

    public void setCursistID(int cursistID) {
        this.cursistID = cursistID;
    }

    public double getPercentageWatched() {
        return percentageWatched;
    }

    public void setPercentageWatched(double percentageWatched) {
        this.percentageWatched = percentageWatched;
    }

    public WatchedContent() {

    }
}
