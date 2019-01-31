package com.imconstantine.netexam.dto.response;

import java.util.Map;

public class ExamStudentsResult {
    private String name;
    private Map<String, GroupDto> groups;

    public ExamStudentsResult() {
        super();
    }

    public ExamStudentsResult(String name, Map<String, GroupDto> groups) {
        this.name = name;
        this.groups = groups;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, GroupDto> getGroups() {
        return groups;
    }

    public void setGroups(Map<String, GroupDto> groups) {
        this.groups = groups;
    }
}
