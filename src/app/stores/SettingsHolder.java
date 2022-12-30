package app.stores;

import java.util.ArrayList;

public class SettingsHolder {
    private ArrayList<Question> questions;
    private String username1;
    private String username2;
    private String category;

    public SettingsHolder(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public String getUsername1() {
        return username1;
    }

    public void setUsername1(String username1) {
        this.username1 = username1;
    }

    public String getUsername2() {
        return username2;
    }

    public void setUsername2(String username2) {
        this.username2 = username2;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
