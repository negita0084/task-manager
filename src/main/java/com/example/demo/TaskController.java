package com.example.demo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TaskController {

    private final TaskService taskService;

    // コンストラクタでDI
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
        System.out.println("TaskController生成");
    }

    @GetMapping("/task/new")
    public String showForm() {
        System.out.println("showForm実行");
        return "task-Registration";
    }
    @GetMapping("/task/detai/{id}")
    public String showdata(@PathVariable Long id, Model model) {
        Task task = taskService.findById(id);
        System.out.println("showdata実行");
        model.addAttribute("task", task);
        return "task-detai";
    }
    @GetMapping("/task/edit/{id}")
    public String showedit(
            @PathVariable Long id,
            Model model) {
        Task task = taskService.findById(id);
        model.addAttribute("task", task);
        return "task-edit";
    }
    
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "index";
    }
    
    @GetMapping("/task")
    public String listTasks(@RequestParam(required = false) String filter, Model model) {

        List<Task> tasks = taskService.findAll();

        if (filter != null) {

            tasks = tasks.stream().filter(task -> {

                long days = ChronoUnit.DAYS.between(
                    LocalDate.now(),
                    task.getDeadline()
                );

                switch (filter) {
                    case "3days":
                        return days <= 3 && days >= 2;
                    case "1day":
                        return days == 1;
                    case "today":
                        return days == 0;
                    case "overdue":
                        return days < 0;
                    case "done":
                        return task.getStatus().equals("完了");
                    default:
                        return true;
                }
            }).toList();
        }
        model.addAttribute("tasks", tasks);
        model.addAttribute("filter", filter);
        return "index";
    }
    @PostMapping("/task/edit")
    public String updateTask(Task task) {

        taskService.update(task);

        return "redirect:/task/detai/" + task.getId();
    }
    @PostMapping("/add")
    public String addTask(Task task) {
        System.out.println("登録完了");
        taskService.save(task);
        return "redirect:/";
    }
    @PostMapping("/task/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.delete(id);
        return "redirect:/";
    }
    
}