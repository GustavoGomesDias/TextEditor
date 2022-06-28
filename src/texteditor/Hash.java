package texteditor;

import java.io.*;
import java.util.ArrayList;
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
            soma += (long) s.charAt(i) * 91 * this.pesos[i] * charSum;

        }
        return soma % MAXTAB;
    }

    private static int getRandomNumberRange(int min, int max) {
        Random r = new Random();
        return r.ints(min, (max + 1)).limit(1).findFirst().getAsInt();
    }

    public void loadWords() throws IOException {
        int nCollision = 0, size = 0;
        BufferedReader buffreader = new BufferedReader(new FileReader("pt.txt"));
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
                    this.words[(int) position] = input;
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

    public String[] getSuggestions(int position) {
        int i = 0, j = 0;
        int plusPos = position, fewerPos = position;
        int lstPos = 0;
        String[] suggestions = new String[100];

        while (j < 50) {
            fewerPos--;
            String word = this.words[(int) fewerPos];
            if (word != null) {
                suggestions[lstPos] = word;
                lstPos++;
            }
            j++;
        }

        while (i < 50) {
            plusPos++;
            String word = this.words[(int) plusPos];
            if (word != null) {
                suggestions[lstPos] = word;
                lstPos++;
            }
            i++;
        }

        return suggestions;
    }
}
