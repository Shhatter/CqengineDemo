package com.example.cqenginedemo;

import com.googlecode.cqengine.attribute.Attribute;

import static com.googlecode.cqengine.query.QueryFactory.attribute;

public class Course {

    private String name;
    private String description;

    public static final Attribute<Course, String> COURSE_NAME = attribute("name", Course::getName);
    public static final Attribute<Course, String> COURSE_DESCRIPTION = attribute("description", Course::getDescription);

    public Course(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


}
