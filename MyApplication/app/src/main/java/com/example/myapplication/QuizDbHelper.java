package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.QuizContract.*;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Buckle_Up.db";
    private static final int DATABASE_VERSION = 4;

    private SQLiteDatabase db;

    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    private void fillQuestionsTable() {
        Question q1 = new Question("What is recurrence for worst case of QuickSort and what is the time complexity in Worst case?", "Recurrence is T(n) = T(n-2) + O(n) and time complexity is O(n^2)", "Recurrence is T(n) = T(n-1) + O(n) and time complexity is O(n^2)", "Recurrence is T(n) = 2T(n/2) + O(n) and time complexity is O(nLogn)", 2);
        addQuestion(q1);
        Question q2 = new Question("Which of the following is not a stable sorting algorithm in its typical implementation.", "Insertion Sort", "Merge Sort", "Quick Sort", 3);
        addQuestion(q2);
        Question q3 = new Question("Which of the following is not true about comparison based sorting algorithms?", "Counting Sort is not a comparison based sorting algortih", "Heap Sort is not a comparison based sorting algorithm.", "Any comparison based sorting algorithm can be made stable by using position as a criteria when two elements are compared", 2);
        addQuestion(q3);
        Question q4 = new Question("Which of the following sorting algorithms has the lowest worst-case complexity?", "Merge Sort", "Bubble Sort", "Quick Sort", 1);
        addQuestion(q4);
        Question q5 = new Question("The worst case running times of Insertion sort, Merge sort and Quick sort, respectively, are:", "Θ(n2), Θ(n log n) and Θ(n2)", "Θ(n2), Θ(n log n) and Θ(n log n)", "Θ(n2), Θ(n2) and Θ(n Log n)", 1);
        addQuestion(q5);
        Question q6 = new Question("What is the best sorting algorithm to use for the elements in array are more than 1 million in general?", "Merge sort.", "Bubble sort.", "Quick sort.", 3);
        addQuestion(q6);
        Question q7 = new Question("Which of the below given sorting techniques has highest best-case runtime complexity.", "Quick sort", "Bubble sort", "Selection sort", 3);
        addQuestion(q7);
        Question q8 = new Question("A sorting technique is called stable if:", "It takes O(nlog n)time", "It maintains the relative order of occurrence of non-distinct elements", "It uses divide and conquer paradigm", 2);
        addQuestion(q8);
        Question q9 = new Question("Which one of the following in place sorting algorithms needs the minimum number of swaps?", "Quick sort", "Quick sort", "Selection sort", 3);
        addQuestion(q9);
        Question q10 = new Question("Which of the following algorithm design technique is used in merge sort?", "Greedy method", "Backtracking", "Divide and Conquer", 3);
        addQuestion(q10);
    }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}