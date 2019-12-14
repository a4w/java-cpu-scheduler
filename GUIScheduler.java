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
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        this.pack();
    }

    void switchExecution(ExecutionSegment segment){
        for(Process p : this.processView.keySet()){
            JPanel pholder = new JPanel();
            if(segment.process == null){
                // Context switch
                pholder.setBackground(Color.BLACK);
            }else if(p == segment.process){
                // Add colored segment
                pholder.setBackground(p.getColor());
            }
            this.processView.get(p).add(pholder);
        }
    }
}
