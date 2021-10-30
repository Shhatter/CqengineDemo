package com.example.cqenginedemo;

import com.googlecode.cqengine.attribute.Attribute;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.List;

import static com.googlecode.cqengine.query.QueryFactory.attribute;

@Value
public class Student {

    int indexNumber;
    String name;
    String surname;
    List<Course> courses;

    public static final Attribute<Student, Integer> STUDENT_ID = attribute("index_number", Student::getIndexNumber);
    public static final Attribute<Student, String> STUDENT_NAME = attribute( "name", Student::getName);
    public static final Attribute<Student, String> STUDENT_SURNAME = attribute("surname", Student::getSurname);

}
