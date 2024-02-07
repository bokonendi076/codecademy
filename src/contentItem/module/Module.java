package module;

public class Module {
    private String title;
    private String version;
    private String contactPersonName;
    private String contactPersonEmail;
    private int followNumber;
    private int contentItemID;
    private int moduleID;

    public Module() {
    }

    public String getTitle() {
        return this.title;
    }

    public String getVersion() {
        return this.version;
    }

    public String getContactPersonName() {
        return this.contactPersonName;
    }

    public String getContactPersonEmail() {
        return this.contactPersonEmail;
    }

    public int getFollowNumber() {
        return this.followNumber;
    }

    public int getContentItemID() {
        return this.contentItemID;
    }

    public int getModuleID() {
        return this.moduleID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public void setContactPersonEmail(String contactPersonEmail) {
        this.contactPersonEmail = contactPersonEmail;
    }

    public void setFollowNumber(int followNumber) {
        this.followNumber = followNumber;
    }

    public void setContentItemID(int contentItemID) {
        this.contentItemID = contentItemID;
    }

    public void setModuleID(int moduleID) {
        this.moduleID = moduleID;
    }

}
