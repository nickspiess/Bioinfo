import java.util.*;
import java.io.*;

public class Bioinfo extends Node {

    /**
     * File name: Bioinfo.java
     * @author SPIESSNA
     * description: This program will take in an index and a text file to read through
     * The file will contain commands on inserting and manipulating SLList's of data
     * The commands will be passed through the initiating sequence class to do data manipulation
     * Version probably 100
     * @since 10/15/19
     */


    /**
     * Main method to drive program
     * @param args - Takes in two command line arguments, the first being the amount of sequences to create,
     * the second being the text file to read through.
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        String size = args[0];
        int numSize = Integer.parseInt(size);
        String fileName = args[1];
        linePass(fileName, numSize);
    }

    /**
     * This method will take in a file and the sequences to generate, will iterate through file, line by line
     * while there is still lines, and will pass the line to the command check class
     * @param file - The file we are reading thorugh
     * @param numSize - The amount of sequences to generate
     * @throws FileNotFoundException
     */
    public static void linePass(String file, int numSize) throws FileNotFoundException {
        Sequence[] sequence = new Sequence[numSize];
        Scanner inputFile = new Scanner(new File(file));
        while (inputFile.hasNextLine()) { // While file contains other lines
            // if there is inputFile hasNext token, continue while it does
            if (inputFile.hasNext()) {
                while (inputFile.hasNext()) { // While line contains tokens
                    String line = inputFile.nextLine(); // Grab line
                    commandCheck(line, sequence); // Pass line to command checker
                }
            }
            // If there is no next token, end.
            else {
                break;
            }
        }
    }


    /**
     * This method will read through a file, grabbing commands and any needed information to pass through to the
     * sequence class in order to do any data manipulation as requested by the file.  It may print or insert as well.
     * @param line - The line we are reading from the file
     * @param sequenceArray - The array of sequences that we generated
     */
    public static void commandCheck(String line, Sequence[] sequenceArray)  {
        int lineWordCount = countWordsUsingStringTokenizer(line);
        String command;
        int index;
        String type;
        String bases;
        // Read through line
        Scanner lineReader = new Scanner(line);
        // Creates variable to check for word count
        while (lineReader.hasNext()) {
            // Grab command
            command = lineReader.next();
            command = command.toLowerCase();
            if (command.equals("insert")) {
                // if == 3, we assume no bases
                if (lineWordCount == 3) {
                    System.out.println();
                    index = lineReader.nextInt();
                    type = lineReader.next();
                    if (index > sequenceArray.length || index < 0) {
                        System.out.println("Invalid position to insert at position: " + index);
                        System.out.println();
                    }
                    else {
                        //SLList<Node> nodeList = new SLList<>();
                        // Generate sequence and add to array of sequences
                        sequenceArray[index] = new Sequence(type, index);
                    }
                }
                // If there are bases in line
                else if (lineWordCount == 4) {
                    // Grab index
                    index = lineReader.nextInt();
                    // Grab type
                    type = lineReader.next();
                    // Uppercase type
                    type = type.toUpperCase();
                    // Grab bases
                    bases = lineReader.next();
                    // Make bases uppercase
                    bases = bases.toUpperCase();
                    // Check if bases and type are correct
                    if (index > sequenceArray.length || index < 0) {
                        System.out.println("Invalid position to insert at position: " + index);
                        System.out.println();
                    }
                    else if (baseAndTypeCheck(type, bases)) {
                        // Generate and insert list
                        sequenceArray[index] = new Sequence(type, bases, index);
                    }
                }
            }
            // Remove command
            if (command.equals("remove")) {
                // Function for removal of sequence at position p remove(index)
                // Grab number
                index = lineReader.nextInt();
                // If no sequence
                if (sequenceArray.length < index || index < 0) {
                    System.out.println("Index is less than or greater than sequence length.");
                    System.out.println();
                }
                else if (sequenceArray[index] == null) {
                    System.out.println("There is no sequence to remove at position: " + index);
                    System.out.println();
                }
                //else
                // If sequence is contained
                else {
                    sequenceArray[index] = null;
                }
            }
            // Print command
            if (command.equals("print")) {
                // Check for words in String
                if (lineWordCount == 2) { // Position print command
                    // Grab index
                    index = lineReader.nextInt();
                    // Empty sequence, inform user
                    if (sequenceArray.length < index || index < 0) {
                        System.out.println("Out of bounds print at position: " + index);
                        System.out.println();
                    }
                    else if (sequenceArray[index] == null) {
                        System.out.println("There is no sequence at position: " + index);
                    }
                    // Print sequence
                    else {
                        System.out.println(sequenceArray[index].toString());
                        System.out.println();
                    }
                } else {
                    // Print all
                    for (int i = 0; i <= sequenceArray.length - 1; i++) {
                        if (sequenceArray[i] == null) {
                            System.out.println(i);
                        }
                        else {
                            System.out.println(sequenceArray[i].toString());
                        }
                    }
                    System.out.println();
                }
            }
            // Splice command
            if (command.equals("splice")) {
                // Grab sequence number
                index = lineReader.nextInt();
                // Grab type
                type = lineReader.next();
                type = type.toUpperCase();
                // Grab bases
                bases = lineReader.next();
                bases = bases.toUpperCase();
                if (index > sequenceArray.length || index < 0) {
                    System.out.println("There is not a sequence at: " + index);
                    System.out.println();
                    return;
                }
                // Int of where to add the splice
                int whereToAdd = lineReader.nextInt();
                // Call splice, further error checking in call
                sequenceArray[index].splice(bases, whereToAdd - 1, type);

                // Function to splice : Insert given bases of certain sequence
                // example command:
                // 0 DNA ATGCATTGCC
                // splice 0 DNA GCGC 6
                // 0 DNA ATGCAT(GCGC)TGCC
            }
            // Clip command
            if (command.equals("clip")) {
                //Check for words in string
                if (lineWordCount == 4) { // clip pos start end
                    // Replace sequence @ position with clipped version of that sequence
                    // 0 RNA AUG(CAU)UGCC
                    // clip 0 3 5
                    // New entry : 0 RNA CAU
                    // Grab index
                    index = lineReader.nextInt();
                    // Grab index to start clip
                    int indexStart = lineReader.nextInt();
                    // Grab index to end clip
                    int indexEnd = lineReader.nextInt();
                    // Make sure we're in bounds of sequence
                    if (index > sequenceArray.length || index < 0) {
                        System.out.println("Out of bounds at position " + index);
                        System.out.println();
                        return;
                    }
                    // Check to see if sequence exists
                    else if (sequenceArray[index] == null) {
                        System.out.println("No sequence exists at position: " + index);
                        System.out.println();
                        return;
                    }
                    // Sequence exists
                    else {
                        // Clip it
                        // Further error checking in method
                        sequenceArray[index].clip(indexStart, indexEnd);
                    }
                }
                // clip pos start
                else {
                    // Replace the sequence @ position pos with position of clip and remaining
                    // Grab index
                    index = lineReader.nextInt();
                    // Grab where we are clipping to
                    int start = lineReader.nextInt();
                    if (index > sequenceArray.length || index < 0) {
                        System.out.println("Out of bounds at position: " + index);
                        System.out.println();
                        return;
                    }
                    // Check to see if sequence exists
                    else if (sequenceArray[index] == null) {
                        System.out.println("No sequence exists at position: " + index);
                        System.out.println();
                        return;
                    }
                    // Check to see if sequence exists
                    if (sequenceArray[index] == null) {
                        System.out.println("No sequence exists at position: " + index);
                        System.out.println();
                    }
                    // Sequence exists
                    else {
                        sequenceArray[index].clip(start);
                    }
                }
            }
            // Copy command
            if (command.equals("copy")) {
                // Copy sequence in pos1(seq1) to pos2(seq2)
                // If no sequence in pos1: output message and return w/ no changes.
                // Grab index
                index = lineReader.nextInt();
                // Grab index we are copying over
                int indexToReplace = lineReader.nextInt();
                // Check if index is larger than 0
                if (index < 0 || index > sequenceArray.length) {
                    System.out.println("No sequence exists at position: " + index);
                    System.out.println();
                }
                // If first index doesn't exist, notify and no change
                else if (sequenceArray[index] == null || sequenceArray[index].getBases() == null) {
                    System.out.println("No bases exist at position " + index);
                    System.out.println();
                    return;
                }
                // If second index is greater than our length, let user know and no change
                else if (indexToReplace > sequenceArray.length || indexToReplace < 0) {
                    System.out.println("Our position to copy to does not exist, making no changes.");
                    System.out.println();
                    return;
                }
                // Valid, continue with copy
                else {
                    sequenceArray[index].copy(sequenceArray, indexToReplace);
                }
            }
            // Swap command
            if (command.equals("swap")) {
                // Swap tails of sequences of pos 1 and pos 2
                // Initiate lists to add
                SLList<String> swap1 = new SLList<>();
                SLList<String> swap2 = new SLList<>();
                // Grab pos1
                index = lineReader.nextInt();
                // Grab start1
                int start1 = lineReader.nextInt();
                // Grab pos 2
                int pos2 = lineReader.nextInt();
                // Grab start2
                int start2 = lineReader.nextInt();
                // If pos1 is less than zero or greater than length
                if (index < 0 || index > sequenceArray.length) {
                    System.out.println("There is no sequence to swap at position: " + index);
                }
                // if pos2 is less than zero or greater than length
                else if (pos2 < 0 || pos2 > sequenceArray.length) {
                    System.out.println("There is no sequence to swap at position: " + pos2);
                }
                // If sequence at index doesn't exist or bases are null
                else if (sequenceArray[index] == null || sequenceArray[index].getBases() == null) {
                    System.out.println("No sequence exists at position " + index);
                    System.out.println();
                }
                // If sequence at pos2 doesn't exist or bases are null
                else if (sequenceArray[pos2] == null || sequenceArray[pos2].getBases() == null) {
                    System.out.println(sequenceArray[index].getBases());
                    System.out.println("No sequence or bases exist at position: " + pos2);
                    System.out.println();
                }
                // Check for start position of swap : notify user if invalid
                else if ((sequenceArray[index].getBases().length() < start1) || (sequenceArray[pos2].getBases().length() < start2)
                || start1 < 0 || start2 < 0) {
                    System.out.println("Start position of swap is not valid");
                    System.out.println();
                }
                // Check for same types
                else if (!(sequenceArray[index].getType().equals(sequenceArray[pos2].getType()))) {
                    System.out.println("Have to be same type to swap.");
                }
                // Error checking complete, call swap
                else {
                    // Create lists we are going to swap
                    swap1 = sequenceArray[index].swap(start1);
                    swap2 = sequenceArray[pos2].swap(start2);
                    System.out.println(swap1.length());
                    // Remove index after start1 swapping positions
                    sequenceArray[index].removeIndex(start1);
                    // Remove pos2 after start2 swapping position
                    sequenceArray[pos2].removeIndex(start2);
                    // Add returned list from pos2 to pos1 (index) at end of list that had end deleter after start2
                    sequenceArray[index].addList(swap2);
                    // Add returned list from pos1 to pos2 at end of list that had end deleted after start1
                    sequenceArray[pos2].addList(swap1);
                }
            }
            // Overlap command
            if (command.equals("overlap")) {
                // Determine longest overlap between two sequences
                // Grab index and pos2
                index = lineReader.nextInt();
                int pos2 = lineReader.nextInt();
                // Check for same type
                if (index < 0 || pos2 < 0 || index > sequenceArray.length || pos2 > sequenceArray.length) {
                    System.out.println("One of your overlap indexes were invalid and do not exist.");
                    System.out.println();
                }
                else if (sequenceArray[index].getBases() == null || sequenceArray[pos2].getBases() == null) {
                    System.out.println("One of your sequences doesn't contain any bases.");
                }
                else if (!(sequenceArray[index].getType().equals(sequenceArray[pos2].getType()))) {
                    System.out.println("Sequences must be of the same type for overlap method.");
                    System.out.println();
                }
                else {
                    sequenceArray[index].overlap(sequenceArray, pos2);
                }
            }
            // Transcribe command
            if (command.equals("transcribe")) {
                // Grab index
                index = lineReader.nextInt();
                // Check for valid index
                if (index < 0) {
                    System.out.println("Pos 1 must be greater than 0.");
                }
                // Check for valid index
                else if (index > sequenceArray.length) {
                    System.out.println("Pos 1 must be less than sequence array length.");
                }
                else if (sequenceArray[index] == null) {
                    System.out.println("Sequence does not exist at sequence: " + index);
                }
                else if (sequenceArray[index].getBases() == null) {
                    System.out.println("No bases exist at sequence: " + index);
                }
                // Check for valid type
                else if (!(sequenceArray[index].getType().equals("DNA"))) {
                    System.out.println("Type must be DNA to transcribe.");
                }
                // Valid, continue
                else {
                    sequenceArray[index].transcribe();
                }
                // Convert DNA to RNA
                // 1. Change its type field to RNA
                // 2. Convert any occurrences of T to U
                // 3. Complement all letters in the sequence
                // - A and U, C and G
                // 4. Reverse the sequence

                // transcribe 3
                // 3 DNA ATGGACCAT
                // 3 RNA AUGGUCCAU

                // If RNA type : Report : "You can only transcribe DNA sequences"
            }
            // Translate command
            if (command.equals("translate")) {
                // Grab index
                index = lineReader.nextInt();
                // Make sure sequence is within sequence array
                if (index < 0 || index > sequenceArray.length) {
                    System.out.println("Sequence must exist within Sequence Array");
                }
                // Make sure sequence index has bases
                else if (sequenceArray[index] == null) {
                    System.out.println("There must be bases at sequence to translate.");
                }
                else if (!(sequenceArray[index].getType().equals("RNA"))) {
                    System.out.println("Sequence must be RNA to translate.");
                }
                else if (sequenceArray[index].getBases() == null) {
                    System.out.println("There are no bases at sequence: " + index);
                }
                else {
                    sequenceArray[index].translate();
                }
                // Converts RNA sequence to amino acid sequence at pos1
                // RNA to AA
                // Translate 3 letter RNA bases to codons (single letter)

                // Only if start codon (AUG) present and if stop codon is present
                // - (UAA) (UAG) (UGA)

                // if (contains("AUG") && ((UAA) || (UAG) || (UGA))
                // else Cannot be translated
            }
        }
    }

    /**
     * Checks for valid base and type
     * @param type the type of string
     * @param bases the string of bases we are checking
     * @return whether the base and type are accurate pairings
     */
    public static boolean baseAndTypeCheck(String type, String bases) {
        // DNA has U : Notify user
        if ((type.equals("DNA")) && ((bases.contains("U")))) {
            int incorrectIndex = bases.indexOf("U");
            System.out.println("Given bases were not correct for given type (DNA) to insert at position: " + incorrectIndex);
            System.out.println();
            return false;
        }
        //  RNA has T : Notify user
        else if ((type.equals("RNA")) && (bases.contains("T"))) {
            int incorrectIndex = bases.indexOf("T");
            System.out.println("Given bases were not correct for given type (RNA) to insert at position: " + incorrectIndex);
            System.out.println();
            return false;
        }
        return true;
    }

    /**
     * Checks for words in string
     * @param sentence the line we are passing
     * @return amount of words in line
     */
    public static int countWordsUsingStringTokenizer (String sentence) {
        // Is sentence null or empty, return 0
        if (sentence == null || sentence.isEmpty()) {
            return 0;
        }
        // Creates and returns token length (Amount of words in line)
        StringTokenizer tokens = new StringTokenizer(sentence);
        return tokens.countTokens();
    }
}
