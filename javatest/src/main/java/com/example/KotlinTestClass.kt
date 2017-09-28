package com.example

/**
 * 作者:王青 wangqing
 * 创建日期：2017/5/31 on 12:39
 * 描述:
 */
class KotlinTestClass : JavaTestClass() { //：实现类的继承

    var meIsInt = 0       //变量，句末不需要加分号
    var meIsString = "String"   //类型推断
    var meIsStr: String = "string"//显示标明类型
    var meIsint: Int = 2
    var str: String? = null   //可为空字符串变量

    val meIsVar = 0         //常量

    override fun hashCode(): Int {
        return super.hashCode()
    }

    /**
     * 空指针安全
     */
    fun testNullSafeOperator(string: String?) {
        print(string?.toCharArray()?.getOrNull(10)?.hashCode())
        testNullSafeOperator(null)//null
        testNullSafeOperator("12345678901")//49
        testNullSafeOperator("123")//null

        if (meIsInt > 10) {

        } else {

        }

        val x = 7
        when (x) {
            in 1..10 -> print("x in in the range")
            //in validNumbers -> print("X is valid")
            in 10..20-> print("x is outside the range")
            else -> print("none ")
        }

        val list=ArrayList<String>()
        for (i in list.indices){
            print(list[i])
        }

    }

}