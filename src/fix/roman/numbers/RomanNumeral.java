package fix.roman.numbers;

import java.util.*;

/**
 * <p>I = 1</p>
 * <p>V = 5</p>
 * <p>X = 10</p>
 * <p>L = 50</p>
 * <p>C = 100</p>
 * <p>D = 500</p>
 * <p>M = 1000</p>
 * <p>Z = 2000</p>
 * <p>
 * <p></p>
 * Important:
 * <p>
 * <p>IIII = IV = 4</p>
 * <p>IIX (= I  + IX) =  IXI = X = 10</p>
 * <p>IIXII = IXIII = 12</p>
 * <p>
 * <p></p>
 * 0 doesn't exist!
 */
public class RomanNumeral {

    private RomanNumeral() {
    }

    public static boolean isRomanDigit(String digit) {
        return digit.equals("I") ||
                digit.equals("V") ||
                digit.equals("X") ||
                digit.equals("L") ||
                digit.equals("C") ||
                digit.equals("D") ||
                digit.equals("M") ||
                digit.equals("Z");
    }

    public static int defineRomanDigit(String romanDigit)
            throws RomanNumeralException {
        switch (romanDigit) {
            case "I":
                return 1;
            case "V":
                return 5;
            case "X":
                return 10;
            case "L":
                return 50;
            case "C":
                return 100;
            case "D":
                return 500;
            case "M":
                return 1000;
            case "Z":
                return 2000;
            default:
                throw new RomanNumeralException();
        }
    }

    public static boolean isRomanNumber(String romanNumber) {
        String[] digits = romanNumber.split("");

        for (String digit : digits)
            if (!isRomanDigit(digit))
                return false;
        return true;
    }

    /**
     *
     * @param romanNumber is a string, that you want to translate to normal numbers.
     * @return if {@param romanNumber} is roman number then this function make from
     * it an arabian one. Else it will give you zero.
     */
    public static int defineRomanNumber(String romanNumber) {
        if (romanNumber.length() == 1)
            return (isRomanDigit(romanNumber)) ? defineRomanDigit(romanNumber) : 0;

        if (romanNumber.length() == 2) {
            if (equals(romanNumber.split("")[1], ".", ")"))
                return defineRomanNumber(romanNumber.split("")[0]);
            else {
                try {
                    return sumRomanNumber(romanNumber.split("")[0],
                            romanNumber.split("")[1]);
                } catch (RomanNumeralException e) {
                    return 0;
                }
            }
        }


        int sum = 0;

        boolean full = true;
        if (equals(romanNumber.split("")[romanNumber.length() - 1], ".", ")"))
            full = false;

        String[] digits;
        if (full)
            digits = romanNumber.split("");
        else
            digits = romanNumber.substring(0, romanNumber.length() - 1).split("");

        for (int i = 0; i < digits.length;) {
            if (isRomanDigit(digits[i])) {
                if (i + 1 < digits.length) {
                    if (isRomanDigit(digits[i + 1])) {
                        sum += defineRomanNumber(getSubstringFromMas(digits, i, (i + 1) + 1));
                        i += 2;
                    } else {
                        sum += defineRomanDigit(digits[i]);
                        i++;
                    }
                } else {
                    sum += defineRomanDigit(digits[i]);
                    break;
                }
            } else break;
        }

        return sum;
    }

    private static String getSubstringFromMas(String[] mas, int i, int j) {
        String toR = "";

        for (int k = i; k < j; k++)
            toR += mas[k];

        return toR;
    }

    static boolean equals(String a, String... b) {
        for (String s : b)
            if (a.equals(s))
                return true;
        return false;
    }

    public static int sumRomanNumber(String first, String second)
            throws RomanNumeralException {
        if (isRomanDigit(first) && isRomanDigit(second)) {
            int num1 = defineRomanDigit(first);
            int num2 = defineRomanDigit(second);

            if (num1 >= num2)
                return num1 + num2;
            else
                return num2 - num1;
        } else
            throw new RomanNumeralException();
    }

    public static int[] findInString(String toFind) {
        LinkedList<Integer> points = new LinkedList<>();
        String[] str = toFind.split(" ");

        loop:
        for (int i = 0; i < str.length; i++) {
            if (!str[i].equals(""))
                if (isRomanDigit(str[i].split("")[0])) {
                    int upTo = match(equals(
                            str[i].split("")[str[i].length() - 1], ".", ")"),
                            str[i].split("").length - 1, str[i].split("").length);
                    for (int j = 0; j < upTo; j++) {
                        String digit = str[i].split("")[j];
                        if (!isRomanDigit(digit))
                            continue loop;
                    }
                    points.add(i);
                }
        }

        int[] toR = new int[points.size()];
        for (int i = 0; i < points.size(); i++)
            toR[i] = points.get(i);

        return toR;
    }

    private static int match(boolean first, int F, int S) {
        return (first) ? F : S;
    }

    public static String changeInString(String toFind) {
        String[] str = toFind.split(" ");

        int[] where = findInString(toFind);

        String toR = "";
        if (where.length != 0) { // if roman number exists in string
            for (int i = 0; i < where[0]; i++)
                toR += str[i] + " ";

            for (int j = 0; j < where.length; j++) {
                toR += defineRomanNumber(str[where[j]]) + " ";

                if (j + 1 < where.length)
                    toR = addToStringFromMas(toR, where[j] + 1, where[j + 1], str);
            }

            for (int i = where[where.length - 1] + 1; i < str.length; i++)
                toR += str[i] + " ";
        } else
            toR = toFind;
        return toR;
    }

    private static String addToStringFromMas(String toAdd, int from, int upTo, String[] mas) {
        String toR = toAdd;

        for (int i = from; i < upTo; i++)
            toR += mas[i];

        return toR;
    }
}

class RomanNumeralException extends RuntimeException {
    static final long serialVersionUID = 3267108574312869012L;

    public RomanNumeralException() {
        super("Illegal roman digit! Check your number!");
    }
}
