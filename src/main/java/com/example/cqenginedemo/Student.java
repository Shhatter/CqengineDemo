package com.example.cqenginedemo;

import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.attribute.MultiValueAttribute;
import com.googlecode.cqengine.query.option.QueryOptions;
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
    public static final Attribute<Student, String> STUDENT_NAME = attribute("name", Student::getName);
    public static final Attribute<Student, String> STUDENT_SURNAME = attribute("surname", Student::getSurname);

    public static final MultiValueAttribute<Student, String> COURSE_NAME = new MultiValueAttribute<>("course_name") {
        public Iterable<String> getValues(Student student, QueryOptions queryOptions) {
            return student.courses.stream().map(Course::getName)::iterator;
        }
    };
    public static final MultiValueAttribute<Student, String> COURSE_DESCRIPTION = new MultiValueAttribute<>("course_description") {
        public Iterable<String> getValues(Student student, QueryOptions queryOptions) {
            return student.courses.stream().map(Course::getDescription)::iterator;
        }
    };

}
