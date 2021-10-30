package com.example.cqenginedemo;

import com.googlecode.cqengine.attribute.Attribute;

import java.util.List;

import static com.googlecode.cqengine.query.QueryFactory.attribute;

public class Student {

    private final int indexNumber;
    private final String name;
    private final String surname;
    private final List<Course> courses;



    public Student(int indexNumber, String name, String surname, List<Course> courses) {
        this.indexNumber = indexNumber;
        this.name = name;
        this.surname = surname;
        this.courses = courses;
    }

    public int getIndexNumber() {
        return indexNumber;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public static final Attribute<Student, Integer> STUDENT_ID = attribute("index_number", Student::getIndexNumber);
    public static final Attribute<Student, String> STUDENT_NAME = attribute( "name", Student::getName);
    public static final Attribute<Student, String> STUDENT_SURNAME = attribute("surname", Student::getSurname);

}
