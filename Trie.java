package trie;

import java.util.ArrayList;

public class Trie {
	
	private Trie() { }
	
	public static TrieNode buildTrie(String[] allWords) {
		TrieNode root = 
				new TrieNode(null, new TrieNode(new Indexes(0, (short) 0, (short) (allWords[0].length()-1)),null,null),null);
		
		for(int i = 1; i < allWords.length; i++) {
			TrieNode prv = root;
			TrieNode ptr = root.firstChild;
			int start = 0;
			boolean Leave = false;
			String word = allWords[i];
			while(!Leave) {
				if(ptr == null) {
					TrieNode AddSib = new TrieNode(new Indexes(i , (short) start, (short) (word.length()-1)), null, null);
					prv.sibling = AddSib;
					break;
				}
				else {
					if(word.charAt(start) == allWords[ptr.substr.wordIndex].charAt(ptr.substr.startIndex)) {
						if(ptr.firstChild == null) {
							int incriment = SimilerCar(allWords[ptr.substr.wordIndex].substring(ptr.substr.startIndex), word.substring(start));
							TrieNode Added = new TrieNode(new Indexes(i, (short)(start + incriment)  , (short)(word.length()-1)) , null, null);;
							TrieNode Replace = new TrieNode(new Indexes(ptr.substr.wordIndex, (short)(ptr.substr.startIndex + incriment), (short)(ptr.substr.endIndex)) , null, Added);
							TrieNode doub = new TrieNode(new Indexes(ptr.substr.wordIndex, (short) (ptr.substr.startIndex), (short)(ptr.substr.startIndex + incriment-1)), Replace,ptr.sibling );
							if(isP(prv, ptr)) {
								prv.firstChild = doub;
								break;
							}
							else {
								prv.sibling = doub;
								break;
							}
						}
						else{
							if(insid(allWords[ptr.substr.wordIndex].substring(ptr.substr.startIndex,ptr.substr.endIndex+1), word.substring(start)) ) {
								start = start + ptr.substr.endIndex - ptr.substr.startIndex+1;
								prv = ptr;
								ptr = ptr.firstChild;
							}
							else {
								int incriment = SimilerCar(allWords[ptr.substr.wordIndex].substring(ptr.substr.startIndex), word.substring(start));
								TrieNode newInput = new TrieNode(new Indexes(i,(short) (start + incriment),(short) (word.length()-1)), null, null); 
								TrieNode ptrNew = new TrieNode(new Indexes(ptr.substr.wordIndex,(short) (ptr.substr.startIndex+incriment),(short) (ptr.substr.endIndex)), ptr.firstChild, newInput);
								TrieNode doub = new TrieNode(new Indexes(ptr.substr.wordIndex,(short) (ptr.substr.startIndex),(short) (ptr.substr.startIndex+incriment-1)), ptrNew, ptr.sibling);
								if(isP(prv, ptr)) {
									prv.firstChild = doub;
									break;
								}
								else {
									prv.sibling = doub;
									break;
								}
							}
							
						}
					}
					else {
						prv = ptr;
						ptr = ptr.sibling;
					}
				}
			}
		}
		return root;
	}
	
	private static int SimilerCar(String word, String ptrW) {
		int counter = 0;
		for(int i = 0; i<word.length(); i++) {
			if(word.charAt(i) == ptrW.charAt(i)) {
				counter++;
			}
			else {
				break;
			}
		}
		return counter;
	}

	private static boolean isP(TrieNode prv , TrieNode ptr) {
		if(prv.firstChild == null) {
			return false;
		}
		else if(prv.firstChild.equals(ptr)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private static boolean insid(String ptrS, String sub) {
		if(sub.length() <= ptrS.length()) {
			return false;
		}
		else if(ptrS.equals(sub.substring(0,ptrS.length()))) {
			return true; 
		}
		else {
			return false;
		}
	}
	
	/**
	 * Given a trie, returns the "completion list" for a prefix, i.e. all the leaf nodes in the 
	 * trie whose words start with this prefix. 
	 * For instance, if the trie had the words "bear", "bull", "stock", and "bell",
	 * the completion list for prefix "b" would be the leaf nodes that hold "bear", "bull", and "bell"; 
	 * for prefix "be", the completion would be the leaf nodes that hold "bear" and "bell", 
	 * and for prefix "bell", completion would be the leaf node that holds "bell". 
	 * (The last example shows that an input prefix can be an entire word.) 
	 * The order of returned leaf nodes DOES NOT MATTER. So, for prefix "be",
	 * the returned list of leaf nodes can be either hold [bear,bell] or [bell,bear].
	 *
	 * @param root Root of Trie that stores all words to search on for completion lists
	 * @param allWords Array of words that have been inserted into the trie
	 * @param prefix Prefix to be completed with words in trie
	 * @return List of all leaf nodes in trie that hold words that start with the prefix, 
	 * 			order of leaf nodes does not matter.
	 *         If there is no word in the tree that has this prefix, null is returned.
	 */
	public static ArrayList<TrieNode> completionList(TrieNode root,
										String[] allWords, String prefix) {
		//System.out.print("aaaaa");
		ArrayList<TrieNode> re = new ArrayList<TrieNode>();
		TrieNode ptr = root.firstChild;
		int start = 0;
		boolean Leave = false;
		while(!Leave) {
			if(ptr == null) {
				System.out.println("ptr == null");
				return null;
			}
			else {
				String ptrW = allWords[ptr.substr.wordIndex];
				String pre = prefix.substring(start);
				if(prefix.charAt(start) == ptrW.charAt(ptr.substr.startIndex)) {
					int preL = pre.length();
					int ptrL = ptr.substr.endIndex - ptr.substr.startIndex + 1;
					if(ptr.firstChild == null) {
						System.out.println("leaf Node");
						//if(prefix.length()-start > ptr.substr.endIndex - ptr.substr.startIndex +1) {
						//	return null;
						//}
						if(preL <= ptrL && prefix.substring(start).equals(ptrW.substring(ptr.substr.startIndex, ptr.substr.startIndex + prefix.length() - start))) {
							re.add(ptr);
							return re;
						}
						return null;
						
					}
					else {
						System.out.println("Not leaf Node");
						if(preL == ptrL && pre.equals(ptrW.substring(ptr.substr.startIndex, ptr.substr.endIndex + 1))) {
							System.out.println("all children");
							break;
						}
						
						else if(preL < ptrL && pre.equals(ptrW.substring(ptr.substr.startIndex, ptr.substr.startIndex + preL))) {
							System.out.println("all children");
							break;
							
						}
						
						else if(preL > ptrL && pre.substring(0, ptr.substr.endIndex - ptr.substr.startIndex + 1).equals(ptrW.substring(ptr.substr.startIndex, ptr.substr.endIndex + 1))) {
							System.out.println("go inside");
							start = start + ptr.substr.endIndex - ptr.substr.startIndex + 1;
							ptr = ptr.firstChild;
						}
						
						else {
							System.out.println("tests failed");
							return null;
						}
					}
				}
				else {
					System.out.println("ptr = ptr.sibling;");
					ptr = ptr.sibling;
				}
			}	
		}
		ArrayList<TrieNode> ret = allChildren(ptr, allWords);
		System.out.println(ret);
		return Compress(ret);
	}
	
	private static ArrayList<TrieNode> allChildren(TrieNode root, String[] words) {
		ArrayList<TrieNode> re = new ArrayList<TrieNode>();
		ArrayList<TrieNode> a = new ArrayList<TrieNode>();
		for (TrieNode ptr=root.firstChild; ptr != null; ptr=ptr.sibling) {
			if(ptr.firstChild == null) {
				re.add(ptr);
				a.addAll(re);
			}
			a.addAll(allChildren(ptr, words));
		}
		return a;	
	}
	
	private static ArrayList<TrieNode> Compress(ArrayList<TrieNode> longer){
		ArrayList<TrieNode> shorter = new ArrayList<TrieNode>();
		int index = 0;
		while(index < longer.size()) {
			if(!shorter.contains(longer.get(index))) {
				shorter.add(longer.get(index));
			}
			index++;
		}
		return shorter;
	}
	
	public static void print(TrieNode root, String[] allWords) {
		System.out.println("\nTRIE\n");
		print(root, 1, allWords);
	}
	
	private static void print(TrieNode root, int indent, String[] words) {
		if (root == null) {
			return;
		}
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		
		if (root.substr != null) {
			String pre = words[root.substr.wordIndex]
					.substring(0, root.substr.endIndex+1);
			System.out.println("      " + pre);
		}
		
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		System.out.print(" ---");
		if (root.substr == null) {
			System.out.println("root");
		} else {
			System.out.println(root.substr);
		}
		
		for (TrieNode ptr=root.firstChild; ptr != null; ptr=ptr.sibling) {
			for (int i=0; i < indent-1; i++) {
				System.out.print("    ");
			}
			System.out.println("     |");
			print(ptr, indent+1, words);
		}
	}
 }
