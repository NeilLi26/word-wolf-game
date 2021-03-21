package ui.gui.sound;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;

/*
simple midi sound player
 */
public class MidiSoundPlayer {
    private static final int firstNoteTime = 250;
    private static final int secondNoteTime = 600;
    private static final int lowerNote = 57;
    private static final int higherNote = 62;
    private static final int instrument = 12;

    MidiChannel[] channels;
    MidiChannel playingChannel;

    //EFFECTS: constructs a new MidiSoundPlayer
    public MidiSoundPlayer() {
        initializeChannels();
    }

    //MODIFIES: this
    //EFFECTS: initializes the channels, and sets an instrument
    private void initializeChannels() {
        try {
            Synthesizer midiSynth = MidiSystem.getSynthesizer();
            midiSynth.open();

            Instrument[] instr = midiSynth.getDefaultSoundbank().getInstruments();
            channels = midiSynth.getChannels();
            midiSynth.loadInstrument(instr[0]);

            playingChannel = channels[0];
            playingChannel.programChange(instrument);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //EFFECTS: plays a sequence of ascending notes
    public void playSoundAscending() {
        playingChannel.noteOn(lowerNote, 100);//On channel 0, play note number 60 with velocity 100

        try {
            Thread.sleep(firstNoteTime); // wait time in milliseconds to control duration
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        playingChannel.noteOff(lowerNote);//turn of the note


        playingChannel.noteOn(higherNote, 100);

        try {
            Thread.sleep(secondNoteTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        playingChannel.noteOff(higherNote);
    }


    //EFFECTS: plays a sequence of descending notes
    public void playSoundDescending() {
        playingChannel.noteOn(higherNote, 100);

        try {
            Thread.sleep(firstNoteTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        playingChannel.noteOff(higherNote);
        playingChannel.noteOn(lowerNote, 100);

        try {
            Thread.sleep(secondNoteTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        playingChannel.noteOff(lowerNote);
    }

}
