package org.example.design.behavioral.mediator.perfect;

/**
 *  此函数实现了中介模式 + 命令模式 的最终版，同时也解决了命令模式的不足之处！
 *
 *  结构：
 *                             数据库命令抽象父类                                                                                     数据库父类[需要命令类]                              中介抽象父类[保存各个类型的实体类]                    数据库枚举类型
 *                                                                                                                              ┌────────────────────┐
 *                                                                                                                              │AbstractDatabase<T> │
 *                                      ┌---------------------------------------------------------------------------------------│ └databaseCommand   │
 *                                      │                                                                                       └────────────────────┘
 *                                      │                                                                                                  ▲
 *                                      │                                                                                                  │  同事抽象父类继承数据库[引用中介类]
 *                                      │                                                                                         ┌─────────────────┐
 *                                      ↓                                                                                         │Colleague<T>     │                           ┌───────────────────────────────────────┐
 *                            ┌──────────────────┐                                                                                │ └mediator       │-------------------------->│ AbstractMediator<T>                   │         ┌───────────────┐
 *                            │DatabaseCommand   │                                                                                └─────────────────┘                           │  └dataMap<DatabaseType, Colleague<T>> │-------->│ DatabaseType  │
 *                            │ └data            │                                                                                         ▲                                    │  └changed(DatabaseCommand<T> command) │         │   └MYSQL      │
 *                            │ └execute(dataMap)│                                                              ┌──────────────────────────┼───────────────────────┐            └───────────────────────────────────────┘         │   └REDIS      │
 *                            └──────────────────┘                                                      ┌──────────────┐           ┌─────────────┐           ┌──────────┐                         ▲                               │   └ES         │
 *                                     ▲                                                                │MysqlDatabase │           │RedisDatabase│           │EsDatabase│                         │                               └───────────────┘
 *          ┌──────────────────────────┼───────────────────────────┐────────────────────┐               │  └dataset    │           │  └dataset   │           │  └dataset│               ┌───────────────────────┐
 *    ┌──────────────┐           ┌─────────────┐           ┌─────────────┐        ┌────────────────┐    └──────────────┘           └─────────────┘           └──────────┘               │SyncMediator           │
 *    │ESCommand     │           │MysqlCommand │           │RedisCommand │        │NewMysqlCommand │                                                                                    │  └changed(command)    │-changed()函数只调用命令类的execute()函数
 *    │  └execute()  │           │  └execute() │           │  └execute() │        │  └execute()    │                                                                                    └───────────────────────┘
 *    └──────────────┘           └─────────────┘           └─────────────┘        └────────────────┘                                                                                              │
 *          ▲                          ▲                           ▲                      ▲                                                                                                       │
 *          └--------------------------└---------------------------└----------------------└-------------------------------------------------------------------------------------------------------┘
 *
 *
 *
 *
 *  总结：此案例将请求单独抽离出来，将各个数据库的逻辑封装在execute(dataMap)函数中，通过函数入参持有中介类的dataMap引用后执行相应的请求逻辑，实现解耦！
 *
 *  与命令模式对比：
 *      不难发现此中介模式的案例和命令模式的案例很相似，在命令模式[command包]下的的案例是先通过中间件[command.usually.two包]然后引出命令模式案例[command.simple包]的优点
 *      不过此方式有局限性，即客户端和服务端是一对一的方式，当客户端需要访问多个服务端的时候，命令模式的命令实体类则需要绑定多个服务端的引用，这就变成了大杂烩
 *      而此案例正是解决中介模式和命令模式的不足，互补长短，将双发的优点结合互补，通过中介模式将服务端的引用统一进行注册管理！通过命令模式将程序解耦并实现扩展！
 *
 *  优化：可以在此基础上 + 备忘录模式，即AbstractDatabase包含一个Caretaker备忘录负责人引用，DatabaseCommand实现Originator发起人接口实现备份！
 *       暂未实现，可参考[behavioral.memento.complete包]
 *
 * Author: GL
 * Date: 2021-11-10
 */
public class Test {
    public static void main(String[] args) {
        // 新建中介实体类后存入各个业务实体类中，再由实体类反注册进中介实体类中
        AbstractMediator<String> syncMediator = new SyncMediator<>();

        MysqlDatabase<String> mysqlDatabase = new MysqlDatabase<>(syncMediator);
        RedisDatabase<String> redisDatabase = new RedisDatabase<>(syncMediator);
        ESDatabase<String> esDatabase = new ESDatabase<>(syncMediator);

        System.out.println("\n---------mysql 添加数据 1，将同步到Redis和ES中----------");
        mysqlDatabase.add("1");
        mysqlDatabase.select();
        redisDatabase.cache();
        esDatabase.count();

        System.out.println("\n---------Redis添加数据 2，将不同步到其它数据库-----------");
        redisDatabase.add("2");
        mysqlDatabase.select();
        redisDatabase.cache();
        esDatabase.count();

        System.out.println("\n---------ES 添加数据 3，只同步到 Mysql-----------");
        esDatabase.add("3");
        mysqlDatabase.select();
        redisDatabase.cache();
        esDatabase.count();

        System.out.println("\n---------假设mysql同步机制变成只同步给es，则可以新建mysqlCommend子类传输即可，实现扩展性！-----------");
        mysqlDatabase.add("4", new NewMysqlCommand<>());
        mysqlDatabase.select();
        redisDatabase.cache();
        esDatabase.count();
    }
}
