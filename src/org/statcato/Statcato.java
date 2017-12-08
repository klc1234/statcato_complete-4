/*
 * Statcato.java
 *
 * Created on February 26, 2008, 10:45 AM
 */

package org.statcato;

import org.statcato.graph.StatcatoChartFrame;
import org.statcato.graph.StatcatoMultipleChartFrame;
import org.statcato.dialogs.calc.probdist.*;
import org.statcato.dialogs.stat.anova.*;
import org.statcato.dialogs.stat.multinomial.*;
import org.statcato.dialogs.stat.correg.*;
import org.statcato.dialogs.stat.hytest.*;
import org.statcato.dialogs.stat.ci.*;
import org.statcato.dialogs.stat.samplesize.*;
import org.statcato.dialogs.stat.basicstats.*;
import org.statcato.dialogs.stat.nonparametrics.*;
import org.statcato.utils.PopupListener;
import org.statcato.dialogs.edit.*;
import org.statcato.dialogs.stat.*;
import org.statcato.dialogs.file.*;
import org.statcato.dialogs.calc.*;
import org.statcato.dialogs.data.*;
import org.statcato.dialogs.graph.*;
import org.statcato.spreadsheet.Spreadsheet;
import org.statcato.spreadsheet.SpreadsheetScrollPane;
import org.statcato.dialogs.*;
import org.statcato.file.FileOperations;
import org.statcato.file.Project;
import org.statcato.utils.HelperFunctions;
import org.statcato.utils.SetStatusTimer;
import org.statcato.statistics.inferential.nonparametrics.WilcoxonSignedRankPValue;

import javax.swing.*;
import java.util.*;
import java.text.DateFormat;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.*;
import java.io.*;
import java.net.URL;
import java.beans.PropertyVetoException;
import javax.swing.undo.*;
import javax.swing.event.*;
import javax.help.*;


/**
 * Statcato main class.  Application frame contains menu, toolbar,
 * and two internal frames.  Handles actions performed on these components.
 * 
 * @author  Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class Statcato extends javax.swing.JFrame implements ActionListener {
   public static final String VERSION = "1.0.2";
   static final private String NEW = "new";
   static final private String OPEN = "open";
   static final private String PRINT = "print";
   static final private String CUT = "cut";
   static final private String PASTE = "paste";
   static final private String COPY = "copy";
   static final private String UNDO = "undo";
   static final private String REDO = "redo";
   static final private String SAVE = "save";
   static final private String IMPORT = "import ";
   static final private String HISTORY = "history";
   static final private String EDIT = "editlastdialog";
   static final private String HELP = "help";
   static final private String SELECTALL = "selectall";
   static final private String CLEAR = "clear";
   static final private String DELETE = "delete";
   static final private String INSERTROW = "insertrow";
   static final private String INSERTCOL = "insertcol";
   static final private String INSERTCELL = "insertcell";
   static final public String DEFAULTHELPFILE = "overview";
   static final private String DEFAULT_STATUS =
           "Enter data into the Data window. " +
                "Use menus for various functions. Results are shown " +
                "in the Log window. F1 for help.";

   // Variable declarations not created by GUI editor
   private MouseListener WSPopupListener;
   private MouseListener LogPopupListener;
    private LogWindow LogTextPane;
    private DatasheetTabbedPane DatasheetPane;   
    public Project project;

    // for edit last dialog and dialog history
    private StatcatoDialog lastDialog = null;
    private JButton editDialogButton = null;
    private JButton dialogHistoryButton = null;
    private DialogHistoryList dialogHistoryList = new DialogHistoryList();
    
    // dialogs
    // graph
    private BarChartDialog barChartDialog;
    private BoxPlotDialog boxPlotDialog;
    private DotPlotDialog dotPlotDialog;
    private HistogramDialog histogramDialog;
    private NormalQuantilePlotDialog normalQuantilePlotDialog;
    private PieChartDialog pieChartDialog;
    private ScatterplotDialog scatterplotDialog;
    private StemAndLeafPlotDialog stemAndLeafPlotDialog;
    // calc
    private CalculatorDialog calculatorDialog;
    private BinomialDistributionDialog binomialDistributionDialog;
    private ChiSquareDistributionDialog chiSquareDistributionDialog;
    private DiscreteDistributionDialog discreteDistributionDialog;
    private ExponentialDistributionDialog exponentialDistributionDialog;
    private FDistributionDialog fDistributionDialog;
    private FrequencyTableDialog frequencyTableDialog;
    private GeometricDistributionDialog geometricDistributionDialog;
    private IntegerDistributionDialog integerDistributionDialog;
    private NormalDistributionDialog normalDistributionDialog;
    private PoissonDistributionDialog poissonDistributionDialog;
    private StudentTDistributionDialog studentTDistributionDialog;
    private UniformDistributionDialog uniformDistributionDialog;
    private HyTestConclusionDialog hyTestConclusionDialog;
    private PvalueDialog pvalueDialog;
    // data
    private ArbitraryDataPatternDialog arbitraryDataPatternDialog;
    private BinomialSampleDialog binomialSampleDialog;
    private DisplayDataDialog displayDataDialog;
    private IntegerSampleDialog integerSampleDialog;
    private NormalSampleDialog normalSampleDialog;
    private RankDialog rankDialog;
    private SampleFromColumnDialog sampleFromColumnDialog;
    private SimpleNumberPatternDialog simpleNumberPatternDialog;
    private SortDialog sortDialog;
    private StandardizeDialog standardizeDialog;
    private UniformSampleDialog uniformSampleDialog;
    private TransposeDialog transposeDialog;
    // stat
    private CI1PopMeanDialog cI1PopMeanDialog;
    private CI1PopPropDialog cI1PopPropDialog;
    private CI1PopVarDialog cI1PopVarDialog;
    private CI2PopMeanDialog cI2PopMeanDialog;
    private CI2PopPropDialog cI2PopPropDialog;
    private CI2PopVarDialog cI2PopVarDialog;
    private CIMatchedPairsDialog cIMatchedPairsDialog;
    private ColumnStatisticsDialog columnStatisticsDialog;
    private ContingencyTableDialog contingencyTableDialog;
    private CorrelationRegressionDialog correlationRegressionDialog;
    private CorrelationMatrixDialog correlationRegressionAllPairsDialog;
    private DescriptiveStatisticsDialog descriptiveStatisticsDialog;
    private GoodnessOfFitDialog goodnessOfFitDialog;
    private HyTest1PopMeanDialog hyTest1PopMeanDialog;
    private HyTest1PopPropDialog hyTest1PopPropDialog;
    private HyTest1PopVarDialog hyTest1PopVarDialog;
    private HyTest2PopMeanDialog hyTest2PopMeanDialog;
    private HyTest2PopPropDialog hyTest2PopPropDialog;
    private HyTest2PopVarDialog hyTest2PopVarDialog;
    private HyTestMatchedPairsDialog hyTestMatchedPairsDialog;
    private MultipleRegressionDialog multipleRegressionDialog;
    private NonLinearModelsDialog nonLinearModelsDialog;
    private NormalityTestDialog normalityTestDialog;
    private OneWayANOVADialog oneWayANOVADialog;
    private RowStatisticsDialog rowStatisticsDialog;
    private SampleSizeMeanDialog sampleSizeMeanDialog;
    private SampleSizePropDialog sampleSizePropDialog;
    private TwoWayANOVADialog twoWayANOVADialog;
    private SignTestDialog signTestDialog;
    private MatchedPairSignTestDialog matchedPairSignTestDialog;
    private WilcoxonSignedRankTestDialog wilcoxonSignedRankTestDialog;
    private WilcoxonRankSumTestDialog mannWhitneyTestDialog;
    private KruskalWallisDialog kruskalWallisDialog;
    private RankCorrelationDialog rankCorrelationDialog;
    private RunsTestDialog runsTestDialog;
    private CrossTabulationDialog crossTabulationDialog;
    //file
    private LoadDatasetDialog loadDatasetDialog;
    private PrintLogDialog printLogDialog;
    private PrintDatasheetDialog printDatasheetDialog;
    private OptionsDialog optionsDialog;
    //edit
    private AddMultipleRowColDialog addMultipleRowColDialog;
    // help
    private AboutDialog aboutDialog;
    private CheckUpdatesDialog checkUpdatesDialog;
    private String helpSetName = "StatcatoHelpSet.hs";
    private HelpSet hs;
    private HelpBroker hb;

    // undo/redo
    /**
     * Undo manager for the application.
     */
    public UndoManager undoManager;
    /**
     * Compound edit object for the application.
     */
    public DialogEdit compoundEdit;
    private MenuItemUndoAction menuUndoAction;
    private MenuItemRedoAction menuRedoAction;
    private IconUndoAction iconUndoAction;
    private IconRedoAction iconRedoAction;

    // list of windows and corresponding menu items
    private ArrayList<JFrame> windowFrameList;
    private ArrayList<StatcatoChartMenuItem> windowMenuList;

    // status timers
    private ArrayList<SetStatusTimer> statusTimerList;

    /** Creates new form Statcato */
    public Statcato() {
        try {
	    // Set System L&F
            UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
        } 
        catch (UnsupportedLookAndFeelException e) {
        // handle exception
        }
        catch (ClassNotFoundException e) {
           // handle exception
        }
        catch (InstantiationException e) {
           // handle exception
        }
        catch (IllegalAccessException e) {
           // handle exception
        }
        initComponents();
        customInitComponents();
        
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                closeApplication();
            }
        });        
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension size = getSize(); // size of jframe
                Dimension toolbarSize = MainToolBar.getSize();
                Dimension menuSize = MainMenuBar1.getSize();

                // update the sizes of the log and Datasheet windows
                int newHeight = (size.height - 110 -
                        toolbarSize.height - menuSize.height)/2;
                showDialogHistory();
                DatasheetInternalFrame.setBounds(25,
                        toolbarSize.height + 20 +newHeight, size.width-60,
                        newHeight);

                // update size of the toolbar
                MainToolBar.setBounds(0, 0, size.width, toolbarSize.height);

                // update size of the status bar
                StatusPanel.setBounds(0,  toolbarSize.height + 20 + 2*newHeight,
                        size.width, 50);
                StatusLabel.setSize(size.width, 40);
            }
        });

        addInternalFrameListener();
        showDialogHistory();

        LogInternalFrame.setVisible(true);
        DatasheetInternalFrame.setVisible(true);
        DatasheetInternalFrame.toBack();
        LogInternalFrame.toFront();

        
        try {
            LogInternalFrame.setSelected(false);
        }
        catch (PropertyVetoException e) {
            System.out.println("cannot select log frame");
        }
        
        // set focus to Datasheet window
        try {
            DatasheetInternalFrame.setSelected(true);
        }
        catch (PropertyVetoException e) {

        }
        setExtendedState(Frame.MAXIMIZED_BOTH);

        // check for new version
        checkUpdates();

        
    }

    /**
     * Adds a listener for the history internal frame.
     */
    private void addInternalFrameListener() {
        InternalFrameListener listener = new InternalFrameListener() {
            @Override
            public void internalFrameActivated(InternalFrameEvent e) {
            }
            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
            }
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                HistoryCheckBoxMenuItem.setSelected(false);
                showDialogHistory();
            }
            @Override
            public void internalFrameDeactivated(InternalFrameEvent e) {
            }
            @Override
            public void internalFrameDeiconified(InternalFrameEvent e) {
            }
            @Override
            public void internalFrameIconified(InternalFrameEvent e) {                
            }
            @Override
            public void internalFrameOpened(InternalFrameEvent e) {
            }
        };
        HistoryInternalFrame.addInternalFrameListener(listener);
    }
    /**
     * Checks for updates from the Statcato web site.
     * Prints out check updates in the status bar.  If a new version
     * is available, the check updates dialog is displayed.
     */
    public void checkUpdates() {
        String currentVersion = HelperFunctions.getVersionNumberFromWeb();
        System.out.println("current version = " + currentVersion);
        if (currentVersion.equals("error")) {
            setStatus("Error obtaining the current version number from web.");
        }
        else if (currentVersion.equals(VERSION)) {
            // this is the current version
            setStatus("Welcome to Statcato " + VERSION + ".");
        }
        else if (currentVersion.compareTo(Statcato.VERSION) < 0) {
            // this is a more current version that the one posted online
            setStatus("Welcome to Statcato " + VERSION + " (pre-release).");
        }
        else {
            // new version available
            setStatus("New version " + currentVersion + " is available." +
                    "  Visit http://www.statcato.org to obtain the latest" +
                    " version.");
            CheckUpdatesMenuItemActionPerformed(null);
        }

        // clear status bar in 5 seconds
        statusTimerList.add(new SetStatusTimer(StatusLabel, DEFAULT_STATUS, 5));
    }

    /**
     * Sets a timer to clear the status bar in the given amount of time in
     * seconds.
     * 
     * @param seconds timer start time
     */
    public void setClearStatusTimer(int seconds) {
        statusTimerList.add(new SetStatusTimer(StatusLabel, "", seconds));
    }

    /**
     * Restores the status bar to the current status in the given amount of
     * time in seconds.
     *
     * @param seconds timer start time
     */
    public void restoreStatusTimer(int seconds) {
        statusTimerList.add(new SetStatusTimer(StatusLabel, StatusLabel.getText(), seconds));
    }

    /**
     * Displays the given string in the status bar.
     * 
     * @param msg string to be displayed
     */
    public void setStatus(String msg) {
        StatusLabel.setText(msg);
    }
    
    /**
     * Sets the status bar to the given status in the given amount of
     * time in seconds
     * 
     * @param msg string to be displayed as status
     * @param seconds timer start time
     */
    public void setStatusTimer(String msg, int seconds) {
        statusTimerList.add(new SetStatusTimer(StatusLabel, msg, seconds));
    }

    public void clearStatusTimerList() {
        Iterator<SetStatusTimer> itr = statusTimerList.iterator();
        while (itr.hasNext()) {
            SetStatusTimer element = itr.next();
            element.cancelTimer();
        }
        statusTimerList = new ArrayList<SetStatusTimer>();
    }

    /**
     * Clears the status bar.
     */
    public void clearStatus() {
        StatusLabel.setText("");
    }

    /**
     * Initializes JavaHelp help documentation module.
     */
    public void addHelp(){
        ClassLoader loader = this.getClass().getClassLoader();
        URL url = getClass().getResource(helpSetName);
        
        try {
            hs = new HelpSet(loader, url);
        } catch (Exception ee) {
            System.out.println ("Trouble in createHelpSet;");
            return;
        }

        hb = hs.createHelpBroker("Main_Window");
        ActionListener contentListener = new CSH.DisplayHelpFromSource(hb);
        CSH.setHelpIDString(HelpMenuItem1, "overview");
        HelpMenuItem1.addActionListener(contentListener);

        Font font = new Font("SansSerif", Font.PLAIN,12);
        hb.setFont(font);
	}

    /**
     * Enables help for the given menu item.
     *
     * @param menu  menu item
     * @param helpFile help file name
     */
    public void addDialogHelp(JMenuItem menu, String helpFile) {
        hb.enableHelpOnButton(menu, helpFile, hs);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        StatcatoDesktopPane = new javax.swing.JDesktopPane();
        DatasheetInternalFrame = new javax.swing.JInternalFrame();
        LogInternalFrame = new javax.swing.JInternalFrame();
        LogScrollPane = new javax.swing.JScrollPane();
        MainToolBar = new javax.swing.JToolBar();
        HistoryInternalFrame = new javax.swing.JInternalFrame();
        HistoryScrollPane = new javax.swing.JScrollPane();
        StatusPanel = new javax.swing.JPanel();
        StatusLabel = new javax.swing.JLabel();
        MainMenuBar1 = new javax.swing.JMenuBar();
        FileMenu1 = new javax.swing.JMenu();
        NewWorksheetMenuItem1 = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JSeparator();
        OpenProjMenuItem = new javax.swing.JMenuItem();
        SaveProjMenuItem = new javax.swing.JMenuItem();
        SaveProjAsMenuItem = new javax.swing.JMenuItem();
        CloseProjMenuItem = new javax.swing.JMenuItem();
        jSeparator26 = new javax.swing.JSeparator();
        OpenWorksheetMenuItem1 = new javax.swing.JMenuItem();
        SaveWorksheetMenuItem1 = new javax.swing.JMenuItem();
        SaveWorksheetAsMenuItem1 = new javax.swing.JMenuItem();
        CloseWorksheetMenuItem1 = new javax.swing.JMenuItem();
        jSeparator13 = new javax.swing.JSeparator();
        LoadDatasetMenuItem1 = new javax.swing.JMenuItem();
        jSeparator14 = new javax.swing.JSeparator();
        ClearSessionMenuItem1 = new javax.swing.JMenuItem();
        jSeparator15 = new javax.swing.JSeparator();
        PrintSessionMenuItem1 = new javax.swing.JMenuItem();
        PrintWorksheetMenuItem1 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        OptionsMenuItem = new javax.swing.JMenuItem();
        jSeparator16 = new javax.swing.JSeparator();
        ExitMenuItem1 = new javax.swing.JMenuItem();
        EditMenu1 = new javax.swing.JMenu();
        UndoMenuItem = new javax.swing.JMenuItem();
        RedoMenuItem = new javax.swing.JMenuItem();
        jSeparator25 = new javax.swing.JSeparator();
        CutMenuItem1 = new javax.swing.JMenuItem();
        CopyMenuItem1 = new javax.swing.JMenuItem();
        PasteMenuItem1 = new javax.swing.JMenuItem();
        jSeparator17 = new javax.swing.JSeparator();
        SelectAllMenuItem1 = new javax.swing.JMenuItem();
        jSeparator18 = new javax.swing.JSeparator();
        ClearCellsMenuItem1 = new javax.swing.JMenuItem();
        DeleteCellsMenuItem1 = new javax.swing.JMenuItem();
        jSeparator19 = new javax.swing.JSeparator();
        InsertRowMenu1 = new javax.swing.JMenu();
        InsertRowAboveMenuItem1 = new javax.swing.JMenuItem();
        InsertRowBelowMenuItem1 = new javax.swing.JMenuItem();
        InsertColumnMenu1 = new javax.swing.JMenu();
        InsertColumnLeftMenuItem1 = new javax.swing.JMenuItem();
        InsertColumnRightMenuItem1 = new javax.swing.JMenuItem();
        InsertCellMenu1 = new javax.swing.JMenu();
        InsertCellAboveMenuItem1 = new javax.swing.JMenuItem();
        InsertCellBelowMenuItem1 = new javax.swing.JMenuItem();
        AddRowColMenuItem1 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        EditLastDialogMenuItem = new javax.swing.JMenuItem();
        HistoryCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        DataMenu1 = new javax.swing.JMenu();
        SortMenuItem1 = new javax.swing.JMenuItem();
        RankMenuItem1 = new javax.swing.JMenuItem();
        TransposeMenuItem1 = new javax.swing.JMenuItem();
        jSeparator20 = new javax.swing.JSeparator();
        DisplayDataMenuItem1 = new javax.swing.JMenuItem();
        jSeparator21 = new javax.swing.JSeparator();
        StandardizeMenuItem1 = new javax.swing.JMenuItem();
        GeneratePatternedDataMenu1 = new javax.swing.JMenu();
        NumberSeqMenuItem1 = new javax.swing.JMenuItem();
        DataSeqMenuItem1 = new javax.swing.JMenuItem();
        GenerateRandomDataMenu = new javax.swing.JMenu();
        BinomialSamplesMenuItem = new javax.swing.JMenuItem();
        IntegerSamplesMenuItem = new javax.swing.JMenuItem();
        jSeparator24 = new javax.swing.JSeparator();
        NormalSamplesMenuItem = new javax.swing.JMenuItem();
        UniformSamplesMenuItem = new javax.swing.JMenuItem();
        jSeparator23 = new javax.swing.JSeparator();
        SampleFromColumnMenuItem = new javax.swing.JMenuItem();
        CalcMenu1 = new javax.swing.JMenu();
        CalculatorMenuItem = new javax.swing.JMenuItem();
        ProbDistMenu1 = new javax.swing.JMenu();
        NormalMenuItem1 = new javax.swing.JMenuItem();
        StudentTMenuItem1 = new javax.swing.JMenuItem();
        ChiSquareMenuItem2 = new javax.swing.JMenuItem();
        FMenuItem1 = new javax.swing.JMenuItem();
        ExponentialMenuItem1 = new javax.swing.JMenuItem();
        UniformMenuItem1 = new javax.swing.JMenuItem();
        jSeparator22 = new javax.swing.JSeparator();
        BinomialMenuItem1 = new javax.swing.JMenuItem();
        DiscreteMenuItem1 = new javax.swing.JMenuItem();
        GeometricMenuItem1 = new javax.swing.JMenuItem();
        IntegerMenuItem1 = new javax.swing.JMenuItem();
        PoissonMenuItem1 = new javax.swing.JMenuItem();
        PvalueMenuItem1 = new javax.swing.JMenuItem();
        FreqTableMenuItem = new javax.swing.JMenuItem();
        StatMenu1 = new javax.swing.JMenu();
        BasicStatisticsMenu1 = new javax.swing.JMenu();
        DescriptiveStatMenuItem1 = new javax.swing.JMenuItem();
        ColStatMenuItem1 = new javax.swing.JMenuItem();
        RowStatMenuItem1 = new javax.swing.JMenuItem();
        NormalityTestMenuItem = new javax.swing.JMenuItem();
        SampleSizeMenu1 = new javax.swing.JMenu();
        MeanMenuItem1 = new javax.swing.JMenuItem();
        PropMenuItem1 = new javax.swing.JMenuItem();
        TestAndCIMenu1 = new javax.swing.JMenu();
        OnePopMeanMenuItem1 = new javax.swing.JMenuItem();
        OnePopPropMenuItem1 = new javax.swing.JMenuItem();
        OnePopVarMenuItem1 = new javax.swing.JMenuItem();
        TwoPopMeanMenuItem1 = new javax.swing.JMenuItem();
        TwoPopPropMenuItem1 = new javax.swing.JMenuItem();
        TwoPopVarMenuItem1 = new javax.swing.JMenuItem();
        MatchedPairsMenuItem1 = new javax.swing.JMenuItem();
        HyTestMenu1 = new javax.swing.JMenu();
        HyTestMeanMenuItem1 = new javax.swing.JMenuItem();
        HyTestPropMenuItem1 = new javax.swing.JMenuItem();
        HyTestVarMenuItem1 = new javax.swing.JMenuItem();
        HyTest2MeanMenuItem1 = new javax.swing.JMenuItem();
        HyTest2PropMenuItem1 = new javax.swing.JMenuItem();
        HyTest2VarMenuItem1 = new javax.swing.JMenuItem();
        HyTestMatchedPairsMenuItem1 = new javax.swing.JMenuItem();
        ConclusionMenuItem = new javax.swing.JMenuItem();
        CorrRegMenu1 = new javax.swing.JMenu();
        TwoVarMenuItem1 = new javax.swing.JMenuItem();
        TwoVarAllPairsMenuItem1 = new javax.swing.JMenuItem();
        MultipleMenuItem1 = new javax.swing.JMenuItem();
        NonLinMenuItem = new javax.swing.JMenuItem();
        RankCorrelationMenuItem2 = new javax.swing.JMenuItem();
        MultinomialMenu1 = new javax.swing.JMenu();
        GoodnessOfFitMenuItem1 = new javax.swing.JMenuItem();
        ContingencyTableMenuItem1 = new javax.swing.JMenuItem();
        CrossTabMenuItem = new javax.swing.JMenuItem();
        ANOVAMenu1 = new javax.swing.JMenu();
        OneANOVAMenuItem1 = new javax.swing.JMenuItem();
        TwoANOVAMenuItem = new javax.swing.JMenuItem();
        NonparamMenu = new javax.swing.JMenu();
        SignTestMenuItem = new javax.swing.JMenuItem();
        MatchedPairSignTestMenuItem = new javax.swing.JMenuItem();
        WilcoxonSignedRankMenuItem = new javax.swing.JMenuItem();
        MannWhitneyTestMenuItem = new javax.swing.JMenuItem();
        KruskalWallisTestMenuItem = new javax.swing.JMenuItem();
        RankCorrelationMenuItem = new javax.swing.JMenuItem();
        RunsTestMenuItem = new javax.swing.JMenuItem();
        GraphMenu1 = new javax.swing.JMenu();
        BarChartMenuItem1 = new javax.swing.JMenuItem();
        BoxPlotMenuItem1 = new javax.swing.JMenuItem();
        dotPlotMenuItem = new javax.swing.JMenuItem();
        HistogramMenuItem1 = new javax.swing.JMenuItem();
        NQPMenuItem1 = new javax.swing.JMenuItem();
        PieChartMenuItem1 = new javax.swing.JMenuItem();
        ScatterplotMenuItem1 = new javax.swing.JMenuItem();
        StemLeafPlotMenuItem = new javax.swing.JMenuItem();
        ResidualPlotMenuItem = new javax.swing.JMenuItem();
        WindowMenu = new javax.swing.JMenu();
        LogMenuItem = new javax.swing.JMenuItem();
        DatasheetMenuItem = new javax.swing.JMenuItem();
        DialogHistoryMenuItem = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JSeparator();
        HelpMenu1 = new javax.swing.JMenu();
        HelpMenuItem1 = new javax.swing.JMenuItem();
        AboutMenuItem1 = new javax.swing.JMenuItem();
        CheckUpdatesMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Statcato");

        StatcatoDesktopPane.setBackground(new java.awt.Color(236, 233, 216));

        DatasheetInternalFrame.setIconifiable(true);
        DatasheetInternalFrame.setMaximizable(true);
        DatasheetInternalFrame.setResizable(true);
        DatasheetInternalFrame.setTitle("Data");
        DatasheetInternalFrame.setVisible(true);

        javax.swing.GroupLayout DatasheetInternalFrameLayout = new javax.swing.GroupLayout(DatasheetInternalFrame.getContentPane());
        DatasheetInternalFrame.getContentPane().setLayout(DatasheetInternalFrameLayout);
        DatasheetInternalFrameLayout.setHorizontalGroup(
            DatasheetInternalFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 636, Short.MAX_VALUE)
        );
        DatasheetInternalFrameLayout.setVerticalGroup(
            DatasheetInternalFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 364, Short.MAX_VALUE)
        );

        DatasheetInternalFrame.setBounds(10, 450, 660, 410);
        StatcatoDesktopPane.add(DatasheetInternalFrame, javax.swing.JLayeredPane.DEFAULT_LAYER);

        LogInternalFrame.setIconifiable(true);
        LogInternalFrame.setMaximizable(true);
        LogInternalFrame.setResizable(true);
        LogInternalFrame.setTitle("Log");
        LogInternalFrame.setVisible(true);

        javax.swing.GroupLayout LogInternalFrameLayout = new javax.swing.GroupLayout(LogInternalFrame.getContentPane());
        LogInternalFrame.getContentPane().setLayout(LogInternalFrameLayout);
        LogInternalFrameLayout.setHorizontalGroup(
            LogInternalFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(LogScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
        );
        LogInternalFrameLayout.setVerticalGroup(
            LogInternalFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(LogScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
        );

        LogInternalFrame.setBounds(10, 50, 330, 390);
        StatcatoDesktopPane.add(LogInternalFrame, javax.swing.JLayeredPane.DEFAULT_LAYER);

        MainToolBar.setRollover(true);
        MainToolBar.setBounds(0, 0, 690, 40);
        StatcatoDesktopPane.add(MainToolBar, javax.swing.JLayeredPane.DEFAULT_LAYER);

        HistoryInternalFrame.setClosable(true);
        HistoryInternalFrame.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        HistoryInternalFrame.setTitle("Dialog History");
        HistoryInternalFrame.setVisible(true);

        javax.swing.GroupLayout HistoryInternalFrameLayout = new javax.swing.GroupLayout(HistoryInternalFrame.getContentPane());
        HistoryInternalFrame.getContentPane().setLayout(HistoryInternalFrameLayout);
        HistoryInternalFrameLayout.setHorizontalGroup(
            HistoryInternalFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(HistoryScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
        );
        HistoryInternalFrameLayout.setVerticalGroup(
            HistoryInternalFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(HistoryScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
        );

        HistoryInternalFrame.setBounds(350, 50, 320, 390);
        StatcatoDesktopPane.add(HistoryInternalFrame, javax.swing.JLayeredPane.DEFAULT_LAYER);

        StatusLabel.setForeground(new java.awt.Color(51, 51, 51));
        StatusLabel.setText("Welcome to Statcato");
        StatusLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout StatusPanelLayout = new javax.swing.GroupLayout(StatusPanel);
        StatusPanel.setLayout(StatusPanelLayout);
        StatusPanelLayout.setHorizontalGroup(
            StatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, StatusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(StatusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 668, Short.MAX_VALUE)
                .addContainerGap())
        );
        StatusPanelLayout.setVerticalGroup(
            StatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(StatusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(StatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        StatusPanel.setBounds(0, 870, 680, 100);
        StatcatoDesktopPane.add(StatusPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        FileMenu1.setMnemonic('F');
        FileMenu1.setText("File");

        NewWorksheetMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        NewWorksheetMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/New16.gif"))); // NOI18N
        NewWorksheetMenuItem1.setMnemonic('n');
        NewWorksheetMenuItem1.setText("New Datasheet");
        NewWorksheetMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewDatasheetMenuItemActionPerformed(evt);
            }
        });
        FileMenu1.add(NewWorksheetMenuItem1);
        FileMenu1.add(jSeparator12);

        OpenProjMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        OpenProjMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Open16.gif"))); // NOI18N
        OpenProjMenuItem.setText("Open Project...");
        OpenProjMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenProjMenuItemActionPerformed(evt);
            }
        });
        FileMenu1.add(OpenProjMenuItem);

        SaveProjMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        SaveProjMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Save16.gif"))); // NOI18N
        SaveProjMenuItem.setText("Save Project...");
        SaveProjMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveProjMenuItemActionPerformed(evt);
            }
        });
        FileMenu1.add(SaveProjMenuItem);

        SaveProjAsMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/SaveAs16.gif"))); // NOI18N
        SaveProjAsMenuItem.setText("Save Project As...");
        SaveProjAsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveProjAsMenuItemActionPerformed(evt);
            }
        });
        FileMenu1.add(SaveProjAsMenuItem);

        CloseProjMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        CloseProjMenuItem.setText("Close Project");
        CloseProjMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseProjMenuItemActionPerformed(evt);
            }
        });
        FileMenu1.add(CloseProjMenuItem);
        FileMenu1.add(jSeparator26);

        OpenWorksheetMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Open16.gif"))); // NOI18N
        OpenWorksheetMenuItem1.setMnemonic('o');
        OpenWorksheetMenuItem1.setText("Open Datasheet...");
        OpenWorksheetMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenDatasheetMenuItemActionPerformed(evt);
            }
        });
        FileMenu1.add(OpenWorksheetMenuItem1);

        SaveWorksheetMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Save16.gif"))); // NOI18N
        SaveWorksheetMenuItem1.setMnemonic('s');
        SaveWorksheetMenuItem1.setText("Save Current Datasheet...");
        SaveWorksheetMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveDatasheetMenuItemActionPerformed(evt);
            }
        });
        FileMenu1.add(SaveWorksheetMenuItem1);

        SaveWorksheetAsMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/SaveAs16.gif"))); // NOI18N
        SaveWorksheetAsMenuItem1.setMnemonic('a');
        SaveWorksheetAsMenuItem1.setText("Save Current Datasheet As...");
        SaveWorksheetAsMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveDatasheetAsMenuItemActionPerformed(evt);
            }
        });
        FileMenu1.add(SaveWorksheetAsMenuItem1);

        CloseWorksheetMenuItem1.setMnemonic('u');
        CloseWorksheetMenuItem1.setText("Close Current Datasheet");
        CloseWorksheetMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseDatasheetMenuItemActionPerformed(evt);
            }
        });
        FileMenu1.add(CloseWorksheetMenuItem1);
        FileMenu1.add(jSeparator13);

        LoadDatasetMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Import16.gif"))); // NOI18N
        LoadDatasetMenuItem1.setMnemonic('d');
        LoadDatasetMenuItem1.setText("Load Dataset (built-in/online)...");
        LoadDatasetMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoadDatasetMenuItemActionPerformed(evt);
            }
        });
        FileMenu1.add(LoadDatasetMenuItem1);
        FileMenu1.add(jSeparator14);

        ClearSessionMenuItem1.setMnemonic('c');
        ClearSessionMenuItem1.setText("Clear Log");
        ClearSessionMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearLogMenuItemActionPerformed(evt);
            }
        });
        FileMenu1.add(ClearSessionMenuItem1);
        FileMenu1.add(jSeparator15);

        PrintSessionMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        PrintSessionMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Print16.gif"))); // NOI18N
        PrintSessionMenuItem1.setMnemonic('p');
        PrintSessionMenuItem1.setText("Print Log...");
        PrintSessionMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintLogMenuItemActionPerformed(evt);
            }
        });
        FileMenu1.add(PrintSessionMenuItem1);

        PrintWorksheetMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Print16.gif"))); // NOI18N
        PrintWorksheetMenuItem1.setMnemonic('e');
        PrintWorksheetMenuItem1.setText("Print/Export Datasheet...");
        PrintWorksheetMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintDatasheetMenuItemActionPerformed(evt);
            }
        });
        FileMenu1.add(PrintWorksheetMenuItem1);
        FileMenu1.add(jSeparator2);

        OptionsMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Properties16.gif"))); // NOI18N
        OptionsMenuItem.setMnemonic('o');
        OptionsMenuItem.setText("Options...");
        OptionsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OptionsMenuItemActionPerformed(evt);
            }
        });
        FileMenu1.add(OptionsMenuItem);
        FileMenu1.add(jSeparator16);

        ExitMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        ExitMenuItem1.setMnemonic('x');
        ExitMenuItem1.setText("Exit");
        ExitMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitMenuItemActionPerformed(evt);
            }
        });
        FileMenu1.add(ExitMenuItem1);

        MainMenuBar1.add(FileMenu1);

        EditMenu1.setMnemonic('e');
        EditMenu1.setText("Edit");
        EditMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditMenu1ActionPerformed(evt);
            }
        });

        UndoMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        UndoMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Undo16.gif"))); // NOI18N
        UndoMenuItem.setText("Undo");
        UndoMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UndoMenuItemActionPerformed(evt);
            }
        });
        EditMenu1.add(UndoMenuItem);

        RedoMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        RedoMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Redo16.gif"))); // NOI18N
        RedoMenuItem.setText("Redo");
        RedoMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RedoMenuItemActionPerformed(evt);
            }
        });
        EditMenu1.add(RedoMenuItem);
        EditMenu1.add(jSeparator25);

        CutMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        CutMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Cut16.gif"))); // NOI18N
        CutMenuItem1.setMnemonic('t');
        CutMenuItem1.setText("Cut");
        CutMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CutMenuItemActionPerformed(evt);
            }
        });
        EditMenu1.add(CutMenuItem1);

        CopyMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        CopyMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Copy16.gif"))); // NOI18N
        CopyMenuItem1.setMnemonic('c');
        CopyMenuItem1.setText("Copy");
        CopyMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CopyMenuItemActionPerformed(evt);
            }
        });
        EditMenu1.add(CopyMenuItem1);

        PasteMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        PasteMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Paste16.gif"))); // NOI18N
        PasteMenuItem1.setMnemonic('p');
        PasteMenuItem1.setText("Paste");
        PasteMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PasteMenuItemActionPerformed(evt);
            }
        });
        EditMenu1.add(PasteMenuItem1);
        EditMenu1.add(jSeparator17);

        SelectAllMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        SelectAllMenuItem1.setMnemonic('a');
        SelectAllMenuItem1.setText("Select All");
        SelectAllMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelectAllMenuItemActionPerformed(evt);
            }
        });
        EditMenu1.add(SelectAllMenuItem1);
        EditMenu1.add(jSeparator18);

        ClearCellsMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_BACK_SPACE, 0));
        ClearCellsMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Remove16.gif"))); // NOI18N
        ClearCellsMenuItem1.setMnemonic('l');
        ClearCellsMenuItem1.setText("Clear Cells");
        ClearCellsMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearCellsMenuItemActionPerformed(evt);
            }
        });
        EditMenu1.add(ClearCellsMenuItem1);

        DeleteCellsMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
        DeleteCellsMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Delete16.gif"))); // NOI18N
        DeleteCellsMenuItem1.setMnemonic('d');
        DeleteCellsMenuItem1.setText("Delete Cells");
        DeleteCellsMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteCellsMenuItemActionPerformed(evt);
            }
        });
        EditMenu1.add(DeleteCellsMenuItem1);
        EditMenu1.add(jSeparator19);

        InsertRowMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/table/RowInsertBefore16.gif"))); // NOI18N
        InsertRowMenu1.setMnemonic('r');
        InsertRowMenu1.setText("Insert Row");

        InsertRowAboveMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/table/RowInsertBefore16.gif"))); // NOI18N
        InsertRowAboveMenuItem1.setMnemonic('a');
        InsertRowAboveMenuItem1.setText("Above");
        InsertRowAboveMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InsertRowAboveMenuItemActionPerformed(evt);
            }
        });
        InsertRowMenu1.add(InsertRowAboveMenuItem1);

        InsertRowBelowMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/table/RowInsertAfter16.gif"))); // NOI18N
        InsertRowBelowMenuItem1.setMnemonic('b');
        InsertRowBelowMenuItem1.setText("Below");
        InsertRowBelowMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InsertRowBelowMenuItemActionPerformed(evt);
            }
        });
        InsertRowMenu1.add(InsertRowBelowMenuItem1);

        EditMenu1.add(InsertRowMenu1);

        InsertColumnMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/table/ColumnInsertBefore16.gif"))); // NOI18N
        InsertColumnMenu1.setMnemonic('u');
        InsertColumnMenu1.setText("Insert Column");

        InsertColumnLeftMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/table/ColumnInsertBefore16.gif"))); // NOI18N
        InsertColumnLeftMenuItem1.setMnemonic('l');
        InsertColumnLeftMenuItem1.setText("To the Left");
        InsertColumnLeftMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InsertColumnLeftMenuItemActionPerformed(evt);
            }
        });
        InsertColumnMenu1.add(InsertColumnLeftMenuItem1);

        InsertColumnRightMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/table/ColumnInsertAfter16.gif"))); // NOI18N
        InsertColumnRightMenuItem1.setMnemonic('r');
        InsertColumnRightMenuItem1.setText("To the Right");
        InsertColumnRightMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InsertColumnRightMenuItemActionPerformed(evt);
            }
        });
        InsertColumnMenu1.add(InsertColumnRightMenuItem1);

        EditMenu1.add(InsertColumnMenu1);

        InsertCellMenu1.setMnemonic('i');
        InsertCellMenu1.setText("Insert Cell");

        InsertCellAboveMenuItem1.setMnemonic('a');
        InsertCellAboveMenuItem1.setText("Above");
        InsertCellAboveMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InsertCellAboveMenuItemActionPerformed(evt);
            }
        });
        InsertCellMenu1.add(InsertCellAboveMenuItem1);

        InsertCellBelowMenuItem1.setMnemonic('b');
        InsertCellBelowMenuItem1.setText("Below");
        InsertCellBelowMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InsertCellBelowMenuItemActionPerformed(evt);
            }
        });
        InsertCellMenu1.add(InsertCellBelowMenuItem1);

        EditMenu1.add(InsertCellMenu1);

        AddRowColMenuItem1.setMnemonic('m');
        AddRowColMenuItem1.setText("Add Multiple Rows/Columns");
        AddRowColMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddRowColMenuItemActionPerformed(evt);
            }
        });
        EditMenu1.add(AddRowColMenuItem1);
        EditMenu1.add(jSeparator1);

        EditLastDialogMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        EditLastDialogMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Edit16.gif"))); // NOI18N
        EditLastDialogMenuItem.setMnemonic('l');
        EditLastDialogMenuItem.setText("Edit Last Dialog");
        EditLastDialogMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditLastDialogMenuItemActionPerformed(evt);
            }
        });
        EditMenu1.add(EditLastDialogMenuItem);

        HistoryCheckBoxMenuItem.setMnemonic('s');
        HistoryCheckBoxMenuItem.setSelected(true);
        HistoryCheckBoxMenuItem.setText("Show Dialog History");
        HistoryCheckBoxMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/History16.gif"))); // NOI18N
        HistoryCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HistoryCheckBoxMenuItemActionPerformed(evt);
            }
        });
        EditMenu1.add(HistoryCheckBoxMenuItem);

        MainMenuBar1.add(EditMenu1);

        DataMenu1.setMnemonic('d');
        DataMenu1.setText("Data");

        SortMenuItem1.setMnemonic('s');
        SortMenuItem1.setText("Sort...");
        SortMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SortMenuItemActionPerformed(evt);
            }
        });
        DataMenu1.add(SortMenuItem1);

        RankMenuItem1.setMnemonic('r');
        RankMenuItem1.setText("Rank...");
        RankMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RankMenuItemActionPerformed(evt);
            }
        });
        DataMenu1.add(RankMenuItem1);

        TransposeMenuItem1.setMnemonic('t');
        TransposeMenuItem1.setText("Transpose...");
        TransposeMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TransposeMenuItem1ActionPerformed(evt);
            }
        });
        DataMenu1.add(TransposeMenuItem1);
        DataMenu1.add(jSeparator20);

        DisplayDataMenuItem1.setMnemonic('d');
        DisplayDataMenuItem1.setText("Display Data in Session Window...");
        DisplayDataMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DisplayDataMenuItemActionPerformed(evt);
            }
        });
        DataMenu1.add(DisplayDataMenuItem1);
        DataMenu1.add(jSeparator21);

        StandardizeMenuItem1.setMnemonic('t');
        StandardizeMenuItem1.setText("Standardize...");
        StandardizeMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StandardizeMenuItemActionPerformed(evt);
            }
        });
        DataMenu1.add(StandardizeMenuItem1);

        GeneratePatternedDataMenu1.setMnemonic('g');
        GeneratePatternedDataMenu1.setText("Generate Patterned Data...");

        NumberSeqMenuItem1.setMnemonic('n');
        NumberSeqMenuItem1.setText("Simple Number Sequence...");
        NumberSeqMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NumberSeqMenuItemActionPerformed(evt);
            }
        });
        GeneratePatternedDataMenu1.add(NumberSeqMenuItem1);

        DataSeqMenuItem1.setMnemonic('a');
        DataSeqMenuItem1.setText("Arbitrary Data  Sequence...");
        DataSeqMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DataSeqMenuItemActionPerformed(evt);
            }
        });
        GeneratePatternedDataMenu1.add(DataSeqMenuItem1);

        DataMenu1.add(GeneratePatternedDataMenu1);

        GenerateRandomDataMenu.setMnemonic('e');
        GenerateRandomDataMenu.setText("Generate Random Data...");

        BinomialSamplesMenuItem.setMnemonic('b');
        BinomialSamplesMenuItem.setText("Binomial...");
        BinomialSamplesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BinomialSamplesMenuItemActionPerformed(evt);
            }
        });
        GenerateRandomDataMenu.add(BinomialSamplesMenuItem);

        IntegerSamplesMenuItem.setMnemonic('i');
        IntegerSamplesMenuItem.setText("Integer...");
        IntegerSamplesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IntegerSamplesMenuItemActionPerformed(evt);
            }
        });
        GenerateRandomDataMenu.add(IntegerSamplesMenuItem);
        GenerateRandomDataMenu.add(jSeparator24);

        NormalSamplesMenuItem.setMnemonic('n');
        NormalSamplesMenuItem.setText("Normal...");
        NormalSamplesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NormalSamplesMenuItemActionPerformed(evt);
            }
        });
        GenerateRandomDataMenu.add(NormalSamplesMenuItem);

        UniformSamplesMenuItem.setMnemonic('u');
        UniformSamplesMenuItem.setText("Uniform...");
        UniformSamplesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UniformSamplesMenuItemActionPerformed(evt);
            }
        });
        GenerateRandomDataMenu.add(UniformSamplesMenuItem);
        GenerateRandomDataMenu.add(jSeparator23);

        SampleFromColumnMenuItem.setMnemonic('s');
        SampleFromColumnMenuItem.setText("Samples from Column...");
        SampleFromColumnMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SampleFromColumnMenuItemActionPerformed(evt);
            }
        });
        GenerateRandomDataMenu.add(SampleFromColumnMenuItem);

        DataMenu1.add(GenerateRandomDataMenu);

        MainMenuBar1.add(DataMenu1);

        CalcMenu1.setMnemonic('c');
        CalcMenu1.setText("Calculate");

        CalculatorMenuItem.setMnemonic('c');
        CalculatorMenuItem.setText("Calculator...");
        CalculatorMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CalculatorMenuItemActionPerformed(evt);
            }
        });
        CalcMenu1.add(CalculatorMenuItem);

        ProbDistMenu1.setMnemonic('p');
        ProbDistMenu1.setText("Probability Distributions");

        NormalMenuItem1.setMnemonic('n');
        NormalMenuItem1.setText("Normal...");
        NormalMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NormalMenuItemActionPerformed(evt);
            }
        });
        ProbDistMenu1.add(NormalMenuItem1);

        StudentTMenuItem1.setMnemonic('s');
        StudentTMenuItem1.setText("Student's t...");
        StudentTMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StudentTMenuItemActionPerformed(evt);
            }
        });
        ProbDistMenu1.add(StudentTMenuItem1);

        ChiSquareMenuItem2.setMnemonic('c');
        ChiSquareMenuItem2.setText("Chi-Square...");
        ChiSquareMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChiSquareMenuItem1ActionPerformed(evt);
            }
        });
        ProbDistMenu1.add(ChiSquareMenuItem2);

        FMenuItem1.setMnemonic('f');
        FMenuItem1.setText("F...");
        FMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FMenuItemActionPerformed(evt);
            }
        });
        ProbDistMenu1.add(FMenuItem1);

        ExponentialMenuItem1.setText("Exponential...");
        ExponentialMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExponentialMenuItem1ActionPerformed(evt);
            }
        });
        ProbDistMenu1.add(ExponentialMenuItem1);

        UniformMenuItem1.setMnemonic('u');
        UniformMenuItem1.setText("Uniform...");
        UniformMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UniformMenuItemActionPerformed(evt);
            }
        });
        ProbDistMenu1.add(UniformMenuItem1);
        ProbDistMenu1.add(jSeparator22);

        BinomialMenuItem1.setMnemonic('b');
        BinomialMenuItem1.setText("Binomial...");
        BinomialMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BinomialMenuItemActionPerformed(evt);
            }
        });
        ProbDistMenu1.add(BinomialMenuItem1);

        DiscreteMenuItem1.setMnemonic('d');
        DiscreteMenuItem1.setText("Discrete...");
        DiscreteMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DiscreteMenuItemActionPerformed(evt);
            }
        });
        ProbDistMenu1.add(DiscreteMenuItem1);

        GeometricMenuItem1.setMnemonic('g');
        GeometricMenuItem1.setText("Geometric...");
        GeometricMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GeometricMenuItemActionPerformed(evt);
            }
        });
        ProbDistMenu1.add(GeometricMenuItem1);

        IntegerMenuItem1.setMnemonic('i');
        IntegerMenuItem1.setText("Integer...");
        IntegerMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IntegerMenuItemActionPerformed(evt);
            }
        });
        ProbDistMenu1.add(IntegerMenuItem1);

        PoissonMenuItem1.setMnemonic('p');
        PoissonMenuItem1.setText("Poisson...");
        PoissonMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PoissonMenuItemActionPerformed(evt);
            }
        });
        ProbDistMenu1.add(PoissonMenuItem1);

        CalcMenu1.add(ProbDistMenu1);

        PvalueMenuItem1.setMnemonic('v');
        PvalueMenuItem1.setText("p-Value...");
        PvalueMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PvalueMenuItemActionPerformed(evt);
            }
        });
        CalcMenu1.add(PvalueMenuItem1);

        FreqTableMenuItem.setMnemonic('f');
        FreqTableMenuItem.setText("Frequency Table...");
        FreqTableMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FreqTableMenuItemActionPerformed(evt);
            }
        });
        CalcMenu1.add(FreqTableMenuItem);

        MainMenuBar1.add(CalcMenu1);

        StatMenu1.setMnemonic('s');
        StatMenu1.setText("Statistics");

        BasicStatisticsMenu1.setMnemonic('b');
        BasicStatisticsMenu1.setText("Basic Statistics");

        DescriptiveStatMenuItem1.setMnemonic('d');
        DescriptiveStatMenuItem1.setText("Descriptive Statistics...");
        DescriptiveStatMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DescriptiveStatMenuItemActionPerformed(evt);
            }
        });
        BasicStatisticsMenu1.add(DescriptiveStatMenuItem1);

        ColStatMenuItem1.setMnemonic('c');
        ColStatMenuItem1.setText("Column Statistics...");
        ColStatMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ColStatMenuItemActionPerformed(evt);
            }
        });
        BasicStatisticsMenu1.add(ColStatMenuItem1);

        RowStatMenuItem1.setMnemonic('r');
        RowStatMenuItem1.setText("Row Statistics...");
        RowStatMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RowStatMenuItemActionPerformed(evt);
            }
        });
        BasicStatisticsMenu1.add(RowStatMenuItem1);

        NormalityTestMenuItem.setMnemonic('n');
        NormalityTestMenuItem.setText("Normality Test...");
        NormalityTestMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NormalityTestMenuItemActionPerformed(evt);
            }
        });
        BasicStatisticsMenu1.add(NormalityTestMenuItem);

        StatMenu1.add(BasicStatisticsMenu1);

        SampleSizeMenu1.setMnemonic('s');
        SampleSizeMenu1.setText("Sample Size");

        MeanMenuItem1.setMnemonic('m');
        MeanMenuItem1.setText("1-Population Mean...");
        MeanMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MeanMenuItemActionPerformed(evt);
            }
        });
        SampleSizeMenu1.add(MeanMenuItem1);

        PropMenuItem1.setMnemonic('p');
        PropMenuItem1.setText("1-Population Proportion...");
        PropMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PropMenuItemActionPerformed(evt);
            }
        });
        SampleSizeMenu1.add(PropMenuItem1);

        StatMenu1.add(SampleSizeMenu1);

        TestAndCIMenu1.setMnemonic('c');
        TestAndCIMenu1.setText("Confidence Intervals");

        OnePopMeanMenuItem1.setMnemonic('m');
        OnePopMeanMenuItem1.setText("1-Population Mean...");
        OnePopMeanMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OnePopMeanMenuItemActionPerformed(evt);
            }
        });
        TestAndCIMenu1.add(OnePopMeanMenuItem1);

        OnePopPropMenuItem1.setMnemonic('p');
        OnePopPropMenuItem1.setText("1-Population Proportion...");
        OnePopPropMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OnePopPropMenuItemActionPerformed(evt);
            }
        });
        TestAndCIMenu1.add(OnePopPropMenuItem1);

        OnePopVarMenuItem1.setMnemonic('v');
        OnePopVarMenuItem1.setText("1-Population Variance...");
        OnePopVarMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OnePopVarMenuItemActionPerformed(evt);
            }
        });
        TestAndCIMenu1.add(OnePopVarMenuItem1);

        TwoPopMeanMenuItem1.setMnemonic('e');
        TwoPopMeanMenuItem1.setText("2-Population Means...");
        TwoPopMeanMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TwoPopMeanMenuItemActionPerformed(evt);
            }
        });
        TestAndCIMenu1.add(TwoPopMeanMenuItem1);

        TwoPopPropMenuItem1.setMnemonic('r');
        TwoPopPropMenuItem1.setText("2-Population Proportions...");
        TwoPopPropMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TwoPopPropMenuItemActionPerformed(evt);
            }
        });
        TestAndCIMenu1.add(TwoPopPropMenuItem1);

        TwoPopVarMenuItem1.setMnemonic('a');
        TwoPopVarMenuItem1.setText("2-Population Variances...");
        TwoPopVarMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TwoPopVarMenuItemActionPerformed(evt);
            }
        });
        TestAndCIMenu1.add(TwoPopVarMenuItem1);

        MatchedPairsMenuItem1.setMnemonic('t');
        MatchedPairsMenuItem1.setText("Matched Pairs...");
        MatchedPairsMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MatchedPairsMenuItemActionPerformed(evt);
            }
        });
        TestAndCIMenu1.add(MatchedPairsMenuItem1);

        StatMenu1.add(TestAndCIMenu1);

        HyTestMenu1.setMnemonic('h');
        HyTestMenu1.setText("Hypothesis Tests");

        HyTestMeanMenuItem1.setMnemonic('m');
        HyTestMeanMenuItem1.setText("1-Population Mean...");
        HyTestMeanMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HyTestMeanMenuItemActionPerformed(evt);
            }
        });
        HyTestMenu1.add(HyTestMeanMenuItem1);

        HyTestPropMenuItem1.setMnemonic('p');
        HyTestPropMenuItem1.setText("1-Population Proportion...");
        HyTestPropMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HyTestPropMenuItemActionPerformed(evt);
            }
        });
        HyTestMenu1.add(HyTestPropMenuItem1);

        HyTestVarMenuItem1.setMnemonic('v');
        HyTestVarMenuItem1.setText("1-Population Variance...");
        HyTestVarMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HyTestVarMenuItemActionPerformed(evt);
            }
        });
        HyTestMenu1.add(HyTestVarMenuItem1);

        HyTest2MeanMenuItem1.setMnemonic('e');
        HyTest2MeanMenuItem1.setText("2-Population Means");
        HyTest2MeanMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HyTest2MeanMenuItemActionPerformed(evt);
            }
        });
        HyTestMenu1.add(HyTest2MeanMenuItem1);

        HyTest2PropMenuItem1.setMnemonic('r');
        HyTest2PropMenuItem1.setText("2-Population Proportions");
        HyTest2PropMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HyTest2PropMenuItemActionPerformed(evt);
            }
        });
        HyTestMenu1.add(HyTest2PropMenuItem1);

        HyTest2VarMenuItem1.setMnemonic('a');
        HyTest2VarMenuItem1.setText("2-Population Variances");
        HyTest2VarMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HyTest2VarMenuItemActionPerformed(evt);
            }
        });
        HyTestMenu1.add(HyTest2VarMenuItem1);

        HyTestMatchedPairsMenuItem1.setMnemonic('t');
        HyTestMatchedPairsMenuItem1.setText("Matched Pairs...");
        HyTestMatchedPairsMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HyTestMatchedPairsMenuItemActionPerformed(evt);
            }
        });
        HyTestMenu1.add(HyTestMatchedPairsMenuItem1);

        ConclusionMenuItem.setMnemonic('h');
        ConclusionMenuItem.setText("Hypothesis Test Conclusion Tool...");
        ConclusionMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConclusionMenuItemActionPerformed(evt);
            }
        });
        HyTestMenu1.add(ConclusionMenuItem);

        StatMenu1.add(HyTestMenu1);

        CorrRegMenu1.setMnemonic('r');
        CorrRegMenu1.setText("Correlation and Regression");

        TwoVarMenuItem1.setMnemonic('l');
        TwoVarMenuItem1.setText("Linear (Two Variables)...");
        TwoVarMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TwoVarMenuItemActionPerformed(evt);
            }
        });
        CorrRegMenu1.add(TwoVarMenuItem1);

        TwoVarAllPairsMenuItem1.setMnemonic('a');
        TwoVarAllPairsMenuItem1.setText("Correlation Matrix");
        TwoVarAllPairsMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TwoVarAllPairsMenuItem1ActionPerformed(evt);
            }
        });
        CorrRegMenu1.add(TwoVarAllPairsMenuItem1);

        MultipleMenuItem1.setMnemonic('m');
        MultipleMenuItem1.setText("Multiple Regression...");
        MultipleMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MultipleMenuItemActionPerformed(evt);
            }
        });
        CorrRegMenu1.add(MultipleMenuItem1);

        NonLinMenuItem.setMnemonic('n');
        NonLinMenuItem.setText("Non-Linear Models...");
        NonLinMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NonLinMenuItemActionPerformed(evt);
            }
        });
        CorrRegMenu1.add(NonLinMenuItem);

        RankCorrelationMenuItem2.setMnemonic('s');
        RankCorrelationMenuItem2.setText("Spearman's Rank Correlation...");
        RankCorrelationMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RankCorrelationMenuItem2ActionPerformed(evt);
            }
        });
        CorrRegMenu1.add(RankCorrelationMenuItem2);

        StatMenu1.add(CorrRegMenu1);

        MultinomialMenu1.setMnemonic('m');
        MultinomialMenu1.setText("Multinomial Experiments");

        GoodnessOfFitMenuItem1.setMnemonic('g');
        GoodnessOfFitMenuItem1.setText("Chi-Square Goodness-of-Fit...");
        GoodnessOfFitMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GoodnessOfFitMenuItemActionPerformed(evt);
            }
        });
        MultinomialMenu1.add(GoodnessOfFitMenuItem1);

        ContingencyTableMenuItem1.setMnemonic('c');
        ContingencyTableMenuItem1.setText("Chi-Square Contingency Table...");
        ContingencyTableMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ContingencyTableMenuItemActionPerformed(evt);
            }
        });
        MultinomialMenu1.add(ContingencyTableMenuItem1);

        CrossTabMenuItem.setMnemonic('t');
        CrossTabMenuItem.setText("Cross Tabulation and Chi-Square...");
        CrossTabMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CrossTabMenuItemActionPerformed(evt);
            }
        });
        MultinomialMenu1.add(CrossTabMenuItem);

        StatMenu1.add(MultinomialMenu1);

        ANOVAMenu1.setMnemonic('a');
        ANOVAMenu1.setText("Analysis of Variance");

        OneANOVAMenuItem1.setMnemonic('o');
        OneANOVAMenuItem1.setText("One-Way ANOVA...");
        OneANOVAMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OneANOVAMenuItemActionPerformed(evt);
            }
        });
        ANOVAMenu1.add(OneANOVAMenuItem1);

        TwoANOVAMenuItem.setText("Two-Way ANOVA...");
        TwoANOVAMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TwoANOVAMenuItemActionPerformed(evt);
            }
        });
        ANOVAMenu1.add(TwoANOVAMenuItem);

        StatMenu1.add(ANOVAMenu1);

        NonparamMenu.setMnemonic('n');
        NonparamMenu.setText("Nonparametrics");

        SignTestMenuItem.setMnemonic('s');
        SignTestMenuItem.setText("1-Sample Sign Test...");
        SignTestMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SignTestMenuItemActionPerformed(evt);
            }
        });
        NonparamMenu.add(SignTestMenuItem);

        MatchedPairSignTestMenuItem.setMnemonic('m');
        MatchedPairSignTestMenuItem.setText("2-Sample Matched-Pair Sign Test...");
        MatchedPairSignTestMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MatchedPairSignTestMenuItemActionPerformed(evt);
            }
        });
        NonparamMenu.add(MatchedPairSignTestMenuItem);

        WilcoxonSignedRankMenuItem.setMnemonic('w');
        WilcoxonSignedRankMenuItem.setText("Wilcoxon Signed Rank Test...");
        WilcoxonSignedRankMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WilcoxonSignedRankMenuItemActionPerformed(evt);
            }
        });
        NonparamMenu.add(WilcoxonSignedRankMenuItem);

        MannWhitneyTestMenuItem.setMnemonic('i');
        MannWhitneyTestMenuItem.setText("Wilcoxon Rank Sum / Mann-Whitney Test...");
        MannWhitneyTestMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MannWhitneyTestMenuItemActionPerformed(evt);
            }
        });
        NonparamMenu.add(MannWhitneyTestMenuItem);

        KruskalWallisTestMenuItem.setMnemonic('k');
        KruskalWallisTestMenuItem.setText("Kruskal-Wallis Test...");
        KruskalWallisTestMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KruskalWallisTestMenuItemActionPerformed(evt);
            }
        });
        NonparamMenu.add(KruskalWallisTestMenuItem);

        RankCorrelationMenuItem.setMnemonic('r');
        RankCorrelationMenuItem.setText("Spearman's Rank Correlation...");
        RankCorrelationMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RankCorrelationMenuItemActionPerformed(evt);
            }
        });
        NonparamMenu.add(RankCorrelationMenuItem);

        RunsTestMenuItem.setMnemonic('u');
        RunsTestMenuItem.setText("Runs Test...");
        RunsTestMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RunsTestMenuItemActionPerformed(evt);
            }
        });
        NonparamMenu.add(RunsTestMenuItem);

        StatMenu1.add(NonparamMenu);

        MainMenuBar1.add(StatMenu1);

        GraphMenu1.setMnemonic('g');
        GraphMenu1.setText("Graph");

        BarChartMenuItem1.setMnemonic('b');
        BarChartMenuItem1.setText("Bar Chart...");
        BarChartMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BarChartMenuItemActionPerformed(evt);
            }
        });
        GraphMenu1.add(BarChartMenuItem1);

        BoxPlotMenuItem1.setMnemonic('o');
        BoxPlotMenuItem1.setText("Box Plot...");
        BoxPlotMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoxPlotMenuItemActionPerformed(evt);
            }
        });
        GraphMenu1.add(BoxPlotMenuItem1);

        dotPlotMenuItem.setMnemonic('d');
        dotPlotMenuItem.setText("Dot Plot...");
        dotPlotMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dotPlotMenuItemActionPerformed(evt);
            }
        });
        GraphMenu1.add(dotPlotMenuItem);

        HistogramMenuItem1.setMnemonic('h');
        HistogramMenuItem1.setText("Histogram...");
        HistogramMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HistogramMenuItemActionPerformed(evt);
            }
        });
        GraphMenu1.add(HistogramMenuItem1);

        NQPMenuItem1.setMnemonic('n');
        NQPMenuItem1.setText("Normal Quantile Plot...");
        NQPMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NQPMenuItemActionPerformed(evt);
            }
        });
        GraphMenu1.add(NQPMenuItem1);

        PieChartMenuItem1.setMnemonic('p');
        PieChartMenuItem1.setText("Pie Chart...");
        PieChartMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PieChartMenuItemActionPerformed(evt);
            }
        });
        GraphMenu1.add(PieChartMenuItem1);

        ScatterplotMenuItem1.setMnemonic('s');
        ScatterplotMenuItem1.setText("Scatterplot...");
        ScatterplotMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ScatterplotMenuItemActionPerformed(evt);
            }
        });
        GraphMenu1.add(ScatterplotMenuItem1);

        StemLeafPlotMenuItem.setMnemonic('t');
        StemLeafPlotMenuItem.setText("Stem-and-Leaf Plot...");
        StemLeafPlotMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StemLeafPlotMenuItemActionPerformed(evt);
            }
        });
        GraphMenu1.add(StemLeafPlotMenuItem);

        ResidualPlotMenuItem.setMnemonic('r');
        ResidualPlotMenuItem.setText("Residual Plot...");
        ResidualPlotMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResidualPlotMenuItemActionPerformed(evt);
            }
        });
        GraphMenu1.add(ResidualPlotMenuItem);

        MainMenuBar1.add(GraphMenu1);

        WindowMenu.setMnemonic('w');
        WindowMenu.setText("Window");

        LogMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/statcato/log.gif"))); // NOI18N
        LogMenuItem.setText("Log");
        LogMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogMenuItemActionPerformed(evt);
            }
        });
        WindowMenu.add(LogMenuItem);

        DatasheetMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/statcato/datasheet.gif"))); // NOI18N
        DatasheetMenuItem.setText("Datasheet");
        DatasheetMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DatasheetMenuItemActionPerformed(evt);
            }
        });
        WindowMenu.add(DatasheetMenuItem);

        DialogHistoryMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/History16.gif"))); // NOI18N
        DialogHistoryMenuItem.setText("Dialog History");
        DialogHistoryMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DialogHistoryMenuItemActionPerformed(evt);
            }
        });
        WindowMenu.add(DialogHistoryMenuItem);
        WindowMenu.add(jSeparator3);

        MainMenuBar1.add(WindowMenu);

        HelpMenu1.setMnemonic('h');
        HelpMenu1.setText("Help");

        HelpMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        HelpMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Help16.gif"))); // NOI18N
        HelpMenuItem1.setMnemonic('h');
        HelpMenuItem1.setText("Help...");
        HelpMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HelpMenuItemActionPerformed(evt);
            }
        });
        HelpMenu1.add(HelpMenuItem1);

        AboutMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        AboutMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/About16.gif"))); // NOI18N
        AboutMenuItem1.setMnemonic('a');
        AboutMenuItem1.setText("About...");
        AboutMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AboutMenuItemActionPerformed(evt);
            }
        });
        HelpMenu1.add(AboutMenuItem1);

        CheckUpdatesMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Search16.gif"))); // NOI18N
        CheckUpdatesMenuItem.setMnemonic('u');
        CheckUpdatesMenuItem.setText("Check for Updates...");
        CheckUpdatesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckUpdatesMenuItemActionPerformed(evt);
            }
        });
        HelpMenu1.add(CheckUpdatesMenuItem);

        MainMenuBar1.add(HelpMenu1);

        setJMenuBar(MainMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(StatcatoDesktopPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(StatcatoDesktopPane)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SaveLogMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveLogMenuItemActionPerformed
        File file = LogTextPane.writeToFile(this, false);
        if (file != null) {
            LogInternalFrame.setTitle("Log - " + file.getName());
            setStatus("Log saved to " + file.getName() + ".");
            setClearStatusTimer(3);
        }
    }//GEN-LAST:event_SaveLogMenuItemActionPerformed

    private void SaveDatasheetMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveDatasheetMenuItemActionPerformed
        FileOperations.saveDatasheet(this, false);
    }//GEN-LAST:event_SaveDatasheetMenuItemActionPerformed

    private void PrintLogMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrintLogMenuItemActionPerformed
        // Print log window
        displayDialog(printLogDialog);
    }//GEN-LAST:event_PrintLogMenuItemActionPerformed

    private void PrintDatasheetMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrintDatasheetMenuItemActionPerformed
        // Print Datasheet (table)
        displayDialog(printDatasheetDialog);
    }//GEN-LAST:event_PrintDatasheetMenuItemActionPerformed

    private void SortMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SortMenuItemActionPerformed
        displayDialog(sortDialog);
    }//GEN-LAST:event_SortMenuItemActionPerformed

    private void ColStatMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ColStatMenuItemActionPerformed
        displayDialog(columnStatisticsDialog);
    }//GEN-LAST:event_ColStatMenuItemActionPerformed

    private void RowStatMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RowStatMenuItemActionPerformed
        displayDialog(rowStatisticsDialog);
    }//GEN-LAST:event_RowStatMenuItemActionPerformed

    private void AboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AboutMenuItemActionPerformed
        aboutDialog.setLocation(0,0);
        displayDialog(aboutDialog);
    }//GEN-LAST:event_AboutMenuItemActionPerformed

    private void OpenLogMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OpenLogMenuItemActionPerformed
        FileOperations.openLogFile(this);
    }//GEN-LAST:event_OpenLogMenuItemActionPerformed

    private void OpenDatasheetMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OpenDatasheetMenuItemActionPerformed
        FileOperations.openDatasheet(this);
        clearUndoManager();
    }//GEN-LAST:event_OpenDatasheetMenuItemActionPerformed

    private void ExitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitMenuItemActionPerformed
        closeApplication();
    }//GEN-LAST:event_ExitMenuItemActionPerformed

    private void SaveDatasheetAsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveDatasheetAsMenuItemActionPerformed
        FileOperations.saveDatasheet(this, true);
}//GEN-LAST:event_SaveDatasheetAsMenuItemActionPerformed

    private void NewDatasheetMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewDatasheetMenuItemActionPerformed
        // add new Datasheet
        DatasheetPane.addDatasheet();
        setStatus("New datasheet added.");
        setClearStatusTimer(3);
    }//GEN-LAST:event_NewDatasheetMenuItemActionPerformed

    private void ClearLogMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearLogMenuItemActionPerformed
        // clear log window
        LogTextPane.clear();
        setStatus("Log cleared.");
        setClearStatusTimer(3);
    }//GEN-LAST:event_ClearLogMenuItemActionPerformed

    private void SaveLogAsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveLogAsMenuItemActionPerformed
        // save as for sesion window
        File file = LogTextPane.writeToFile(this, true);
        if (file != null) {
            LogInternalFrame.setTitle("Log - " + file.getName());
            setStatus("Log saved to " + file.getName());
            setClearStatusTimer(3);
        }        
    }//GEN-LAST:event_SaveLogAsMenuItemActionPerformed

    private void CloseDatasheetMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseDatasheetMenuItemActionPerformed
        // close current Datasheet
        DatasheetPane.closeCurrentDatasheet();
        clearUndoManager();
    }//GEN-LAST:event_CloseDatasheetMenuItemActionPerformed

    private void DescriptiveStatMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DescriptiveStatMenuItemActionPerformed
        displayDialog(descriptiveStatisticsDialog);       
    }//GEN-LAST:event_DescriptiveStatMenuItemActionPerformed

    private void DisplayDataMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DisplayDataMenuItemActionPerformed
        displayDialog(displayDataDialog);       
    }//GEN-LAST:event_DisplayDataMenuItemActionPerformed

    private void StandardizeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StandardizeMenuItemActionPerformed
        displayDialog(standardizeDialog); 
}//GEN-LAST:event_StandardizeMenuItemActionPerformed

    private void HelpMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HelpMenuItemActionPerformed
        
}//GEN-LAST:event_HelpMenuItemActionPerformed

    private void NumberSeqMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NumberSeqMenuItemActionPerformed
        displayDialog(simpleNumberPatternDialog); 
    }//GEN-LAST:event_NumberSeqMenuItemActionPerformed

    private void DataSeqMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DataSeqMenuItemActionPerformed
        displayDialog(arbitraryDataPatternDialog);
    }//GEN-LAST:event_DataSeqMenuItemActionPerformed

    private void PvalueMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PvalueMenuItemActionPerformed
        // bring up p-value dialog
        displayDialog(pvalueDialog);
}//GEN-LAST:event_PvalueMenuItemActionPerformed

    private void RankMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RankMenuItemActionPerformed
        displayDialog(rankDialog);
    }//GEN-LAST:event_RankMenuItemActionPerformed

    private void ClearCellsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearCellsMenuItemActionPerformed
        // clear selected cells
        getSpreadsheet().clearSelectedCells();
}//GEN-LAST:event_ClearCellsMenuItemActionPerformed

    private void DeleteCellsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteCellsMenuItemActionPerformed
        getSpreadsheet().deleteSelectedCells();
}//GEN-LAST:event_DeleteCellsMenuItemActionPerformed

    private void InsertRowAboveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InsertRowAboveMenuItemActionPerformed
        // insert row above current row
        getSpreadsheet().insertRowAbove();
    }//GEN-LAST:event_InsertRowAboveMenuItemActionPerformed

    private void InsertRowBelowMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InsertRowBelowMenuItemActionPerformed
        // insert row below current row
        getSpreadsheet().insertRowBelow();
    }//GEN-LAST:event_InsertRowBelowMenuItemActionPerformed

    private void InsertColumnLeftMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InsertColumnLeftMenuItemActionPerformed
        // insert column to the left of the current column
        getSpreadsheet().insertColumnLeft();
    }//GEN-LAST:event_InsertColumnLeftMenuItemActionPerformed

    private void InsertColumnRightMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InsertColumnRightMenuItemActionPerformed
        // insert column to the right of the current column
        getSpreadsheet().insertColumnRight();
    }//GEN-LAST:event_InsertColumnRightMenuItemActionPerformed

    private void InsertCellAboveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InsertCellAboveMenuItemActionPerformed
        // insert cell above the current cell
        getSpreadsheet().insertCellAbove();
        
    }//GEN-LAST:event_InsertCellAboveMenuItemActionPerformed

    private void InsertCellBelowMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InsertCellBelowMenuItemActionPerformed
        // insert cell celow the current cell
        getSpreadsheet().insertCellBelow();
    }//GEN-LAST:event_InsertCellBelowMenuItemActionPerformed

    private void SelectAllMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectAllMenuItemActionPerformed
        if (DatasheetInternalFrame.isSelected()) {
            // select all cells in spreadsheet
            getSpreadsheet().selectAllCells();
        }
        else {
            LogTextPane.getActionMap().get(DefaultEditorKit.selectAllAction).actionPerformed(evt);
        }
    }//GEN-LAST:event_SelectAllMenuItemActionPerformed

    private void NormalMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NormalMenuItemActionPerformed
        displayDialog(normalDistributionDialog);      
}//GEN-LAST:event_NormalMenuItemActionPerformed

    private void PieChartMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PieChartMenuItemActionPerformed
        displayDialog(pieChartDialog);         
}//GEN-LAST:event_PieChartMenuItemActionPerformed

    private void BoxPlotMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoxPlotMenuItemActionPerformed
        displayDialog(boxPlotDialog);
}//GEN-LAST:event_BoxPlotMenuItemActionPerformed

    private void StudentTMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StudentTMenuItemActionPerformed
        displayDialog(studentTDistributionDialog);  
}//GEN-LAST:event_StudentTMenuItemActionPerformed

    private void ChiSquareMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChiSquareMenuItem1ActionPerformed
        displayDialog(chiSquareDistributionDialog);
}//GEN-LAST:event_ChiSquareMenuItem1ActionPerformed

    private void FMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FMenuItemActionPerformed
        displayDialog(fDistributionDialog);
}//GEN-LAST:event_FMenuItemActionPerformed

    private void UniformMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UniformMenuItemActionPerformed
        displayDialog(uniformDistributionDialog);
}//GEN-LAST:event_UniformMenuItemActionPerformed

    private void BinomialMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BinomialMenuItemActionPerformed
        displayDialog(binomialDistributionDialog);
    }//GEN-LAST:event_BinomialMenuItemActionPerformed

    private void PoissonMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PoissonMenuItemActionPerformed
        displayDialog(poissonDistributionDialog); 
    }//GEN-LAST:event_PoissonMenuItemActionPerformed

    private void GeometricMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GeometricMenuItemActionPerformed
        displayDialog(geometricDistributionDialog);
    }//GEN-LAST:event_GeometricMenuItemActionPerformed

    private void IntegerMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IntegerMenuItemActionPerformed
        displayDialog(integerDistributionDialog);
}//GEN-LAST:event_IntegerMenuItemActionPerformed

    private void DiscreteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DiscreteMenuItemActionPerformed
        displayDialog(discreteDistributionDialog);
}//GEN-LAST:event_DiscreteMenuItemActionPerformed

    private void OnePopMeanMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OnePopMeanMenuItemActionPerformed
        displayDialog(cI1PopMeanDialog);
}//GEN-LAST:event_OnePopMeanMenuItemActionPerformed

    private void OnePopPropMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OnePopPropMenuItemActionPerformed
        displayDialog(cI1PopPropDialog);
}//GEN-LAST:event_OnePopPropMenuItemActionPerformed

    private void OnePopVarMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OnePopVarMenuItemActionPerformed
        displayDialog(cI1PopVarDialog);
}//GEN-LAST:event_OnePopVarMenuItemActionPerformed

    private void PropMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PropMenuItemActionPerformed
        displayDialog(sampleSizePropDialog);
    }//GEN-LAST:event_PropMenuItemActionPerformed

    private void MeanMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MeanMenuItemActionPerformed
        displayDialog(sampleSizeMeanDialog);
    }//GEN-LAST:event_MeanMenuItemActionPerformed

    private void HyTestMeanMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HyTestMeanMenuItemActionPerformed
        displayDialog(hyTest1PopMeanDialog);
}//GEN-LAST:event_HyTestMeanMenuItemActionPerformed

    private void HyTestPropMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HyTestPropMenuItemActionPerformed
        displayDialog(hyTest1PopPropDialog);
    }//GEN-LAST:event_HyTestPropMenuItemActionPerformed

    private void HyTestVarMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HyTestVarMenuItemActionPerformed
        displayDialog(hyTest1PopVarDialog);
    }//GEN-LAST:event_HyTestVarMenuItemActionPerformed

    private void TwoPopPropMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TwoPopPropMenuItemActionPerformed
        displayDialog(cI2PopPropDialog);
    }//GEN-LAST:event_TwoPopPropMenuItemActionPerformed

    private void HyTest2PropMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HyTest2PropMenuItemActionPerformed
        displayDialog(hyTest2PopPropDialog);
}//GEN-LAST:event_HyTest2PropMenuItemActionPerformed

    private void TwoPopMeanMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TwoPopMeanMenuItemActionPerformed
        displayDialog(cI2PopMeanDialog);
    }//GEN-LAST:event_TwoPopMeanMenuItemActionPerformed

    private void HyTest2MeanMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HyTest2MeanMenuItemActionPerformed
        displayDialog(hyTest2PopMeanDialog);
}//GEN-LAST:event_HyTest2MeanMenuItemActionPerformed

    private void MatchedPairsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MatchedPairsMenuItemActionPerformed
        displayDialog(cIMatchedPairsDialog);
    }//GEN-LAST:event_MatchedPairsMenuItemActionPerformed

    private void HyTestMatchedPairsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HyTestMatchedPairsMenuItemActionPerformed
        displayDialog(hyTestMatchedPairsDialog);
}//GEN-LAST:event_HyTestMatchedPairsMenuItemActionPerformed

    private void TwoPopVarMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TwoPopVarMenuItemActionPerformed
        displayDialog(cI2PopVarDialog);
}//GEN-LAST:event_TwoPopVarMenuItemActionPerformed

    private void HyTest2VarMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HyTest2VarMenuItemActionPerformed
        displayDialog(hyTest2PopVarDialog);
}//GEN-LAST:event_HyTest2VarMenuItemActionPerformed

    private void LoadDatasetMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoadDatasetMenuItemActionPerformed
        displayDialog(loadDatasetDialog);       
    }//GEN-LAST:event_LoadDatasetMenuItemActionPerformed

    private void CutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CutMenuItemActionPerformed
        if (DatasheetInternalFrame.isSelected()) {
            KeyStroke keyStroke = KeyStroke.getKeyStroke("control X");
            ActionListener action = getSpreadsheet().getActionForKeyStroke(keyStroke);
            action.actionPerformed(new ActionEvent(getSpreadsheet(), 
                    ActionEvent.ACTION_PERFORMED, "Cut"));
        }
        else {
            (new DefaultEditorKit.CutAction()).actionPerformed(evt);
        }

    }//GEN-LAST:event_CutMenuItemActionPerformed

    private void CopyMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CopyMenuItemActionPerformed
        if (DatasheetInternalFrame.isSelected()) {
            KeyStroke keyStroke = KeyStroke.getKeyStroke("control C");
            ActionListener action = getSpreadsheet().getActionForKeyStroke(keyStroke);
            action.actionPerformed(new ActionEvent(getSpreadsheet(), 
                    ActionEvent.ACTION_PERFORMED, "Copy"));
        }
        else {
            (new DefaultEditorKit.CopyAction()).actionPerformed(evt);
        }
    }//GEN-LAST:event_CopyMenuItemActionPerformed

    private void PasteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PasteMenuItemActionPerformed
        if (DatasheetInternalFrame.isSelected()) {
            KeyStroke keyStroke = KeyStroke.getKeyStroke("control V");
            ActionListener action = getSpreadsheet().getActionForKeyStroke(keyStroke);
            action.actionPerformed(new ActionEvent(getSpreadsheet(), 
                    ActionEvent.ACTION_PERFORMED, "Paste", ActionEvent.CTRL_MASK));
            // set change status of worksheet
            DatasheetPane.setChangedStatus();
        }
        else {
            (new DefaultEditorKit.PasteAction()).actionPerformed(evt);
            LogTextPane.setChangedStatus();
        }
    }//GEN-LAST:event_PasteMenuItemActionPerformed

    private void HistogramMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HistogramMenuItemActionPerformed
        displayDialog(histogramDialog);
}//GEN-LAST:event_HistogramMenuItemActionPerformed

    private void ScatterplotMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ScatterplotMenuItemActionPerformed
        displayDialog(scatterplotDialog);
}//GEN-LAST:event_ScatterplotMenuItemActionPerformed

    private void BarChartMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BarChartMenuItemActionPerformed
        displayDialog(barChartDialog);
}//GEN-LAST:event_BarChartMenuItemActionPerformed

    private void AddRowColMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddRowColMenuItemActionPerformed
        displayDialog(addMultipleRowColDialog);
}//GEN-LAST:event_AddRowColMenuItemActionPerformed

    private void GoodnessOfFitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GoodnessOfFitMenuItemActionPerformed
        displayDialog(goodnessOfFitDialog);
    }//GEN-LAST:event_GoodnessOfFitMenuItemActionPerformed

    private void ContingencyTableMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ContingencyTableMenuItemActionPerformed
        displayDialog(contingencyTableDialog);
}//GEN-LAST:event_ContingencyTableMenuItemActionPerformed

    private void TwoVarMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TwoVarMenuItemActionPerformed
        displayDialog(correlationRegressionDialog);
    }//GEN-LAST:event_TwoVarMenuItemActionPerformed

    private void MultipleMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MultipleMenuItemActionPerformed
        displayDialog(multipleRegressionDialog);
}//GEN-LAST:event_MultipleMenuItemActionPerformed

    private void OneANOVAMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OneANOVAMenuItemActionPerformed
        displayDialog(oneWayANOVADialog);
    }//GEN-LAST:event_OneANOVAMenuItemActionPerformed

    private void NQPMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NQPMenuItemActionPerformed
        displayDialog(normalQuantilePlotDialog);
}//GEN-LAST:event_NQPMenuItemActionPerformed

    private void NonLinMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NonLinMenuItemActionPerformed
        displayDialog(nonLinearModelsDialog);
}//GEN-LAST:event_NonLinMenuItemActionPerformed

    private void SampleFromColumnMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SampleFromColumnMenuItemActionPerformed
        displayDialog(sampleFromColumnDialog);
    }//GEN-LAST:event_SampleFromColumnMenuItemActionPerformed

    private void IntegerSamplesMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IntegerSamplesMenuItemActionPerformed
        displayDialog(integerSampleDialog);
}//GEN-LAST:event_IntegerSamplesMenuItemActionPerformed

    private void UniformSamplesMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UniformSamplesMenuItemActionPerformed
        displayDialog(uniformSampleDialog);
}//GEN-LAST:event_UniformSamplesMenuItemActionPerformed

    private void NormalSamplesMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NormalSamplesMenuItemActionPerformed
        displayDialog(normalSampleDialog);
    }//GEN-LAST:event_NormalSamplesMenuItemActionPerformed

    private void BinomialSamplesMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BinomialSamplesMenuItemActionPerformed
        displayDialog(binomialSampleDialog);
    }//GEN-LAST:event_BinomialSamplesMenuItemActionPerformed

    private void FreqTableMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FreqTableMenuItemActionPerformed
        displayDialog(frequencyTableDialog);
    }//GEN-LAST:event_FreqTableMenuItemActionPerformed

    private void ConclusionMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConclusionMenuItemActionPerformed
        displayDialog(hyTestConclusionDialog);
}//GEN-LAST:event_ConclusionMenuItemActionPerformed

    private void CalculatorMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CalculatorMenuItemActionPerformed
        displayDialog(calculatorDialog);
}//GEN-LAST:event_CalculatorMenuItemActionPerformed

    private void TwoANOVAMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TwoANOVAMenuItemActionPerformed
        displayDialog(twoWayANOVADialog);
}//GEN-LAST:event_TwoANOVAMenuItemActionPerformed

    private void UndoMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UndoMenuItemActionPerformed
        
    }//GEN-LAST:event_UndoMenuItemActionPerformed

    private void RedoMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RedoMenuItemActionPerformed
        
    }//GEN-LAST:event_RedoMenuItemActionPerformed

    private void dotPlotMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dotPlotMenuItemActionPerformed
        displayDialog(dotPlotDialog);
}//GEN-LAST:event_dotPlotMenuItemActionPerformed

    private void NormalityTestMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NormalityTestMenuItemActionPerformed
        displayDialog(normalityTestDialog);
    }//GEN-LAST:event_NormalityTestMenuItemActionPerformed

    private void StemLeafPlotMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StemLeafPlotMenuItemActionPerformed
        displayDialog(stemAndLeafPlotDialog);
    }//GEN-LAST:event_StemLeafPlotMenuItemActionPerformed

    private void LogMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogMenuItemActionPerformed
        LogInternalFrame.toFront();
}//GEN-LAST:event_LogMenuItemActionPerformed

    private void DatasheetMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DatasheetMenuItemActionPerformed
        DatasheetInternalFrame.toFront();
    }//GEN-LAST:event_DatasheetMenuItemActionPerformed

    private void SaveProjMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveProjMenuItemActionPerformed
        FileOperations.saveProject(this, false);
    }//GEN-LAST:event_SaveProjMenuItemActionPerformed

    private void OpenProjMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OpenProjMenuItemActionPerformed
        FileOperations.openProjectFile(this);
        if (!project.getName().equals("")) {
            setTitle("Statcato - " + project.getName());
            setStatus("Project " + project.getName() + " opened.");
            setClearStatusTimer(3);
            clearUndoManager();
        }
    }//GEN-LAST:event_OpenProjMenuItemActionPerformed

    private void SaveProjAsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveProjAsMenuItemActionPerformed
        FileOperations.saveProject(this, true);
    }//GEN-LAST:event_SaveProjAsMenuItemActionPerformed

    private void CloseProjMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseProjMenuItemActionPerformed
        if (project.close()) {
            setTitle("Statcato");
            DatasheetPane.addDatasheet();
            clearUndoManager();
        }
    }//GEN-LAST:event_CloseProjMenuItemActionPerformed

    private void EditLastDialogMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditLastDialogMenuItemActionPerformed
        if (lastDialog != null) {
            lastDialog.setVisible(true);
        }
    }//GEN-LAST:event_EditLastDialogMenuItemActionPerformed

    private void EditMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditMenu1ActionPerformed
        
    }//GEN-LAST:event_EditMenu1ActionPerformed

    private void HistoryCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HistoryCheckBoxMenuItemActionPerformed
        showDialogHistory();
    }//GEN-LAST:event_HistoryCheckBoxMenuItemActionPerformed

    private void DialogHistoryMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DialogHistoryMenuItemActionPerformed
        if (HistoryCheckBoxMenuItem.isSelected()) {
                HistoryCheckBoxMenuItem.setSelected(false);
            }
            else {
                HistoryCheckBoxMenuItem.setSelected(true);
            }
            HistoryCheckBoxMenuItemActionPerformed(evt);
    }//GEN-LAST:event_DialogHistoryMenuItemActionPerformed

    private void CheckUpdatesMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckUpdatesMenuItemActionPerformed
        displayDialog(checkUpdatesDialog);
    }//GEN-LAST:event_CheckUpdatesMenuItemActionPerformed

    private void OptionsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OptionsMenuItemActionPerformed
        displayDialog(optionsDialog);
    }//GEN-LAST:event_OptionsMenuItemActionPerformed

    private void SignTestMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SignTestMenuItemActionPerformed
        displayDialog(signTestDialog);
    }//GEN-LAST:event_SignTestMenuItemActionPerformed

    private void MatchedPairSignTestMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MatchedPairSignTestMenuItemActionPerformed
        displayDialog(matchedPairSignTestDialog);
    }//GEN-LAST:event_MatchedPairSignTestMenuItemActionPerformed

    private void WilcoxonSignedRankMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_WilcoxonSignedRankMenuItemActionPerformed
        displayDialog(wilcoxonSignedRankTestDialog);
    }//GEN-LAST:event_WilcoxonSignedRankMenuItemActionPerformed

    private void MannWhitneyTestMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MannWhitneyTestMenuItemActionPerformed
        displayDialog(mannWhitneyTestDialog);
}//GEN-LAST:event_MannWhitneyTestMenuItemActionPerformed

    private void KruskalWallisTestMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KruskalWallisTestMenuItemActionPerformed
        displayDialog(kruskalWallisDialog);
    }//GEN-LAST:event_KruskalWallisTestMenuItemActionPerformed

    private void RankCorrelationMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RankCorrelationMenuItemActionPerformed
        displayDialog(rankCorrelationDialog);
    }//GEN-LAST:event_RankCorrelationMenuItemActionPerformed

    private void RankCorrelationMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RankCorrelationMenuItem2ActionPerformed
        displayDialog(rankCorrelationDialog);
}//GEN-LAST:event_RankCorrelationMenuItem2ActionPerformed

    private void RunsTestMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RunsTestMenuItemActionPerformed
        displayDialog(runsTestDialog);
    }//GEN-LAST:event_RunsTestMenuItemActionPerformed

    private void CrossTabMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CrossTabMenuItemActionPerformed
        displayDialog(crossTabulationDialog);
    }//GEN-LAST:event_CrossTabMenuItemActionPerformed

    private void ExponentialMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExponentialMenuItem1ActionPerformed
        displayDialog(exponentialDistributionDialog);
    }//GEN-LAST:event_ExponentialMenuItem1ActionPerformed

    private void TransposeMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TransposeMenuItem1ActionPerformed
        displayDialog(transposeDialog);
}//GEN-LAST:event_TransposeMenuItem1ActionPerformed

    private void TwoVarAllPairsMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TwoVarAllPairsMenuItem1ActionPerformed
        displayDialog(correlationRegressionAllPairsDialog);
    }//GEN-LAST:event_TwoVarAllPairsMenuItem1ActionPerformed

    private void ResidualPlotMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResidualPlotMenuItemActionPerformed
        correlationRegressionDialog.selectResidualCheckBox();
        displayDialog(correlationRegressionDialog);
    }//GEN-LAST:event_ResidualPlotMenuItemActionPerformed
   
    /**
     * Shows the dialog history frame.
     */
    public void showDialogHistory() {
        Dimension size = getSize(); // size of jframe
        Dimension toolbarSize = MainToolBar.getSize();
        Dimension menuSize = MainMenuBar1.getSize();

        // update the sizes of the log and Datasheet windows
        int newHeight = (size.height - 110 -
                toolbarSize.height - menuSize.height)/2;
        if (HistoryCheckBoxMenuItem.isSelected()) {
            dialogHistoryButton.setSelected(true);
            LogInternalFrame.setBounds(25,
                    toolbarSize.height + 10, size.width*3/4-60,
                    newHeight);
            HistoryInternalFrame.setBounds(25 + size.width*3/4 - 50,
                    toolbarSize.height + 10, size.width/4 - 5,
                    newHeight);
            // display a list of dialogs

            HistoryScrollPane.setViewportView(dialogHistoryList);
            HistoryInternalFrame.setVisible(true);
        }
        else {
            dialogHistoryButton.setSelected(false);
            LogInternalFrame.setBounds(25,
                    toolbarSize.height + 10, size.width-60,
                    newHeight);
            HistoryInternalFrame.setVisible(false);
        }
    }

    

    /**
     * Sets the title of the log internal frame.
     * 
     * @param title a string to be displayed at the top of the log frame
     */
    public void setLogTitle(String title) {
        LogInternalFrame.setTitle(title);
    }
    
    /**
     * Returns the title of the log internal frame.
     * 
     * @return the string displayed at the top of the log frame
     */
    public String getLogTitle() {
        return LogInternalFrame.getTitle();
    }
    
    /**
     * Sets the title of the active tab in the Datasheet pane.
     * 
     * @param title a string to be displayed at the top of the active tab
     */
    public void setCurrentTabTitle(String title) {
        DatasheetPane.setCurrentTabTitle(title);
    }
    
    /**
     * Returns the title of the active tab in the Datasheet pane.
     * 
     * @return the string displayed at the top of the active tab
     */
    public String getCurrentTabTitle() {
        return DatasheetPane.getCurrentTabTitle();
    }
    
    /**
     * Ensures that the application is ready to be disposed and
     * actually disposes this application frame.  Prompts for any
     * unsaved files before disposing this application.
     */
    private void closeApplication() {

        if (project.close()) {            
            // close application
            project.exit();
            dispose();
            System.exit(0);
        }
    }
    
    /**
     * Creates an image icon given the file path and description of an image.
     * 
     * @param path  a string denoting the image file path
     * @param description a string descripting the image
     * @return an image icon
     */
    public ImageIcon createImageIcon(String path,
                                           String description) {
        URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            //System.out.println("found file " + path);
            return new ImageIcon(imgURL, description);
        } else {
            System.out.println("Couldn't find file: " + path);
            return null;
        }
    }
    
    
    /**
     * Initializes components not created by the GUI editor.
     * Creates the log text pane and Datasheet pane.  Sets
     * the frame icons.  Sets menu shortcuts and toolbar.
     */
    private void customInitComponents() {  
        addHelp();

        // setup undo & redo
        undoManager = new UndoManager();
        undoManager.setLimit(20);
        menuUndoAction = new MenuItemUndoAction();
        menuRedoAction = new MenuItemRedoAction();
        iconUndoAction = new IconUndoAction();
        iconRedoAction = new IconRedoAction();
        UndoMenuItem.setAction(menuUndoAction);
        RedoMenuItem.setAction(menuRedoAction);
        
        LogTextPane = new LogWindow(this);       
        LogScrollPane.setViewportView(LogTextPane);
        LogTextPane.requestFocusInWindow();
        createPopupMenu();
        DatasheetPane = new DatasheetTabbedPane(this, WSPopupListener);

        LogTextPane.getDocument().addUndoableEditListener(
                new MyUndoableEditListener());
        getSpreadsheet().addUndoableEditListener(
                new MyUndoableEditListener());  
        
        javax.swing.GroupLayout DatasheetInternalFrameLayout = 
                new javax.swing.GroupLayout(DatasheetInternalFrame.getContentPane());
        DatasheetInternalFrame.getContentPane().setLayout(DatasheetInternalFrameLayout);
        DatasheetInternalFrameLayout.setHorizontalGroup(
            DatasheetInternalFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(DatasheetPane, javax.swing.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
        );
        DatasheetInternalFrameLayout.setVerticalGroup(
            DatasheetInternalFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(DatasheetPane, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
        );
        
        // set frame icon
        setIconImage(createImageIcon("small.gif", "Statcato").getImage());
        LogInternalFrame.setFrameIcon(createImageIcon("log.gif", "log"));
        DatasheetInternalFrame.setFrameIcon(createImageIcon("datasheet.gif", 
                "datasheet"));
        HistoryInternalFrame.setFrameIcon(
                createImageIcon("/toolbarButtonGraphics/general/History24.gif",
                "history"));
        
        // setup tool bar & popup
        setupToolBar();
        LogTextPane.addMouseListener(LogPopupListener);  
        
        // setup dialogs
        setupDialogs();  
        
       
        // initialize project file
        project = new Project(this);

        // set color of desktop pane and status bar
        StatcatoDesktopPane.setBackground(MainToolBar.getBackground());
        StatusPanel.setBackground(MainToolBar.getBackground());
        StatusLabel.setBackground(MainToolBar.getBackground());

        // edit last dialog
        EditLastDialogMenuItem.setEnabled(false);

        // initializes lists that keep track of window frames and corresponding
        // menus
        windowFrameList = new ArrayList<JFrame>();
        windowMenuList = new ArrayList<StatcatoChartMenuItem>();

        // initializes lists for keeping track of timers for setting status
        statusTimerList = new ArrayList<SetStatusTimer>();
    }

    /**
     * Add the given frame to the list of window frames.  Adds a corresponding
     * menu item in the window menu.
     *
     * @param frame StatcatoChartFrame instance to be added
     */
    public void addWindowFrame(JFrame frame) {
        windowFrameList.add(frame);
        // add a new menu item
        StatcatoChartMenuItem item =
                new StatcatoChartMenuItem(frame.getTitle(), frame);
        windowMenuList.add(item);
        WindowMenu.add(item);
    }

    public ArrayList<JFrame> getChartFrames() {
        return windowFrameList;
    }

    /**
     * Removes the given frame from the list of window frames.   Removes
     * the corresponding menu item from the window menu.
     *
     * @param frame JFrame instance to be removed
     */
    public void removeWindowFrame(JFrame frame) {
        int index = -1;
        if (frame instanceof StatcatoChartFrame) {
            index = windowFrameList.indexOf((StatcatoChartFrame)frame);
        }
        else if (frame instanceof StatcatoMultipleChartFrame) {
            index = windowFrameList.indexOf((StatcatoMultipleChartFrame)frame);
        }
        if (index < 0) {
            System.err.println("Statcato.java: failed to remove window frame");
        } else {
            windowFrameList.remove(index);
            StatcatoChartMenuItem item = windowMenuList.get(index);
            windowMenuList.remove(index);
            WindowMenu.remove(item);
        }
    }

    /**
     * Removes all frames from the list of window frames.  Disposes
     * all frames.
     */
    public void removeChartFrames() {
        Iterator<JFrame> it = windowFrameList.iterator();
        while (it.hasNext()) {
            JFrame frame = it.next();
            removeWindowFrame(frame);          
            frame.dispose();
            it = windowFrameList.iterator();
        }
    }
        
    /**
     * Initializes dialogs.
     */
    private void setupDialogs() {        
        Frame f = (Frame)SwingUtilities.getAncestorOfClass(Frame.class, this);
        // graph dialogs
        barChartDialog = new BarChartDialog(this, true, this);
        barChartDialog.setLocationRelativeTo(this);       
        boxPlotDialog = new BoxPlotDialog(this, true, this);
        boxPlotDialog.setLocationRelativeTo(this); 
        dotPlotDialog = new DotPlotDialog(this, true);
        dotPlotDialog.setLocationRelativeTo(this);
        histogramDialog = new HistogramDialog(this, true, this);
        histogramDialog.setLocationRelativeTo(this); 
        normalQuantilePlotDialog = new NormalQuantilePlotDialog(this, true, this);
        normalQuantilePlotDialog.setLocationRelativeTo(this); 
        pieChartDialog = new PieChartDialog(this, true, this);
        pieChartDialog.setLocationRelativeTo(this); 
        scatterplotDialog = new ScatterplotDialog(this, true, this);
        scatterplotDialog.setLocationRelativeTo(this); 
        stemAndLeafPlotDialog = new StemAndLeafPlotDialog(this, true);
        stemAndLeafPlotDialog.setLocationRelativeTo(this); 
        
        // calc dialogs
        binomialDistributionDialog = new BinomialDistributionDialog(this, true, this);
        binomialDistributionDialog.setLocationRelativeTo(this); 
        calculatorDialog = new CalculatorDialog(this, true);
        calculatorDialog.setLocationRelativeTo(this); 
        chiSquareDistributionDialog = new ChiSquareDistributionDialog(this, true, this);
        chiSquareDistributionDialog.setLocationRelativeTo(this); 
        discreteDistributionDialog = new DiscreteDistributionDialog(this, true, this);
        discreteDistributionDialog.setLocationRelativeTo(this);
        exponentialDistributionDialog = new ExponentialDistributionDialog(this, true);
        exponentialDistributionDialog.setLocationRelativeTo(this);
        fDistributionDialog = new FDistributionDialog(this, true, this);
        fDistributionDialog.setLocationRelativeTo(this); 
        frequencyTableDialog = new FrequencyTableDialog(this, true, this);
        frequencyTableDialog.setLocationRelativeTo(this); 
        geometricDistributionDialog = new GeometricDistributionDialog(this, true, this);
        geometricDistributionDialog.setLocationRelativeTo(this); 
        hyTestConclusionDialog = new HyTestConclusionDialog(this, true);
        hyTestConclusionDialog.setLocationRelativeTo(this);
        integerDistributionDialog = new IntegerDistributionDialog(this, true, this);
        integerDistributionDialog.setLocationRelativeTo(this); 
        normalDistributionDialog = new NormalDistributionDialog(this, true, this);
        normalDistributionDialog.setLocationRelativeTo(this); 
        poissonDistributionDialog = new PoissonDistributionDialog(this, true, this);
        poissonDistributionDialog.setLocationRelativeTo(this); 
        studentTDistributionDialog = new StudentTDistributionDialog(this, true, this);
        studentTDistributionDialog.setLocationRelativeTo(this); 
        uniformDistributionDialog = new UniformDistributionDialog(this, true, this);
        uniformDistributionDialog.setLocationRelativeTo(this);
        pvalueDialog = new PvalueDialog(this, true, this);
        pvalueDialog.setLocationRelativeTo(this);
        
        // data dialogs
        arbitraryDataPatternDialog = new ArbitraryDataPatternDialog(this, true, this);
        arbitraryDataPatternDialog.setLocationRelativeTo(this);         
        binomialSampleDialog = new BinomialSampleDialog(this, true, this);
        binomialSampleDialog.setLocationRelativeTo(this);      
        displayDataDialog = new DisplayDataDialog(this, true, this);
        displayDataDialog.setLocationRelativeTo(this);  
        integerSampleDialog = new IntegerSampleDialog(this, true, this);
        integerSampleDialog.setLocationRelativeTo(this);
        normalSampleDialog = new NormalSampleDialog(this, true, this);
        normalSampleDialog.setLocationRelativeTo(this);
        rankDialog = new RankDialog(this, true, this);
        rankDialog.setLocationRelativeTo(this);
        sampleFromColumnDialog = new SampleFromColumnDialog(this, true, this);
        sampleFromColumnDialog.setLocationRelativeTo(this);
        simpleNumberPatternDialog = new SimpleNumberPatternDialog(this, true, this);
        simpleNumberPatternDialog.setLocationRelativeTo(this);
        sortDialog = new SortDialog(this, true, this);
        sortDialog.setLocationRelativeTo(this);
        standardizeDialog = new StandardizeDialog(this, true, this);
        standardizeDialog.setLocationRelativeTo(this);
        uniformSampleDialog = new UniformSampleDialog(this, true, this);
        uniformSampleDialog.setLocationRelativeTo(this);
        transposeDialog = new TransposeDialog(this, true);
        transposeDialog.setLocationRelativeTo(this);
        
        // stat dialogs
        cI1PopMeanDialog = new CI1PopMeanDialog(this, true, this);
        cI1PopMeanDialog.setLocationRelativeTo(this);
        cI1PopPropDialog = new CI1PopPropDialog(this, true, this);
        cI1PopPropDialog.setLocationRelativeTo(this);
        cI1PopVarDialog = new CI1PopVarDialog(this, true, this);
        cI1PopVarDialog.setLocationRelativeTo(this);
        cI2PopMeanDialog = new CI2PopMeanDialog(this, true, this);
        cI2PopMeanDialog.setLocationRelativeTo(this);
        cI2PopPropDialog = new CI2PopPropDialog(this, true, this);
        cI2PopPropDialog.setLocationRelativeTo(this);
        cI2PopVarDialog = new CI2PopVarDialog(this, true, this);
        cI2PopVarDialog.setLocationRelativeTo(this);
        cIMatchedPairsDialog = new CIMatchedPairsDialog(this, true, this);
        cIMatchedPairsDialog.setLocationRelativeTo(this);
        columnStatisticsDialog = new ColumnStatisticsDialog(this, true, this);
        columnStatisticsDialog.setLocationRelativeTo(this);
        contingencyTableDialog = new ContingencyTableDialog(this, true, this);
        contingencyTableDialog.setLocationRelativeTo(this);
        correlationRegressionDialog = new CorrelationRegressionDialog(this, true, this);
        correlationRegressionDialog.setLocationRelativeTo(this);
        correlationRegressionAllPairsDialog = new CorrelationMatrixDialog(this, true, this);
        correlationRegressionAllPairsDialog.setLocationRelativeTo(this);
        descriptiveStatisticsDialog = new DescriptiveStatisticsDialog(this, true, this);
        descriptiveStatisticsDialog.setLocationRelativeTo(this);
        goodnessOfFitDialog = new GoodnessOfFitDialog(this, true, this);
        goodnessOfFitDialog.setLocationRelativeTo(this);
        hyTest1PopMeanDialog = new HyTest1PopMeanDialog(this, true, this);
        hyTest1PopMeanDialog.setLocationRelativeTo(this);
        hyTest1PopPropDialog = new HyTest1PopPropDialog(this, true, this);
        hyTest1PopPropDialog.setLocationRelativeTo(this);
        hyTest1PopVarDialog = new HyTest1PopVarDialog(this, true, this);
        hyTest1PopVarDialog.setLocationRelativeTo(this);
        hyTest2PopMeanDialog = new HyTest2PopMeanDialog(this, true, this);
        hyTest2PopMeanDialog.setLocationRelativeTo(this);
        hyTest2PopPropDialog = new HyTest2PopPropDialog(this, true, this);
        hyTest2PopPropDialog.setLocationRelativeTo(this);
        hyTest2PopVarDialog = new HyTest2PopVarDialog(this, true, this);
        hyTest2PopVarDialog.setLocationRelativeTo(this);
        hyTestMatchedPairsDialog = new HyTestMatchedPairsDialog(this, true, this);
        hyTestMatchedPairsDialog.setLocationRelativeTo(this);
        multipleRegressionDialog = new MultipleRegressionDialog(this, true, this);
        multipleRegressionDialog.setLocationRelativeTo(this);
        nonLinearModelsDialog = new NonLinearModelsDialog(this, true, this);
        nonLinearModelsDialog.setLocationRelativeTo(this);
        normalityTestDialog = new NormalityTestDialog(this, true);
        normalityTestDialog.setLocationRelativeTo(this);
        oneWayANOVADialog = new OneWayANOVADialog(this, true, this);
        oneWayANOVADialog.setLocationRelativeTo(this);
        rowStatisticsDialog = new RowStatisticsDialog(this, true, this);
        rowStatisticsDialog.setLocationRelativeTo(this);
        sampleSizeMeanDialog = new SampleSizeMeanDialog(this, true, this);
        sampleSizeMeanDialog.setLocationRelativeTo(this);
        sampleSizePropDialog = new SampleSizePropDialog(this, true, this);
        sampleSizePropDialog.setLocationRelativeTo(this);
        twoWayANOVADialog = new TwoWayANOVADialog(this, true);
        twoWayANOVADialog.setLocationRelativeTo(this);
        signTestDialog = new SignTestDialog(this, true);
        signTestDialog.setLocationRelativeTo(this);
        matchedPairSignTestDialog = new MatchedPairSignTestDialog(this, true);
        matchedPairSignTestDialog.setLocationRelativeTo(this);
        wilcoxonSignedRankTestDialog = new WilcoxonSignedRankTestDialog(this, true);
        wilcoxonSignedRankTestDialog.setLocationRelativeTo(this);
        // initializes Wilcoxon Signed Rank p-value/critical value class
        new WilcoxonSignedRankPValue();
        mannWhitneyTestDialog = new WilcoxonRankSumTestDialog(this, true);
        mannWhitneyTestDialog.setLocationRelativeTo(this);
        kruskalWallisDialog = new KruskalWallisDialog(this, true);
        kruskalWallisDialog.setLocationRelativeTo(this);
        rankCorrelationDialog = new RankCorrelationDialog(this, true);
        rankCorrelationDialog.setLocationRelativeTo(this);
        runsTestDialog = new RunsTestDialog(this, true);
        runsTestDialog.setLocationRelativeTo(this);
        crossTabulationDialog = new CrossTabulationDialog(this, true);
        crossTabulationDialog.setLocationRelativeTo(this);
        
        // file dialogs
        loadDatasetDialog = new LoadDatasetDialog(this, true, this);
        loadDatasetDialog.setLocationRelativeTo(this);
        printLogDialog = new PrintLogDialog(this, true, this);
        printLogDialog.setLocationRelativeTo(this);
        printDatasheetDialog = new PrintDatasheetDialog(this, true, this);
        printDatasheetDialog.setLocationRelativeTo(this);
        optionsDialog = new OptionsDialog(this, true);
        optionsDialog.setLocationRelativeTo(this);

        // edit dialogs
        addMultipleRowColDialog = new AddMultipleRowColDialog(this, true, this);
        addMultipleRowColDialog.setLocationRelativeTo(this);

        // help dialog
        aboutDialog = new AboutDialog(this, true, this);
        aboutDialog.setLocationRelativeTo(this);
        checkUpdatesDialog = new CheckUpdatesDialog(this, true);
        checkUpdatesDialog.setLocationRelativeTo(this);
    }
    
    /**
     * Creates popup menus for the log and Datasheet panes
     * and creates their popup listeners.
     */
    private void createPopupMenu() {
        JMenuItem menuItem;

        //Create the popup menu.
        JPopupMenu LogPopup = new JPopupMenu();
        menuItem = new JMenuItem("Cut");    // cut
        menuItem.setActionCommand(CUT);
        menuItem.addActionListener(this);
        LogPopup.add(menuItem);
        menuItem = new JMenuItem("Copy");   // copy
        menuItem.setActionCommand(COPY);
        menuItem.addActionListener(this);
        LogPopup.add(menuItem);
        menuItem = new JMenuItem("Paste");  // paste
        menuItem.setActionCommand(PASTE);
        menuItem.addActionListener(this);
        LogPopup.add(menuItem);
        LogPopup.addSeparator();
        menuItem = new JMenuItem("Select All");     // select all
        menuItem.setActionCommand(SELECTALL);
        menuItem.addActionListener(this);
        LogPopup.add(menuItem);
        menuItem = new JMenuItem("Clear Window");   // clear
        menuItem.setActionCommand(CLEAR);
        menuItem.addActionListener(this);
        LogPopup.add(menuItem);
        LogPopup.addSeparator();
        menuItem = new JMenuItem("Print");  // print
        menuItem.setActionCommand(PRINT);
        menuItem.addActionListener(this);
        LogPopup.add(menuItem);
       
        //Add listener to the text area so the popup menu can come up.
        LogPopupListener = new PopupListener(LogPopup);
        //LogTextPane.addMouseListener(LogPopupListener);

        //Create the popup menu.
        JPopupMenu DatasheetPopup = new JPopupMenu();
        menuItem = new JMenuItem("Cut");    // cut
        menuItem.setActionCommand(CUT);
        menuItem.addActionListener(this);
        DatasheetPopup.add(menuItem);
        menuItem = new JMenuItem("Copy");   // copy
        menuItem.setActionCommand(COPY);
        menuItem.addActionListener(this);
        DatasheetPopup.add(menuItem);
        menuItem = new JMenuItem("Paste");  // paste
        menuItem.setActionCommand(PASTE);
        menuItem.addActionListener(this);
        DatasheetPopup.add(menuItem);        
        menuItem = new JMenuItem("Select All"); // select all
        menuItem.setActionCommand(SELECTALL);
        menuItem.addActionListener(this);
        DatasheetPopup.add(menuItem);
        DatasheetPopup.addSeparator();
        menuItem = new JMenuItem("Clear Cells"); // clear
        menuItem.setActionCommand(CLEAR);
        menuItem.addActionListener(this);
        DatasheetPopup.add(menuItem);
        menuItem = new JMenuItem("Delete Cells"); // delete
        menuItem.setActionCommand(DELETE);
        menuItem.addActionListener(this);
        DatasheetPopup.add(menuItem);
        DatasheetPopup.addSeparator();
        menuItem = new JMenuItem("Insert Row Above");   // insert row above
        menuItem.setActionCommand(INSERTROW);
        menuItem.addActionListener(this);
        DatasheetPopup.add(menuItem);        
        menuItem = new JMenuItem("Insert Column to Left");   // insert column left
        menuItem.setActionCommand(INSERTCOL);
        menuItem.addActionListener(this);
        DatasheetPopup.add(menuItem);        
        menuItem = new JMenuItem("Insert Cell Above");   // insert cell above
        menuItem.setActionCommand(INSERTCELL);
        menuItem.addActionListener(this);
        DatasheetPopup.add(menuItem);
        DatasheetPopup.addSeparator();
        menuItem = new JMenuItem("Print"); // print
        menuItem.setActionCommand(PRINT);
        menuItem.addActionListener(this);
        DatasheetPopup.add(menuItem);
       
        //Add listener to the text area so the popup menu can come up.
        WSPopupListener = new PopupListener(DatasheetPopup); 
        //DatasheetPane.addMouseListener(WSPopupListener);
        //getSpreadsheet().addMouseListener(WSPopupListener);     
    }
    

    /**
     * Sets up the toolbar and add buttons to it.
     */
    private void setupToolBar() {
        MainToolBar.setFloatable(false);
        MainToolBar.setRollover(true);
        JButton button = null;

        // new button
        button = makeToolBarButton("/toolbarButtonGraphics/general/New24.gif", NEW,
                                      "New Datasheet", "New", null);
        button.setFocusable(false);
        MainToolBar.add(button);
        
        // open button
        button = makeToolBarButton("/toolbarButtonGraphics/general/Open24.gif", OPEN,
                                      "Open", "Open", null);
        button.setFocusable(false);
        MainToolBar.add(button);
        
        // save button
        button = makeToolBarButton("/toolbarButtonGraphics/general/Save24.gif", SAVE,
                                      "Save", "Save", null);
        button.setFocusable(false);
        MainToolBar.add(button);
        
        // import  button
        button = makeToolBarButton("/toolbarButtonGraphics/general/Import24.gif", IMPORT,
                                      "Load Dataset", "Load", null);
        button.setFocusable(false);
        MainToolBar.add(button);
        
        // print button
        button = makeToolBarButton("/toolbarButtonGraphics/general/Print24.gif", PRINT,
                                      "Print", "Print", null);
        button.setFocusable(false);
        MainToolBar.add(button);
        
        MainToolBar.addSeparator();
        
        // Cut button
        button = makeToolBarButton("/toolbarButtonGraphics/general/Cut24.gif", CUT,
                                      "Cut", "Cut", null);
        button.setFocusable(false);
        MainToolBar.add(button);
        
        // copy button
        button = makeToolBarButton("/toolbarButtonGraphics/general/Copy24.gif", COPY,
                                      "Copy", "Copy", null);
        button.setFocusable(false);
        MainToolBar.add(button);
        
        // paste button
        button = makeToolBarButton("/toolbarButtonGraphics/general/Paste24.gif", PASTE,
                                      "Paste", "Paste", null);
        button.setFocusable(false);
        MainToolBar.add(button);
        
        MainToolBar.addSeparator();
        
        // undo button        
        button = makeToolBarButton("/toolbarButtonGraphics/general/Undo24.gif", UNDO,
                                      "Undo","Undo", iconUndoAction);
        button.setFocusable(false);
        button.setEnabled(false);
        
        MainToolBar.add(button);
        
        // redo button
        button = makeToolBarButton("/toolbarButtonGraphics/general/Redo24.gif", REDO,
                                      "Redo", "Redo", iconRedoAction);
        button.setFocusable(false);
        MainToolBar.add(button);
        button.setEnabled(false);
        MainToolBar.addSeparator();

        // edit last dialog button
        editDialogButton = makeToolBarButton("/toolbarButtonGraphics/general/Edit24.gif", EDIT,
                                      "Edit Last Dialog", "Edit Last Dialog", null);
        editDialogButton.setFocusable(false);
        MainToolBar.add(editDialogButton);
        editDialogButton.setEnabled(false);

        // history button
        dialogHistoryButton = makeToolBarButton("/toolbarButtonGraphics/general/History24.gif", HISTORY,
                                      "Dialog History", "Dialog History", null);
        dialogHistoryButton.setFocusable(false);
        MainToolBar.add(dialogHistoryButton);
        MainToolBar.addSeparator();
        
        // help button
        button = makeToolBarButton("/toolbarButtonGraphics/general/Help24.gif", HELP,
                                      "Help", "Help", null);
        button.setFocusable(false);
        MainToolBar.add(button);
        hb.enableHelpOnButton(button, "overview", hs);
    }
    
    /**
     * Creates a button for the given image and sets its 
     * action command, tooltip, and alternative text.
     * 
     * @param imageName a string representing the image file name
     * @param actionCommand a string representing the action command 
     * @param toolTipText a string to be displayed as tooltip
     * @param altText a string to be displayed when the image is not available
     * @return the button created from the image
     */
    protected JButton makeToolBarButton(String imageName,
                                           String actionCommand,
                                           String toolTipText,
                                           String altText,
                                           Action action) {
        //Look for the image.
        //String imgLocation = "images/"
                             //+ imageName
                             //+ ".gif";
        URL imageURL = this.getClass().getResource(imageName);
        JButton button;
        //Create and initialize the button.
        if (action == null)
            button = new JButton();
        else
            button = new JButton(action);
        button.setActionCommand(actionCommand);
        button.setToolTipText(toolTipText);
        button.addActionListener(this);

        if (imageURL != null) {                      //image found
            button.setIcon(new ImageIcon(imageURL, altText));
        } else {                                     //no image found
            button.setText(altText);
            System.err.println("Resource not found: "
                               + imageName);
        }

        return button;
    }
    
    /**
     * Returns the active spreadsheet.
     * 
     * @return the active spreadsheet
     */ 
    public Spreadsheet getSpreadsheet() {
        return ((SpreadsheetScrollPane)DatasheetPane.getSelectedComponent()).getSpreadsheet();
    }    
    
    /**
     * Returns the Datasheet tabbed pane.
     * 
     * @return the Datasheet tabbed pane
     */
    public DatasheetTabbedPane getDatasheetTabbedPane() {
        return DatasheetPane;
    }
    
    /**
     * Returns the log text pane.
     * 
     * @return the log text pane.
     */
    public LogWindow getLogTextPane() {
        return LogTextPane;
    }
    
    /**
     * Returns the project object.
     * 
     * @return the project object.
     */
    public Project getProject() {
        return project;
    }
    
    /**
     * Return a string of formatted current date and time.
     * 
     * @return a string containing current date and time
     */
    private String getDateTime() {
        Date date = new Date();
        return DateFormat.getInstance().format(date);
    }
    
    /**
     * Displays an error dialog with the given messages
     * 
     * @param message a string to be displayed in the dialog
     */
    public void showErrorDialog(String message) {
        Toolkit.getDefaultToolkit().beep(); 
        JOptionPane.showMessageDialog(this, message,
            "Error", JOptionPane.ERROR_MESSAGE);
    } 
    
    /**
     * Adds the given message to the log text pane.
     * 
     * @param message a string to be added to the log text pane
     */
    public void addLogText(String message) {
        LogTextPane.addText(message);
        // make Log frame active
        try {
            LogInternalFrame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {}
    }
 
    /**
     * Adds the given message as a heading to the log text pane.
     * 
     * @param message a string to be added as a heading to the log text pane.
     */
    public void addLogHeading(String message) {
        LogTextPane.addHeading(message);
    } 
    
    /**
     * Adds the given heading and text to the log text pane.
     * @param heading
     * @param text
     */
    public void addLogParagraph(String heading, String text) {
        LogTextPane.addParagraph(heading, text);
        try {
            LogInternalFrame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {}
    }

    public void addLogIcon(Icon c) {
        LogTextPane.insertIcon(c);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        String description = null;

        // Handle each button.
        if (NEW.equals(cmd)) { //NEW button clicked
            NewDatasheetMenuItemActionPerformed(e);
        } else if (OPEN.equals(cmd)) { // OPEN button clicked            
            OpenProjMenuItemActionPerformed(e);           
        } else if (IMPORT.equals(cmd)) { // IMPORT button clicked
            LoadDatasetMenuItemActionPerformed(e);
        } else if (SAVE.equals(cmd)) { // SAVE button clicked
            SaveProjMenuItemActionPerformed(e);           
        } else if (PRINT.equals(cmd)) { // PRINT button clicked
            if (DatasheetInternalFrame.isSelected()) {
                PrintDatasheetMenuItemActionPerformed(e);
            }
            else {
                PrintLogMenuItemActionPerformed(e);
            }
        } else if (HELP.equals(cmd)) { // HELP button clicked
            HelpMenuItemActionPerformed(e);
        } else if (CUT.equals(cmd)) { // CUT button clicked
            CutMenuItemActionPerformed(e);
        } else if (COPY.equals(cmd)) { // COPY button clicked
            CopyMenuItemActionPerformed(e);
        } else if (PASTE.equals(cmd)) { // PASTE button clicked
            PasteMenuItemActionPerformed(e);
        } else if (SELECTALL.equals(cmd)) { // SELECT ALL button clicked
            SelectAllMenuItemActionPerformed(e);
        } else if (CLEAR.equals(cmd)) { // CLEAR
            if (DatasheetInternalFrame.isSelected()) {
                ClearCellsMenuItemActionPerformed(e);
            }
            else {
                ClearLogMenuItemActionPerformed(e);
            }
        } else if (DELETE.equals(cmd)) {    // DELETE
            DeleteCellsMenuItemActionPerformed(e);
        } else if (INSERTROW.equals(cmd)) { // INSERT ROW ABOVE
            InsertRowAboveMenuItemActionPerformed(e);
        } else if (INSERTCOL.equals(cmd)) { // INSERT COLUMN LEFT
            InsertColumnLeftMenuItemActionPerformed(e);
        } else if (INSERTCELL.equals(cmd)) { // INSERT CELL ABOVE
            InsertCellAboveMenuItemActionPerformed(e);
        } else if (UNDO.equals(cmd)) { // UNDO
            UndoMenuItemActionPerformed(e);
        } else if (REDO.equals(cmd)) { // REDO
            RedoMenuItemActionPerformed(e);
        } else if (HISTORY.equals(cmd)) { // HISTORY
            if (HistoryCheckBoxMenuItem.isSelected()) {
                HistoryCheckBoxMenuItem.setSelected(false);
            }
            else {
                HistoryCheckBoxMenuItem.setSelected(true);
            }
            HistoryCheckBoxMenuItemActionPerformed(e);
        } else if (EDIT.equals(cmd)) {  // EDIT LAST DIALOG
            EditLastDialogMenuItemActionPerformed(e);
        }
    }

    private <T extends StatcatoDialog> void displayDialog(T dialog) {                                                 
        dialog.pack();
        dialog.setVisible(true);
        lastDialog = dialog;
        editDialogButton.setEnabled(true);
        EditLastDialogMenuItem.setEnabled(true);
        dialogHistoryList.addDialog(dialog);
    }            
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Statcato().setVisible(true);
            }
        });
    }
    
    /**
     * Adds the given edit to the undo manager and udpates the undo/redo
     * menus and icons.
     * 
     * @param edit undoable edit
     */
    public void addCompoundEdit(UndoableEdit edit) {
        undoManager.addEdit(edit);
        menuUndoAction.updateUndoState();
        menuRedoAction.updateRedoState();
        iconUndoAction.updateUndoState();
        iconRedoAction.updateRedoState();
    }
    
    /**
     * Clears all edits in the undo manager and updates the undo/redo
     * menus and icons.
     */
    public void clearUndoManager() {
        undoManager.discardAllEdits();
        menuUndoAction.updateUndoState();
        menuRedoAction.updateRedoState();
        iconUndoAction.updateUndoState();
        iconRedoAction.updateRedoState();
    }
    
    /**
     * Adds an undoable edit listener to the selected Datasheet
     */
    public void addUndoListenerToDatasheet() {
        getSpreadsheet().addUndoableEditListener(
                new MyUndoableEditListener());
    }




    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu ANOVAMenu1;
    private javax.swing.JMenuItem AboutMenuItem1;
    private javax.swing.JMenuItem AddRowColMenuItem1;
    private javax.swing.JMenuItem BarChartMenuItem1;
    private javax.swing.JMenu BasicStatisticsMenu1;
    private javax.swing.JMenuItem BinomialMenuItem1;
    private javax.swing.JMenuItem BinomialSamplesMenuItem;
    private javax.swing.JMenuItem BoxPlotMenuItem1;
    private javax.swing.JMenu CalcMenu1;
    private javax.swing.JMenuItem CalculatorMenuItem;
    private javax.swing.JMenuItem CheckUpdatesMenuItem;
    private javax.swing.JMenuItem ChiSquareMenuItem2;
    private javax.swing.JMenuItem ClearCellsMenuItem1;
    private javax.swing.JMenuItem ClearSessionMenuItem1;
    private javax.swing.JMenuItem CloseProjMenuItem;
    private javax.swing.JMenuItem CloseWorksheetMenuItem1;
    private javax.swing.JMenuItem ColStatMenuItem1;
    private javax.swing.JMenuItem ConclusionMenuItem;
    private javax.swing.JMenuItem ContingencyTableMenuItem1;
    private javax.swing.JMenuItem CopyMenuItem1;
    private javax.swing.JMenu CorrRegMenu1;
    private javax.swing.JMenuItem CrossTabMenuItem;
    private javax.swing.JMenuItem CutMenuItem1;
    private javax.swing.JMenu DataMenu1;
    private javax.swing.JMenuItem DataSeqMenuItem1;
    private javax.swing.JInternalFrame DatasheetInternalFrame;
    private javax.swing.JMenuItem DatasheetMenuItem;
    private javax.swing.JMenuItem DeleteCellsMenuItem1;
    private javax.swing.JMenuItem DescriptiveStatMenuItem1;
    private javax.swing.JMenuItem DialogHistoryMenuItem;
    private javax.swing.JMenuItem DiscreteMenuItem1;
    private javax.swing.JMenuItem DisplayDataMenuItem1;
    private javax.swing.JMenuItem EditLastDialogMenuItem;
    private javax.swing.JMenu EditMenu1;
    private javax.swing.JMenuItem ExitMenuItem1;
    private javax.swing.JMenuItem ExponentialMenuItem1;
    private javax.swing.JMenuItem FMenuItem1;
    private javax.swing.JMenu FileMenu1;
    private javax.swing.JMenuItem FreqTableMenuItem;
    private javax.swing.JMenu GeneratePatternedDataMenu1;
    private javax.swing.JMenu GenerateRandomDataMenu;
    private javax.swing.JMenuItem GeometricMenuItem1;
    private javax.swing.JMenuItem GoodnessOfFitMenuItem1;
    private javax.swing.JMenu GraphMenu1;
    private javax.swing.JMenu HelpMenu1;
    private javax.swing.JMenuItem HelpMenuItem1;
    private javax.swing.JMenuItem HistogramMenuItem1;
    private javax.swing.JCheckBoxMenuItem HistoryCheckBoxMenuItem;
    private javax.swing.JInternalFrame HistoryInternalFrame;
    private javax.swing.JScrollPane HistoryScrollPane;
    private javax.swing.JMenuItem HyTest2MeanMenuItem1;
    private javax.swing.JMenuItem HyTest2PropMenuItem1;
    private javax.swing.JMenuItem HyTest2VarMenuItem1;
    private javax.swing.JMenuItem HyTestMatchedPairsMenuItem1;
    private javax.swing.JMenuItem HyTestMeanMenuItem1;
    private javax.swing.JMenu HyTestMenu1;
    private javax.swing.JMenuItem HyTestPropMenuItem1;
    private javax.swing.JMenuItem HyTestVarMenuItem1;
    private javax.swing.JMenuItem InsertCellAboveMenuItem1;
    private javax.swing.JMenuItem InsertCellBelowMenuItem1;
    private javax.swing.JMenu InsertCellMenu1;
    private javax.swing.JMenuItem InsertColumnLeftMenuItem1;
    private javax.swing.JMenu InsertColumnMenu1;
    private javax.swing.JMenuItem InsertColumnRightMenuItem1;
    private javax.swing.JMenuItem InsertRowAboveMenuItem1;
    private javax.swing.JMenuItem InsertRowBelowMenuItem1;
    private javax.swing.JMenu InsertRowMenu1;
    private javax.swing.JMenuItem IntegerMenuItem1;
    private javax.swing.JMenuItem IntegerSamplesMenuItem;
    private javax.swing.JMenuItem KruskalWallisTestMenuItem;
    private javax.swing.JMenuItem LoadDatasetMenuItem1;
    private javax.swing.JInternalFrame LogInternalFrame;
    private javax.swing.JMenuItem LogMenuItem;
    private javax.swing.JScrollPane LogScrollPane;
    private javax.swing.JMenuBar MainMenuBar1;
    private javax.swing.JToolBar MainToolBar;
    private javax.swing.JMenuItem MannWhitneyTestMenuItem;
    private javax.swing.JMenuItem MatchedPairSignTestMenuItem;
    private javax.swing.JMenuItem MatchedPairsMenuItem1;
    private javax.swing.JMenuItem MeanMenuItem1;
    private javax.swing.JMenu MultinomialMenu1;
    private javax.swing.JMenuItem MultipleMenuItem1;
    private javax.swing.JMenuItem NQPMenuItem1;
    private javax.swing.JMenuItem NewWorksheetMenuItem1;
    private javax.swing.JMenuItem NonLinMenuItem;
    private javax.swing.JMenu NonparamMenu;
    private javax.swing.JMenuItem NormalMenuItem1;
    private javax.swing.JMenuItem NormalSamplesMenuItem;
    private javax.swing.JMenuItem NormalityTestMenuItem;
    private javax.swing.JMenuItem NumberSeqMenuItem1;
    private javax.swing.JMenuItem OneANOVAMenuItem1;
    private javax.swing.JMenuItem OnePopMeanMenuItem1;
    private javax.swing.JMenuItem OnePopPropMenuItem1;
    private javax.swing.JMenuItem OnePopVarMenuItem1;
    private javax.swing.JMenuItem OpenProjMenuItem;
    private javax.swing.JMenuItem OpenWorksheetMenuItem1;
    private javax.swing.JMenuItem OptionsMenuItem;
    private javax.swing.JMenuItem PasteMenuItem1;
    private javax.swing.JMenuItem PieChartMenuItem1;
    private javax.swing.JMenuItem PoissonMenuItem1;
    private javax.swing.JMenuItem PrintSessionMenuItem1;
    private javax.swing.JMenuItem PrintWorksheetMenuItem1;
    private javax.swing.JMenu ProbDistMenu1;
    private javax.swing.JMenuItem PropMenuItem1;
    private javax.swing.JMenuItem PvalueMenuItem1;
    private javax.swing.JMenuItem RankCorrelationMenuItem;
    private javax.swing.JMenuItem RankCorrelationMenuItem2;
    private javax.swing.JMenuItem RankMenuItem1;
    private javax.swing.JMenuItem RedoMenuItem;
    private javax.swing.JMenuItem ResidualPlotMenuItem;
    private javax.swing.JMenuItem RowStatMenuItem1;
    private javax.swing.JMenuItem RunsTestMenuItem;
    private javax.swing.JMenuItem SampleFromColumnMenuItem;
    private javax.swing.JMenu SampleSizeMenu1;
    private javax.swing.JMenuItem SaveProjAsMenuItem;
    private javax.swing.JMenuItem SaveProjMenuItem;
    private javax.swing.JMenuItem SaveWorksheetAsMenuItem1;
    private javax.swing.JMenuItem SaveWorksheetMenuItem1;
    private javax.swing.JMenuItem ScatterplotMenuItem1;
    private javax.swing.JMenuItem SelectAllMenuItem1;
    private javax.swing.JMenuItem SignTestMenuItem;
    private javax.swing.JMenuItem SortMenuItem1;
    private javax.swing.JMenuItem StandardizeMenuItem1;
    private javax.swing.JMenu StatMenu1;
    private javax.swing.JDesktopPane StatcatoDesktopPane;
    private javax.swing.JLabel StatusLabel;
    private javax.swing.JPanel StatusPanel;
    private javax.swing.JMenuItem StemLeafPlotMenuItem;
    private javax.swing.JMenuItem StudentTMenuItem1;
    private javax.swing.JMenu TestAndCIMenu1;
    private javax.swing.JMenuItem TransposeMenuItem1;
    private javax.swing.JMenuItem TwoANOVAMenuItem;
    private javax.swing.JMenuItem TwoPopMeanMenuItem1;
    private javax.swing.JMenuItem TwoPopPropMenuItem1;
    private javax.swing.JMenuItem TwoPopVarMenuItem1;
    private javax.swing.JMenuItem TwoVarAllPairsMenuItem1;
    private javax.swing.JMenuItem TwoVarMenuItem1;
    private javax.swing.JMenuItem UndoMenuItem;
    private javax.swing.JMenuItem UniformMenuItem1;
    private javax.swing.JMenuItem UniformSamplesMenuItem;
    private javax.swing.JMenuItem WilcoxonSignedRankMenuItem;
    private javax.swing.JMenu WindowMenu;
    private javax.swing.JMenuItem dotPlotMenuItem;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator19;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator20;
    private javax.swing.JSeparator jSeparator21;
    private javax.swing.JSeparator jSeparator22;
    private javax.swing.JSeparator jSeparator23;
    private javax.swing.JSeparator jSeparator24;
    private javax.swing.JSeparator jSeparator25;
    private javax.swing.JSeparator jSeparator26;
    private javax.swing.JSeparator jSeparator3;
    // End of variables declaration//GEN-END:variables

    
       
    /**
     * Undo action for the undo menu item.
     */
    public class MenuItemUndoAction extends AbstractAction {
        public MenuItemUndoAction() {
            super("Undo", createImageIcon("/toolbarButtonGraphics/general/Undo16.gif", "Undo"));
            setEnabled(false);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                undoManager.undo();
            } catch (CannotUndoException ex) {
                System.out.println("Unable to undo: " + ex);
                ex.printStackTrace();
            }
            // update UI
            updateUndoState();  
            menuRedoAction.updateRedoState();
            iconUndoAction.updateUndoState();
            iconRedoAction.updateRedoState();
        }

        protected void updateUndoState() {
            if (undoManager.canUndo()) {
                setEnabled(true);
                KeyStroke accKey = KeyStroke.getKeyStroke("ctrl Z");
                putValue(Action.ACCELERATOR_KEY, accKey);
                putValue(Action.NAME, undoManager.getUndoPresentationName());
            } else {
                setEnabled(false);
                putValue(Action.NAME, "Undo");
            }
        }
    }

    /**
     * Redo action for the redo menu item.
     */
    public class MenuItemRedoAction extends AbstractAction {
        public MenuItemRedoAction() {
            super("Redo", createImageIcon("/toolbarButtonGraphics/general/Redo16.gif", "Redo"));
            setEnabled(false);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                undoManager.redo();
            } catch (CannotRedoException ex) {
                System.out.println("Unable to redo: " + ex);
                ex.printStackTrace();
            }
            // update UI
            updateRedoState();
            menuUndoAction.updateUndoState();
            iconRedoAction.updateRedoState();
            iconUndoAction.updateUndoState();
        }

        protected void updateRedoState() {
            if (undoManager.canRedo()) {
                setEnabled(true);
                KeyStroke accKey = KeyStroke.getKeyStroke("ctrl Y");
                putValue(Action.ACCELERATOR_KEY, accKey);
                putValue(Action.NAME, undoManager.getRedoPresentationName());
            } else {
                setEnabled(false);
                putValue(Action.NAME, "Redo");
            }
        }
    }
    
    /**
     * Undo action for the undo toolbar icon.
     */
    public class IconUndoAction extends AbstractAction {
        public IconUndoAction() {
            super();
            setEnabled(false);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                undoManager.undo();
            } catch (CannotUndoException ex) {
                System.out.println("Unable to undo: " + ex);
                ex.printStackTrace();
            }
            // update UI
            updateUndoState();
            iconRedoAction.updateRedoState();
            menuUndoAction.updateUndoState();
            menuRedoAction.updateRedoState();
        }

        protected void updateUndoState() {
            if (undoManager.canUndo()) {
                setEnabled(true);
            } else {
                setEnabled(false);
            }
        }
    }

    /**
     * Redo action for the redo toolbar icon.
     */
    public class IconRedoAction extends AbstractAction {
        public IconRedoAction() {
            super();
            setEnabled(false);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                undoManager.redo();
            } catch (CannotRedoException ex) {
                System.out.println("Unable to redo: " + ex);
                ex.printStackTrace();
            }
            // update UI
            updateRedoState();
            iconUndoAction.updateUndoState();
            menuUndoAction.updateUndoState();
            menuRedoAction.updateRedoState();
        }

        protected void updateRedoState() {
            if (undoManager.canRedo()) {
                setEnabled(true);
            } else {
                setEnabled(false);
            }
        }
    }
    
    /**
     * Undoable edit listener for both the log window and the Datasheet pane.
     */
    public class MyUndoableEditListener
          implements UndoableEditListener {
        @Override
        public void undoableEditHappened(UndoableEditEvent e) {
            //Remember the edit and update the menus
            if (compoundEdit != null && compoundEdit.isInProgress())
                compoundEdit.addEdit(e.getEdit());
            else
                addCompoundEdit(e.getEdit());
        }
    }  

}
