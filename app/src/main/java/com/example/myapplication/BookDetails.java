package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BookDetails extends AppCompatActivity {
    private Book book;
    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        db = new DatabaseHelper(this);
        Intent intent = getIntent();
        if (intent != null) {
            book = intent.getParcelableExtra("book");
            if (book != null) {
                TextView bn = findViewById(R.id.textViewBookName);
                bn.setText(book.getBookName());
                TextView author = findViewById(R.id.textViewAuthor);
                author.setText("Author: " + book.getAuthor());
                TextView isbn = findViewById(R.id.textViewISBN);
                isbn.setText("ISBN: " + book.getIsbn());
                TextView tvp = findViewById(R.id.textViewPrice);
                tvp.setText("Price: " +Float.toString(book.getPrice()));
                TextView tvpy = findViewById(R.id.textViewPubYear);
                tvpy.setText("Publication Year: " +book.getPubYear());
            }
        }

        Button rentButton = findViewById(R.id.rentButton);
        if (book.getRentedBy() == User.currentUser.getId()) {
            rentButton.setVisibility(View.GONE);
        }
        rentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BookDetails.this);
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to Rent This Book?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.rentBook(book.getIsbn(), User.currentUser.getId());
                        Toast.makeText(BookDetails.this, "Rented Successfully", Toast.LENGTH_SHORT).show();
                        finish();

                    }
                });
                builder.setNegativeButton("Cancel", null); // Do nothing when "Cancel" is clicked
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        Button deleteButton = findViewById(R.id.deleteButton);
        if (book.getAddedBy() != User.currentUser.getId()) {
            deleteButton.setVisibility(View.GONE);
        }
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BookDetails.this);
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to delete This Book?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.DeleteOne(book.getIsbn());
                        Toast.makeText(BookDetails.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                builder.setNegativeButton("Cancel", null); // Do nothing when "Cancel" is clicked
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        Button returnButton = findViewById(R.id.returnButton);
        if (book.getRentedBy() != User.currentUser.getId()) {
            returnButton.setVisibility(View.GONE);
        }
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BookDetails.this);
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to Return This Book?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.returnBook(book.getIsbn());
                        Toast.makeText(BookDetails.this, "Returned Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                builder.setNegativeButton("Cancel", null); // Do nothing when "Cancel" is clicked
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }
}