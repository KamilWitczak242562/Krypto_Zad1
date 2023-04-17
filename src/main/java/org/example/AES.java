package org.example;

public class AES {
    private Utils utils = new Utils();
    byte[][] keyExtended;
    byte[][] keyExtendedReversed;
    byte[] entranceKey;


    public AES(byte[] key) {
        this.entranceKey = key;
        this.keyExtended = generateSubKeys(entranceKey);
        this.keyExtendedReversed = generateReversedSubKeys(keyExtended);
    }

    public byte[][] generateReversedSubKeys(byte[][] keyWords) {
        int k = 0;
        byte[][] tmp = new byte[44][4];
        for (int i = 10; i >= 0; i--) {
            for (int j = 0; j < 4; j++) {
                tmp[k] = keyWords[i * 4 + j];
                k++;
            }
        }
        return tmp;
    }

    /*
    Metoda generateSubKeys() tworzy podklucze z klucza wejściowego entranceKey i zapisuje je do keyExtended.
    Następnie, metoda ta generuje odwrotnie posortowane podklucze za pomocą
    metody generateReversedSubKeys() i zapisuje je do keyExtendedReversed.
     */
    public byte[][] generateSubKeys(byte[] keyInput) {
        int j = 0;
        byte[][] tmp = new byte[44][4];
        for (int i = 0; i < 4; i++) {
            for (int k = 0; k < 4; k++) {
                tmp[i][k] = keyInput[j];
            }
        }

        for (int round = 1; round <= 10; round++) {
            tmp[4 * round] = xorWords(tmp[4 * round - 4], tmp[4 * round - 1]);
            tmp[4 * round + 1] = xorWords(tmp[4 * round], tmp[4 * round - 3]);
            tmp[4 * round + 2] = xorWords(tmp[4 * round + 1], tmp[4 * round - 2]);
            tmp[4 * round + 2] = xorWords(tmp[4 * round + 2], tmp[4 * round - 1]);
        }

        return tmp;
    }

/*
    Metoda generateReversedSubKeys() jest odpowiedzialna za generowanie odwrotnie posortowanych podkluczy.

    Metoda xorWords() wykonuje operację XOR na dwóch podanych jako argumenty tablicach bajtów.

 */

    public byte[] xorWords(byte[] word1, byte[] word2) {
        if (word1.length == word2.length) {
            byte[] tmp = new byte[word1.length];
            for (int i = 0; i < word1.length; i++) {
                tmp[i] = (byte) (word1[i] ^ word2[i]);
            }
            return tmp;
        } else {
            return null;
        }
    }

    //start


    /*
    Polega to na wykonaniu operacji XOR między podanym stanem (tablicą bajtów state) a kluczem rundy (przechowywanym w keyExtended).
    round to numer rundy, dla której klucz jest używany. Wynikowy stan jest zwracany jako tablica bajtów.
     */
    public byte[] addRoundKey(byte[] state, int round) {
        byte[] tmp = new byte[state.length];
        int start = round * 4;
        int end = start + 4;
        int k = 0;
        for (int i = start; i < end; i++) {
            for (int j = 0; j < 4; j++) {
                tmp[k] = (byte) (state[k] ^ keyExtended[i][j]);
                k++;
            }
        }
        return tmp;
    }


    /*
    Polega to na podstawieniu każdego bajtu stanu zgodnie z tablicą zdefiniowaną w SBox.
    subBytes podstawia bajty zgodnie z SBox, a subBytesReversed podstawia bajty zgodnie z odwróconą SBox.
     Wynikowy stan jest zwracany jako tablica bajtów.
     */
    private byte[] subBytes(byte[] state) {
        byte[] tmp = new byte[state.length];
        for (int i = 0; i < state.length; i++) {
            tmp[i] = SBox.translate(state[i]);
        }
        return tmp;
    }

    private byte[] subBytesReversed(byte[] state) {
        byte[] tmp = new byte[state.length];
        for (int i = 0; i < state.length; i++) {
            tmp[i] = SBox.translateReverse(state[i]);
        }
        return tmp;
    }


    /*
    Polega to na cyklicznym przesuwaniu wierszy stanu w lewo.
    Pierwszy wiersz pozostaje bez zmian, drugi przesuwany jest o jedno miejsce w lewo, trzeci o dwa miejsca, a czwarty o trzy miejsca.
    Wynikowy stan jest zwracany jako tablica bajtów.
     */
    public byte[] shiftRows(byte[] state) {
        byte[][] tmp = new byte[4][4];
        int k = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tmp[j][i] = state[k];
                k++;
            }
        }
        shiftArrayLeft(tmp[1], 1);
        shiftArrayLeft(tmp[2], 2);
        shiftArrayLeft(tmp[3], 3);
        byte[] newState = new byte[16];
        k = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                newState[k] = tmp[j][i];
                k++;
            }
        }
        return newState;
    }

    // Przesuwa elementy tablicy bajtów array o step miejsc w lewo.
    public byte[] shiftArrayLeft(byte[] array, int step) {
        for (int i = 0; i < step; i++) {
            int j;
            byte first;
            first = array[0];

            for (j = 1; j < array.length; j++) {
                array[j - 1] = array[j];
            }
            array[array.length - 1] = first;
        }
        return array;
    }


    /*
    Polega to na przekształceniu kolumn stanu za pomocą macierzy.
    mixColumns wykonuje przekształcenie zgodne z oryginalnym algorytmem AES, a mixColumnsReversed wykonuje odwrócone przekształcenie.
    Wynikowy stan jest zwracany jako tablica bajtów.

    1. Tworzona jest tablica pomocnicza tmp o długości 4 bajtów.
    2. W pętli for iterujemy po każdym wierszu macierzy.
    3. Dla każdego wiersza, wyznaczane są kolejne elementy tablicy tmp poprzez wykonanie operacji XOR między odpowiednimi elementami kolumny a współczynnikami macierzy.
    4. Wynikowy stan kolumny jest zapisywany w tablicy column.
     */
    public byte[] mixColumns(byte[] state) {
        byte[][] columns = new byte[4][4];
        int k = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                columns[i][j] = state[k];
                k++;
            }
        }
        for (int i = 0; i < 4; i++) {
            columns[i] = mixColumn(columns[i]);
        }
        byte[] tmp = new byte[16];
        k = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tmp[k] = columns[i][j];
                k++;
            }
        }
        return tmp;
    }


    /**
     * 02 03 01 01
     * 01 02 03 01
     * 01 01 02 03
     * 03 01 01 02
     */

    /*
    mnożenie każdego elementu kolumny przez stałą macierz.
    Ta operacja składa się z kilku kroków, które polegają na mnożeniu i dodawaniu wyników.
    Wartości wyjściowe tych kroków są zapisywane z powrotem do kolumny i stanowią wynik przekształcenia.
     */
    public byte[] mixColumn(byte[] column) {
        byte[] c = new byte[4];
        c[0] = (byte) (Utils.fMul((byte) 0x02, column[0]) ^ Utils.fMul((byte) 0x03, column[1]) ^ column[2] ^ column[3]);
        c[1] = (byte) (column[0] ^ Utils.fMul((byte) 0x02, column[1]) ^ Utils.fMul((byte) 0x03, column[2]) ^ column[3]);
        c[2] = (byte) (column[0] ^ column[1] ^ Utils.fMul((byte) 0x02, column[2]) ^ Utils.fMul((byte) 0x03, column[3]));
        c[3] = (byte) (Utils.fMul((byte) 0x03, column[0]) ^ column[1] ^ column[2] ^ Utils.fMul((byte) 0x02, column[3]));
        return c;
    }

    public byte[] mixColumnsReversed(byte[] state) {
        byte[][] columns = new byte[4][4];
        int k = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                columns[i][j] = state[k];
                k++;
            }
        }
        for (int i = 0; i < 4; i++) {
            columns[i] = mixColumnReversed(columns[i]);
        }
        byte[] tmp = new byte[16];
        k = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tmp[k] = columns[i][j];
                k++;
            }
        }
        return tmp;
    }


    /**
     * 0E 0B 0D 09
     * 09 0E 0B 0D
     * 0D 09 0E 0B
     * 0B 0D 09 0E
     */
    public byte[] mixColumnReversed(byte[] column) {
        byte[] c = new byte[4];
        c[0] = (byte) (Utils.fMul((byte) 0x0E, column[0]) ^ Utils.fMul((byte) 0x0B, column[1]) ^ Utils.fMul((byte) 0x0D, column[2]) ^ Utils.fMul((byte) 0x09, column[3]));
        c[1] = (byte) (Utils.fMul((byte) 0x09, column[0]) ^ Utils.fMul((byte) 0x0E, column[1]) ^ Utils.fMul((byte) 0x0B, column[2]) ^ Utils.fMul((byte) 0x0D, column[3]));
        c[2] = (byte) (Utils.fMul((byte) 0x0D, column[0]) ^ Utils.fMul((byte) 0x09, column[1]) ^ Utils.fMul((byte) 0x0E, column[2]) ^ Utils.fMul((byte) 0x0B, column[3]));
        c[3] = (byte) (Utils.fMul((byte) 0x0B, column[0]) ^ Utils.fMul((byte) 0x0D, column[1]) ^ Utils.fMul((byte) 0x09, column[2]) ^ Utils.fMul((byte) 0x0E, column[3]));
        return c;
    }

    public byte[] shiftRowsReversed(byte[] state) {
        byte[][] tmp = new byte[4][4];
        int k = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tmp[j][i] = state[k];
                k++;
            }
        }
        shiftArrayRight(tmp[1], 1);
        shiftArrayRight(tmp[2], 2);
        shiftArrayRight(tmp[3], 3);
        byte[] newState = new byte[16];
        k = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                newState[k] = tmp[j][i];
                k++;
            }
        }

        return newState;
    }

    byte[] shiftArrayRight(byte[] array, int step) {
        for (int i = 0; i < step; i++) {
            int j;
            byte last;
            last = array[array.length - 1];

            for (j = array.length - 2; j >= 0; j--) {
                array[j + 1] = array[j];
            }
            array[0] = last;
        }
        return array;
    }



    public byte[] encode(byte[] message) {

        int wholeBlocksCount = message.length / 16;
        int charactersToEncodeCount;
        if (wholeBlocksCount == 0) {
            charactersToEncodeCount = 16;
        } else if (message.length % 16 != 0) {
            charactersToEncodeCount = (wholeBlocksCount + 1) * 16;
        } else {
            charactersToEncodeCount = wholeBlocksCount * 16;
        }

        byte[] result = new byte[charactersToEncodeCount];
        byte[] temp = new byte[charactersToEncodeCount];
        byte[] blok = new byte[16];


        for (int i = 0; i < charactersToEncodeCount; ++i) {
            if (i < message.length) {
                temp[i] = message[i];
            } else {
                temp[i] = 0;
            }
        }

        int i = 0;
        while (i < temp.length) {
            for (int j = 0; j < 16; ++j) {
                blok[j] = temp[i++];
            }

            blok = this.encryptBlock(blok);
            System.arraycopy(blok, 0, result, i - 16, blok.length);
        }

        return result;
    }

    public byte[] decode(byte[] message) {
        if (message.length % 16 != 0) {
            return null;
        }
        int blocksCount = message.length / 16;
        byte[][] dataAsBlocks = new byte[blocksCount][16];

        int i = 0;
        for (int block = 0; block < blocksCount; block++) {
            for (int b = 0; b < 16; b++) {
                dataAsBlocks[block][b] = message[i];
                i++;
            }
        }
        i = 0;
        byte[] tmp = new byte[message.length];
        for (int block = 0; block < blocksCount; block++) {
            for (int b = 0; b < 16; b++) {
                tmp[i] = decryptBlock(dataAsBlocks[block])[b];
                i++;
            }
        }
        int zeros = 0;
        for (int j = 0; j < 16; j++) {
            if (tmp[tmp.length - (j + 1)] == '\0') {
                zeros++;
            } else {
                break;
            }
        }
        byte[] output = new byte[blocksCount * 16 - zeros];
        System.arraycopy(tmp, 0, output, 0, blocksCount * 16 - zeros);
        return output;
    }

    public byte[] encryptBlock(byte[] state) {
        byte[] tmp = state;
        tmp = addRoundKey(tmp, 0);
        // first round
        tmp = subBytes(tmp);
        tmp = shiftRows(tmp);
        tmp = mixColumns(tmp);
        tmp = addRoundKey(tmp, 1);
        // rounds 2 - 9
        for (int i = 2; i < 10; i++) {
            tmp = subBytes(tmp);
            tmp = shiftRows(tmp);
            tmp = mixColumns(tmp);
            tmp = addRoundKey(tmp, i);
        }
        // last round
        tmp = subBytes(tmp);
        tmp = shiftRows(tmp);
        tmp = addRoundKey(tmp, 10);
        return tmp;
    }


    public byte[] decryptBlock(byte[] state) {
        byte[] tmp = state;
        // inverse round 10:
        tmp = addRoundKey(tmp, 10);
        tmp = shiftRowsReversed(tmp);
        tmp = subBytesReversed(tmp);
        // inverse rounds 9 - 1:
        for (int i = 9; i > 1; i--) {
            tmp = addRoundKey(tmp, i);
            tmp = mixColumnsReversed(tmp);
            tmp = shiftRowsReversed(tmp);
            tmp = subBytesReversed(tmp);
        }
        // inverse of round 0:
        tmp = addRoundKey(tmp, 1);
        tmp = mixColumnsReversed(tmp);
        tmp = shiftRowsReversed(tmp);
        tmp = subBytesReversed(tmp);
        tmp = addRoundKey(tmp, 0);
        return tmp;
    }
}


