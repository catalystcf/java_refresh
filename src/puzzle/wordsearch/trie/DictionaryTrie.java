package puzzle.wordsearch.trie;


public class DictionaryTrie {

	TreeNode root;
	
	class TreeNode
	{
		TreeNode []  children;
		boolean isWord;
		
		TreeNode()
		{
			this.children = new TreeNode[26];
		}
		
		TreeNode createAndGet( char ch, boolean isLast)
		{

			int i = ch - (int)'a';
			
			if ( null == this.children[i] )
			{
				this.children[i] = new TreeNode();
			}
			
			if (isLast)
			{
				this.children[i].isWord = true;
			}
			
			return this.children[i];
		}

		public TreeNode get(char ch) {
			int i = ch - (int)'a';
			
			return this.children[i];
		}
		
		public String toString()
		{
			StringBuffer sb = new StringBuffer();
			
			if (isWord) 
				sb.append( "!");
			else
				sb.append("[");
			
			for(int i =0;i < this.children.length; i++)
			{
				if ( this.children[i] != null)
				{
					char ch = (char) ( 'a' + i );
					
					sb.append(ch).append( "=").append( this.children[i].toString());
				}
				
			}
			
			sb.append("]");
			
			return sb.toString();
		}

		public int getWordCount() {
			
			int count = this.isWord ? 1 : 0;
			for( TreeNode child:this.children)
			{
				if (child != null)
					count += child.getWordCount();
			}
			return count;
		}
	}
	
	public DictionaryTrie() {
		root = new TreeNode();
	}
	
	public int getWordCount()
	{
		return root.getWordCount();
	}

	/** add a word to the dictionary */
	public void addWord(String word) {
		
		addWord(this.root, word.toLowerCase(), 0);
		
	}
	
	void addWord(TreeNode node, String word, int charI)
	{
		char ch = word.charAt(charI);
		boolean isLast = word.length() == (charI + 1);
				
		TreeNode nextNode = node.createAndGet(ch, isLast );
		
		if ( !isLast)
			addWord(nextNode, word, charI+1);
		

	}

	 boolean isWord(String string) {
		 
		 return isWord( this.root,  string, 0);
	 }
	 
	 boolean isWord( TreeNode node, String string, int idx)
	 {
		 
		 TreeNode nextNode = node.get(string.charAt(idx));
		 if ( null == nextNode) 
			 return false;
		 
		 if ( (idx+1) == string.length() ) 
			 return nextNode.isWord;
		 
		 
		 return isWord(nextNode, string, idx+1);
		 
	 }
	

}
