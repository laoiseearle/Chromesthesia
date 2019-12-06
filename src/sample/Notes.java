package sample;

import java.util.Random;

public class Notes {

    private String currentNote;
    private String previousNote = "";
    private String keyPressed;
    private int notePositionIndex;
    private boolean isTrebleClef = true;
    private boolean flatsShown = false;

    private String[] notesTreble = {
            "C3", "Db3", "D3", "Eb3", "E3", "F3", "Gb3", "G3",
            "Ab3", "A3", "Bb3", "B3", "C4", "Db4", "D4", "Eb4",
            "E4", "F4", "Gb4", "G4", "Ab4", "A4" };
    private String[] notesBass = {
            "Eb1", "E1", "F1", "Gb1", "G1", "Ab1", "A1", "Bb1",
            "B1", "C2", "Db2", "D2", "Eb2", "E2", "F2", "Gb2",
            "G2", "Ab2", "A2", "Bb2", "B2", "C2" };
    private int[] notesPos = {
            142, 133, 133, 124, 124, 115, 106, 106,
            97, 97, 88, 88, 126, 116, 116, 107,
            107, 97, 88, 88, 80, 80 };
    public void setFlatsShown(boolean flatsShown){
        this.flatsShown = flatsShown;
    }
    public boolean isFlatsShown() {
        return flatsShown;
    }
    public void setTrebleClef(boolean isTrebleClef) {
        this.isTrebleClef = isTrebleClef;
    }
    public boolean isTrebleClef() {
        return isTrebleClef;
    }
    public void setKeyPressed(String key){
        this.keyPressed = key;
    }
    public String getKeyPressed(){
        return keyPressed;
    }
    public String getCurrentNote(){
        return currentNote;
    }
    public int getNoteIndexFromArray() {
        return notePositionIndex;
    }
    public int getNotePosition(){
        if(!isTrebleClef){
            /* Notes with flats have the same position as their previous note ie E and Eb, while notes F and C don't contain flats.
               Since notesBass uses a different sequence of notes, notes E, A & B need to be decremented by 1 to keep the same position as notesTreble */
            if(notePositionIndex == 1 || notePositionIndex == 6 || notePositionIndex == 8 || notePositionIndex == 13 || notePositionIndex == 18 || notePositionIndex == 20){
                return notesPos[notePositionIndex - 1];
            }
        }
        return notesPos[notePositionIndex];
    }
    public boolean isCorrectGuess(){
        return currentNote.substring(0, currentNote.length() - 1).equals(keyPressed);
    }
    public String noteColour(){
        switch (currentNote.substring(0, 1)){
            case "C":{ return "Red"; }
            case "D":{ return "Orange"; }
            case "E":{ return "Yellow"; }
            case "F":{ return "Green"; }
            case "G":{ return "LBlue"; }
            case "A":{ return "DBlue"; }
            case "B":{ return "Purple"; }
        }
        return null;
    }
    public void generateNote(){
        if (isTrebleClef){
            currentNote = randomNote(notesTreble);
        } else{
            currentNote = randomNote(notesBass);
        }
        if((!flatsShown && currentNote.substring(1, 2).equals("b")) || currentNote.equals(previousNote)){
            generateNote();
        } else{
            previousNote = currentNote;
        }
    }
    private String randomNote(String[] array){
        Random random = new Random();
        int randomIndex = random.nextInt(array.length);
        notePositionIndex = randomIndex;
        return array[randomIndex];
    }
}
