package br.course.services.impl;

import br.course.models.LessonModel;
import br.course.models.ModuleModel;
import br.course.repositories.LessonRepository;
import br.course.repositories.ModuleRepository;
import br.course.services.ModuleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository repository;
    private final LessonRepository lessonRepository;

    @Transactional
    @Override
    public void delete(ModuleModel moduleModel) {
        List<LessonModel> lessonModelList = lessonRepository.findAllLessonsIntoModule(moduleModel.getModuleId());
        if (lessonModelList != null && !lessonModelList.isEmpty()) {
            lessonRepository.deleteAll(lessonModelList);
        }
        repository.delete(moduleModel);
    }

}
