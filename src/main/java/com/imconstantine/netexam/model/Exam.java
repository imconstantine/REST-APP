package com.imconstantine.netexam.model;


import java.util.List;
import java.util.Objects;

public class Exam {

    private int id;
    private String examName;
    private int semester;
    private boolean ready;
    private Integer questionsCountPerExam;
    private Integer timeInMinutes;
    private boolean showDetails;
    private List<Question> questions;

    public Exam() {
        super();
    }

    public Exam(int id, String examName, int semester, boolean ready) {
        super();
        setId(id);
        setExamName(examName);
        setSemester(semester);
        setReady(ready);
    }

    public Exam(String examName, int semester, boolean ready, Integer questionsCountPerExam, Integer timeInMinutes, boolean showDetails, List<Question> questions) {
        this.examName = examName;
        this.semester = semester;
        this.ready = ready;
        this.questionsCountPerExam = questionsCountPerExam;
        this.timeInMinutes = timeInMinutes;
        this.showDetails = showDetails;
        this.questions = questions;
    }

    public Exam(String examName, int semester, boolean ready) {
        this(0, examName, semester, ready);
    }

    public Integer getQuestionsCountPerExam() {
        return questionsCountPerExam;
    }

    public void setQuestionsCountPerExam(Integer questionsCountPerExam) {
        this.questionsCountPerExam = questionsCountPerExam;
    }

    public Integer getTimeInMinutes() {
        return timeInMinutes;
    }

    public void setTimeInMinutes(Integer timeInMinutes) {
        this.timeInMinutes = timeInMinutes;
    }

    public Boolean getShowDetails() {
        return showDetails;
    }

    public void setShowDetails(Boolean showDetails) {
        this.showDetails = showDetails;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Exam)) return false;
        Exam exam = (Exam) o;
        return getSemester() == exam.getSemester() &&
                isReady() == exam.isReady() &&
                Objects.equals(getExamName(), exam.getExamName()) &&
                Objects.equals(getQuestions(), exam.getQuestions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getExamName(), getSemester(), isReady(), getQuestions());
    }

    @Override
    public String toString() {
        return "Exam{" +
                "examName='" + examName + '\'' +
                ", semester=" + semester +
                ", ready=" + ready +
                ", questions=" + questions +
                '}';
    }
}
