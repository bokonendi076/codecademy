package course;

public class Course {
    private String name;
    private String subject;
    private String introductionText;
    private String difficultyLevel;
    private int courseId;
    private int moduleId;
    private boolean certificate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getIntroductionText() {
        return introductionText;
    }

    public void setIntroductionText(String introductionText) {
        this.introductionText = introductionText;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel2) {
        this.difficultyLevel = difficultyLevel2;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public void setCertificate(boolean certificate) {
        this.certificate = certificate;
    }

    public boolean hasCertificate() {
        return certificate;
    }

}