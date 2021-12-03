package org.example.design.creative.build;

/**
 *  一、建造者模式：当一个类的构造函数参数个数超过4个，而且这些参数有些是可选的参数，考虑使用构造者模式！
 *
 *      1、此模式是专门为某个实体类的参数可选问题而诞生，先来看张图[build/build1.png]
 *          我们有一个房屋类，房屋类有必选参数[窗口, 门等], 同时也有多个可选参数，比如花园，车库，游泳池等。
 *          如果采用传统的方式进行创建该类，则会有众多重载构造函数, 见图[build/build2.png]
 *          即便拥有大量输入参数的构造函数也有缺陷： 这些参数也不是每次都要全部用上的。
 *
 *      2、而建造者方式就是为了解决此问题：建造者模式将对象构造代码从产品类中抽取出来，并将其放在一个名为生成器的独立对象中，以抽象方法的方式来代替属性，并由各个子类实现赋值函数。
 *          而对于业务中固定实现的子类，还可以再新增一个主管类[director]来生成固定的产品
 *
 *      3、随着建造者模式流行，java的lombok包中也提供了基于建造者模式生成的注解：@Builder, 此注解的实现方式是通过建造者模式的简化变体。
 *
 *  二、案例介绍：
 *
 *      1、传统建造者模式[参考complete包]
 *      2、简化版模拟@builder注解[参考simple包]
 *
 *
 * Author: GL
 * Date: 2021-12-02
 */
public class Explain {
}
