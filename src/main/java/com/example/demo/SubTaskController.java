package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SubTaskController {

    private final SubTaskRepository subTaskRepository;
    private final TaskRepository taskRepository;

    public SubTaskController(SubTaskRepository subTaskRepository,
                             TaskRepository taskRepository) {
        this.subTaskRepository = subTaskRepository;
        this.taskRepository = taskRepository;
    }

    @PostMapping("/subtask/add")
    public String addSubTask(@RequestParam Long taskId,
                             @RequestParam String title) {

        Task task = taskRepository.findById(taskId).orElseThrow();

        SubTask subTask = new SubTask();
        subTask.setTitle(title);
        subTask.setTask(task);

        subTaskRepository.save(subTask);

        return "redirect:/task/edit/" + taskId;
    }
    @PostMapping("/subtask/update")
    public String updateSubTask(@RequestParam Long id,
                                @RequestParam(required = false) Boolean done) {

        SubTask sub = subTaskRepository.findById(id).orElseThrow();

        sub.setDone(done != null && done);

        subTaskRepository.save(sub);

        return "redirect:/task/edit/" + sub.getTask().getId();
    }
    @PostMapping("/subtask/delete")
    public String deleteSubTask(@RequestParam Long id) {

        SubTask sub = subTaskRepository.findById(id)
                .orElseThrow();

        Long taskId = sub.getTask().getId();

        subTaskRepository.delete(sub);

        return "redirect:/task/edit/" + taskId;
    }
}