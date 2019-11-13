import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.printf("Вы используете калькулятор домашнего производства) Пока функционируют функции '+' и '-' и данные системы счисления: [2-16]\nВведите два числа, систему счисления и знак операции, чтобы ответ был выведен на экран:\n");
        String a = in.next(), b = in.next();
        int syst = in.nextInt();
        String sign = in.next();
        if(syst <= 16 && syst>1) {
            switch (sign) {
                case "-":
                    System.out.println(SimpleArithmetic.sub(a, b, syst));
                    break;
                case "+":
                    System.out.println(SimpleArithmetic.sum(a, b, syst));
                    break;
                case "*":
                    System.out.println(SimpleArithmetic.mult(a, b, syst));
                    break;
                default:
                    System.out.println("Не знаю таких операций");
            }
        } else System.out.println("Не знаю таких систем счисления...");
    }
}
//.......................................................................................................................................
//.......................................................................................................................................
class SimpleArithmetic { //функционален в пределах 10 первых систем [1-10]
    static String sum(String a, String b, int syst ) {
        int k = Math.max(a.length(),b.length());
        String res ="";
        int[] arrRes = new int[k+1]; //инициализация переменных

        for (int i = 0; i < a.length(); i++) {
            switch(a.charAt(a.length()-i-1)) {
                case 'A': arrRes[k-i]= 10; break;
                case 'B': arrRes[k-i] = 11; break;
                case 'C': arrRes[k-i] = 12; break;
                case 'D': arrRes[k-i] = 13; break;
                case 'E': arrRes[k-i] = 14; break;
                case 'F': arrRes[k-i] = 15; break;
                default: arrRes[k-i] = Integer.parseInt(a.charAt(a.length()-i-1)+"");
            } //вброс 1 числа
        }
        for (int i = 0; i < b.length(); i++) { //вброс 2 числа и суммирование разрядов 1 и 2 чисел
            switch(b.charAt(b.length()-i-1)) {
                case 'A': arrRes[k-i] += 10; break;
                case 'B': arrRes[k-i] += 11; break;
                case 'C': arrRes[k-i] += 12; break;
                case 'D': arrRes[k-i] += 13; break;
                case 'E': arrRes[k-i] += 14; break;
                case 'F': arrRes[k-i] += 15; break;
                default: arrRes[k-i] += Integer.parseInt(b.charAt(b.length()-i-1)+"");
            }
        }
        for (int i = 0; i < k+1; i++) { //приводим число в должный формат(работаем с переполненными разрядами)
            if(arrRes[k-i]>=syst) {
                arrRes[k-i] -= syst;
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
            };
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
            switch(a.charAt(a.length()-i-1)) {
                case 'A': arrRes[k-i-1]= 10; break;
                case 'B': arrRes[k-i-1] = 11; break;
                case 'C': arrRes[k-i-1] = 12; break;
                case 'D': arrRes[k-i-1] = 13; break;
                case 'E': arrRes[k-i-1] = 14; break;
                case 'F': arrRes[k-i-1] = 15; break;
                default: arrRes[k-i-1] = Integer.parseInt(a.charAt(a.length()-i-1)+"");
            };
        }
        int help1 ;
        for (int i = 0; i < b.length(); i++) {//непосредственно вычитание (чуть ниже доработки)
            switch(b.charAt(b.length()-i-1)) {
                case 'A': help1= 10; break;
                case 'B': help1 = 11; break;
                case 'C': help1 = 12; break;
                case 'D': help1 = 13; break;
                case 'E': help1 = 14; break;
                case 'F': help1 = 15; break;
                default: help1 = Integer.parseInt(b.charAt(b.length()-i-1)+"");
            }
            if(arrRes[k-1-i] >= help1) {
                arrRes[k-i-1] -= help1;
            }
            else {
                arrRes[k - i - 2] -= 1;
                arrRes[k - i - 1] -= help1 - syst;
            }
        }

        for (int i = arrRes.length-1; i > 0 ; i--) { //это необходимо, чтобы не было подобного: "1-110", "1-10" и т.д.
            if(arrRes[i] < 0) {
                arrRes[i-1] -= 1;
                arrRes[i] += syst;
            }
        }
        for (int i = 0; i < k; i++) { // тут мы переносим элементы массива в результат и "обрубаем" незначащие нули
            switch(arrRes[i]) {
                case 15: res += "F"; break;
                case 14: res += "E"; break;
                case 13: res += "D"; break;
                case 12: res += "C"; break;
                case 11: res += "B"; break;
                case 10: res += "A"; break;
                default: res += arrRes[i];
            }
            if(res.charAt(0) == '0' && res.length() > 1) res = res.substring(1);
        }
        if(!check) res = "-" + res; //проверка на отрицательность (мы ведь могли поменять числа местами в самом начале)

        return res;
    }

    static String multLowerThanSyst(String a, String b, int syst) {
        String res ="";
        int[] arr = new int[a.length() + 1];
        for (int i = a.length(); i > 0; i--) {
            arr[i] += Integer.parseInt(a.charAt(i-1)+"") * Integer.parseInt(b);
            if(arr[i]>=syst) {
                arr[i-1] += arr[i]/syst;
                arr[i] = arr[i]%syst;
            }
            res = arr[i] + res;
        }
        res =  arr[0] + res;
        while(res.length() > 1 && res.charAt(0) == '0') {
            res = res.substring(1);
        }
        return res;
    }

    static String mult(String a, String b, int syst) {
        String res ="0", res1 = "";
        int[] arr = new int[a.length()+b.length()];
        for (int i = 0; i < b.length(); i++) {
            res1 = multLowerThanSyst(a, b.charAt(b.length()-i-1)+"", syst);
            for (int j = 0; j < i; j++) {
                res1 += "0";
            }
            res = sum(res, res1, syst);
        }

        return res;
    }

    static  String div(String a, String b, int syst) {
        return "";
    }
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
class Compare {
    static boolean isFirstBigger(String a, String b) {
        boolean res = true;
        if(a.length() == b.length()) {
            for (int i = 0; i < a.length(); i++) {
                if (a.charAt(i)  < b.charAt(i) ) { //Integer.parseInt(a.charAt(i) + "") < Integer.parseInt(b.charAt(i) + "")
                    res = false;
                    break;
                }
            }
        } else if(a.length() < b.length()) res = false;

        return res;
    }
} //в классе 1 метод, используемый при вычитании

