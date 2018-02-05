/*
 *
 * Rob Ranallo
 * Aaron Liezert
 * Gaston C. Marian
 * CSC 370, First Project
 * Due 29 January 2018
 * Making a program to simulate operating system processes as specified in the 
 * following link: http://raider.mountunion.edu/csc/CSC370/Spring2018/projects/project01/index.html
 *
 * This class handles GUI as well as calling all the other classes. MainFrame is good for leading
 *
 * Personal Work Log:
 * Date           Hours     Goals                   Progress                                                Comments
 * 1/20/2018      1         Base line, classes      GUI set up, basic process class, buttons                What is a thread??
 *                          and methods             are clickable, if not running a thread properly
 * 
 * 1/27/2018      3         The rest of             GUI reads in data, sorts into an ArrayList of           I didn't finish, actually. Goal missed. Also, I had a lot of issues 
 *                          the project             Processes, clock ticks and runs properly                because I never started my thread. Debugging is a valuable tool
 *
 * 1/28/2018      1.5       Actually finish         Lots of try/catch blocks to deal with errors in         The Simulator is doing a lot of work,
 *                                                  input and such. Data is passed around and used          but I feel like it could be doing more. 
 *                                                  to calculate what goes where as the time ticks.         Currently a lot handled by MainFrame
 *                                                                                                          Cleaned up the statusString method and I'm proud of how much nicer it is                                   
 */
package firstproject;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Gaston Marain
 */
public class MainFrame extends javax.swing.JFrame {
    private ArrayList<Process> processes, newList, readyList;
    private static ClockStarter CS;
    private boolean clicked = false;
    private boolean clocked = false;
    private Thread thread;
    private int clockLimit;
    private Simulator sim;
    private String er;
    
    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        MyChangeListener mcl = new MyChangeListener();
        speedSlider.addChangeListener(mcl);
        processes = new ArrayList<>();
        newList = new ArrayList<>();
        readyList = new ArrayList<>();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        playPauseButton = new javax.swing.JButton();
        clockLabel = new javax.swing.JLabel();
        readDataButton = new javax.swing.JButton();
        oneTickButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        inputArea = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        outputArea = new javax.swing.JTextArea();
        speedSlider = new javax.swing.JSlider();
        statusButton = new javax.swing.JButton();
        speedLabel = new javax.swing.JLabel();
        processesLabel = new javax.swing.JLabel();
        endedLabel = new javax.swing.JLabel();
        readyLabel = new javax.swing.JLabel();
        runningLabel = new javax.swing.JLabel();
        waitingLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        playPauseButton.setText("Run/Pause");
        playPauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playPauseButtonActionPerformed(evt);
            }
        });
        getContentPane().add(playPauseButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, -1, -1));

        clockLabel.setText("-1");
        getContentPane().add(clockLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 20, -1, -1));

        readDataButton.setText("Read Data");
        readDataButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                readDataButtonActionPerformed(evt);
            }
        });
        getContentPane().add(readDataButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        oneTickButton.setText("One Tick");
        oneTickButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                oneTickButtonActionPerformed(evt);
            }
        });
        getContentPane().add(oneTickButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(383, 50, 80, -1));

        inputArea.setColumns(20);
        inputArea.setRows(5);
        jScrollPane1.setViewportView(inputArea);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 300, 200));

        outputArea.setColumns(20);
        outputArea.setRows(5);
        jScrollPane2.setViewportView(outputArea);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 140, 310, 200));

        speedSlider.setMaximum(200);
        speedSlider.setMinimum(10);
        getContentPane().add(speedSlider, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 50, 101, 20));

        statusButton.setText("Status");
        statusButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusButtonActionPerformed(evt);
            }
        });
        getContentPane().add(statusButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 110, -1, -1));

        speedLabel.setText("Speed");
        getContentPane().add(speedLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 70, -1, -1));

        processesLabel.setText("Processes");
        getContentPane().add(processesLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 400, -1, -1));

        endedLabel.setText("Ended");
        getContentPane().add(endedLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 400, -1, -1));

        readyLabel.setText("Ready");
        getContentPane().add(readyLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 500, -1, -1));

        runningLabel.setText("Running");
        getContentPane().add(runningLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 500, -1, -1));

        waitingLabel.setText("Waiting");
        getContentPane().add(waitingLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 560, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void readDataButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_readDataButtonActionPerformed
        clicked = false;
        try{
            sim.clearSim();
        }catch(Exception ex){}
        try{
            String inString = inputArea.getText();
            String[] inArray = inString.split("\n");
            if (!(inArray.length%3 == 1)){                  // Validation that input is right number of lines
                outputArea.setText(" \nERROR:\nInput data is an invaid format."
                        +"\nProper format requires a first line time quantum,"
                        +"\nfollowed by groups of three lines representing "
                        +"\ncreation time, name, and trace tape for the process.");
                return;
            }
            try{                                            // Validation that input starts first line with time quantum
                clockLimit = Integer.parseInt(inArray[0]);
            } catch (Exception ex){
                outputArea.setText("\nERROR:\nInput data validation failed.\nMake sure the input format is correct."
                        +"\nFirst line is a number, the time quantum");
                return;
            }
            try{                                         // Validation that input is made of processes that have start time
                processProcesses(inArray);
            } catch (Exception ex){
                outputArea.setText("ERROR:\nInput data validation failed.\nMake sure the input format is correct."
                        +"\n3 lines - \n\ta number for time, \n\ta string name, \n\tand a tape\n"+er);
                return;
            }
            outputArea.setText("\n Data Read Successfully");
            sim = new Simulator(clockLimit, processes);
        }
        catch(Exception ex){outputArea.setText("\n Enter Data in Left Text Area");}
    }//GEN-LAST:event_readDataButtonActionPerformed

    private void playPauseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playPauseButtonActionPerformed
        if (!clicked){
            CS = new ClockStarter(clockLabel);
            thread = new Thread(CS);
            thread.start();
            clicked = !clicked;
        }
        CS.switchRun();
    }//GEN-LAST:event_playPauseButtonActionPerformed

    private void oneTickButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_oneTickButtonActionPerformed
        if (!clicked){
            CS = new ClockStarter(clockLabel);
            thread = new Thread(CS);
            thread.start();
            clicked = !clicked;
        }
        CS.incrementTime();
        try{
            sim.setClockTime(CS.getCurrentTime());
        }catch(Exception ex){
            
        }
        
    }//GEN-LAST:event_oneTickButtonActionPerformed

    private void statusButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusButtonActionPerformed
        try{
            sim.getProcessList();
            if (!clocked){
                outputArea.setText("OUTPUT\n\n");
                clocked = !clocked;
            }
            try{
                sim.setClockTime(CS.getCurrentTime());
            }
            catch(Exception ex){
                sim.setClockTime(-1);
            }
            outputArea.append(statusString());
            outputArea.setCaretPosition(outputArea.getText().length());}
        catch(Exception ex){
            outputArea.setText("\n You need to enter valid data\nand click to read it in");
        }
    }//GEN-LAST:event_statusButtonActionPerformed

    // Method to create the output for the current time and return a string to be 
    // appended when the status button is clicked
    private String statusString(){       
        String retVal = "";
        String clockT = "";
        String timeQ = "timeQuantum --    "+ clockLimit+ "\n";
        String processLS = "processList.size() --    "+processes.size() + "\n";
        String processL = "";
        String newLS = "";
        String newL = "";
        String readyLS = "";
        String readyL = "";
        
        try{                                                        // Sets the clock time in case ClockStarter hasn't
            clockT = "clock --    "+ CS.getCurrentTime()+ "\n";
        } catch(Exception ex){
            clockT = "clock --   -1\n";
        }
        
        for (int i = 0; i < processes.size(); i++){                 // sets up processL
                processL = processL + "  process " + i + " ----    " 
                        + processes.get(i).toString() + "\n";
        }
        
        newList = sim.getNewList();
        newLS = newLS + "newList.size() -- " + newList.size()+ "\n";
        
        if (newList.size() > 0){                                        // sets up newL
            for (int i = 0; i < newList.size(); i++){
                newL = newL + " " + i + " ----    " 
                        + newList.get(i).toString() + "\n";
            }
        }
        
        readyList = sim.getReadyList();
        readyLS = readyLS + "readyList.size() -- " + readyList.size()+ "\n";
        
        if (readyList.size() > 0){                                                     // sets up readyL
            for (int i = 0; i < readyList.size(); i++){
                readyL = readyL + " " + i + " ----    " + readyList.get(i).toString() + "\n";
            }
        }
        
        retVal = clockT + timeQ + processLS + processL + newLS + newL + readyLS + readyL + "\n";
        return retVal;
        
    }
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Method takes in the text entered in the input field, uses them to make process objects,
    // then adds each process to the processes ArrayList. Errors are thrown if something can't
    // be made into a process (likely, the processes enterance time is not an Integer value)
    // and passes the problem input back to be output for the user to see
    private void processProcesses(String[] inArray) {
        for (int i = 1; i < inArray.length; i += 3){
            try{
                Process temp = new Process(Integer.parseInt(inArray[i]), inArray[i+1], inArray[i+2]);
                processes.add(temp);
            }catch (Exception ex){
                er = "Check here: \nLine " + i + " - " +inArray[i]
                        + "\nLine " + (i+1) + " - " +inArray[i+1]
                        + "\nLine " + (i+2) + " - " +inArray[i+2];
                throw ex;
            }
        }
    }
    
    private class MyChangeListener implements ChangeListener{
            @Override
            public void stateChanged(ChangeEvent ce) {
                CS.setSleepTime(speedSlider.getValue());    // Current method can require up to 5 seconds of wait time after moving slider
//                try {                                     // This can be remedied with a thread.interrupt(), but then that adds 1 to the
//                    thread.sleep(200);                    // tick clock for every unit the slider is slid, calling changelistener constantly
//                } catch (InterruptedException ex) { }     // trying to use another thread sleeping to ignore input didn't work
//                thread.interrupt();
                
            }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JLabel clockLabel;
    private javax.swing.JLabel endedLabel;
    private javax.swing.JTextArea inputArea;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton oneTickButton;
    private javax.swing.JTextArea outputArea;
    private javax.swing.JButton playPauseButton;
    private javax.swing.JLabel processesLabel;
    private javax.swing.JButton readDataButton;
    private javax.swing.JLabel readyLabel;
    private javax.swing.JLabel runningLabel;
    private javax.swing.JLabel speedLabel;
    private javax.swing.JSlider speedSlider;
    private javax.swing.JButton statusButton;
    private javax.swing.JLabel waitingLabel;
    // End of variables declaration//GEN-END:variables
}
