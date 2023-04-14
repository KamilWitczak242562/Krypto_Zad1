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
}


