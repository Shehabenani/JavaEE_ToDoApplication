package com.fcai.SoftwareTesting;

import com.fcai.SoftwareTesting.todo.Todo;
import com.fcai.SoftwareTesting.todo.TodoCreateRequest;
import com.fcai.SoftwareTesting.todo.TodoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class TodoServiceTest {

    private TodoServiceImpl todoService;

    @BeforeEach //before each method it initalize this
    public void doBeforeEachTestCase() {
        todoService = new TodoServiceImpl();
        //TodoServiceImpl.todos = new ArrayList<>();
    }

    @Test
    public void testCreateTodo() {
        TodoCreateRequest todoCreateRequest = new TodoCreateRequest("ToDo List", "Read a book");
        // Todo todo = new Todo("1","ToDo List","Read a book",false);


        Todo todoResult = todoService.create(todoCreateRequest);

        //Check that created result is not null
        assertNotNull(todoResult);

        assertEquals("1", todoResult.getId());
        assertEquals("ToDo List", todoResult.getTitle());
        assertEquals("Read a book", todoResult.getDescription());
    }

    @Test
    void testCreateWithNullRequest() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            todoService.create(null);
        });
        assertEquals("Todo cannot be null", exception.getMessage());
    }

    @Test
    public void testCreateWithEmptyTitle() {
        TodoCreateRequest todoResult = new TodoCreateRequest("", "Read a book");

        IllegalArgumentException argumentException = assertThrows(IllegalArgumentException.class, () -> {
            todoService.create(todoResult);

        });
        assertEquals("Todo title cannot be empty", argumentException.getMessage());
    }

    @Test
    public void testCreateWithEmptyDesc() {
        TodoCreateRequest todoResult = new TodoCreateRequest("ToDo List", "");
        IllegalArgumentException argumentException = assertThrows(IllegalArgumentException.class, () -> {
            todoService.create(todoResult);

        });
        assertEquals("Todo description cannot be empty", argumentException.getMessage());
    }

    @Test
    public void testReadWithNullId() {
        IllegalArgumentException argumentException = assertThrows(IllegalArgumentException.class, () -> {
            todoService.read(null);

        });
        assertEquals("Todo id cannot be null", argumentException.getMessage());
    }

    @Test
    public void testReadWithEmptyId() {
        IllegalArgumentException argumentException = assertThrows(IllegalArgumentException.class, () -> {
            todoService.read("");

        });
        assertEquals("Todo id cannot be empty", argumentException.getMessage());
    }

    @Test
    public void testReadWithId() {
       TodoCreateRequest todolist = new TodoCreateRequest("ToDo", "Read a book");
       Todo result = todoService.create(todolist);
       Todo todo = todoService.read(result.getId());

       //Checking the Te id is not null
        assertNotNull(todo);

        //assertEquals("1", todo);
        assertEquals("ToDo", todo.getTitle());
        assertEquals("Read a book",todo.getDescription());

    }

    @Test
    public void testReadNullTodo(){
        TodoServiceImpl todoService = new TodoServiceImpl();

        //create some dummy objects
        Todo todo1 = new Todo("1", "ToDo", "Read a book", false);
        Todo todo2 = new Todo("2", "Done", "Do a task", false);
        Todo todo3 = new Todo("3", "In_Progress", "Have lunch", false);

        //add the dummy objects
        todoService.create(new TodoCreateRequest(todo1.getTitle(), todo1.getDescription()));
        todoService.create(new TodoCreateRequest(todo2.getTitle(), todo2.getDescription()));
        todoService.create(new TodoCreateRequest(todo3.getTitle(), todo3.getDescription()));

        // Test reading a non-existent Todo
        IllegalArgumentException exception =assertThrows(IllegalArgumentException.class, () -> todoService.read("4"));
        assertEquals("Todo not found",exception.getMessage());
    }

    @Test
    public void testReadTodoNotFoundId() {
        //List with Id--> 2 is not found in the todos list
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            todoService.read("2");
        });
        assertEquals("Todo not found", thrown.getMessage());
    }
    @Test
    public void testUpdate() {
        TodoCreateRequest todoListRequest = new TodoCreateRequest("ToDo", "Read a Book");
        Todo createdTodo = todoService.create(todoListRequest);

        Todo updatedTodo = todoService.update(createdTodo.getId(), true);

        assertNotNull(updatedTodo); //checking that updated todo is not null
        assertEquals(createdTodo.getId(), updatedTodo.getId());//checking that the same todoList is the updated one
        assertTrue(updatedTodo.isCompleted());
    }

    @Test
    public void testDelete() {
        //creating a dummy todo request or testing
        TodoCreateRequest todoListRequest = new TodoCreateRequest("ToDo", "Read a Book");
        Todo todo = todoService.create(todoListRequest);

        todoService.delete(todo.getId()); //deleting the list given its Id

        IllegalArgumentException argumentException = assertThrows(IllegalArgumentException.class, () -> {
            todoService.read(todo.getId());
        });
        assertEquals("Todo not found", argumentException.getMessage());
    }

    @Test
    public void testListTodos() {
        //creating 3 dummy TodoCreateRequest objects for testing
        TodoCreateRequest request1 = new TodoCreateRequest("ToDo", "Read a Book");
        TodoCreateRequest request2 = new TodoCreateRequest("In_Progress", "Do Assignment");
        TodoCreateRequest request3 = new TodoCreateRequest("Done", "Watch a movie");

        //creating the above requests
        todoService.create(request1);
        todoService.create(request2);
        todoService.create(request3);

        List<Todo> todos = todoService.list(); //setting The list of objects in todos object

        assertNotNull(todos);
        assertEquals(3, todos.size());
    }

  @Test
   public void testCompletedToDos(){
        //creating 3 dummy request objects
      TodoCreateRequest request1 = new TodoCreateRequest("ToDo", "Read a Book");
      TodoCreateRequest request2 = new TodoCreateRequest("In_Progress", "Do Assignment");
      TodoCreateRequest request3 = new TodoCreateRequest("Done", "Watch a movie");

      Todo todo1=todoService.create(request1);
      Todo todo2=todoService.create(request2);
      Todo todo3=todoService.create(request3);

      todoService.update(todo1.getId(), true); //setting that todo1  completed successfully
      todoService.update(todo2.getId(), true);//setting that todo2  completed successfully
      todoService.update(todo3.getId(), false);//setting that todo3  didnt complete

      List<Todo> completedTodos = todoService.listCompleted(); //push the completed todos in the completed todos object
      assertEquals(2, completedTodos.size()); //todo1 , todo2
      assertTrue(completedTodos.get(0).isCompleted()); //todo1
      assertTrue(completedTodos.get(1).isCompleted()); //todo2

      //assertFalse(completedTodos.get(2).isCompleted());//todo3 is not completed

  }




}




