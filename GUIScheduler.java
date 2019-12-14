import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.HashMap;
import java.awt.Color;

class GUIScheduler extends JFrame{
    private static final long serialVersionUID = 1L;

    private JPanel chart;

    HashMap<Process, JPanel> processView;

    GUIScheduler(){
        // Main frame
        super("Java CPU scheduler simulator");
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        // Gantt chart
        this.chart = new JPanel();
        this.chart.setLayout(new BoxLayout(this.chart, BoxLayout.PAGE_AXIS));
        this.add(chart);

        this.pack();
        this.setVisible(true);
    }

    /*
     * Access process by index, therefore, we need to access JPanel of segments by index && processes by index
     */
    void setProcesses(Process[] _processes){
        this.processView = new HashMap<>();
        this.chart.removeAll();
        this.chart.repaint();
        for(int i = 0; i < _processes.length; ++i){
            JPanel row = new JPanel();
            row.setLayout(new BoxLayout(row, BoxLayout.LINE_AXIS));
            JLabel processLabel = new JLabel(_processes[i].getName());
            row.add(processLabel);
            this.processView.put(_processes[i], row); // To access later
            this.chart.add(row);
        }
    }

    void switchExecution(ExecutionSegment segment){
        for(Process p : this.processView.keySet()){
            JPanel pholder = new JPanel();
            if(p == segment.process){
                // Add colored segment
                pholder.setBackground(Color.RED);
            }
            this.processView.get(p).add(pholder);
        }
    }
}
