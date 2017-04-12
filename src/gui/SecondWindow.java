package gui;

import java.util.LinkedList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class SecondWindow {

	protected Shell shell;
	private Logic logic;
	private LinkedList<Table> tableList = new LinkedList<>();
	private Button swapButton;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 * @wbp.parser.entryPoint
	 */
	public void start(Logic logic) {
		this.logic = logic;
		try {
			SecondWindow window = new SecondWindow(logic);
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SecondWindow(Logic logic) {
		super();
		this.logic = logic;
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	private void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		shell.setLayout(new GridLayout(logic.amountOfGroups, true));
		System.out.println(logic.amountOfGroups);
		for (int i = 1; i <= logic.amountOfGroups; i++) {

			Label groupLabel = new Label(shell, SWT.NONE);
			groupLabel.setText("Group " + i);
			GridData groupLabelGrid = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
			groupLabel.setLayoutData(groupLabelGrid);
		}
		for (int i = 1; i <= logic.amountOfGroups; i++) {

			Table table = new Table(shell, SWT.MULTI | SWT.BORDER);
			table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
			for (int j = 0; j < logic.personsInGroup; j++) {
				TableItem tableItem_1 = new TableItem(table, SWT.NONE);
				tableItem_1.setText(logic.groupsList.get(i - 1)[j]);
			}
			tableList.add(table);
		}

		swapButton = new Button(shell, SWT.NONE);
		swapButton.setText("Swap");
		swapButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				int[] playerIndices = new int[2];
				int[] groupIndices = new int[2];
				int i = 0;
				String toBeSwappedZero = "";
				String toBeSwappedOne = "";
				for (Table table : tableList) {
					if (table.getSelectionCount() == 1) {
						if (toBeSwappedZero == "") {
							groupIndices[0] = i;
							playerIndices[0] = table.getSelectionIndex();
							toBeSwappedZero = table.getSelection().toString();
						} else {
							groupIndices[1] = i;
							playerIndices[1] = table.getSelectionIndex();
							toBeSwappedOne = table.getSelection().toString();
						}
					}
					if (table.getSelectionCount() == 2) {
						playerIndices = table.getSelectionIndices();
						groupIndices[0] = i;
						groupIndices[1] = i;
						toBeSwappedZero = table.getItem(playerIndices[0]).getData().toString();
						toBeSwappedOne = table.getItem(playerIndices[1]).getData().toString();
					}
					i++;
				}
				System.out.println(toBeSwappedOne);
				tableList.get(groupIndices[0]).getItem(playerIndices[0]).setText(toBeSwappedOne);
				tableList.get(groupIndices[1]).getItem(playerIndices[1]).setText(toBeSwappedZero);
			}
		});
	}

}