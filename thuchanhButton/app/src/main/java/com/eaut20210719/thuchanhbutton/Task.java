package com.eaut20210719.thuchanhbutton;

public class Task {
    private String name;
    private boolean isCompleted;
    private boolean isPriority;

    public Task(String name, boolean isCompleted, boolean isPriority) {
        this.name = name;
        this.isCompleted = isCompleted;
        this.isPriority = isPriority;
    }

    public String getName() {
        return name;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public boolean isPriority() {
        return isPriority;
    }

    public void setPriority(boolean priority) {
        isPriority = priority;
    }
}
