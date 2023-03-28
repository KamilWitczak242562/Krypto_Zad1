package org.example;

import java.util.ArrayList;
import java.util.List;

public class AES {
    private int[] sbox = {0x63, 0x7C, 0x77, 0x7B, 0xF2, 0x6B, 0x6F,
            0xC5, 0x30, 0x01, 0x67, 0x2B, 0xFE, 0xD7, 0xAB, 0x76, 0xCA, 0x82,
            0xC9, 0x7D, 0xFA, 0x59, 0x47, 0xF0, 0xAD, 0xD4, 0xA2, 0xAF, 0x9C,
            0xA4, 0x72, 0xC0, 0xB7, 0xFD, 0x93, 0x26, 0x36, 0x3F, 0xF7, 0xCC,
            0x34, 0xA5, 0xE5, 0xF1, 0x71, 0xD8, 0x31, 0x15, 0x04, 0xC7, 0x23,
            0xC3, 0x18, 0x96, 0x05, 0x9A, 0x07, 0x12, 0x80, 0xE2, 0xEB, 0x27,
            0xB2, 0x75, 0x09, 0x83, 0x2C, 0x1A, 0x1B, 0x6E, 0x5A, 0xA0, 0x52,
            0x3B, 0xD6, 0xB3, 0x29, 0xE3, 0x2F, 0x84, 0x53, 0xD1, 0x00, 0xED,
            0x20, 0xFC, 0xB1, 0x5B, 0x6A, 0xCB, 0xBE, 0x39, 0x4A, 0x4C, 0x58,
            0xCF, 0xD0, 0xEF, 0xAA, 0xFB, 0x43, 0x4D, 0x33, 0x85, 0x45, 0xF9,
            0x02, 0x7F, 0x50, 0x3C, 0x9F, 0xA8, 0x51, 0xA3, 0x40, 0x8F, 0x92,
            0x9D, 0x38, 0xF5, 0xBC, 0xB6, 0xDA, 0x21, 0x10, 0xFF, 0xF3, 0xD2,
            0xCD, 0x0C, 0x13, 0xEC, 0x5F, 0x97, 0x44, 0x17, 0xC4, 0xA7, 0x7E,
            0x3D, 0x64, 0x5D, 0x19, 0x73, 0x60, 0x81, 0x4F, 0xDC, 0x22, 0x2A,
            0x90, 0x88, 0x46, 0xEE, 0xB8, 0x14, 0xDE, 0x5E, 0x0B, 0xDB, 0xE0,
            0x32, 0x3A, 0x0A, 0x49, 0x06, 0x24, 0x5C, 0xC2, 0xD3, 0xAC, 0x62,
            0x91, 0x95, 0xE4, 0x79, 0xE7, 0xC8, 0x37, 0x6D, 0x8D, 0xD5, 0x4E,
            0xA9, 0x6C, 0x56, 0xF4, 0xEA, 0x65, 0x7A, 0xAE, 0x08, 0xBA, 0x78,
            0x25, 0x2E, 0x1C, 0xA6, 0xB4, 0xC6, 0xE8, 0xDD, 0x74, 0x1F, 0x4B,
            0xBD, 0x8B, 0x8A, 0x70, 0x3E, 0xB5, 0x66, 0x48, 0x03, 0xF6, 0x0E,
            0x61, 0x35, 0x57, 0xB9, 0x86, 0xC1, 0x1D, 0x9E, 0xE1, 0xF8, 0x98,
            0x11, 0x69, 0xD9, 0x8E, 0x94, 0x9B, 0x1E, 0x87, 0xE9, 0xCE, 0x55,
            0x28, 0xDF, 0x8C, 0xA1, 0x89, 0x0D, 0xBF, 0xE6, 0x42, 0x68, 0x41,
            0x99, 0x2D, 0x0F, 0xB0, 0x54, 0xBB, 0x16};


    private int[] inv_sbox = {0x52, 0x09, 0x6A, 0xD5, 0x30, 0x36, 0xA5,
            0x38, 0xBF, 0x40, 0xA3, 0x9E, 0x81, 0xF3, 0xD7, 0xFB, 0x7C, 0xE3,
            0x39, 0x82, 0x9B, 0x2F, 0xFF, 0x87, 0x34, 0x8E, 0x43, 0x44, 0xC4,
            0xDE, 0xE9, 0xCB, 0x54, 0x7B, 0x94, 0x32, 0xA6, 0xC2, 0x23, 0x3D,
            0xEE, 0x4C, 0x95, 0x0B, 0x42, 0xFA, 0xC3, 0x4E, 0x08, 0x2E, 0xA1,
            0x66, 0x28, 0xD9, 0x24, 0xB2, 0x76, 0x5B, 0xA2, 0x49, 0x6D, 0x8B,
            0xD1, 0x25, 0x72, 0xF8, 0xF6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xD4,
            0xA4, 0x5C, 0xCC, 0x5D, 0x65, 0xB6, 0x92, 0x6C, 0x70, 0x48, 0x50,
            0xFD, 0xED, 0xB9, 0xDA, 0x5E, 0x15, 0x46, 0x57, 0xA7, 0x8D, 0x9D,
            0x84, 0x90, 0xD8, 0xAB, 0x00, 0x8C, 0xBC, 0xD3, 0x0A, 0xF7, 0xE4,
            0x58, 0x05, 0xB8, 0xB3, 0x45, 0x06, 0xD0, 0x2C, 0x1E, 0x8F, 0xCA,
            0x3F, 0x0F, 0x02, 0xC1, 0xAF, 0xBD, 0x03, 0x01, 0x13, 0x8A, 0x6B,
            0x3A, 0x91, 0x11, 0x41, 0x4F, 0x67, 0xDC, 0xEA, 0x97, 0xF2, 0xCF,
            0xCE, 0xF0, 0xB4, 0xE6, 0x73, 0x96, 0xAC, 0x74, 0x22, 0xE7, 0xAD,
            0x35, 0x85, 0xE2, 0xF9, 0x37, 0xE8, 0x1C, 0x75, 0xDF, 0x6E, 0x47,
            0xF1, 0x1A, 0x71, 0x1D, 0x29, 0xC5, 0x89, 0x6F, 0xB7, 0x62, 0x0E,
            0xAA, 0x18, 0xBE, 0x1B, 0xFC, 0x56, 0x3E, 0x4B, 0xC6, 0xD2, 0x79,
            0x20, 0x9A, 0xDB, 0xC0, 0xFE, 0x78, 0xCD, 0x5A, 0xF4, 0x1F, 0xDD,
            0xA8, 0x33, 0x88, 0x07, 0xC7, 0x31, 0xB1, 0x12, 0x10, 0x59, 0x27,
            0x80, 0xEC, 0x5F, 0x60, 0x51, 0x7F, 0xA9, 0x19, 0xB5, 0x4A, 0x0D,
            0x2D, 0xE5, 0x7A, 0x9F, 0x93, 0xC9, 0x9C, 0xEF, 0xA0, 0xE0, 0x3B,
            0x4D, 0xAE, 0x2A, 0xF5, 0xB0, 0xC8, 0xEB, 0xBB, 0x3C, 0x83, 0x53,
            0x99, 0x61, 0x17, 0x2B, 0x04, 0x7E, 0xBA, 0x77, 0xD6, 0x26, 0xE1,
            0x69, 0x14, 0x63, 0x55, 0x21, 0x0C, 0x7D};

    private byte[] key;

    private byte[] state;


    public AES(byte[] key, byte[] state) {
        this.key = key;
        this.state = state;
    }

    public byte[][] generateRoundKeys() {
        byte[][] keys = new byte[44][4];
        int i = 0;
        int j = 0;
        while (i < 4) {
            keys[i][0] = key[j];
            keys[i][1] = key[j++];
            keys[i][2] = key[j++];
            keys[i][3] = key[j++];
            i++;
            j++;
        }
        return keys;
    }

    //wykonuje operację XOR dla każdego bajtu wejściowego bloku z odpowiadającym mu bajtem w kluczu rundy.
    private byte[][] addRoundKey(byte[][] state, byte[][] roundKey, int round) {
        byte[][] result = new byte[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = (byte) (state[i][j] ^ roundKey[round * 4 + i][j]);
            }
        }
        return result;
    }

    //podstawienia każdego bajtu z wejściowego bloku 16 bajtów zgodnie ze statyczną tablicą sbox.
    private byte[][] subBytes(byte[][] state) {
        byte[][] temp = new byte[4][4];
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                temp[i][j] = (byte) (sbox[(state[i][j] & 0xff)]);
        return temp;
    }

    private byte[][] de_subBytes(byte[][] state) {
        byte[][] temp = new byte[4][4];
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                temp[i][j] = (byte) (inv_sbox[(state[i][j] & 0xff)]);
        return temp;
    }

    //przesuwa każdy wiersz wejściowego bloku o określoną liczbę bajtów, zgodnie ze specyficzną dla każdego wiersza wartością.
    private byte[][] shiftRows(byte[][] state) {
        byte[][] result = new byte[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = state[i][(j + i) % 4];
            }
        }
        return result;
    }

    private byte[][] de_shiftRows(byte[][] state) {
        byte[][] result = new byte[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][(j + 1) % 4] = state[i][j];
            }
        }
        return result;
    }

    //mieszanie kolumny jednego bloku stanu, który jest reprezentowany przez 4 bajty.
    private byte[][] mixColumns(byte[][] state) {
        byte[][] result = new byte[4][4];
        byte[] col = new byte[4];
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                col[i] = state[i][j];
            }
            byte[] mixedCol = mixColumn(col);
            for (int i = 0; i < 4; i++) {
                result[i][j] = mixedCol[i];
            }
        }
        return result;
    }

    //implementuje krok MixColumns w algorytmie AES, który polega na wykonaniu określonych operacji matematycznych na każdej kolumnie macierzy stanu
    private byte[] mixColumn(byte[] col) {
        int[] result = new int[4];
        byte one = (byte) 0x01;
        byte two = (byte) 0x02;
        byte three = (byte) 0x03;
        result[0] = mul(two, col[0]) ^ mul(three, col[1]) ^ mul(one, col[2]) ^ mul(one, col[3]);
        result[1] = mul(one, col[0]) ^ mul(two, col[1]) ^ mul(three, col[2]) ^ mul(one, col[3]);
        result[2] = mul(one, col[0]) ^ mul(one, col[1]) ^ mul(two, col[2]) ^ mul(three, col[3]);
        result[3] = mul(three, col[0]) ^ mul(one, col[1]) ^ mul(one, col[2]) ^ mul(two, col[3]);
        for (int i = 0; i < 4; i++) {
            col[i] = (byte) (result[i]);
        }
        return col;
    }

    private byte[][] de_MixColumns(byte[][] state) {
        byte[][] result = new byte[4][4];
        byte[] col = new byte[4];
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                col[i] = state[i][j];
            }
            byte[] mixedCol = de_MixColumn(col);
            for (int i = 0; i < 4; i++) {
                result[i][j] = mixedCol[i];
            }
        }
        return result;
    }

    private byte[] de_MixColumn(byte[] col) {
        int[] result = new int[4];
        byte nine = (byte) 0x09;
        byte eleven = (byte) 0x0B;
        byte thirteen = (byte) 0x0D;
        byte fourteen = (byte) 0x0E;
        result[0] = mul(fourteen, col[0]) ^ mul(eleven, col[1]) ^ mul(thirteen, col[2]) ^ mul(nine, col[3]);
        result[1] = mul(nine, col[0]) ^ mul(fourteen, col[1]) ^ mul(eleven, col[2]) ^ mul(thirteen, col[3]);
        result[2] = mul(thirteen, col[0]) ^ mul(nine, col[1]) ^ mul(fourteen, col[2]) ^ mul(eleven, col[3]);
        result[3] = mul(eleven, col[0]) ^ mul(thirteen, col[1]) ^ mul(nine, col[2]) ^ mul(fourteen, col[3]);
        for (int i = 0; i < 4; i++) {
            col[i] = (byte) (result[i]);
        }
        return col;
    }


    //mnożenie dwóch bajtów (a i b) w ciele Galois o rozmiarze 2^8
    //trzeba się pochylić
    private byte mul(byte multiplier, byte multiplicand) {
        byte result = 0;
        byte temp;
        while (multiplier != 0) {
            if ((multiplier & 1) != 0)
                result = (byte) (result ^ multiplicand);
            temp = (byte) (multiplicand & 0x20);
            multiplicand = (byte) (multiplicand << 1);
            if (temp != 0)
                multiplicand = (byte) (multiplicand ^ 0x1b);
            multiplier = (byte) ((multiplier & 0xff) >> 1);
        }
        return result;
    }

    /*
    1. Inicjalizuje zmienne multiplier, multiplicand, result i temp.
    2. Wykonuje pętlę while, która działa dopóki multiplier nie będzie równa 0.
    3. Wewnątrz pętli sprawdza, czy najmniej znaczący bit zmiennej multiplier jest równy 1. Jeśli tak, to wykonuje operację XOR między result i multiplicand.
    4. Przesuwa bity zmiennej multiplicand o jedno miejsce w lewo i zapisuje wynik w tej samej zmiennej.
    5. Sprawdza, czy 6-ty bit zmiennej bb jest równy 1. Jeśli tak, to wykonuje operację XOR między multiplicand i 0x1b.
    6. Przesuwa bity zmiennej multiplier o jedno miejsce w prawo i zapisuje wynik w tej samej zmiennej.
    7. Zwraca wartość zmiennej result.
   */


    public byte[][] encryptOne(byte[][] matrix, byte[][] key) {
        matrix = addRoundKey(matrix, key, 0);
        for (int round = 1; round < 10; round++) {
            matrix = subBytes(matrix);
            matrix = shiftRows(matrix);
            matrix = mixColumns(matrix);
            matrix = addRoundKey(matrix, key, round);
        }
        matrix = subBytes(matrix);
        matrix = shiftRows(matrix);
        matrix = addRoundKey(matrix, key, 10);
        return matrix;
    }

    public byte[][] decryptOne(byte[][] matrix, byte[][] key) {
        matrix = addRoundKey(matrix, key, 10);
        for (int round = 9; round > 0; round--) {
            matrix = de_subBytes(matrix);
            matrix = de_shiftRows(matrix);
            matrix = addRoundKey(matrix, key, round);
            matrix = de_MixColumns(matrix);
        }
        matrix = de_subBytes(matrix);
        matrix = de_shiftRows(matrix);
        matrix = addRoundKey(matrix, key, 0);
        return matrix;
    }

    //szyfruje podany blok 16 bajtów z użyciem klucza szyfrującego. Wykorzystuje do tego celu wszystkie powyższe metody oraz dodatkowe operacje.
    public byte[] encrypt() {
        byte[][] key = generateRoundKeys();
        List<Byte> result = new ArrayList<>();
        int y = 0;
        int x = 0;
        do {
            byte[][] block = new byte[4][4];
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (y < state.length) {
                        block[i][j] = state[y];
                        y++;
                    } else {
                        block[i][j] = 0x00;
                        x++;
                        if (i == 3 && j == 3) {
                            block[i][j] = (byte) x;
                        }
                    }
                }
            }
            block = encryptOne(block, key);
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    result.add(block[i][j]);
                }
            }
        } while (y != state.length);
        byte[] bytes = new byte[result.size()];
        for (int i = 0; i < result.size(); i++) {
            bytes[i] = result.get(i);
        }
        return bytes;
    }

    public byte[] decrypt() {
        byte[][] key = generateRoundKeys();
        List<Byte> result = new ArrayList<>();
        int y = 0;
        do {
            byte[][] block = new byte[4][4];
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (y < state.length) {
                        block[i][j] = state[y];
                        y++;
                    }
                }
            }
            block = decryptOne(block, key);
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    result.add(block[i][j]);
                }
            }
        } while (y != state.length);
        int zeroes = (int) result.get(result.size() - 1);

        byte[] bytes = new byte[result.size()];
        for (int i = 0; i < result.size(); i++) {
            bytes[i] = result.get(i);
        }
        return bytes;
    }

}


