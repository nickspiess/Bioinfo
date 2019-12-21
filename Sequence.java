import java.util.*;
import java.lang.*;

public class Sequence extends SLList{

    /**
     * File name: Sequence.java
     * @author SPIESSNA
     * description: This program initiates sequences of type and bases for DNA and will apply
     * necessary methods to alter the data as requested from the Bioinfo class
     * Version probably 150
     * @since 10/15/19
     */

    private String type;
    private SLList bases;
    private int index;

    /**Constructor for bases
     * @param type - the type for the list
     * @param b - the Single Linked List of bases
     * @param index - the index where it is located
     */
    public Sequence(String type, String b, int index) {
        this.type = type;
        this.index = index;
        bases = new SLList<String>();
        for (int i = 0; i <= b.length() - 1; i++) {
            bases.add(b.substring(i, i+1));
        }
    }

    /**Constructor for sequence without bases
     * @param type - the type for the list
     * @param index - the index the list will be at
     */
    public Sequence(String type, int index) {
        this.type = type;
        bases = null;
        this.index = index;
    }


    /** Get your type
     * @return type - the type of the sequence
     */
    public String getType() {
        return type;
    }


    /** Method for getting bases
     * @return baseString - the bases of the sequence
     */
    public String getBases() {
        StringBuilder baseString = new StringBuilder();
        if (bases == null) {
            return null;
        }
        else {
            for (int i = 0; i <= bases.length() - 1; i++) {
                baseString.append(bases.getValue(i));
            }
        }
        return baseString.toString();
    }


    /** Method for adding SLList to
     * @return bases - the SLList of bases
     */
    public void addList(SLList basesToInsert) {
        for (int i = 0; i<=basesToInsert.length() - 1; i++) {
            bases.add(basesToInsert.getValue(i));
        }
    }

    public void removeIndex(int indexToRemoveAt) {
        for (int i = indexToRemoveAt; i<= bases.length()-1; i+=0) {
            bases.remove(i);
        }
    }

    /** Method for splicing a list
     * @param baseStringToAdd - The base string we are adding
     * @param locationToAdd - the index we are adding at
     * @param typeToAdd - The type we are adding
     */
    public void splice(String baseStringToAdd, int locationToAdd, String typeToAdd) {
        System.out.println("Location to add " + locationToAdd);
        if ((type.equals("DNA") && baseStringToAdd.contains("U")) || (type.equals("RNA") && baseStringToAdd.contains("T"))) {
            System.out.println("The sequence you want to splice in is not made of valid bases.");
        }
        // If splice type is different then existing type
        else if (!(type.equals(typeToAdd))) {
            System.out.println("Cannot splice if the types do not agree.");
            System.out.println();
        }
        // If no sequence at position
        else if (type == null) {
            System.out.println("There is no sequence at position " + locationToAdd);
            System.out.println();
        }
        // Greater than or equal to SLList length
        else if (bases.length() < locationToAdd || locationToAdd < 0) {
            System.out.println("Invalid position to insert at position: " + (locationToAdd+1));
            System.out.println();
        }
        // Valid, continue with splice and insert using SLList method
        else {
            // New List
            SLList<String> addedList = new SLList<>();
            // Adding values from base string to new list
            for (int i = 0; i <= baseStringToAdd.length() - 1; i++) {
                addedList.add(baseStringToAdd.substring(i, i+1));
            }
            // Inserting new list into existing bases
            bases.insertList(addedList, locationToAdd);
        }
    }

    /** Method for clipping middle portion of a list
     * @param indexStart - the start index of the clip
     * @param indexEnd - the end index of the clip
     */
    public void clip(int indexStart, int indexEnd) {
        // If there are no bases there
        if (bases == null) {
            System.out.println("There are no bases to clip");
            System.out.println();
            return;
        }
        // Error for start index less than zero
        if (indexStart < 0) {
            System.out.println("Index cannot be less than 0.");
            System.out.println();
            return;
        }
        // Error for either sequence being greater than length of bases
        else if (indexStart > bases.length() || indexEnd > bases.length()) {
            System.out.println("Indexes cannot be greater than the length of bases.");
            System.out.println();
            return;
        }
        // Error if end is less than start
        else if (indexStart > indexEnd) {
            System.out.println("Start index cannot be greater than end index");
            System.out.println();
            return;
        }
        // Valid, continue with clip
        else {
            SLList<String> newClip = new SLList<String>();
            // Grab bases
            int indexCount = 0;
            for (int i = indexStart; i <= indexEnd; i++) {
                newClip.insert(indexCount, bases.getValue(i).toString());
                indexCount++;
            }
            bases = newClip;
        }
    }

    /** Method for clipping the remainder of the list
     * @param start - the position we are starting the clip
     */
    public void clip(int start) {
        // If there are no bases there
        if (bases == null) {
            System.out.println("There are no bases to clip");
            System.out.println();
            return;
        }
        SLList<String> newClip = new SLList<>();
        // Error for start index less than zero
        if (start < 0) {
            System.out.println("Index cannot be less than 0.");
            System.out.println();
            return;
        }
        // Make sure index isn't greater than sequence length
        else if (start > bases.length()) {
            System.out.println("Index is larger than length of bases");
            System.out.println();
            return;
        }
        // Valid, continue with clip
        else {
            //SLList<String> newClip = new SLList<>();
            // Iterate through list, adding to new list and replacing
            for (int i = start; i <= bases.length() - 1; i++) {
                newClip.add(bases.getValue(i).toString());
            }
            bases = newClip;
        }
    }

    /** Method for swapping tails of lists
     * @param start - the position we are starting the swap at
     * @return newClip - the SLList we will insert into new list
     */
    public SLList swap(int start) {
        // Create swap list we will return
        SLList<String> swap = new SLList<>();
        // Iterate through list, adding to new list
        for (int i = start; i <= bases.length() - 1; i++) {
            swap.add(bases.getValue(i).toString());
        }
        System.out.println(swap);
        // Return the list we will eventually insert
        return swap;
    }

    /** Method for swapping tails of lists
     * SHOUTS TO WIKIBOOKS FOR HELP ON THIS METHOD
     * @param sequence - the sequence we are passing though
     * @param pos2 - the position in the sequence we are checking
     */
    public void overlap(Sequence[] sequence, int pos2) {
        String base1 = getBases();
        String base2 = sequence[pos2].getBases();
        int beg = 0;
        int most = 0;
        for (int i = 0; i < base1.length(); i++) {
            for (int j = 0; j < base2.length(); j++) {
                int x = 0;
                while (base1.charAt(i + x) == base2.charAt(j+x))
                {
                    x++;
                    if (((i+x) >= base1.length()) || ((j+x) >= base2.length())) {
                        break;
                    }
                }
                if (x > most)
                {
                    most = x;
                    beg = i;
                }
            }
        }
        if (beg > 0 && most > 0 && (most-beg > 1)) {
            System.out.println("Overlap starts at index " + beg + " bases that overlap " + base1.substring(beg, (beg+most)));
            System.out.println();
        }
        else {
            System.out.println("There is no overlap in these sequences.");
            System.out.println();
        }
    }


    /** Method for transcribing DNA sequences
     * Changes all values to need values and reverses the string, changing type as well
     */
    public void transcribe() {
        type = "RNA";
        // Translate T's into U's
        for (int i = 0; i <= bases.length() - 1; i++) {
            // Translate T's to U's
            if (bases.getValue(i).toString().equals("T")) {
                bases.remove(i);
                bases.insert(i, "U");
            }
            // Translate U's to A's
            if (bases.getValue(i).toString().equals("U")) {
                bases.remove(i);
                bases.insert(i, "A");
            }
            // Translate U's to A's
            else if (bases.getValue(i).toString().equals("A")) {
                bases.remove(i);
                bases.insert(i, "U");
            }
            // Translate C's to G's
            else if (bases.getValue(i).toString().equals("C")) {
                bases.remove(i);
                bases.insert(i, "G");
            }
            // Translate G's to C's
            else if (bases.getValue(i).toString().equals("G")) {
                bases.remove(i);
                bases.insert(i, "C");
            }
        }
        bases.reverse();
    }


    /**
     * Method to check if a list of bases are able to be translated
     * Will iterate through each letter, checking for start codon, regular codons and a stop codon
     * Will change string to AA sequence and type to AA if bases apply
     */
    public void translate() {
        // Codon list
        SLList<String> codons = new SLList<>();
        String letterCheck = "";
        // Our counter to traverse the bases
        int i = 0;

        // While we are able to iterate through list with three codons at end still
        while (i + 2 < bases.length() - 1) {
            letterCheck = bases.getValue(i).toString() + bases.getValue(i + 1).toString() + bases.getValue(i + 2).toString();
            // Iterate through until we get a start codon
            while (!letterCheck.equals("AUG")) {
                // Checking if i is greater than length of bases
                i++;
                // Checking if i is greater than length of bases
                if ((i + 2 == (bases.length())) || (i+2 > bases.length())) {
                    break;
                }
                // Update letterCheck
                letterCheck = bases.getValue(i).toString() + bases.getValue(i + 1).toString() + bases.getValue(i + 2).toString();
            }
            // If we get one, we iterate through, trying to get different codons
            if (letterCheck.equals("AUG")) {
                codons.add("M");
                // Iterate by three
                i+=3;
                // While our letterChecker doesn't return a stop codon and our last value isn't null
                while ((!(letterCheck.equals("UAA")) || (!letterCheck.equals("UAG")) || (!letterCheck.equals("UGA")))
                        || (i + 1 == (bases.length())) || (i+1 > bases.length()))
                {
                    // Checking if i is greater than length of bases
                    if ((i + 2 == (bases.length())) || (i+2 > bases.length()) || ((letterCheck.equals("UAA")) || (letterCheck.equals("UAG")) || (letterCheck.equals("UGA")))) {
                        break;
                    }
                    //Update our letter check
                    letterCheck = bases.getValue(i).toString() + bases.getValue(i + 1).toString() + bases.getValue(i + 2).toString();

                    // If codon checker returns null, iterate i by 1
                    if (codonChecker(letterCheck) == null)
                    {
                        i++;
                    }
                    // Else, we assume that our codon checker returns a value and that we are adding that letter to codon
                    // list and iterating by three
                    else
                        {
                        codons.add(codonChecker(letterCheck));
                        i+=3;
                    }
                }
            }
        }
        // If codon list is more than just M, translate information
        if (codons.length() > 1) {
            bases = codons;
            type = "AA";
        }
        // Else, if codon list didn't get anything or codon list is just M, inform user of no open frame
        else if(codons.length() == 0 || codons.length() == 1) {
            System.out.println("There is no open reading frame in this sequence.");
            System.out.println();
        }
    }

    /**
     * Helper method to check if codon is a valid codon
     * @param codon - The codon we are passing
     * @return the letter of the amino acid sequence
     */
    public String codonChecker(String codon) {
        // Check for F Amino Acids
        if (codon.equals("UUU") || codon.equals("UUC")) {
            return "F";
        }
        // Check for L amino acids
        else if (codon.equals("UUA") || codon.equals("UUG") || codon.equals("CUU") || codon.equals("CUC") || codon.equals("CUA")
                || codon.equals("CUG")) {
            return "L";
        }
        // Check for I amino acids
        else if (codon.equals("AUU") || codon.equals("AUC") || codon.equals("AUA")) {
            return "I";
        }
        // Check for M amino acids
        else if (codon.equals("AUG")) {
            return "M";
        }
        // Check for V amino acids
        else if (codon.equals("GUU") || codon.equals("GUC") || codon.equals("GUA") || codon.equals("GUG")) {
            return "V";
        }
        // Check for S amino acids
        else if (codon.equals("UCU") || codon.equals("UCC") || codon.equals("UCA") || codon.equals("UCG")) {
            return "S";
        }
        // Check for P amino acids
        else if (codon.equals("CCU") || codon.equals("CCC") || codon.equals("CCA") || codon.equals("CCG")) {
            return "P";
        }
        // Check for T amino acids
        else if (codon.equals("ACU") || codon.equals("ACC") || codon.equals("ACA") || codon.equals("ACG")) {
            return "T";
        }
        // Check for A amino acids
        else if (codon.equals("GCU") || codon.equals("GCC") || codon.equals("GCA") || codon.equals("GCG")) {
            return "A";
        }
        // Check for H Amino Acids
        else if (codon.equals("CAU") || codon.equals("CAC")) {
            return "H";
        }
        // Check for Q Amino Acids
        else if (codon.equals("CAA") || codon.equals("CAG")) {
            return "Q";
        }
        // Check for N Amino Acids
        else if (codon.equals("AAU") || codon.equals("AAC")) {
            return "N";
        }
        // Check for K Amino Acids
        else if (codon.equals("AAA") || codon.equals("AAG")) {
            return "K";
        }
        // Check for D Amino Acids
        else if (codon.equals("GAU") || codon.equals("GAC")) {
            return "D";
        }
        // Check for E Amino Acids
        else if (codon.equals("GAA") || codon.equals("GAG")) {
            return "E";
        }
        // Check for C Amino Acids
        else if (codon.equals("UGU") || codon.equals("UGC")) {
            return "C";
        }
        // Check for W Amino Acids
        else if (codon.equals("UGG")) {
            return "W";
        }
        // Check for R amino acids
        else if (codon.equals("CGU") || codon.equals("CGC") || codon.equals("CGA") || codon.equals("CGG") || codon.equals("AGA") || codon.equals("AGG")) {
            return "R";
        }
        // Check for S amino acids
        else if (codon.equals("AGU") || codon.equals("AGC")) {
            return "S";
        }
        // Check for G amino acids
        else if (codon.equals("GGU") || codon.equals("GGC") || codon.equals("GGA") || codon.equals("GGG")) {
            return "G";
        }
        else {
            return null;
        }
    }


    /** Method for copying one sequence to another
     * @param sequence - The sequence we are working with
     * @param indexToCopyOver - the Index we want to copy over.
     */
    public void copy(Sequence[] sequence, int indexToCopyOver) {
        // If sequence exists but bases are null
        if (bases == null) {
            sequence[indexToCopyOver] = new Sequence(type, index);
        }
        // else if there is sequence and bases, make independent copy
        else {
            // Create independent copy
            String newBases = "";
            for (int i = 0; i <= bases.length() - 1; i++) {
                newBases = newBases + (bases.getValue(i).toString());

            }

            sequence[indexToCopyOver] = new Sequence(type, newBases, indexToCopyOver);
        }
    }


    /**
     * Method to return the String of the sequence
     * @return the index, type and bases put in string format
     */
    public String toString() {
        if (bases == null) {
            return index + "  " + type;
        }
        else {
            return index + "  " + type + " " + bases.toString().replace("[", "")
                    .replace("]", "");
        }
    }

}
