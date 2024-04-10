package com.pcl.healthism.bussiness.common.tools;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ChineseWordsToNumbersUtil {
    final static List<String> allowedStrings = Arrays.asList("零", "一", "两", "二", "三", "四", "五", "六", "七", "八", "九", "十", "百", "千", "万", "亿");

    public static String convertTextualNumbersInDocument(String inputText) {

        // splits text into words and deals with hyphenated numbers. Use linked
        // list due to manipulation during processing
        List<String> words = new LinkedList<String>(cleanAndTokenizeText(inputText));

        // replace all the textual numbers
        words = replaceTextualNumbers(words);

        // put spaces back in and return the string. Should be the same as input
        // text except from textual numbers
        return String.join("", words);
    }

    public static void main(String[] args) {

        String sentence = "二十个包子";
        String words = convertTextualNumbersInDocument(sentence);
        System.out.println("Paragraph before: " + sentence);
        System.out.println("Paragraph after: " + words);
    }

    private static List<String> replaceTextualNumbers(List<String> words) {

        // holds each group of textual numbers being processed together. e.g.
        // "one" or "five hundred and two"
        List<String> processingList = new LinkedList<String>();

        int i = 0;
        while (i < words.size() || !processingList.isEmpty()) {

            // caters for sentences only containing one word (that is a number)
            String word = "";
            if (i < words.size()) {
                word = words.get(i);
            }

            // strip word of all punctuation to make it easier to process
            String wordStripped = word;

            // 2nd condition: skip "and" words by themselves and at start of num
            if (allowedStrings.contains(word) && !(processingList.size() == 0 && word.equals("and"))) {
                words.remove(i); // remove from main list, will process later
                processingList.add(word);
            } else if (processingList.size() > 0) {
                // found end of group of textual words to process

                // main logic here, does the actual conversion
                String wordAsDigits = String.valueOf(convertWordsToNum(processingList));

                wordAsDigits = retainPunctuation(processingList, wordAsDigits);
                words.add(i, String.valueOf(wordAsDigits));

                processingList.clear();
                i += 2;
            } else {
                i++;
            }
        }

        return words;
    }

    private static String retainPunctuation(List<String> processingList, String wordAsDigits) {

        String lastWord = processingList.get(processingList.size() - 1);
        char lastChar = lastWord.trim().charAt(lastWord.length() - 1);
        if (!Character.isLetter(lastChar)) {
            wordAsDigits += lastChar;
        }

        String firstWord = processingList.get(0);
        char firstChar = firstWord.trim().charAt(0);
        if (!Character.isLetter(firstChar)) {
            wordAsDigits = firstChar + wordAsDigits;
        }

        return wordAsDigits;
    }

    private static List<String> cleanAndTokenizeText(String sentence) {
        List<String> words = new LinkedList<>(Arrays.asList(sentence.split("")));
        return words;
    }

    private static long convertWordsToNum(List<String> words) {
        long finalResult = 0;
        long intermediateResult = 0;
        for (String str : words) {
            System.out.println(str);
            if (str.equals("零")) {
                intermediateResult += 0;
            } else if (str.equals("一")) {
                intermediateResult += 1;
            } else if (str.equals("两")) {
                intermediateResult += 2;
                System.out.println(intermediateResult);
            } else if (str.equals("二")) {
                intermediateResult += 2;
                System.out.println(intermediateResult);
            } else if (str.equals("三")) {
                intermediateResult += 3;
            } else if (str.equals("四")) {
                intermediateResult += 4;
            } else if (str.equals("五")) {
                intermediateResult += 5;
            } else if (str.equals("六")) {
                intermediateResult += 6;
            } else if (str.equals("七")) {
                intermediateResult += 7;
            } else if (str.equals("八")) {
                intermediateResult += 8;
            } else if (str.equals("九")) {
                intermediateResult += 9;
            } else if (str.equals("十")) {
                if (intermediateResult == 0) {
                    intermediateResult += 10;
                } else {
                    intermediateResult *= 10;
                }
            } else if (str.equals("百")) {
                intermediateResult *= 100;
            } else if (str.equals("千")) {
                intermediateResult *= 1000;
                finalResult += intermediateResult;
                intermediateResult = 0;
            } else if (str.equals("万")) {
                intermediateResult *= 10000;
                finalResult += intermediateResult;
                intermediateResult = 0;
            } else if (str.equals("亿")) {
                intermediateResult *= 100000000;
                finalResult += intermediateResult;
                intermediateResult = 0;
            }
        }
        finalResult += intermediateResult;
        return finalResult;
    }

}