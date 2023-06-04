package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddBook extends AppCompatActivity {
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        db = new DatabaseHelper(this);
        Button btnCreateBook = findViewById(R.id.btnCreateBook);
        Button returnToHome = findViewById(R.id.returnToHome);
        returnToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddBook.this, Home.class));
            }
        });
        btnCreateBook.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText etBookName = findViewById(R.id.etBookName);
                EditText etISBN = findViewById(R.id.etISBN);
                EditText etPubYear = findViewById(R.id.etPubYear);
                EditText etAuthor = findViewById(R.id.etAuthor);
                EditText etPrice = findViewById(R.id.etPrice);

                String bookName = etBookName.getText().toString();
                String isbn = etISBN.getText().toString();
                String pubYear = etPubYear.getText().toString();
                String author = etAuthor.getText().toString();
                String price = etPrice.getText().toString();
                if (TextUtils.isEmpty(bookName)) {
                    etBookName.setError("Book Name is required");
                    return;
                }

                if (TextUtils.isEmpty(isbn)) {
                    etISBN.setError("ISBN is required");
                    return;
                }

                if (TextUtils.isEmpty(pubYear)) {
                    etPubYear.setError("Year of Publication is required");
                    return;
                }


                if (TextUtils.isEmpty(author)) {
                    etAuthor.setError("Author Name is required");
                    return;
                }

                if (TextUtils.isEmpty(price)) {
                    etPrice.setError("Price is required");
                    return;
                }
                float priceValue = Float.parseFloat(price);
                if (priceValue < 0) {
                    etPrice.setError("Price cannot be negative");
                    return;
                }
                Book newBook = new Book(isbn, bookName, pubYear, author, priceValue);
                db.addOne(newBook);
                Toast.makeText(getApplicationContext(), "Book entry created successfully", Toast.LENGTH_SHORT).show();


            }
        });
    }
}