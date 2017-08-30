package com.yyk.retrofitrxandroid.service.bean;

/**
 * Created by YYK on 2017/8/25.
 */

public class Student {

    private int id;
    private String name;
    private Course[] courses;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Course[] getCourses() {
        return courses;
    }

    public void setCourses(Course[] courses) {
        this.courses = courses;
    }
}
