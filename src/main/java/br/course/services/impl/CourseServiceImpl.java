package br.course.services.impl;

import br.course.models.CourseModel;
import br.course.models.LessonModel;
import br.course.models.ModuleModel;
import br.course.repositories.CourseRepository;
import br.course.repositories.LessonRepository;
import br.course.repositories.ModuleRepository;
import br.course.services.CourseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repository;
    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;

    @Transactional
    @Override
    public void delete(CourseModel courseModel) {
        List<ModuleModel> moduleModelList = moduleRepository.findALLModulesIntoCourse(courseModel.getCourseId());
        if (moduleModelList != null && !moduleModelList.isEmpty()) {
            moduleModelList.forEach(module -> {
                List<LessonModel> lessonModelList = lessonRepository.findAllLessonsIntoModule(module.getModuleId());
                if (lessonModelList != null && !lessonModelList.isEmpty()) {
                    lessonRepository.deleteAll(lessonModelList);
                }
            });
            moduleRepository.deleteAll(moduleModelList);
        }
        repository.delete(courseModel);
    }

    @Override
    public CourseModel save(CourseModel courseModel) {
        return repository.save(courseModel);
    }

    @Override
    public Optional<CourseModel> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Page<CourseModel> findAll(Specification<CourseModel> spec, Pageable pageable) {
        return repository.findAll(spec, pageable);
    }

}
