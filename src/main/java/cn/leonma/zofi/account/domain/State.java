package cn.leonma.zofi.account.domain;

/**
 * Created by Leon on 17/4/15.
 */
public enum State {
    ACTIVE(1), LOCKED(2);

    private final int state;
    State(int state) {
        this.state = state;
    }
    public static State valueOf(int value) {    //    手写的从int到enum的转换函数
        switch (value) {
            case 1:
                return ACTIVE;
            case 2:
                return LOCKED;
            default:
                return null;
        }
    }
    public int value() {
        return this.state;
    }
}
