package com.example.admin;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("junit5功能测试类")
@SpringBootTest                              //引入Spring的测试驱动，为复合注解（ExtendWith）,引入后可以使用自动注入取到容器中的值
public class Junit5Test {
    @Autowired
    JdbcTemplate jdbcTemplate;


    @DisplayName("测试 displayName 注解")
    @Test
    void testDisplayName(){
        System.out.println(1);
        System.out.println(jdbcTemplate);           //只有设置了 @SpringBootTest 注解才能从容器中取到值
    }

    @Disabled
    @DisplayName("测试方法2")
    @Test
    void test2(){
        System.out.println(2);
    }

    @RepeatedTest(5)                            //重复测试 5 次
    @Test
    void test3(){
        System.out.println(5);
    }

    /*
    * 规定方法的超时时间
    * */
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    @Test
    void testTimeOut() throws InterruptedException {
        Thread.sleep(600);
    }


    @BeforeEach
    void testBeforeEach(){
        System.out.println("我是每个单元测试之前都要执行的方法哦:)");
    }

    @AfterEach
    void testAfterEach(){
        System.out.println("我是每个单元测试结束后都要执行的方法哦:)");
    }

    /*
    * BeforeAll 和 AfterAll 都只执行一次。需使用 static 保证
    * */
    @BeforeAll
    static void testBeforeAll(){
        System.out.println("所有测试要开始了....");
    }

    @AfterAll
    static void testAfterAll(){
        System.out.println("所有测试都结束了....");
    }

    //断言：前面断言失败，其后续的代码都不会执行
    @DisplayName("测试简单断言")
    @Test
    void testSimpleAssertions(){
        int cal = cal(2, 3);
        assertEquals(5, cal, "业务逻辑计算失败");            //期望值、实际值、断言失败后返回的 msg

        Object o1 = new Object(), o2 = o1;
//        Object o2 = new Object();
        assertSame(o1, o2, "两个对象不一样");
    }

    int cal(int a, int b) { return a + b; }

    @DisplayName("测试组合断言，都成功才算成功")
    @Test
    void all(){
        assertAll("test",
                () -> assertTrue(true & true, "结果不为 true"),
                () -> assertSame(1, 1, "结果不是期望值 1"));
    }

    @DisplayName("异常断言, 成功断言出了指定异常才行")
    @Test
    void testException(){
        //断定业务逻辑一定出现异常；否则会报错
        assertThrows(ArithmeticException.class, ()-> {
            int i = 10 / 0;
        }, "业务逻辑竟然正常运行了");
    }

    @DisplayName("测试前置条件")
    @Test
    void testAssumption(){
        //前置条件正常，后续的才会执行。否者直接跳过（与断言的报错不一样）
        Assumptions.assumeTrue(false, "结果不是 true");
        System.out.println("111111");
    }

    @DisplayName("参数化测试")
    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5})
    void testParam(int i){
        System.out.println(i);
    }

    @DisplayName("参数化测试")
    @ParameterizedTest
    @MethodSource("stringProvider")
    void testParam2(String i){
        System.out.println(i);
    }

    static Stream<String> stringProvider(){
        return Stream.of("apple","banana","yammi");
    }
}
