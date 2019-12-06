package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;

public class Controller {

    private Notes notes = new Notes();
    private boolean hasStarted = false;
    private float correctGuessCount = 0f;
    private float incorrectGuessCount = 0f;
    @FXML
    private Label correctGuessCountLbl;
    @FXML
    private Label incorrectGuessCountLbl;
    @FXML
    private Label guessPercentageLbl;
    @FXML
    private Label noteNamesLbl;
    @FXML
    private Button startBtn;
    @FXML
    private Button pianoColoursBtn;
    @FXML
    private Button clefBtn;
    @FXML
    private Button pianoKeyNamesBtn;
    @FXML
    private Button staffNoteNamesBtn;
    @FXML
    private Button flatButton;
    @FXML
    private ImageView noteImage;
    @FXML
    private ImageView pianoColoursImage;
    @FXML
    private ImageView pianoKeyNamesImage;
    @FXML
    private ImageView trebleClefImage;
    @FXML
    private ImageView bassClefImage;
    @FXML
    private ImageView flatImage;
    @FXML
    private ImageView ledgerLineImage;
    @FXML
    private Image noteColourImage;

    @FXML
    private void pianoKeyPressed(ActionEvent event){
        final Node source = (Node)event.getSource();
        String id = source.getId();
        notes.setKeyPressed(id);
        musicPlayer();
    }
    private void musicPlayer(){
        AudioClip clip = new AudioClip(getClass().getResource("/resources/audio/" + notes.getKeyPressed() + ".wav").toString());
        clip.play();
        if(hasStarted){
            newGuess();
        }
    }
    private void newGuess(){
        if (notes.isCorrectGuess()) {
            correctGuessCount++;
            updateGuessCount();
            newNote();
        } else{
            incorrectGuessCount++;
            updateGuessCount();
        }
    }
    private void newNote(){
        notes.generateNote();
        changeNotePosition();
        changeNoteColour();
        changeStaffNoteNames();
        changeLedgerLine();
        changeFlat();
    }
    private void changeNotePosition(){
        // rotate stem of note
        if (notes.getNoteIndexFromArray() <= 11){
            noteImage.setRotate(180);
        } else{
            noteImage.setRotate(0);
        }
        noteImage.setLayoutY(notes.getNotePosition());
    }
    private void changeStaffNoteNames(){
        if(noteNamesLbl.isVisible()){
            noteNamesLbl.setText(notes.getCurrentNote().substring(0, notes.getCurrentNote().length() - 1));
        }
    }
    private void changeFlat(){
        if (notes.isFlatsShown() && notes.getCurrentNote().substring(1, 2).equals("b")){
            changeFlatPosition();
            flatImage.setVisible(true);
        } else{
            flatImage.setVisible(false);
        }
    }
    private void changeLedgerLine(){
        if(notes.getNoteIndexFromArray() == 0 || (!notes.isTrebleClef() && notes.getNoteIndexFromArray() == 1)) {
            ledgerLineImage.setVisible(true);
            ledgerLineImage.setLayoutY(190);
        } else if (notes.getNoteIndexFromArray() == 21 || (notes.isTrebleClef() && notes.getNoteIndexFromArray() == 20)) {
            ledgerLineImage.setVisible(true);
            ledgerLineImage.setLayoutY(80);
        } else{
            ledgerLineImage.setVisible(false);
        }
    }
    private void changeFlatPosition(){
        if(notes.getNoteIndexFromArray() <= 11){
            flatImage.setLayoutY(notes.getNotePosition() + 16);
        } else{
            flatImage.setLayoutY(notes.getNotePosition() - 32);
        }
    }
    private void changeNoteColour(){
        String setNoteColour = "Black";
        if (pianoColoursImage.isVisible()){
            setNoteColour = notes.noteColour();
        }
        noteColourImage = new Image(getClass().getResourceAsStream("/resources/images/note" + setNoteColour + ".png"));
        noteImage.setImage(noteColourImage);
    }
    private void startGame(){
        newNote();
        startBtn.setText("STOP");
        noteImage.setVisible(true);
    }
    private void stopGame(){
        startBtn.setText("START");
        noteNamesLbl.setText("Press the Start Button!");
        noteImage.setVisible(false);
        ledgerLineImage.setVisible(false);
        flatImage.setVisible(false);
        resetGuessCount();
    }
    private void updateGuessCount(){
        correctGuessCountLbl.setText(Math.round(correctGuessCount) + "");
        incorrectGuessCountLbl.setText(Math.round(incorrectGuessCount) + "");
        guessPercentageLbl.setText(Math.round(correctGuessCount * 100 / (correctGuessCount + incorrectGuessCount)) + "%");
    }
    private void resetGuessCount(){
        correctGuessCount = 0;
        incorrectGuessCount = 0f;
        updateGuessCount();
    }
    @FXML
    private void toggleStartStop(){
        hasStarted = !hasStarted;
        if (hasStarted){
            startGame();
        } else{
            stopGame();
        }
    }
    @FXML
    private void toggleStaffNoteNames(){
        noteNamesLbl.setVisible(!noteNamesLbl.isVisible());
        if(noteNamesLbl.isVisible()){
            staffNoteNamesBtn.setText("Staff Names: ON");
            if(hasStarted){
                changeStaffNoteNames();
            }
        } else{
            staffNoteNamesBtn.setText("Staff Names: OFF");
        }
    }
    @FXML
    private void toggleClef() {
        notes.setTrebleClef(!notes.isTrebleClef());
        if (notes.isTrebleClef()){
            trebleClefImage.setVisible(true);
            bassClefImage.setVisible(false);
            clefBtn.setText("Clef: TREBLE");
        } else{
            trebleClefImage.setVisible(false);
            bassClefImage.setVisible(true);
            clefBtn.setText("Clef: BASS");
        }
        if(hasStarted){
            newNote();
        }
    }
    @FXML
    private void toggleColours(){
        pianoColoursImage.setVisible(!pianoColoursImage.isVisible());
        if (pianoColoursImage.isVisible()){
            pianoColoursBtn.setText("Colours: ON");
        } else{
            pianoColoursBtn.setText("Colours: OFF");
        }
        if(hasStarted) {
            changeNoteColour();
        }
    }
    @FXML
    private void togglePianoKeyNames() {
        pianoKeyNamesImage.setVisible(!pianoKeyNamesImage.isVisible());
        if(pianoKeyNamesImage.isVisible()){
            pianoKeyNamesBtn.setText("Piano Names: ON");
        } else{
            pianoKeyNamesBtn.setText("Piano Names: OFF");
        }
    }
    @FXML
    private void toggleFlats(){
        notes.setFlatsShown(!notes.isFlatsShown());
        if(notes.isFlatsShown()){
            flatButton.setText("Flat Notes: ON");
        } else{
            flatButton.setText("Flat Notes: OFF");
            if(hasStarted && notes.getCurrentNote().substring(1, 2).equals("b")){
                newNote();
            }
        }
    }
}