package com.example.cqenginedemo;

import com.googlecode.cqengine.attribute.Attribute;
import lombok.Value;

import static com.googlecode.cqengine.query.QueryFactory.attribute;

@Value
public class Course {

    String name;
    String description;

    public static final Attribute<Course, String> COURSE_NAME = attribute("name", Course::getName);
    public static final Attribute<Course, String> COURSE_DESCRIPTION = attribute("description", Course::getDescription);
}
