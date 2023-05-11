package com.example.todo.Room.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.todo.Room.Converter.DateConverter;
import com.example.todo.Room.Dao.EventDao;
import com.example.todo.Room.Entity.Event;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Event.class}, version = 1)
@TypeConverters({DateConverter.class})
public abstract class EventDatabase extends RoomDatabase {

    public abstract EventDao eventDao();

    private static volatile EventDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static EventDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (EventDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    EventDatabase.class, "event_database")
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);

                                    // If you want to keep data through app restarts,
                                    // comment out the following block
                                    databaseWriteExecutor.execute(() -> {
                                        // Populate the database in the background.
                                        // If you want to start with more words, just add them.
                                        EventDao dao = INSTANCE.eventDao();
                                        dao.deleteAllEvents();

                                        Date date = new Date();
                                        Event event = new Event();
                                        event.setTitle("test");
                                        event.setStatus(0);
                                        event.setContent("123456");
                                        event.setUser_id("lIvgkGKSvePj3pmEH7BZr4fI6vm2");
                                        event.setDate(date);
                                        dao.insertEvent(event);

                                    });
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                EventDao dao = INSTANCE.eventDao();
                dao.deleteAllEvents();

                Event event = new Event();
                event.setTitle("test");
                event.setStatus(0);
                event.setContent("123456");
                event.setUser_id("lIvgkGKSvePj3pmEH7BZr4fI6vm2");
                dao.insertEvent(event);

            });
        }
    };
}
