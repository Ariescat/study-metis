package jdk8.coion;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class Main {

    /**
     * 这里的测试是: 为什么compareTo也能通过
     * List<Integer> list = new ArrayList<>();
     * list.sort(Integer::compare);
     * list.sort(Integer::compareTo);
     */
    public static void main(String[] args) {
        testFun(Test::a1);
        testFun(Test::a2);
//        testFun(Test::a3); // 编译不通过
        testFun(new Test("test")::a3);

        testFun2(Test::b1);
//        testFun2(Test::b2); // 编译不通过
//        testFun2(Test::b3); // 注意！编译不通过
        testFun2(new Test("test")::b3);

        testFun3(Test::c1);
//        testFun3(Test::c2); // 编译不通过
        testFun3(new Test("test")::c2);

        testFun4(Test::d1);
//        testFun4(Test::d2); // 编译不通过
        testFun4(new Test("test")::d2);

        /**
         * 因此，猜测 若接口的参数类型一样 可直接用::调用实例方法
         */

        // --------------------------------------------------

        /**
         * 后面补充：其实上面猜测是片面的，::是lambda表达式的方法引用，不能脱离lambda的传参来盲目猜测
         */
        List<Integer> list = new ArrayList<>();
        list.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
//                return o1.compareTo(o2);
                return Integer.compare(o1, o2);
            }
        });
        list.sort(Integer::compare);
        list.sort(Integer::compareTo);

        list.stream().map(new Function<Integer, Byte>() {
            @Override
            public Byte apply(Integer i) {
                return i.byteValue();
            }
        });
        list.stream().map(Integer::byteValue);
    }

    static void testFun(Fun f) {
//        System.err.println(f.fun(new Test("test1"), new Test("test2")));
    }

    static void testFun2(Fun2 f2) {
    }

    static void testFun3(Fun3 f2) {
    }

    static void testFun4(Fun4 f2) {
    }

    static class Test {
        private String desc;

        Test(String desc) {
            this.desc = desc;
        }

        // ---------------------- Fun ----------------------
        static int a1(Test o1, Test o2) {
            return 1;
        }

        int a2(Test o) {
            return 2;
        }

        public int a3(Test o1, Test o2) {
            return 3;
        }

        // ---------------------- Fun2 ----------------------
        static int b1(int o1, Test o2) {
            return 1;
        }

        int b2(Test o) {
            return 2;
        }

        public int b3(int o1, Test o2) {
            return 3;
        }

        // ---------------------- Fun3 ----------------------
        static int c1(int o1) {
            return 1;
        }

        int c2(int o) {
            return 2;
        }

        // ---------------------- Fun4 ----------------------
        static int d1() {
            return 1;
        }

        int d2() {
            return 2;
        }
    }

    interface Fun {
        /**
         * param1与param2 类型一样
         */
        int fun(Test o1, Test o2);
    }

    interface Fun2 {
        /**
         * param1与param2 类型 不一样
         */
        int fun(int o1, Test o2);
    }

    interface Fun3 {
        /**
         * 只有一个参数
         */
        int fun(int o1);
    }

    interface Fun4 {
        /**
         * 无参
         */
        int fun();
    }
}
