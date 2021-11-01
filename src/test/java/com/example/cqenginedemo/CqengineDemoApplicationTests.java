package com.example.cqenginedemo;

import com.googlecode.cqengine.ConcurrentIndexedCollection;
import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.codegen.AttributeBytecodeGenerator;
import com.googlecode.cqengine.index.navigable.NavigableIndex;
import com.googlecode.cqengine.index.radixreversed.ReversedRadixTreeIndex;
import com.googlecode.cqengine.query.parser.cqn.CQNParser;
import com.googlecode.cqengine.query.parser.sql.SQLParser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.Serializable;
import java.util.List;

import static com.googlecode.cqengine.codegen.AttributeBytecodeGenerator.createAttributes;
import static com.googlecode.cqengine.codegen.MemberFilters.ALL_MEMBERS;
import static com.googlecode.cqengine.codegen.MemberFilters.METHODS_ONLY;
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
    public static final Student STUDENT_TWO = new Student(300, "Leszek", "Kowalski", List.of(ANGIELSKI));
    public static final Student STUDENT_ONE = new Student(1, "Tomasz", "Nowak", List.of(FIZYKA, ANALIZA, ANGIELSKI));
    public static final Student STUDENT_THREE = new Student(4440, "Anna", "Kowalska", List.of(FIZYKA, ANALIZA, MATEMATYKA_DYSKRETNA));
    public static final Student STUDENT_FOUR = new Student(5556, "Lucyna", "Małolepsza", List.of(MATEMATYKA_DYSKRETNA));

    public static final List<Student> STUDENT_LIST = List.of(STUDENT_ONE, STUDENT_TWO, STUDENT_THREE, STUDENT_FOUR );

    @Test
    void simpleQueriesByFunctions() {

        var students = new ConcurrentIndexedCollection<Student>();

        students.addIndex(NavigableIndex.onAttribute(Student.STUDENT_ID));
        students.addIndex(ReversedRadixTreeIndex.onAttribute(Student.STUDENT_NAME));
        students.addAll(STUDENT_LIST);

        var queryOne = equal(Student.STUDENT_NAME, "Tomasz");
        assertEquals(1, students.retrieve(queryOne).size());

        var queryTwo = startsWith(Student.STUDENT_SURNAME, "Kowal");
        assertEquals(2, students.retrieve(queryTwo).size());

        var queryThree = and(startsWith(Student.STUDENT_SURNAME, "Kowal"),
            greaterThan(Student.STUDENT_ID, 300));
        assertEquals(1, students.retrieve(queryThree).size());

    }

    @Test
    void simpleQueriesBySQL() {
        var students = new ConcurrentIndexedCollection<Student>();
        var parser = new SQLParser<>(Student.class);
        parser.registerAttributes(Student.getAllAttributes());
        students.addIndex(NavigableIndex.onAttribute(Student.STUDENT_ID));
        students.addIndex(ReversedRadixTreeIndex.onAttribute(Student.STUDENT_NAME));
        students.addAll(STUDENT_LIST);

        var queryOne = equal(Student.COURSE_NAME, "Tomasz");
        assertEquals(1,
            parser.retrieve(students, "SELECT * FROM students WHERE (course_name in ('Fizyka') and name LIKE 'Tom%')").size());

        assertEquals(1,
            parser.retrieve(students, "SELECT * FROM students WHERE name LIKE 'Tom%'").size());
    }

    @Test
    void nestedQuery() {

        var students = new ConcurrentIndexedCollection<Student>();

        students.addIndex(NavigableIndex.onAttribute(Student.STUDENT_ID));
        students.addIndex(ReversedRadixTreeIndex.onAttribute(Student.STUDENT_NAME));

        students.addAll(STUDENT_LIST);

        var queryOne = contains(Student.COURSE_NAME, "Matematyka dyskretna");
        assertEquals(2, students.retrieve(queryOne).size());

    }

}
