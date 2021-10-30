package com.example.cqenginedemo;

import com.googlecode.cqengine.ConcurrentIndexedCollection;
import com.googlecode.cqengine.IndexedCollection;
import com.googlecode.cqengine.index.navigable.NavigableIndex;
import com.googlecode.cqengine.index.radixreversed.ReversedRadixTreeIndex;
import com.googlecode.cqengine.query.Query;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

import static com.googlecode.cqengine.query.QueryFactory.equal;
import static com.googlecode.cqengine.query.QueryFactory.startsWith;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CqengineDemoApplicationTests {

    @Test
    void simpleQuery() {

        IndexedCollection<Student> students = new ConcurrentIndexedCollection<>();

        students.addIndex(NavigableIndex.onAttribute(Student.STUDENT_ID));
        students.addIndex(ReversedRadixTreeIndex.onAttribute(Student.STUDENT_NAME));

        students.add(new Student(1, "Tomasz", "Nowak", List.of(new Course("Fizyka", "Podstawy fizyki molekularnej"),
            new Course("Analiza", "Analiza matematyczna dla studentów informatyki"), new Course("Język Angielski",
                "Nauka języka na poziomie B2"))));
        students.add(new Student(300, "Leszek", "Kowalski", List.of(new Course("Język Angielski",
            "Nauka języka na poziomie B2"))));
        students.add(new Student(4440, "Anna", "Kowalska", List.of(new Course("Fizyka", "Podstawy fizyki molekularnej"),
            new Course("Analiza", "Analiza matematyczna dla studentów informatyki"))));

        Query<Student> query = equal(Student.STUDENT_NAME, "Tomasz");
        var elements = students.retrieve(query).stream().collect(Collectors.toList());
        assertEquals(1, elements.size());
    }

}
