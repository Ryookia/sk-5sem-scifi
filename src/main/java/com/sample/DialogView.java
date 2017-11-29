package com.sample;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DialogView extends JDialog implements ActionListener {
	
	static final long serialVersionUID = 1;
		
	private JPanel buttonPanel;
	private JPanel questionPanel;
	private JLabel contentDescription;
	private JPanel mainPanel;
	
	private ModelQuestion model;
	
    public DialogView (ModelQuestion model) {       

    	setModal(true);
    	this.model = model;
        mainPanel = new JPanel(new GridBagLayout());
		buttonPanel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.5;
		constraints.gridx = 0;
		constraints.gridy = 0; 
		contentDescription = new JLabel(model.getContent());
		JPanel contentPanel = new JPanel();
		contentPanel.add(contentDescription);
		mainPanel.add(contentPanel, constraints);
	
		addButtons(model.getAnswerList());
		this.add(mainPanel);
		pack();
    	Toolkit toolkit = Toolkit.getDefaultToolkit();
    	Dimension dimensions = toolkit.getScreenSize();
		setLocation((int)((dimensions.width - getWidth()) / 2), (int)((dimensions.height - getHeight()) / 2));
        
    }
    
    public void addButtons(String[] buttonValueList) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.5;
		constraints.gridx = 0;
		constraints.gridy = 1; 

		Container content = getContentPane();
//		content.remove(mainPanel);
		mainPanel.remove(buttonPanel);
		
		if (buttonValueList == null) {
			return;
		}
				
		buttonPanel = new JPanel(new GridBagLayout());
		int i = 0;
		for(String buttonValue : buttonValueList) {
			constraints.gridy = i ;
			JButton button = new JButton(buttonValue);
			button.setActionCommand(buttonValue);
			button.addActionListener(this);
			buttonPanel.add(button, constraints);
			i++;
		}
		constraints.gridy = 1; 
		mainPanel.add(buttonPanel, constraints);
		mainPanel.invalidate();
		mainPanel.validate();
		this.pack();
		content.invalidate();
		content.validate();
	}

    public String start() {
        setVisible(true);
        return model.getChosenAnswer();
    }
    
	@Override
	public void actionPerformed(ActionEvent event) {
		String answer = event.getActionCommand();
		model.setChosenAnswer(answer);
		setVisible(false);
		
	}

    
}
