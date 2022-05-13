package gsonClasses;

import user.model.User;

public class UserPutRequestData {
    private int task;
    private User user;

    public UserPutRequestData(int task, User user) {
        this.task = task;
        this.user = user;
    }

    public int getTask() {
        return task;
    }

    public void setTask(int task) {
        this.task = task;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
