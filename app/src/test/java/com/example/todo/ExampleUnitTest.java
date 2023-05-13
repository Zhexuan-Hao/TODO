package com.example.todo;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.todo.RealtimeDatabase.EventService;
import com.example.todo.Room.Entity.Event;

import java.util.Date;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void realtimedatabase() {
        Event event = new Event();
        event.setUser_id("123456");
        event.setEvent_id(0);
        event.setTitle("test");
        event.setContent("testtest");
        event.setDate(new Date());
        event.setStatus(0);

        EventService service = new EventService();
        service.insertEvent(event);
    }
}