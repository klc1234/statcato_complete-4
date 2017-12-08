/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.graph;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.statcato.*;
import org.statcato.file.FileOperations;
import org.statcato.utils.HelperFunctions;

/**
 * A frame for multiple charts.  Contains a menu for common operations. 
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @see org.jfree.chart
 * @since 1.0.2
 */
public class StatcatoMultipleChartFrame extends JFrame implements 
        ActionListener, Printable {
   /**
    * For clipboard support
    */
    private transient ImageSelection imgsel;
   /**
    * Parent frame
    */
   public transient Statcato parent;
   /**
    * JFreeChart objects contained in this frame
    */
   protected transient ArrayList<JFreeChart> chartList;
   /**
    * ChartPanel objects contained in this frame
    */
   protected transient ArrayList<ChartPanel> chartPanelList;
   /**
    * Chart title
    */
   protected String title;
   
   /**
    * Constructor, given the frame title and the chart.
    * 
    * @param title string to be displayed at the top of frame
    * @param charts an ArrayList of JFreeChart instances to be contained in the frame
    * @param parent0 parent frame
    */
   public StatcatoMultipleChartFrame(String title, 
           ArrayList<JFreeChart> charts, Statcato parent0){
      super(title);
      this.title = title;
      this.chartList = charts;
      this.parent = parent0;
      chartPanelList = new ArrayList<ChartPanel>();
      setupFrame();
   }

   /**
    * Helper function for constructor.
    * Sets up menu bar and frame properties.
    */
   private void setupFrame() {
   JMenuBar menuBar = new JMenuBar();
      JMenu FileMenu = new JMenu("Graph");
      JMenuItem copy = new JMenuItem("Copy Graph to Clipboard");
      FileMenu.add(copy);
      copy.setActionCommand("copy");
      copy.addActionListener(this);

      JMenuItem save = new JMenuItem("Save...");
      FileMenu.add(save);
      save.setActionCommand("save");
      save.addActionListener(this);
      
      JMenuItem print = new JMenuItem("Print...");
      FileMenu.add(print);
      print.setActionCommand("print");
      print.addActionListener(this);
      
      menuBar.add(FileMenu);
      setJMenuBar(menuBar);
      imgsel = new ImageSelection();

      setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

      parent.addWindowFrame(this);

      if (chartList.size() == 1) {
          getContentPane().add(new ChartPanel(chartList.get(0)));
      }
      else {
          JPanel panel = new JPanel(new GridLayout(0, 2));
          panel.setBackground(Color.white);
          for (int i = 0; i < chartList.size(); ++ i) {
              ChartPanel c = new ChartPanel(chartList.get(i));
              chartPanelList.add(c);
              panel.add(c);
          }
          getContentPane().add(panel);
      }
      addCloseListener();

      restore();
   }
   
   /**
    * Adds a listener for the close button.  Prompts to confirm closing
    * a chart.
    */
   public void addCloseListener() {
       addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                Object[] options = {"Confirm", "Cancel"};
                int choice = JOptionPane.showOptionDialog(StatcatoMultipleChartFrame.this,
                    "Are you sure you want to remove the graph from the project?",
                    "Closing Chart...",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null,
                    options, options[0]);

                if (choice == JOptionPane.YES_OPTION) {
                    // remove frame
                    parent.removeWindowFrame(StatcatoMultipleChartFrame.this);
                    StatcatoMultipleChartFrame.this.dispose();
                }
            }
        });
   }

   /**
    * Returns the title of the frame.
    * 
    * @return title
    */
   public String getChartTitle() {
       return title;
   }

   /**
    * Returns an ArrayList of charts.
    * 
    * @return ArrayList<JFreeChart>
    */
   public ArrayList<JFreeChart> getCharts() {
       return chartList;
   }
   
   /** 
    * Sets the parent frame of this object.
    */
   public void setParent(Statcato parent) {
       this.parent = parent;
   }

   /**
    * Restores the frame to normal size and brings it to front.
    */
   public void restore() {
       setState(Frame.NORMAL);
       toFront();
   }

   @Override
   public void actionPerformed(ActionEvent ae){
       
      if(ae.getActionCommand().equals("copy")){
         setClipboard(GraphUtils.joinBufferedChartImages(chartList));
      }
      else if (ae.getActionCommand().equals("save")) {
          JFileChooser fileChooser = new JFileChooser(FileOperations.getRecentProject()==
                    null?null:FileOperations.getRecentProject().getParentFile());
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "PNG", "png");
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setFileFilter(filter);

        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getPath();
                if (!filename.endsWith(".png")) {
                    filename = filename + ".png";
                }
                BufferedImage image = GraphUtils.joinBufferedChartImages(chartList);
                File file = new File(filename);
                
                try {
                    OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
                    GraphUtils.writeBufferedImageAsPNG(os, 
                    image, 
                    image.getWidth(), image.getHeight());
                }
                catch (IOException e) {
                    HelperFunctions.showErrorDialog(this, "Unexpected error while saving");
                }
        }
      }
      else if (ae.getActionCommand().equals("print")) {
          createChartPrintJob();
      }
      else {
          for (int i = 0; i < chartPanelList.size(); ++i) {
            chartPanelList.get(i).actionPerformed(ae);
          }
      }
   }

   /**
     * Creates a print job for the chart.
     */
    public void createChartPrintJob() {
        PrinterJob job = PrinterJob.getPrinterJob();
        PageFormat pf = job.defaultPage();
        PageFormat pf2 = job.pageDialog(pf);
        if (pf2 != pf) {
            job.setPrintable(this, pf2);
            if (job.printDialog()) {
                try {
                    job.print();
                }
                catch (PrinterException e) {
                    JOptionPane.showMessageDialog(this, e);
                }
            }
        }
    }

    /**
     * Prints the charts on a single page.
     *
     * @param g  the graphics context.
     * @param pf  the page format to use.
     * @param pageIndex  the index of the page. If not <code>0</code>, nothing
     *                   gets print.
     *
     * @return The result of printing.
     */
    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) {

        if (pageIndex != 0) {
            return NO_SUCH_PAGE;
        }
        Graphics2D g2 = (Graphics2D) g;
      
        GraphUtils.drawCharts(g2, chartList, 
                (int) pf.getImageableX(), 
                (int) pf.getImageableY(),
                (int) pf.getImageableWidth(), 
                (int) pf.getImageableHeight());
        return PAGE_EXISTS;
    }

   /**
    * Sets the content of the clipboard to the given image.
    * 
    * @param image Image object
    */
   private void setClipboard(Image image){
      imgsel.setImage(image);
       Toolkit.getDefaultToolkit().getSystemClipboard().setContents(imgsel, null);
    }

    
    private class ImageSelection implements Transferable{
       private Image image;
       public void setImage(Image image) {
          this.image = image;
       }
       @Override
       public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{DataFlavor.imageFlavor};
        }
       @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return DataFlavor.imageFlavor.equals(flavor);
        }
       @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
            if (!DataFlavor.imageFlavor.equals(flavor)) {
                throw new UnsupportedFlavorException(flavor);
            }
            return image;
        }
    }
} 