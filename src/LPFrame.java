import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.Stack;
import javax.swing.JButton;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.JMenuBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.MatteBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.BoxLayout;
import com.eteks.jeks.JeksTable;

/**
 * This class is mostly auto-generated from Eclipse's GUI maker.
 * 
 * @author Evan Burton
 *
 */

public class LPFrame {

	private JFrame frmSimplexer;
	private JeksTable table;
	private Tableau tab;
	private DefaultTableModel tableModel;
	private JTextField rowField;
	private JTextField colField;
	private Stack<Tableau> history;
	private JTextField outputField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LPFrame window = new LPFrame();
					window.frmSimplexer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LPFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSimplexer = new JFrame();
		frmSimplexer.setIconImage(Toolkit.getDefaultToolkit().getImage(LPFrame.class.getResource("/Icon.png")));
		frmSimplexer.setTitle("Simplexer");
		frmSimplexer.setBounds(100, 100, 576, 329);
		frmSimplexer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Default size is 3 rows and 7 columns
		tab = new Tableau(3, 7);

		history = new Stack<>();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 521, 0 };
		gridBagLayout.rowHeights = new int[] { 156, 156, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		frmSimplexer.getContentPane().setLayout(gridBagLayout);

		JPanel buttonPanel = new JPanel();

		JPanel drawingPanel = new JPanel();
		GridBagConstraints gbc_drawingPanel = new GridBagConstraints();
		gbc_drawingPanel.fill = GridBagConstraints.BOTH;
		gbc_drawingPanel.insets = new Insets(0, 0, 5, 0);
		gbc_drawingPanel.gridx = 0;
		gbc_drawingPanel.gridy = 0;
		frmSimplexer.getContentPane().add(drawingPanel, gbc_drawingPanel);
		
		tableModel = new DefaultTableModel(100, 100);
		table = new JeksTable(tableModel);
		
		
		table.setColumnSelectionAllowed(true);
		table.setFillsViewportHeight(true);

		tableModel.addTableModelListener(e -> {
			
			if(e.getType() == TableModelEvent.UPDATE){
				int row = table.getSelectedRow();
				int col = table.getSelectedColumn();
				
				try{
					
					//table.getExpressionParser().getModelValue();
					
					double val = Double.parseDouble(table.getValueAt(row, col).toString());
					
					tab.set(row, col, val);
				}catch(Exception ex){
					//tab.set(row, col, 0);
				}
			}
			
			table.repaint();
			
		});
		
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			/**
			 * Default serial ID
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int col) {
				final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
				
				if((row == tab.getRows() - 1 && col < tab.getCols()) || (col == tab.getCols() - 1 && row < tab.getRows())){
					c.setBackground(Color.LIGHT_GRAY);
				}else{
					c.setBackground(Color.WHITE);
				}

				return c;
			}
		});

		drawingPanel.setLayout(new BoxLayout(drawingPanel, BoxLayout.X_AXIS));

		JScrollPane jpane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jpane.setAlignmentY(Component.TOP_ALIGNMENT);
		jpane.setPreferredSize(new Dimension(454, 50));
		jpane.setViewportBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		drawingPanel.add(jpane);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setCellSelectionEnabled(true);
		table.putClientProperty("terminateEditOnFocusLost", true);
		table.setGridColor(Color.BLUE);

		GridBagConstraints gbc_buttonPanel = new GridBagConstraints();
		gbc_buttonPanel.fill = GridBagConstraints.BOTH;
		gbc_buttonPanel.gridx = 0;
		gbc_buttonPanel.gridy = 1;
		frmSimplexer.getContentPane().add(buttonPanel, gbc_buttonPanel);
		buttonPanel.setLayout(new GridLayout(0, 2, 0, 0));

		JPanel buttonPanelLeft = new JPanel();
		buttonPanel.add(buttonPanelLeft);

		JPanel setSizePanel = new JPanel();
		buttonPanelLeft.add(setSizePanel);
		// Set Size
		JButton setSizeButton = new JButton("Set Size");
		setSizeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int rows = Integer.parseInt(rowField.getText());
				int cols = Integer.parseInt(colField.getText());

				if (rows < 0 || cols < 0)
					return;

				tableModel.setColumnCount(cols);
				tableModel.setRowCount(rows);
				tab.reshape(rows, cols);
				history.clear();
			}
		});
		setSizePanel.add(setSizeButton);

		rowField = new JTextField();
		setSizePanel.add(rowField);
		rowField.setColumns(3);

		colField = new JTextField();
		setSizePanel.add(colField);
		colField.setColumns(3);

		JPanel runButtonPanel = new JPanel();
		buttonPanelLeft.add(runButtonPanel);
		// Simplex Iterator
		JButton btnSimplex = new JButton("Iterate");
		/////////////////////// Simplex Iteration Button //////////////////
		btnSimplex.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!tab.simplexExit()) {

					history.push(tab.copy());

					Pivot p = tab.selectPivot();
					// Make pivot indices 1-based for math familiarity
					p.row++;
					p.col++;

					outputField.setText("Pivoting on: " + p);

					tab.simplexIteration();

					updateTable();
				} else {
					outputField.setText("Simplex Algorithm Completed");
				}
			}

		});
		runButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		runButtonPanel.add(btnSimplex);
		// Simplex
		JButton btnSimplex_1 = new JButton("Run");
		//////////////////////// Simplex Method Button ////////////////////
		btnSimplex_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				history.push(tab.copy());
				Tableau.OUTPUT output = tab.runSimplexMethod();

				if (output == Tableau.OUTPUT.SUCCESS)
					outputField.setText("Simplex Algorithm Completed");
				else
					outputField.setText("Max iterations exceeded!");

				updateTable();
			}
		});
		runButtonPanel.add(btnSimplex_1);

		JButton btnPivot = new JButton("Pivot");
		/////////// Pivot Button////////////////
		btnPivot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Pivot p = tab.selectPivot();
				// Make pivot indices 1-based for math familiarity
				p.row++;
				p.col++;

				outputField.setText("Pivot: " + p);

			}
		});
		runButtonPanel.add(btnPivot);

		JPanel editPanel = new JPanel();
		buttonPanelLeft.add(editPanel);
		JButton btnUndoSimplexIteration = new JButton("Undo");
		editPanel.add(btnUndoSimplexIteration);
		btnUndoSimplexIteration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Check if history empty or no action performed
				if (history.isEmpty() || tab.equals(history.peek())) {
					outputField.setText("Nothing to undo!");
					return;
				}

				tab = history.pop();

				updateTable();
				outputField.setText("");
				table.repaint();
			}
		});

		JPanel buttonPanelRight = new JPanel();
		buttonPanel.add(buttonPanelRight);

		JPanel addButtonPanel = new JPanel();
		buttonPanelRight.add(addButtonPanel);
		addButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		// Add Row Button
		JButton newRowButton = new JButton("Add Row");
		addButtonPanel.add(newRowButton);

		////////////////////////////// Add Col ////////////////////////
		// Add Column Button
		JButton newColButton = new JButton("Add Col");
		addButtonPanel.add(newColButton);

		JPanel deleteButtonPanel = new JPanel();
		buttonPanelRight.add(deleteButtonPanel);
		deleteButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton deleteRow = new JButton("Delete Row");
		deleteButtonPanel.add(deleteRow);
		////////////////////////////// Delete Col ////////////////////////
		JButton btnDeleteCol = new JButton("Delete Col");
		btnDeleteCol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//tableModel.setColumnCount(tab.getCols() - 1);
				tab.deleteCol(tab.getCols() - 1);
				table.repaint();
			}
		});
		deleteButtonPanel.add(btnDeleteCol);

		JPanel clearButtonPanel = new JPanel();
		buttonPanelRight.add(clearButtonPanel);

		JButton btnClear = new JButton("Clear");
		clearButtonPanel.add(btnClear);

		JMenuBar menuBar = new JMenuBar();
		frmSimplexer.setJMenuBar(menuBar);

		outputField = new JTextField();
		outputField.setToolTipText("Displays output information such as pivots.");
		outputField.setEditable(false);
		menuBar.add(outputField);
		outputField.setColumns(10);
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < tab.getRows(); i++) {
					for (int j = 0; j < tab.getCols(); j++) {
						tableModel.setValueAt("", i, j);
						tab.set(i, j, 0);
					}
				}
			}
		});
		////////////////////////////// Delete Row ////////////////////////
		deleteRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//tableModel.removeRow(tab.getRows() - 1);
				tab.deleteRow(tab.getRows() - 1);
				table.repaint();
			}
		});
		
		newColButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(tab.getCols() > tableModel.getColumnCount())
					tableModel.setColumnCount(tab.getCols() + 1);
				tab.addCol();
				table.repaint();
			}
		});

		newRowButton.addActionListener(e -> {
			tableModel.addRow(new Double[tab.getRows() + 1]);
			tab.addRow();
			table.repaint();
			//tableModel.fireTableDataChanged();
		});

		/*
		 * Adds TableModelListener to watch for changes made and update internal
		 * table.
		 */
		/*
		 * Clears the table's values by setting them to 0.
		 */

		/*
		 * Use the Tableau object's stack to store and load previous instances.
		 */
	}

	/**
	 * Goes through the tableModel and sets internal representation accordingly.
	 */
	public void updateTable() {
		for (int i = 0; i < tab.getRows(); i++) {
			for (int j = 0; j < tab.getCols(); j++) {
				tableModel.setValueAt(tab.get(i, j), i, j);
			}
		}
		table.repaint();
	}

	/**
	 * Gets the JTable object which is used to manage spreadsheet activities.
	 * 
	 * @return JTable object
	 */
	public JTable getTable() {
		return table;
	}

}
