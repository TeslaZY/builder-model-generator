# builder-model-generator
   This plugin is used to automatically generate database entity classes.The purpose is to provide a plug-in that enables the automatically generated entity class to satisfy the builder design pattern, so that it is not necessary to call the setter method of different parameters multiple times when generating the entity object, because the constructor is split in multiple calls during the construction process. The JavaBean in question may be in an inconsistent state.At the same time, variable parameters are provided, and different construction methods are not required for different parameters.

 静态工厂和构造方法都有一个限制：它们不能很好地扩展到很多可选参数的情景。当变量数量较多以后，对构造参数传入参数值可能会不可控，而且容易出错，不易查询bug。如果使用JavaBean模式时——调用一个午餐构造函数，然后调用不同的setter方法来设置每个必须的参数——虽然有点冗长，但创建实例很容易，并且易于阅读所生成的代码，但是JavaBeans 模式本身有严重的缺陷。由于构造方法被分割成了多次调用，所以在构造过程中 JavaBean 可能处于不一致的状态。该类没有通过检查构造参数参数的有效性来强制一致性的选项。在不一致的状态下尝试使用对象可能会导致一些错误，这些错误与平常代码的BUG很是不同，因此很难调试。

   Builder模式的一种形式结合了可伸缩构造方法模式的安全性和 JavaBean 模式的可读性，客户端不直接构造所需的对象，而是调用一个包含所有必需参数的构造方法 (或静态工厂)得到获得一个 builder 对象。然后，客户端调用 builder 对象的与 setter 相似方法来设置你想设置的可选参数。最后，客户端调用builder对象的一个无参的 build 方法来生成对象，该对象通常是不可变的。

   比如如下的对象生成方式：

   User user = new User.Builder().userName("111").userEmail("11@111.com").userAge(0);

   这种方式代码易于理解，也不容易设置错误的参数。

   所以我设想能不能有一个工具，可以向mybatis插件一样，自动生成满足这种模式的实体类。

   安装和使用方法如下：

1、IDEA插件商店BuilderModelGenerator，下载使用即可（目前未通过Intellij Platform审核）

2、重启IDEA

3、找到已经写好变量的实体类，选中类名右键点击generator→ Genegrate Builder Model Class即可

4、在需要生成对象的代码中使用如下调用
User user = new User.Builder().userName("111").userEmail("111@111.com").userAddress("11").build();

附：生成的实体类代码样例

public class User {
    private String userName;
 private String userEmail;
 private int userAge;
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
