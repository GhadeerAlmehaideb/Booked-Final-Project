package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class Home extends AppCompatActivity
{
    Button btnLogout;
    Button btn_Add;
    ListView lv_BookList;
    DatabaseHelper db;
    private BookListAdapter bookListAdapter;
    TextView welcomeText;

    @Override
    protected void onResume() {
        super.onResume();
        ShowBooksOnListView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnLogout = findViewById(R.id.logout);
        btn_Add = findViewById(R.id.btn_Add);
        welcomeText = findViewById(R.id.welcome_text);

        lv_BookList = findViewById(R.id.lv_BookList);

        db = new DatabaseHelper(this);
        welcomeText.append(" " + User.currentUser.getFullName());
        ShowBooksOnListView();


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User.currentUser = null;
                Intent intoMain= new Intent(Home.this,MainActivity.class);
                startActivity(intoMain);
            }
        });

        btn_Add.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, AddBook.class));
            }
        });

        /*lv_BookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Book ClickedBook = (Book) adapterView.getItemAtPosition(i);
                dataBaseHelper.DeleteOne(ClickedBook);
                ShowBooksOnListView();
                Toast.makeText(Home.this, "Deleted" + ClickedBook.toString(), Toast.LENGTH_SHORT).show();
            }
        });*/

    }
    private void ShowBooksOnListView() {
        bookListAdapter = new BookListAdapter(this, db.getEverything(), new BookListAdapter.Callback() {
            @Override
            public void onSucessfulChange() {
                ShowBooksOnListView();
            }
        }, new BookListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Book book) {
                Intent intent = new Intent(Home.this, BookDetails.class);
                intent.putExtra("book", book);
                startActivity(intent);
            }
        });
        lv_BookList.setAdapter(bookListAdapter);
    }

}
