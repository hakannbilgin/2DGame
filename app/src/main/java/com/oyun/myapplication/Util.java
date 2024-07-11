package com.oyun.myapplication;

import java.util.Random;

public class Util {


    public static int generateRandomNumber(int range) {
        // Random sınıfından bir nesne oluşturuyoruz
        Random random = new Random();

        // 0'dan 100'e kadar rastgele bir sayı üretiyoruz (100 dahil değil)

        // Rastgele sayıyı döndürüyoruz
        return random.nextInt(range);
    }
}
