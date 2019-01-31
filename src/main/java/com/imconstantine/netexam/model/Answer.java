package com.imconstantine.netexam.model;

import java.util.Objects;

public class Answer {

    private int id;
    private String body;

    public Answer() {
        super();
    }

    public Answer(String string) {
        body = string;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Answer)) return false;
        Answer answer = (Answer) o;
        return Objects.equals(getBody(), answer.getBody());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBody());
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", body='" + body + '\'' +
                '}';
    }
}
