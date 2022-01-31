package com.vodafoneH2.vodh2.convertUtil;

import java.util.Arrays;
import java.util.List;

public class ConvertUtil {
    public ConvertUtil() {
    }

    public static List<String> convertEnterenceSplit(String enterence){
        String[] splittedSerie = enterence.split(" ");
        return Arrays.asList(splittedSerie);
    }
}