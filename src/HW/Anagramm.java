package HW;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Function;

public class Anagramm {


	public static void main(String[] args) {
		String baseWord = "electricity";

		System.out.println("-------isAnagram returns true--------");
		System.out.println(baseWord + " | " + "electric" + " -> " + isAnagram(baseWord, "electric"));
		System.out.println(baseWord + " | " + "city" + " -> " + isAnagram(baseWord, "city"));
		System.out.println(baseWord + " | " + "tet" + " -> " + isAnagram(baseWord, "tet"));
		System.out.println(baseWord + " | " + "let" + " -> " + isAnagram(baseWord, "let"));
		System.out.println(baseWord + " | " + "tree" + " -> " + isAnagram(baseWord, "tree"));
		System.out.println(baseWord + " | " + "critic" + " -> " + isAnagram(baseWord, "critic"));

		System.out.println("\n-------isAnagram returns false-------");
		System.out.println(baseWord + " | " + "tot" + " -> " + isAnagram(baseWord,"tot"));
		System.out.println(baseWord + " | " + "tet " + " -> " + isAnagram(baseWord, "tet "));
		System.out.println(baseWord + " | " + "teeeet" + " -> " + isAnagram(baseWord, "teeeet"));
		System.out.println(baseWord + " | " + " toot" + " -> " + isAnagram(baseWord, " toot"));
		System.out.println(baseWord + " | " + "1tot" + " -> " + isAnagram(baseWord, "1tot"));
		System.out.println(baseWord + " | " + "electricityy" + " -> " + isAnagram(baseWord, "electricityy"));
	}


	public static boolean isAnagram(String word, String anagram) {
		if(Objects.isNull(word) || Objects.isNull(anagram)) throw new IllegalArgumentException("Base word or anagram can't be null");
		if(word.isEmpty() || anagram.isEmpty()) throw new IllegalArgumentException("Base word or anagram can't be empty");
		
		// Converting string to Map, key - character from the input string, value - number of entries of character in the input string
		Function<String, Map<Character, Integer>> stringToMap = new Function<String, Map<Character,Integer>>() {	
			@Override
			public Map<Character, Integer> apply(String t) {
				Map<Character, Integer> result = new HashMap<>();
				for (char ch : t.toCharArray()) {
					Integer prev = result.get(ch);
					if(Objects.isNull(prev)) result.put(ch, 1); 
					else result.put(ch, 1 + prev);
				}
				return result;
			}
		};
		
		// Strings 'word' and 'anagram' are converted to maps
		Map<Character, Integer> wordMap = stringToMap.apply(word);
		Map<Character, Integer> anagramMap = stringToMap.apply(anagram);
		
		// Comparing two maps, first arg - 'word' converted to map, second arg - 'anagram' converted to map
		BiPredicate<Map<Character, Integer>, Map<Character, Integer>> mapsCompare = 
				new BiPredicate<Map<Character, Integer>, Map<Character, Integer>>(){
			@Override
			public boolean test(Map<Character, Integer> baseWord, Map<Character, Integer> anagram) {
				// If number of unique symbols in 'word' less then number of unique symbols in 'anagram' - returns false
				if(baseWord.size() < anagram.size()) return false;
				for(Map.Entry<Character, Integer> entry: anagram.entrySet()) {
					Character key = entry.getKey();
					Integer valueInAnagram = entry.getValue();
					Integer valueInWord = baseWord.get(key);
					// If character from 'anagram' doesn't presented in 'word' or used more times then used in 'word' - returns false 
					if(Objects.isNull(valueInWord) || valueInWord < valueInAnagram) return false;
				}
				return true;
			}
			
		};
		
		// returns result of map comparison
		return mapsCompare.test(wordMap, anagramMap);
	}
}
