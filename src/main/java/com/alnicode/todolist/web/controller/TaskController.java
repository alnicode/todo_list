package com.alnicode.todolist.web.controller;

import com.alnicode.todolist.domain.dto.TaskRequest;
import com.alnicode.todolist.domain.dto.TaskResponse;
import com.alnicode.todolist.domain.dto.TodoListResponse;
import com.alnicode.todolist.domain.service.ICrudService;
import com.alnicode.todolist.domain.service.ITaskService;
import com.alnicode.todolist.domain.service.ITodoListService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import static com.alnicode.todolist.util.AppConstants.JWT_NAME;

@RestController
@RequestMapping("/tasks")
@Validated
public class TaskController extends CrudController<TaskRequest, TaskResponse> {
    @Autowired
    private ITaskService service;

    @Autowired
    private ITodoListService todoListService;

    @Override
    protected ICrudService<TaskRequest, TaskResponse> service() {
        return this.service;
    }

    @GetMapping("/{id}/todolist")
    @ApiOperation(value = "Get the to-do list by task id", authorizations = {@Authorization(value = JWT_NAME)})
    public ResponseEntity<TodoListResponse> getTodoList(@NotNull @Min(1L) @PathVariable("id") long taskId) {
        return ResponseEntity.of(this.todoListService.getByTask(taskId));
    }

    @GetMapping("/status/{status}")
    @ApiOperation(value = "Get all the tasks by status", authorizations = {@Authorization(value = JWT_NAME)})
    public ResponseEntity<List<TaskResponse>> getByStatus(@NotNull @PathVariable("status") boolean status) {
        return ResponseEntity.of(this.service.getByStatus(status));
    }
    
    @PostMapping("/{id}/tag/{tagId}")
    @ApiOperation(value = "Add an existing tag to the task", authorizations = {@Authorization(value = JWT_NAME)})
    public ResponseEntity<TaskResponse> addTag(@NotNull @Min(1L) @PathVariable("id") long taskId,
            @NotNull @Min(1L) @PathVariable("tagId") long tagId) {
        return ResponseEntity.of(this.service.addTag(taskId, tagId));
    }

    @DeleteMapping("/{id}/tag/{tagId}")
    @ApiOperation(value = "Remove an existing tag to the task", authorizations = {@Authorization(value = JWT_NAME)})
    public ResponseEntity<TaskResponse> removeTag(@NotNull @Min(1L) @PathVariable("id") long taskId,
            @NotNull @Min(1L) @PathVariable("tagId") long tagId) {
        return ResponseEntity.of(this.service.removeTag(taskId, tagId));
    }
}
