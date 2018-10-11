
/*
Created by: GREG WOO
Program: Implementing several classical ciphers
*/

public class Message {

	public String message;
	public int lengthOfMessage;

	public Message (String m){
		message = m;
		lengthOfMessage = m.length();
		this.makeValid();
	}

	public Message (String m, boolean b){
		message = m;
		lengthOfMessage = m.length();
	}

	/**
	 * makeValid modifies message to remove any character that is not a letter and turn Upper Case into Lower Case
	 */
	public void makeValid(){

		// We first create an array to put all the char of the message
    int length = this.lengthOfMessage;
    char[] letterArray = new char[length];
    int counter = 0;


    for(int i = 0; i < length; i++) {
      // We remove all the non letter char
      if((this.message.charAt(i) < 123) && (this.message.charAt(i) > 96 ) ) {
        letterArray[counter] = this.message.charAt(i);
        counter ++;

        // we turn upper case into lower case
      } else if ((this.message.charAt(i) > 64) && (this.message.charAt(i) < 91)) {
        letterArray[counter] = (char) (this.message.charAt(i) + 32);
        counter ++;
      }
    }

    //then we convert the array in the message string
    String temp = "";

    for (int j = 0; j < letterArray.length ; j++) {
      temp += letterArray[j];
    }

    // Here we update the attributes
    this.message = temp.trim();
    this.lengthOfMessage = counter;

	}

	/**
	 * prints the string message
	 */
	public void print(){
		System.out.println(message);
	}

	/**
	 * tests if two Messages are equal
	 */
	public boolean equals(Message m){
		if (message.equals(m.message) && lengthOfMessage == m.lengthOfMessage){
			return true;
		}
		return false;
	}

	/**
	 * caesarCipher implements the Caesar cipher : it shifts all letter by the number 'key' given as a parameter.
	 * @param key
	 */
	public void caesarCipher(int key){

		char[] letterArray = new char[this.lengthOfMessage];
    int counter = 0;
    String temp = "";
    char letterChecker;

    // We add the key to each char of the message string
    for(int i = 0; i < this.lengthOfMessage; i++) {
      letterChecker = (char) (this.message.charAt(i) + key);

      // We check if the char we get is a lower case letter
      if (!((letterChecker > 96) && (letterChecker < 123))) {

        // If the char is not a lower case letter, we adjust based on the key
        if(key >=0) {
          letterArray[counter] = (char) (letterChecker - 26);

        } else {
          letterArray[counter] = (char) (letterChecker + 26);
        }

      } else {
        letterArray[counter] = letterChecker;
      }

      temp += letterArray[counter];
      counter ++;

    }

    this.message = temp.trim();

	}

	public void caesarDecipher(int key){
		this.caesarCipher(- key);
	}

	/**
	 * caesarAnalysis breaks the Caesar cipher
	 * you will implement the following algorithm :
	 * - compute how often each letter appear in the message
	 * - compute a shift (key) such that the letter that happens the most was originally an 'e'
	 * - decipher the message using the key you have just computed
	 */
	public void caesarAnalysis(){

		// We create an array to store every letter of the message
    char[] letterArray = new char[this.lengthOfMessage];

    // We put every char of the message into the array
    for(int i = 0; i < this.lengthOfMessage; i++) {
      letterArray[i] = this.message.charAt(i);
    }

    // We compare each char of the array to the letters of the message
    // We store the char with the most appearances
    int biggestCounter = 0;
    char popularChar = ' ';

    for(int j = 0; j < this.lengthOfMessage; j++) {
      int appearanceCounter = 0;

      for(int k = 0; k < this.lengthOfMessage; k++) {

        if(letterArray[j] == this.message.charAt(k)) {
          appearanceCounter ++;

          if( appearanceCounter > biggestCounter) {
            popularChar = letterArray[j];
            biggestCounter = appearanceCounter;
          }
        }
      }
    }

    // Then we find the index for the popularChar
    // and we find the key based on the hypothesis that
    // the popularChar is e with index 101
    int indexPopularChar = (int) popularChar;
    int keyFound = indexPopularChar - 101;

    this.caesarDecipher(keyFound);

	}

	/**
	 * vigenereCipher implements the Vigenere Cipher : it shifts all letter from message by the corresponding shift in the 'key'
	 * @param key
	 */
	public void vigenereCipher (int[] key){

		// We create an array to store every letter of the message
    char[] letterArray = new char[this.lengthOfMessage];

    // We put every char of the message into the array
    for(int i = 0; i < this.lengthOfMessage; i++) {
      letterArray[i] = this.message.charAt(i);
    }

    int keyLength = key.length;
    char letterChecker;
    String temp = "";

    for(int j = 0; j < letterArray.length; j++) {
      letterChecker = (char) (letterArray[j] + key[j%keyLength]);

      // We check if the char we get is a lower case letter
      if (!((letterChecker > 96) && (letterChecker < 123))) {

        // If the char is not a lower case letter, we adjust based on the key
        if(key[j%keyLength] >=0) {
          letterArray[j] = (char) (letterChecker - 26);

        } else {
          letterArray[j] = (char) (letterChecker + 26);
        }

      } else {
        letterArray[j] = letterChecker;
      }

      // Here we store each char inside the temp string
      // to recreate the message
      temp += letterArray[j];

    }

    this.message = temp.trim();

	}

	/**
	 * vigenereDecipher deciphers the message given the 'key' according to the Vigenere Cipher
	 * @param key
	 */
	public void vigenereDecipher (int[] key){

		int sizeKey = key.length;
    int[] newKey = new int[sizeKey];
    int counter = 0;

    for(int oneKey : key){

      newKey[counter] = -oneKey;
      counter++;

    }

    this.vigenereCipher(newKey);

	}

	/**
	 * transpositionCipher performs the transition cipher on the message by reorganizing the letters and eventually adding characters
	 * @param key
	 */
	public void transpositionCipher (int key){

		// First we want to see how many *s we have to add in the message
    int lengthCipher = this.lengthOfMessage;

    while(lengthCipher % key != 0) {
      lengthCipher++;
    }

    char[] letterArray = new char[lengthCipher];

    // We put every char of the message into the array
    // and we put * to fill the gaps
    for(int i = 0; i < lengthCipher; i++) {

      if( i < this.lengthOfMessage) {
        letterArray[i] = this.message.charAt(i);

      } else {
        letterArray[i] = '*';
      }
    }

    char[] secondLetterArray = new char[lengthCipher];
    int secondCounter = 0;

    // Here we store in the secondLetterArray the characters
    // of each column to decipher the message
    for(int j = 0; j < key; j++) {

      for(int k = 0; k < lengthCipher; k++) {

        if(k%key == j) {
          secondLetterArray[secondCounter] = letterArray[k];
          secondCounter ++;
        }
      }
    }

    String temp = "";

    // Finally we convert the array to a string
    for(int l = 0; l < lengthCipher; l++) {

      temp += secondLetterArray[l];
    }

    this.message = temp.trim();
    this.lengthOfMessage = lengthCipher;

	}

	/**
	 * transpositionDecipher deciphers the message given the 'key'  according to the transition cipher.
	 * @param key
	 */
	public void transpositionDecipher (int key){

		int lengthCipher = this.lengthOfMessage;
    int secondLengthCipher = lengthCipher / key;
    this.transpositionCipher(secondLengthCipher);

    // We create an array to store every letter of the message
    char[] letterArray = new char[this.lengthOfMessage];

    // We put every char of the message into the array
    int counter = 0;
    String temp = "";

    for(int i = 0; i < this.lengthOfMessage; i++) {

      if(this.message.charAt(i) != '*') {

        letterArray[counter] = this.message.charAt(i);
        temp += letterArray[counter];
        counter++;
      }
    }

    this.message = temp.trim();
    this.lengthOfMessage = counter;

	}

}
