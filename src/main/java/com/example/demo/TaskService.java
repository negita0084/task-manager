package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // 一覧取得
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    // 登録
    public void save(Task task) {
        taskRepository.save(task);
    }
    
    //詳細
    public Task findById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }
    
    //編集
    public void update(Task task) {
    	taskRepository.save(task);
    }
    
   //削除
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}