package com.github.alexander_wilms.primenumbers;

public class PrintPrimes {
    public static void main(String[] args) {
        for(int i = 2; i <= 100; i++) {
            if(prime(i)) {
                System.out.println(i);
            }
        }
    }

    public static boolean prime(int number) {
        return checkPrime(number, number);
    }

    public static boolean checkPrime(int number, int div) {
        if(div == number) {
            return checkPrime(number, div-1);
        } else if(div == 1) {
            return true;
        } else {
            if(number % div == 0) {
                return false;
            } else {
                return checkPrime(number, div-1);
            }
        }
    }
}
