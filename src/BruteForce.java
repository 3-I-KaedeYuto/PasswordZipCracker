import java.util.ArrayList;
import java.util.List;

public class BruteForce {
    public final int length;
    public final boolean has_integer;
    public final boolean has_lower_alphabet;
    public final boolean has_upper_alphabet;
    public final boolean has_symbol;

    public String now_password;

    private static final char[] symbol_list = {
            '!','\"', '#', '$', '%', '&', '\'', '(', ')', '=', '+', '-', '*', '/', '\\', '?', '.', ',',
            '<', '>', '_', '@', '`', '{', '}', '[', ']', ':', ';', '^', '~', '|'
    };
    private static final int integers = 10;
    private static final int lower_alphabets = 26;
    private static final int upper_alphabets = 26;
    private static final int symbols = symbol_list.length;

    private List<Character> characters;
    private int[] indexes;


    public BruteForce(int length, boolean has_integer, boolean has_lower_alphabet, boolean has_upper_alphabet, boolean has_symbol){
        this.length = length;
        this.has_integer = has_integer;
        this.has_lower_alphabet = has_lower_alphabet;
        this.has_upper_alphabet = has_upper_alphabet;
        this.has_symbol = has_symbol;
        characters = new ArrayList();
        if (this.has_integer) addIntegers();
        if (this.has_lower_alphabet) addLowerAlphabets();
        if (this.has_upper_alphabet) addUpperAlphabets();
        if (this.has_symbol) addSymbols();
        indexes = new int[length];
        for (int i = 0; i < length; i++) {
            indexes[i] = 0;
        }
        indexes[0] = -1;
    }

    private void addIntegers(){
        add('0', integers);
    }

    private void addLowerAlphabets(){
        add('a', lower_alphabets);
    }

    private  void addUpperAlphabets(){
        add('A', upper_alphabets);
    }

    private void addSymbols(){
        for (char c : symbol_list) {
            characters.add(c);
        }
    }

    private void add(int first, int length){
        for (int i = 0; i < length; i++) {
            characters.add((char)(first + i));
        }
    }

    public synchronized String next(){
        indexes[0]++;
        for (int i = 0; i < length - 1; i++) {
            if (indexes[i] >= characters.size()){
                indexes[i] = 0;
                indexes[i + 1]++;
            }
        }
        if (indexes[length - 1] >= characters.size()) return null;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(characters.get(indexes[length - i - 1]));
        }
        now_password = result.toString();
        return now_password;
    }
}

