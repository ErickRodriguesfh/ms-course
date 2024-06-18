package br.course.dto;

import br.course.enums.CourseLevel;
import br.course.enums.CourseStatus;

import java.util.UUID;

public record CourseDto(String name, String description, String imageUrl, CourseStatus courseStatus, UUID userInstructor, CourseLevel courseLevel) {
}
