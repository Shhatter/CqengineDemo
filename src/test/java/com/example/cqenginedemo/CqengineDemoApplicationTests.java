package com.example.cqenginedemo;

import com.googlecode.cqengine.ConcurrentIndexedCollection;
import com.googlecode.cqengine.index.navigable.NavigableIndex;
import com.googlecode.cqengine.index.radixreversed.ReversedRadixTreeIndex;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.googlecode.cqengine.query.QueryFactory.and;
import static com.googlecode.cqengine.query.QueryFactory.contains;
import static com.googlecode.cqengine.query.QueryFactory.equal;
import static com.googlecode.cqengine.query.QueryFactory.greaterThan;
import static com.googlecode.cqengine.query.QueryFactory.startsWith;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CqengineDemoApplicationTests {

    public static final Course FIZYKA = new Course("Fizyka", "Podstawy fizyki molekularnej");
    public static final Course ANALIZA = new Course("Analiza", "Analiza matematyczna dla studentów informatyki");
    public static final Course MATEMATYKA_DYSKRETNA = new Course("Matematyka dyskretna",
        "Zajęcia matematyczne gdzie studenci które zajmują się badaniem struktur nieciągłych");
    public static final Course ANGIELSKI = new Course("Język Angielski", "Nauka języka na poziomie B2");

    @Test
    void simpleQuery() {

        var students = new ConcurrentIndexedCollection<Student>();

        students.addIndex(NavigableIndex.onAttribute(Student.STUDENT_ID));
        students.addIndex(ReversedRadixTreeIndex.onAttribute(Student.STUDENT_NAME));

        students.add(new Student(1, "Tomasz", "Nowak", List.of(FIZYKA, ANALIZA, ANGIELSKI)));
        students.add(new Student(300, "Leszek", "Kowalski", List.of(ANGIELSKI)));
        students.add(new Student(4440, "Anna", "Kowalska", List.of(FIZYKA, ANALIZA, MATEMATYKA_DYSKRETNA)));

        var queryOne = equal(Student.STUDENT_NAME, "Tomasz");
        assertEquals(1, students.retrieve(queryOne).size());

        var queryTwo = startsWith(Student.STUDENT_SURNAME, "Kowal");
        assertEquals(2, students.retrieve(queryTwo).size());

        var queryThree = and( startsWith(Student.STUDENT_SURNAME, "Kowal"),greaterThan(Student.STUDENT_ID,300));
        assertEquals(1, students.retrieve(queryThree).size());

    }


    @Test
    void nestedQuery() {

        var students = new ConcurrentIndexedCollection<Student>();

        students.addIndex(NavigableIndex.onAttribute(Student.STUDENT_ID));
        students.addIndex(ReversedRadixTreeIndex.onAttribute(Student.STUDENT_NAME));

        students.add(new Student(1, "Tomasz", "Nowak", List.of(FIZYKA, ANALIZA, ANGIELSKI)));
        students.add(new Student(300, "Leszek", "Kowalski", List.of(ANGIELSKI)));
        students.add(new Student(4440, "Anna", "Kowalska", List.of(FIZYKA, ANALIZA, MATEMATYKA_DYSKRETNA)));

        var queryOne = contains(Student.COURSE_NAME, "Matematyka dyskretna");
        assertEquals(1, students.retrieve(queryOne).size());

    }

}
