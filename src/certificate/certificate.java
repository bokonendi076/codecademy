package certificate;

public class certificate {
    private int certificateId;
    private int grade;
    private String approverName;
    private String cursistEmailAddress;
    private String courseName;

    public certificate(int certificateId, int grade, String approverName, String cursistEmailAddress,
            String courseName) {
        this.certificateId = certificateId;
        this.grade = grade;
        this.approverName = approverName;
        this.cursistEmailAddress = cursistEmailAddress;
        this.courseName = courseName;
    }

    public int getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(int certificateId) {
        this.certificateId = certificateId;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public String getCursistEmailAddress() {
        return cursistEmailAddress;
    }

    public void setCursistEmailAddress(String cursistEmailAddress) {
        this.cursistEmailAddress = cursistEmailAddress;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
