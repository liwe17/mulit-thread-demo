package com.thread.test;

/**
 * @Author: Doug Li
 * @Date 2021/2/5
 * @Describe: 使用场景2
 *
 * 假设一个用户系统，当一个请求进来的时候，一个线程会负责执行这个请求，然后这个请求就会依次调用service-1()、service-2()、service-3()
 * 这3个方法可能是分布在不同的类中的, 需要获取用户信息
 */
public class ThreadLocalDemo03 {

    public static void main(String[] args) {
        User user = new User("test");
        new Service1().service1(user);
    }

}

class Service1 {
    public void service1(User user){
        //给ThreadLocal赋值，后续的服务直接通过ThreadLocal获取就行了。
        UserContextHolder.holder.set(user);
        new Service2().service2();
    }
}

class Service2 {
    public void service2(){
        User user = UserContextHolder.holder.get();
        System.out.println("service2拿到的用户:"+user.name);
        new Service3().service3();
    }
}

class Service3 {
    public void service3(){
        User user = UserContextHolder.holder.get();
        System.out.println("service3拿到的用户:"+user.name);
        //在整个流程执行完毕后，一定要执行remove
        UserContextHolder.holder.remove();
    }
}

//ThreadLocal持有工具类
class UserContextHolder {
    //创建ThreadLocal保存User对象
    public static ThreadLocal<User> holder = new ThreadLocal<>();
}

//用户实体
class User {
    String name;
    public User(String name){
        this.name = name;
    }
}