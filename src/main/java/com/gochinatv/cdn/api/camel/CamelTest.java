package com.gochinatv.cdn.api.camel;


import org.joda.time.DateTime;

public class CamelTest {


    public static void main(String[] args) {
        //test1();

    }


    public static void test1(){
        test2();
    }

    private static void test2() {
        test3();
    }

    private static void test3() {
        StackTraceElement[] stackElements = Thread.currentThread().getStackTrace();
        /*for (StackTraceElement stackElement : stackElements) {
            System.out.println(stackElement);
        }*/
        StringBuffer sbf =new StringBuffer();
        for(StackTraceElement e:stackElements){
            if(sbf.length()>0){
                sbf.append(" <- ");
                sbf.append(System.getProperty("line.separator"));
            }
            sbf.append(java.text.MessageFormat.format("{0}.{1}() {2}"
                    ,e.getClassName()
                    ,e.getMethodName()
                    ,e.getLineNumber()));
        }
        System.out.println(sbf.toString());
        try {
            throw new Exception();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
