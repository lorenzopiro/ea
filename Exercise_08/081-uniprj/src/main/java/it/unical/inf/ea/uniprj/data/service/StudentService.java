package it.unical.inf.ea.uniprj.data.service;

import it.unical.inf.ea.uniprj.data.dao.UniSpecification;
import it.unical.inf.ea.uniprj.data.dto.StudentValue;
import it.unical.inf.ea.uniprj.data.entities.Student;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface StudentService {
  Student save(Student student);

  Collection<Student> findAll(Specification<Student> spec);

  int count(Student.Gender gender);

  List<Student> get(LocalDate date, String t);

  List<Student> getAllByBirthDate(LocalDate from, LocalDate to);

  List<Student> getByLastname(String lastname);

  List<Student> getByLastNameAndFirstName(String lastname, String firstname);

  List<StudentValue> countByGender();

  List<Student> getByGender(Student.Gender gender);

  List<Student> getByName(String name);

  List<Student> filter (UniSpecification.Filter f);
}
