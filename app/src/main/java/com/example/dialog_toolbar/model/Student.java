package com.example.dialog_toolbar.model;

import com.example.dialog_toolbar.utils.Utils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("unused")
public class Student {
    @JsonProperty("id")
    private String mId;
    private FullName mFullName;
    @JsonProperty("gender")
    private String mGender;
    @JsonProperty("birth_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date mBirthDate;
    @JsonProperty("email")
    private String mEmail;
    @JsonProperty("address")
    private String mAddress;
    @JsonProperty("major")
    private String mMajor;
    @JsonProperty("gpa")
    private float mGpa;
    @JsonProperty("year")
    private int mYear;


    public Student() {
    }
    public Student(String id ){
        this.mId = id;
    }

    @JsonProperty("full_name")
    public void unPackFullName(Map<String, Object> fullName){
        this.mFullName = new FullName(
                Objects.requireNonNull(fullName.get("first")).toString(),
                Objects.requireNonNull(fullName.get("last")).toString(),
                Objects.requireNonNull(fullName.get("mid")).toString()
        );
    }
    public String getId() {
        return mId;
    }

    public String getFullName() {
        return mFullName.mLast + " " + mFullName.mMid + " " + mFullName.mFirst;
    }

    public String getFirstName(){
        return mFullName.getFirst();
    }
    public String getLastName(){
        return mFullName.getLast();
    }
    public String getGender() {
        return mGender;
    }

    public String getBirthDate() {
        return Utils.dateToString(mBirthDate);
    }

    public String getEmail() {
        return mEmail;
    }

    public String getAddress() {
        return mAddress;
    }

    public String getMajor() {
        return mMajor;
    }

    public float getGpa() {
        return mGpa;
    }

    public int getYear() {
        return mYear;
    }

    public void setId(String id) {
        mId = id;
    }

    public void setFullName(String fullName) {
        if (this.mFullName == null) {
            this.mFullName = new FullName();
        }
        this.mFullName.setFullName(fullName);
    }


    public void setGender(String gender) {
        mGender = gender;
    }

    public void setBirthDate(String birthDate) {
        mBirthDate = Utils.stringToDate(birthDate);
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public void setMajor(String major) {
        mMajor = major;
    }

    public void setGpa(float gpa) {
        mGpa = gpa;
    }

    public void setYear(int year) {
        mYear = year;
    }

    static class FullName {
        @JsonProperty("first")
        private String mFirst;
        @JsonProperty("last")
        private String mLast;
        @JsonProperty("mid")
        private String mMid;

        public FullName(){

        }
        public FullName(String first, String last, String mid) {
            mFirst = first;
            mLast = last;
            mMid = mid;
        }
        public void setFullName(String fullName) {
            String[] names = fullName.split("\\s+");
            if (names.length == 1) {
              setFirst(names[0]);
            } else if (names.length == 2) {
                setFirst(names[0]);
                setLast(names[1]);
            } else if (names.length > 2) {
                StringBuilder mid = new StringBuilder();
                setFirst(names[names.length - 1]);
                setLast(names[0]);
                for (int i = 1; i < names.length - 1; i++) {
                    mid.append(names[i]).append(" ");
                }
                setMid(mid.toString());
            }
        }

        public String getFirst() {
            return mFirst;
        }

        public void setFirst(String first) {
            mFirst = first;
        }

        public String getLast() {
            return mLast;
        }

        public void setLast(String last) {
            mLast = last;
        }

        public String getMid() {
            return mMid;
        }

        public void setMid(String mid) {
            mMid = mid;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(mId, student.mId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId);
    }
}
