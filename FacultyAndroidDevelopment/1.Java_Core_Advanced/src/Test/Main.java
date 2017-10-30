package Test;

import javax.sound.midi.*;
import java.util.Scanner;

/**
 * Test
 * Created by Михаил Силантьев on 19.10.2017.
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int note=1;
        int[] array={44,42,44,42,45,44,43,46,46,46,47,48,42,42,42}; //3131/432/ 55567/111
        Thread t1=new Thread(new Runnable() {
            @Override
            public void run() {
                play(1,note,array);
            }
        });
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        while (true){
//            String text=sc.nextLine();
//            try {
//                note=Integer.parseInt(text);
//            } catch (NumberFormatException e) {
//                e.printStackTrace();
//            }
//            play(1,note,[{44,42,44,42}]);
//        }
    }

    public static synchronized void play(int instrument,int note,int[] array) {
        try {

            for (int i = 0; i < array.length; i++) {
                Sequencer player= MidiSystem.getSequencer();
                player.open();
                Sequence seq=new Sequence(Sequence.PPQ,4);
                Track track=seq.createTrack();

                MidiEvent event=null;
//                ShortMessage first=new ShortMessage();
//                first.setMessage(192,1,instrument,0);
//                MidiEvent changeInsrument=new MidiEvent(first,1);
//                track.add(changeInsrument);

                ShortMessage a=new ShortMessage();
                a.setMessage(144,1,array[i],100);
                MidiEvent noteOn=new MidiEvent(a,10);
                track.add(noteOn);

//                ShortMessage b=new ShortMessage();
//                b.setMessage(128,1,array[i],100);
//                MidiEvent noteOff=new MidiEvent(b,5);
//                track.add(noteOff);

                player.setSequence(seq);
                player.start();


                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

