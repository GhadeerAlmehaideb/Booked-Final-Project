package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.EditText;

public class Book implements Parcelable {
    private String isbn;
    private String bookName;
    private String pubYear;
    private String author;
    private float price;
    private String rentStartedAt;
    private String rentEndedAt;
    private int rentedBy;
    private int addedBy;

    public Book(String isbn, String bookName, String pubYear, String author, float price) {
        this.isbn = isbn;
        this.bookName = bookName;
        this.pubYear = pubYear;
        this.author = author;
        this.price = price;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getPubYear() {
        return pubYear;
    }

    public void setPubYear(String pubYear) {
        this.pubYear = pubYear;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getRentStartedAt() {
        return rentStartedAt;
    }

    public void setRentStartedAt(String rentStartedAt) {
        this.rentStartedAt = rentStartedAt;
    }

    public String getRentEndedAt() {
        return rentEndedAt;
    }

    public void setRentEndedAt(String rentEndedAt) {
        this.rentEndedAt = rentEndedAt;
    }

    public int getRentedBy() {
        return rentedBy;
    }

    public void setRentedBy(int rentedBy) {
        this.rentedBy = rentedBy;
    }
    public int getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(int addedBy) {
        this.addedBy = addedBy;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(bookName);
        dest.writeString(author);
        dest.writeString(pubYear);
        dest.writeFloat(price);
        dest.writeString(isbn);
        dest.writeInt(rentedBy);
        dest.writeInt(addedBy);
    }
    protected Book(Parcel in) {
        bookName = in.readString();
        author = in.readString();
        pubYear = in.readString();
        price = in.readFloat();
        isbn = in.readString();
        rentedBy = in.readInt();
        addedBy = in.readInt();
    }
    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

}
