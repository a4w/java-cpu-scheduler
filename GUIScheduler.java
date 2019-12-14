import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Box;
import java.util.HashMap;
import java.awt.Color;
import java.awt.Dimension;

class GUIScheduler extends JFrame{
    private static final long serialVersionUID = 1L;

    class ProcessView{
        public JPanel gantt;
        public JLabel name;
        public JLabel priority;
    }

    private JPanel chart;
    private JPanel summary;
    private JLabel avgWaiting;
    private JLabel avgTurnAround;

    private HashMap<Process, ProcessView> processView;

    GUIScheduler(){
        // Main frame
        super("Java CPU scheduler simulator");
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        // Gantt chart
        this.chart = new JPanel();
        this.chart.setLayout(new BoxLayout(this.chart, BoxLayout.PAGE_AXIS));
        this.add(chart);

        // Add space 
        this.add(Box.createRigidArea(new Dimension(0, 30)));

        // Summary
        this.summary = new JPanel();
        this.summary.setLayout(new BoxLayout(this.summary, BoxLayout.PAGE_AXIS));
        this.summary.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3, true));
        this.add(summary);

        // Averages
        this.avgWaiting = new JLabel("Average Waiting time: 0.00");
        this.avgTurnAround = new JLabel("Average Turnaround time: 0.00");
        this.add(avgWaiting);
        this.add(avgTurnAround);

        this.setSize(new Dimension(1000, 400));
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
        this.chart.add(new JLabel("Chart"));

        this.summary.removeAll();
        this.summary.repaint();
        this.summary.add(new JLabel("Summary"));

        // Add Col header row
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.LINE_AXIS));
        row.add(new JLabel("PID"));
        row.add(Box.createHorizontalGlue());
        row.add(new JLabel("Name"));
        row.add(Box.createHorizontalGlue());
        row.add(new JLabel("Priority"));
        this.summary.add(row);

        for(int i = 0; i < _processes.length; ++i){
            ProcessView pv = new ProcessView();
            pv.gantt = new JPanel();
            pv.gantt.setLayout(new BoxLayout(pv.gantt, BoxLayout.LINE_AXIS));
            JLabel yAxisLabel = new JLabel(_processes[i].getName());
            pv.gantt.add(yAxisLabel);
            pv.gantt.add(Box.createRigidArea(new Dimension(20, 0)));
            this.chart.add(pv.gantt);

            JPanel summaryRow = new JPanel();
            summaryRow.setLayout(new BoxLayout(summaryRow, BoxLayout.LINE_AXIS));
            pv.name = new JLabel(_processes[i].getName());
            pv.name.setSize(200, 50);
            pv.priority = new JLabel(String.valueOf(_processes[i].getPriority()));
            summaryRow.add(new JLabel(String.valueOf(i)));
            summaryRow.add(Box.createHorizontalGlue());
            summaryRow.add(pv.name);
            summaryRow.add(Box.createHorizontalGlue());
            summaryRow.add(pv.priority);
            this.summary.add(summaryRow);

            this.processView.put(_processes[i], pv); // To access later
        }

    }

    void switchExecution(ExecutionSegment segment){
        double avgWaitingC = 0;
        double avgTurnaroundC = 0;
        final int n = this.processView.size();
        // Segment size 
        final int scale = 20;
        final int size = (segment.end_time - segment.start_time) * scale;
        for(Process p : this.processView.keySet()){
            JPanel pholder = new JPanel();
            pholder.setMaximumSize(new Dimension(size, 20));
            pholder.setMinimumSize(new Dimension(size, 20));
            if(segment.process == null){
                // Context switch
                pholder.setBackground(Color.BLACK);
            }else if(p == segment.process){
                // Add colored segment
                pholder.setBackground(p.getColor());
            }
            this.processView.get(p).gantt.add(pholder);
            avgWaitingC += p.getWaitingTime() / (double) n;
            avgTurnaroundC += p.getTurnAround() / (double) n;
        }
        // Update averages
        this.avgWaiting.setText("Average waiting time: " + avgWaitingC);
        this.avgTurnAround.setText("Average turnaround time: " + avgTurnaroundC);
    }
}
