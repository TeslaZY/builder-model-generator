import model.User;

/**
 * @Description
 * @Author teslazy
 * @Date 2019-08-16 19:18
 **/

public class Main {
    public static void main(String[] args){
        User user = new User.Builder().userName("zhangyuyu").userEmail("zhangyuyu1@xiaomi.com").userAddress("南京").build();
    }
}
