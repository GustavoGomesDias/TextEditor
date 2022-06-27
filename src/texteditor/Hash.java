package texteditor;

import java.io.*;
import java.util.LinkedList;
import java.util.Random;

public class Hash {
    public static final int MAXSTR = 256;
    public static final int MAXTAB = 10000000;
    String[] words = new String[MAXTAB];
    int[] pesos;

    public Hash() {
        this.pesos = geraVetorPesos();
    }

    public long h(String s) {

        int i, ci;
        long soma = 0;
        long charSum = 0;

        for (ci = 0; ci < s.length(); ci++) {
            charSum += s.charAt(ci);
        }
        for (i = 0; i < s.length(); i++) {
            soma += (long) s.charAt(i) * 31 * this.pesos[i] * charSum;

        }
        return soma % MAXTAB;
    }

    private static int getRandomNumberRange(int min, int max) {
        Random r = new Random();
        return r.ints(min, (max + 1)).limit(1).findFirst().getAsInt();
    }

    public void loadWords() throws IOException {
        int nCollision = 0, size = 0;
        BufferedReader buffreader = new BufferedReader(new FileReader("pt.dic"));
        String input = buffreader.readLine();
        while(input != null) {
            if (!input.contains("%")) {
                long position = h(input);
                if (this.words[(int) position] != null) {
                    long i = position;
                    while (this.words[(int) i] != null) {
                        i++;
                    }

                    this.words[(int) i] = input;
                } else {
                    if (input == "teste") {
                        System.out.println(position);
                    }
                    this.words[(int) position] = input;
                    System.out.println(this.words[(int) position]);
                }
            }
            size++;
            input = buffreader.readLine();
        }
        System.out.println(nCollision);
        System.out.println(size);
    }

    public int[] geraVetorPesos() {
        int i;
        int[] vetor = new int [MAXSTR];
        int intervaloInicial = 50;
        for (i=0 ; i < vetor.length; i++)
        {
            vetor [i] = getRandomNumberRange(intervaloInicial , MAXSTR);
        }

        return vetor;
    }
}
