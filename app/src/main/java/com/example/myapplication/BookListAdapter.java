package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class BookListAdapter extends ArrayAdapter <Book> {
    public interface Callback {
        void onSucessfulChange();
    }
    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Book book);
    }


    private Callback callback;

    private Context context;
    private List<Book> bookList;
    DatabaseHelper db;

    public BookListAdapter(Context context, List<Book> bookList, Callback cb, OnItemClickListener listener) {
        super(context, 0, bookList);
        this.context = context;
        this.bookList = bookList;
        db = new DatabaseHelper(context);
        callback = cb;
        this.itemClickListener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }

        // Get the book information for the current position
        final Book book = bookList.get(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(book);
                }
            }
        });
        // Bind the data to the TextView elements in the list item
        TextView textViewBookName = convertView.findViewById(R.id.textViewBookName);
        textViewBookName.setText(book.getBookName()+"\n \nClick to View Details");

        Button rentButton = convertView.findViewById(R.id.rentButton);

        System.out.println(book.getRentedBy() + " -> " + book.getAddedBy() + " -> " + User.currentUser.getId());
        if (book.getRentedBy() == User.currentUser.getId()) {
            rentButton.setVisibility(View.GONE);
        }
        rentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to Rent This Book?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.rentBook(book.getIsbn(), User.currentUser.getId());
                        Toast.makeText(context, "Rented Successfully", Toast.LENGTH_SHORT).show();
                        if (callback != null) {
                            callback.onSucessfulChange();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", null); // Do nothing when "Cancel" is clicked
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        Button deleteButton = convertView.findViewById(R.id.deleteButton);
        if (book.getAddedBy() != User.currentUser.getId()) {
            deleteButton.setVisibility(View.GONE);
        }
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to delete This Book?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.DeleteOne(book.getIsbn());
                        Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        if (callback != null) {
                            callback.onSucessfulChange();
                        }

                    }
                });
                builder.setNegativeButton("Cancel", null); // Do nothing when "Cancel" is clicked
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        Button returnButton = convertView.findViewById(R.id.returnButton);
        if (book.getRentedBy() != User.currentUser.getId()) {
            returnButton.setVisibility(View.GONE);
        }
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to Return This Book?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.returnBook(book.getIsbn());
                        Toast.makeText(context, "Returned Successfully", Toast.LENGTH_SHORT).show();
                        if (callback != null) {
                            callback.onSucessfulChange();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", null); // Do nothing when "Cancel" is clicked
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return convertView;
    }
}
