package edu.hbuas.campustodo.service;

import edu.hbuas.campustodo.model.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import edu.hbuas.campustodo.model.Priority;

import java.util.List;

class TaskServiceTest {

    @Test
    void shouldAddTask() {
        TaskService service = new TaskService();

        var task = service.addTask("完成需求评审");

        assertEquals(1L, task.getId());
        assertEquals("完成需求评审", task.getTitle());
        assertFalse(task.isCompleted());
        assertEquals(1, service.listAll().size());
    }

    @Test
    void shouldRejectBlankTitle() {
        TaskService service = new TaskService();

        assertThrows(IllegalArgumentException.class,
                () -> service.addTask("   "));
    }
    @Test
    @DisplayName("根据优先级筛选任务，只返回匹配优先级的任务")
    void testFilterByPriority() {
        Task highTask1 = service.addTask("写高优先级作业");
        highTask1.setPriority(Priority.HIGH);

        Task highTask2 = service.addTask("复习高优先级考试");
        highTask2.setPriority(Priority.HIGH);

        Task lowTask = service.addTask("看剧");
        lowTask.setPriority(Priority.LOW);

        List<Task> highPriorityTasks = service.filterByPriority(Priority.HIGH);

        assertAll(
            () -> assertEquals(2, highPriorityTasks.size(), "应该只筛出 2 个高优先级任务"),
            () -> assertTrue(highPriorityTasks.stream().allMatch(t -> t.getPriority() == Priority.HIGH), "筛选出的任务必须都是 HIGH 优先级")
        );
    }
}
