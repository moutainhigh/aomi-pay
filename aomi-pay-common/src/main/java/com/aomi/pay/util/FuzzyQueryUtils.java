package com.aomi.pay.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author hdq
 * @Date 2020/8/11
 * @Version 1.0
 */
public class FuzzyQueryUtils     {

    public static List fuzzyQuery(String name, List list) {
        List fuzzyQuery = new ArrayList();
        //大小写不敏感的时候，多加一个条件
        Pattern pattern = Pattern.compile(name, Pattern.CASE_INSENSITIVE);
        for (int i = 0; i < list.size(); i++) {
            Matcher matcher = pattern.matcher((list.get(i)).toString());
            if (matcher.find()) {
                fuzzyQuery.add(list.get(i));
            }
        }
        return fuzzyQuery;
    }
}
