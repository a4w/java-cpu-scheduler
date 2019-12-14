import javax.swing.*;
import java.awt.*;

class GUIScheduler extends JFrame{
    JFrame frame;
    JPanel chart;
    JPanel summary;

    JPanel[] segments;
    Process[] processes;
    
    GUIScheduler(Process[] processes){
        frame = new JFrame();
        chart = new JPanel();
        summary = new JPanel();
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.processes = processes;
        segments = new JPanel[processes.length];
        for(int i = 0; i < processes.length; ++i){
            chart.add(new JLabel(processes[i].getName()));
            JPanel panel = new JPanel();
            segments[i] = panel;
            panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
            chart.add(panel);
        }
        frame.add(chart);
        frame.pack();
        frame.setVisible(true);

        
    }
    void switchExecution(ExecutionSegment segment){
        for(int i = 0; i < segments.length; ++i){
            if(this.processes[i] == segment.process){
                // Color
                JPanel colored = new JPanel();
                colored.setBackground(Color.BLACK);
                segments[i].add(colored);
            }else{
                // Transparent
                JPanel trans = new JPanel();
                segments[i].add(trans);
            }
        }
    }
}