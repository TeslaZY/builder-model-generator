package model;

/**
 * @Description
 * @Author teslazy
 * @Date 2019-08-16 19:13
 **/

public class User {
    private String userName;
    private String userEmail;
    private int  userAge;
    private String userSalary;
    private String userTelephone;
    private String userAddress;
    private String userHeight;

    private User(Builder builder) {
        userName = builder.userName;
        userEmail = builder.userEmail;
        userAge = builder.userAge;
        userSalary = builder.userSalary;
        userTelephone = builder.userTelephone;
        userAddress = builder.userAddress;
        userHeight = builder.userHeight;

    }


    public static class Builder {
        private String userName;
        private String userEmail;
        private int userAge;
        private String userSalary;
        private String userTelephone;
        private String userAddress;
        private String userHeight;

        public Builder userName(String var) {
            userName = var;
            return this;
        }

        public Builder userEmail(String var) {
            userEmail = var;
            return this;
        }

        public Builder userAge(int var) {
            userAge = var;
            return this;
        }

        public Builder userSalary(String var) {
            userSalary = var;
            return this;
        }

        public Builder userTelephone(String var) {
            userTelephone = var;
            return this;
        }

        public Builder userAddress(String var) {
            userAddress = var;
            return this;
        }

        public Builder userHeight(String var) {
            userHeight = var;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
