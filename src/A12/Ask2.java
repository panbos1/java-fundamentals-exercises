package A12;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.*;


class PalindromikesFraseis {
    public static void main(String[] args) {


        long time = System.nanoTime();


        System.out.println("Please give me your word here for check.");

        Scanner in = new Scanner(System.in);
        String nextLine = in.nextLine();

        System.out.println(nextLine);
        System.out.println("This is the time :");
        System.out.println(System.nanoTime());

        if (isPalindromikiFrash(nextLine)){
            System.out.println("Congrats your word is palindromic !");
        }else {
            System.out.println("Ooops something went wrong with your word try again !");
        }

    }

    public static boolean isPalindromikiFrash(String nextLine) {
        nextLine = nextLine.replaceAll("[-+^*]", " ");
        nextLine=(Normalizer.normalize(nextLine, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "")).toLowerCase();
        char[] tmp = nextLine.toCharArray();
        for(int i=0;i<nextLine.length();i++)
        {
            if(nextLine.charAt(i) == 'ς')
            {
                tmp[i] = 'σ';
            }
        }
        nextLine = String.valueOf(tmp);
        StringBuilder sb=new StringBuilder(nextLine);
        sb.reverse();
        String reversed = sb.toString();
        nextLine = nextLine.replace(" ", "");
        reversed = reversed.replace(" ", "");
        char[] original = nextLine.toCharArray();
        char[] reverse = reversed.toCharArray();


        for (int i = nextLine.length() - 1; i >= 0; i--) {
            reversed += nextLine.charAt(i);

        }
        boolean palindrome= true;
        for (int i = 0; i < nextLine.length(); i++) {
            if (!Arrays.equals(original, reverse)) {
                palindrome = false;
            }
        }
        if (palindrome) {
            return true;
        } else {
            return false;




        }



    }




}

 class PalindromikesLexikou {
    public static void main(String[] args) {
        String filename = "gr.dic";

        long startTime = System.currentTimeMillis();

        ArrayList<String> words = new ArrayList<>();
        ArrayList<String> palindromes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineWords = line.split("\\s+");
                Collections.addAll(words, lineWords);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int wordCount = words.size();
        double totalWordLength = words.stream().mapToInt(String::length).sum();

        for (String word : words) {
            if (isPalindrome(word)) {
                palindromes.add(word);
            }
        }

        long endTime = System.currentTimeMillis();
        double executionTime = (endTime - startTime) / 1000.0;


        Collections.sort(palindromes, (a, b) -> Integer.compare(b.length(), a.length()));


        double palindromePercentage = (double) palindromes.size() / wordCount * 100;


        System.out.println("Total count" + wordCount);
        System.out.println("Average lenght of words: " + totalWordLength / wordCount);
        System.out.println("size of palidromic words" + palindromes.size());
        System.out.println("palidromic words: " + palindromes);
        System.out.println("(palidromic words percentage" + palindromePercentage + "%");
        System.out.println("execution Time " + executionTime + " seconds");
    }

    // Συνάρτηση που ελέγχει αν μια λέξη είναι παλίνδρομη
    public static boolean isPalindrome(String word) {
        String reversed = new StringBuilder(word).reverse().toString();
        return word.equals(reversed);
    }
}

