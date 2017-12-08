/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.graph;

import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.*;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.statcato.*;
import org.statcato.utils.HelperFunctions;

/**
 * A frame for graphs.  Contains a menu for common operations. 
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @see org.jfree.chart
 * @since 1.0
 */
public class StatcatoChartFrame extends ChartFrame implements ActionListener {
   /**
    * For clipboard support
    */
    private transient ImageSelection imgsel;
   /**
    * Parent frame
    */
   public transient Statcato parent;
   /**
    * JFreeChart object contained in this frame
    */
   protected transient JFreeChart chart;
   /**
    * Chart title
    */
   protected String title;
   
   /**
    * Constructor, given the frame title and the chart.
    * 
    * @param title string to be displayed at the top of frame
    * @param chart JFreeChart instance to be contained in the frame
    */
   public StatcatoChartFrame(String title, JFreeChart chart, Statcato parent0){
      super(title, chart);
      this.title = title;
      this.chart = chart;
      this.parent = parent0;
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
      
      JMenuItem properties = new JMenuItem("Properties...");
      FileMenu.add(properties);
      properties.setActionCommand("properties");
      properties.addActionListener(this);
      
      menuBar.add(FileMenu);
      setJMenuBar(menuBar);
      imgsel = new ImageSelection();

      setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

      parent.addWindowFrame(this);

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
                int choice = JOptionPane.showOptionDialog(StatcatoChartFrame.this,
                    "Are you sure you want to remove the graph from the project?",
                    "Closing Chart...",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null,
                    options, options[0]);

                if (choice == JOptionPane.YES_OPTION) {
                    // remove frame
                    parent.removeWindowFrame(StatcatoChartFrame.this);
                    StatcatoChartFrame.this.dispose();
                }
            }
        });
   }

   /**
    * Returns the JFreeChart object associated with this frame.
    * 
    * @return JFreeChart object
    */
   public JFreeChart getChart() {
       return chart;
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
         setClipboard(getChartPanel().getChart().createBufferedImage(
         ChartPanel.DEFAULT_WIDTH, ChartPanel.DEFAULT_HEIGHT, 
                 BufferedImage.TYPE_INT_ARGB,null));
      }
      else if (ae.getActionCommand().equals("save")) {
          try {
            getChartPanel().doSaveAs();
          }
          catch (IOException e) {
            HelperFunctions.showErrorDialog(this, "Unexpected error");
          }
      }
      else if (ae.getActionCommand().equals("print")) {
          getChartPanel().createChartPrintJob();
      }
      else if (ae.getActionCommand().equals("properties")) {
          getChartPanel().doEditChartProperties();
          getChartPanel().repaint();
      }
      else {
          getChartPanel().actionPerformed(ae);
      }
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