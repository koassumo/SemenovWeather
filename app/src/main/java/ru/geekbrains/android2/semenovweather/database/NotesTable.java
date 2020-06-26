package ru.geekbrains.android2.semenovweather.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class NotesTable {
    private final static String TABLE_NAME = "Notes";
    private final static String COLUMN_ID = "_id";
    private final static String COLUMN_NOTE = "note";
    private final static String COLUMN_TITLE = "title";

    static void createTable(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NOTE + " INTEGER);");
        //CREATE TABLE Notes (_id INTEGER PRIMARY KEY AUTOINCREMENT, note INTEGER)
    }

    static void onUpgrade(SQLiteDatabase database) {
        database.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COLUMN_TITLE
                + " TEXT DEFAULT 'Default title'");
    }

    public static void addNote(int note, SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE, note);

        database.insert(TABLE_NAME, null, values);
    }

    public static void editNote(int noteToEdit, int newNote, SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE, newNote);

        database.update(TABLE_NAME, values, COLUMN_NOTE + "=" + noteToEdit, null);
        //database.update(TABLE_NAME, values, "%s = %s", new String[] {COLUMN_NOTE, String.valueOf(noteToEdit)});
        /*database.execSQL("UPDATE " + TABLE_NAME + " set " + COLUMN_NOTE + " = " + newNote + " WHERE "
                + COLUMN_NOTE + " = " + noteToEdit + ";");*/
        //UPDATE Notes set note = 10 WHERE note = 5
    }

    public static void deleteNote(int note, SQLiteDatabase database) {
        database.delete(TABLE_NAME, COLUMN_NOTE + " = " + note, null);
    }

    public static void deleteAll(SQLiteDatabase database) {
        database.delete(TABLE_NAME, null, null);
        //DELETE * FROM Notes
    }

    public static List<Integer> getAllNotes(SQLiteDatabase database) {
        /*Cursor cursor = database.query(TABLE_NAME, new String[]{COLUMN_NOTE}, COLUMN_NOTE + " = 1", null,
                COLUMN_NOTE, null, "DESC");*/
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return getResultFromCursor(cursor);
    }

    private static List<Integer> getResultFromCursor(Cursor cursor) {
        List<Integer> result = null;

        if(cursor != null && cursor.moveToFirst()) {//попали на первую запись, плюс вернулось true, если запись есть
            result = new ArrayList<>(cursor.getCount());

            int noteIdx = cursor.getColumnIndex(COLUMN_NOTE);
            do {
                result.add(cursor.getInt(noteIdx));
            } while (cursor.moveToNext());
        }

        try { cursor.close(); } catch (Exception ignored) {}
        return result == null ? new ArrayList<Integer>(0) : result;
    }
}
