package uz.quickly.domain.VM;

public class UserWpmVM {

    private Long userId;

    private Long wpm;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getWpm() {
        return wpm;
    }

    public void setWpm(Long wpm) {
        this.wpm = wpm;
    }

    @Override
    public String toString() {
        return "UserWpmVM{" +
                "userId=" + userId +
                ", wpm=" + wpm +
                '}';
    }
}
