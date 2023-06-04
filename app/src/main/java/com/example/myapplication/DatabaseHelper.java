package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String BOOK_TABLE = "Book_Table";
    public static final String COLUMN_BOOK_NAME = "Book_NAME";
    public static final String COLUMN_BOOK_ISBN = "BOOK_ISBN";
    public static final String COLUMN_AUTHOR = "AUTHOR_NAME";
    public static final String COLUMN_PUBYEAR = "PUB_YEAR";
    public static final String COLUMN_PRICE = "PRICE";

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FULL_NAME = "full_name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_CONTACT_NO = "contact_no";
    public static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_RENT_STARTED_AT = "rent_started_at";
    private static final String COLUMN_RENT_ENDED_AT = "rent_ended_at";
    private static final String COLUMN_RENTED_BY = "rented_by";
    private  static final String COLUMN_ADDED_BY = "added_by";
    public DatabaseHelper(@Nullable Context context) {
        super(context, "book.db", null, 1);
    }

    // when creating the database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_USERS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_FULL_NAME + " TEXT,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_CONTACT_NO + " TEXT,"
                + COLUMN_GENDER + " TEXT"
                + ")");
        String createTableStatement = "CREATE TABLE " + BOOK_TABLE + " ("
                + COLUMN_BOOK_NAME + " TEXT, "
                + COLUMN_BOOK_ISBN + " TEXT, "
                + COLUMN_AUTHOR + " TEXT, "
                + COLUMN_PUBYEAR + " TEXT, "
                + COLUMN_RENT_STARTED_AT + " TEXT, "
                + COLUMN_RENTED_BY + " INTEGER ,"
                + COLUMN_ADDED_BY + " INTEGER ,"
                + COLUMN_PRICE + " FLOAT"
                + ")";
        sqLiteDatabase.execSQL(createTableStatement);
        seedBooks(sqLiteDatabase);


    }
    private void seedBooks(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        // Book 1
        values.put(COLUMN_BOOK_NAME, "The Great Gatsby");
        values.put(COLUMN_BOOK_ISBN, "9780743273565");
        values.put(COLUMN_AUTHOR, "F. Scott Fitzgerald");
        values.put(COLUMN_PUBYEAR, "1925");
        values.put(COLUMN_PRICE, 9.99f);
        values.put(COLUMN_ADDED_BY, -1);
        db.insert(BOOK_TABLE, null, values);

        // Book 2
        values.clear();
        values.put(COLUMN_BOOK_NAME, "To Kill a Mockingbird");
        values.put(COLUMN_BOOK_ISBN, "9780061120084");
        values.put(COLUMN_AUTHOR, "Harper Lee");
        values.put(COLUMN_PUBYEAR, "1960");
        values.put(COLUMN_PRICE, 12.99f);
        values.put(COLUMN_ADDED_BY, -1);
        db.insert(BOOK_TABLE, null, values);

        // Add more books as needed

        // Book N
        // values.clear();
        // values.put(...);
        // db.insert(BOOK_TABLE, null, values);
    }
    // when upgrading
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public User getUser(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        String selection = COLUMN_EMAIL + " = ?";
        String[] selectionArgs = { email };

        Cursor cursor = db.query(TABLE_USERS, null, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            String fullName = cursor.getString(cursor.getColumnIndex(COLUMN_FULL_NAME));
            String password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
            String contactNo = cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NO));
            String gender = cursor.getString(cursor.getColumnIndex(COLUMN_GENDER));
            int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            user = new User(fullName, email, password, contactNo, gender);
            user.setId(id);
        }

        cursor.close();
        db.close();

        return user;
    }
    public boolean login(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = { email, password };

        Cursor cursor = db.query(TABLE_USERS, null, selection, selectionArgs, null, null, null);

        boolean loggedIn = cursor.moveToFirst();

        cursor.close();
        db.close();
        if (loggedIn) {
            User.currentUser = getUser(email);
        }

        return loggedIn;
    }
    public boolean insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FULL_NAME, user.getFullName());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_CONTACT_NO, user.getContactNo());
        values.put(COLUMN_GENDER, user.getGender());

        int newRowId = (int) db.insert(TABLE_USERS, null, values);
        if (newRowId != -1) {
            user.setId(newRowId);
            User.currentUser =user;
        }
        return newRowId != -1;
    }
    public boolean addOne(Book book){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();


        cv.put(COLUMN_BOOK_NAME, book.getBookName());
        cv.put(COLUMN_BOOK_ISBN, book.getIsbn());
        cv.put(COLUMN_AUTHOR, book.getAuthor());
        cv.put(COLUMN_PUBYEAR, book.getPubYear());
        cv.put(COLUMN_PRICE, book.getPrice());
        cv.put(COLUMN_ADDED_BY, User.currentUser.getId());

        long insert = db.insert(BOOK_TABLE, null, cv);
        if(insert == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean DeleteOne(String isbn) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = COLUMN_BOOK_ISBN + " = ?";
        String[] whereArgs = { isbn };
        int rowsDeleted = db.delete(BOOK_TABLE, whereClause, whereArgs);
        db.close();
        return rowsDeleted > 0;
    }
    public List<Book> getEverything(){
        List<Book> returnList = new ArrayList<>();
        // get data from database
        String queryString = "Select * from "+ BOOK_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            // loop through cursor results
            do{
                String BName = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_NAME));
                String ISBN = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_ISBN));
                String author = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR));
                String pubYear = cursor.getString(cursor.getColumnIndex(COLUMN_PUBYEAR));
                Float price = cursor.getFloat(cursor.getColumnIndex(COLUMN_PRICE));
                int addedBy = cursor.getInt(cursor.getColumnIndex(COLUMN_ADDED_BY));
                int rentedBy = cursor.getInt(cursor.getColumnIndex(COLUMN_RENTED_BY));
                Book newBook = new Book(ISBN,BName,pubYear,author,price);
                newBook.setAddedBy(addedBy);
                newBook.setRentedBy(rentedBy);
                returnList.add(newBook);
            }while (cursor.moveToNext());
        } else{
            // nothing happens. no one is added.
        }
        //close
        cursor.close();
        db.close();
        return returnList;
    }
    public void rentBook(String isbn, int rentedBy) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_RENT_STARTED_AT, getCurrentDateTime());
        values.put(COLUMN_RENTED_BY, rentedBy);

        String whereClause = COLUMN_BOOK_ISBN + " = ?";
        String[] whereArgs = {isbn};
        System.out.println(isbn + " BOO RENTED BY" +rentedBy );
        db.update(BOOK_TABLE, values, whereClause, whereArgs);
        db.close();
    }

    public void returnBook(String isbn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.putNull(COLUMN_RENT_STARTED_AT);
        values.putNull(COLUMN_RENTED_BY);

        String whereClause = COLUMN_BOOK_ISBN + " = ?";
        String[] whereArgs = {isbn};

        db.update(BOOK_TABLE, values, whereClause, whereArgs);
        db.close();
    }

    private String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

}