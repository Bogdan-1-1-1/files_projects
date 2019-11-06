import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.printf("Вы используете калькулятор домашнего производства) Пока функционируют функции '+' и '-' и данные системы счисления: [1-10]+{16}\nВведите два числа, систему счисления и знак операции, чтобы ответ был выведен на экран:\n");
        String a = in.next(), b = in.next();
        int syst = in.nextInt();
        String sign = in.next();
        if(syst <= 10) {
            switch (sign) {
                case "-":
                    System.out.println(SimpleArithmetics.sub(a, b, syst));
                    break;
                case "+":
                    System.out.println(SimpleArithmetics.sum(a, b, syst));
                    break;
                default:
                    System.out.println("Не знаю таких операций");
            }
        } else if(syst == 16) {
            switch(sign) {
                case "-": System.out.println(Sixteen.sub(a,b)); break;
                case "+": System.out.println(Sixteen.sum(a,b)); break;
            }
        }
    }
}
//.......................................................................................................................................
//.......................................................................................................................................
class Sixteen {
    static String sum(String a, String b) {
        int k = Math.max(a.length(),b.length());
        String res ="";
        int[] arrRes = new int[k+1];

        for (int i = 0; i < a.length(); i++) {
            arrRes[k-i] = Integer.parseInt(a.charAt(a.length()-i-1)+"");
        }
        for (int i = 0; i < b.length(); i++) {
            arrRes[k-i] += Integer.parseInt(b.charAt(b.length()-i-1)+"");
        }
        for (int i = 0; i < k+1; i++) {
            if(arrRes[k-i]>=16) {
                arrRes[k-i] -= 16;
                arrRes[k-i-1] +=1;
            }

            switch(arrRes[k-i]) {
                case 15: res = "F" + res; break;
                case 14: res = "E" + res; break;
                case 13: res = "D" + res; break;
                case 12: res = "C" + res; break;
                case 11: res = "B" + res; break;
                case 10: res = "A" + res; break;
                default: res = arrRes[k-i] + res;
            }
        }
        if(arrRes[0] == 0) res = res.substring(1);

        return res;
    }

    static String sub(String a, String b) {
        int k = Math.max(a.length(),b.length());
        boolean check = Compare.isFirstBigger(a,b);
        String res ="", help = "";
        int[] arrRes = new int[k];

        if(!check) {
            help = a;
            a = b;
            b = help;
        }
        for (int i = 0; i < k; i++) {
            arrRes[k-i-1] = Integer.parseInt(a.charAt(a.length()-i-1)+"");
        }

        for (int i = 0; i < b.length(); i++) {                                       //*
            if(arrRes[k-1-i] >= Integer.parseInt(b.charAt(b.length()-i-1)+"")) {
                arrRes[k-i-1] -= Integer.parseInt(b.charAt(b.length()-i-1)+"");
            }
            else {
                arrRes[k - i - 2] -= 1;
                arrRes[k - i - 1] -= Integer.parseInt(b.charAt(b.length() - i - 1) + "") - 16;
            }

        }
        for (int i = arrRes.length-1; i > 0 ; i--) { //*
            if(arrRes[i] < 0) {
                arrRes[i-1] -= 1;
                arrRes[i] += 16;
            }
        }
        for (int i = 0; i < k; i++) {
            switch(arrRes[i]) {
                case 15: res += "F"; break;
                case 14: res += "E"; break;
                case 13: res += "D"; break;
                case 12: res += "C"; break;
                case 11: res += "B"; break;
                case 10: res += "A"; break;
                default: res += arrRes[i];
            }
            if(res.charAt(0) == '0') res = res.substring(1);
        }
        if(!check) res = "-" + res;

        return res;
    }
}

class SimpleArithmetics { //функционален в пределах 10 первых систем [1-10]
    static String sum(String a, String b, int syst ) {
        int k = Math.max(a.length(),b.length());
        String res ="";
        int[] arrRes = new int[k+1]; //инициализация переменных

        for (int i = 0; i < a.length(); i++) {
            arrRes[k-i] = Integer.parseInt(a.charAt(a.length()-i-1)+""); //вброс 1 числа
        }
        for (int i = 0; i < b.length(); i++) { //вброс 2 числа и суммирование разрядов 1 и 2 чисел
            arrRes[k-i] += Integer.parseInt(b.charAt(b.length()-i-1)+"");
        }
        for (int i = 0; i < k+1; i++) { //приводим число в должный формат(работаем с переполненными разрядами)
            if(arrRes[k-i]>=syst) {
                arrRes[k-i] -= syst;
                arrRes[k-i-1] +=1;
            }
            res = arrRes[k-i] + res;
        }
        if(arrRes[0] == 0) res = res.substring(1); // избавляемся от незначащих нулей

        return res;
    }

    static String sub(String a, String b, int syst) {
        int k = Math.max(a.length(),b.length());
        boolean check = Compare.isFirstBigger(a,b);
        String res ="", help = "";
        int[] arrRes = new int[k]; //инициализация переменных

        if(!check) {  //проверка на то, какое число больше + возможна замена
            help = a;
            a = b;
            b = help;
        }
        for (int i = 0; i < k; i++) { // первое число расходится по массиву
            arrRes[k-i-1] = Integer.parseInt(a.charAt(a.length()-i-1)+"");
        }

        for (int i = 0; i < b.length(); i++) {                                       //непосредственно вычитание (чуть ниже доработки)
            if(arrRes[k-1-i] >= Integer.parseInt(b.charAt(b.length()-i-1)+"")) {
                arrRes[k-i-1] -= Integer.parseInt(b.charAt(b.length()-i-1)+"");
            }
            else {
                arrRes[k - i - 2] -= 1;
                arrRes[k - i - 1] -= Integer.parseInt(b.charAt(b.length() - i - 1) + "") - syst;
            }

        }
        for (int i = arrRes.length-1; i > 0 ; i--) { //это необходимо, чтобы не было подобного: "1-110", "1-10" и т.д.
            if(arrRes[i] < 0) {
                arrRes[i-1] -= 1;
                arrRes[i] += syst;
            }
        }
        for (int i = 0; i < k; i++) { // тут мы переносим элементы массива в результат и "обрубаем" незначащие нули
            res += arrRes[i];
            if(res.charAt(0) == '0') res = res.substring(1);
        }
        if(!check) res = "-" + res; //проверка на отрицательность (мы ведь могли поменять числа местами в самом начале)

        return res;
    }
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
class Compare { //в классе 1 метод, используемый при вычитании
    static boolean isFirstBigger(String a, String b) {
        boolean res = true;
        if(a.length() == b.length()) {
            for (int i = 0; i < a.length(); i++) {
                if (Integer.parseInt(a.charAt(i) + "") < Integer.parseInt(b.charAt(i) + "")) {
                    res = false;
                    break;
                }
            }
        } else if(a.length() < b.length()) res = false;

        return res;
    }
}
