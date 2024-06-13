package com.fcai.SoftwareTesting;

import com.fcai.SoftwareTesting.todo.Todo;
import com.fcai.SoftwareTesting.todo.TodoController;
import com.fcai.SoftwareTesting.todo.TodoCreateRequest;
import com.fcai.SoftwareTesting.todo.TodoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class SoftwareTestingApplicationTests {
	protected MockMvc mvc;
	@Mock
	public TodoServiceImpl todoService;
	@InjectMocks
	public TodoController todoController;
	@BeforeEach
	protected void setUp() {
		mvc = MockMvcBuilders.standaloneSetup(todoController).build();
		todoController = new TodoController(todoService);
	}

	@Test
	public void createTest() throws Exception{
		TodoCreateRequest request = new TodoCreateRequest("Todo", "Assignment 2");
		Todo todo = new Todo("Todo","Assignment 2");
		when(todoService.create(request)).thenReturn(todo);
		mvc.perform(post("/todo/create")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"title\": \"Todo\", \"description\": \"Assignment 2\"}"))
				.andExpect(status().isOk());
	}
	@Test
	public void readTest() throws Exception{
		Todo todo = new Todo("Todo","Assignment 2");
		when(todoService.read(todo.getId())).thenReturn(todo);
		mvc.perform(get("/read?id=1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	@Test
	public void deleteTest() throws Exception{
		mvc.perform(delete("/delete?id=1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void updateTest() throws Exception{
		Todo todo = new Todo("Todo","Assignment 2");
		todo.setCompleted(true);
		when(todoService.update(todo.getId(), true)).thenReturn(todo);

		mvc.perform(put("/todo/update?id=1&completed=true")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void listTest() throws Exception {
		// Arrange
		Todo todo1 = new Todo("Todo 1", "Description 1");
		Todo todo2 = new Todo("Todo 2", "Description 2");
		List<Todo> todos = new ArrayList<>();
		todos.add(todo1);
		todos.add(todo2);
		when(todoService.list()).thenReturn(todos);
		mvc.perform(get("/list")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void completedList() throws Exception{
		Todo todo1 = new Todo("Todo 1", "Description 1");
		Todo todo2 = new Todo("Todo 2", "Description 2");
		List<Todo> todos = new ArrayList<>();
		todos.add(todo1);
		todos.add(todo2);
		todo1.setCompleted(true);
		todo2.setCompleted(true);
		when(todoService.listCompleted()).thenReturn(todos);
		mvc.perform(get("/listCompleted")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

//	@Test
//	void contextLoads() {
//	}

}
