package com.sample;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.html.ImageView;

public class View extends JFrame implements ActionListener {
	static final long serialVersionUID = 1;
	
	private OnViewClicked listener;
	
	private JPanel buttonPanel;
	private JPanel questionPanel;
	private JLabel contentDescription;
	private JPanel mainPanel;
	
	
	View(OnViewClicked listener, String text, String[] buttonTestList){
//		setLocationRelativeTo(null);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dimensions = toolkit.getScreenSize();
		setSize((int)(dimensions.width * 0.75), (int)(dimensions.height * 0.75));
		setLocation((int)(dimensions.width * 0.25 / 2), (int)(dimensions.height * 0.25 / 2));
		this.listener = listener;
		mainPanel = new JPanel(new GridBagLayout());
		buttonPanel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.5;
		constraints.gridx = 0;
		constraints.gridy = 0; 
		contentDescription = new JLabel(text);
		JPanel contentPanel = new JPanel();
		contentPanel.add(contentDescription);
		mainPanel.add(contentPanel, constraints);
	
		addButtons(buttonTestList);
		this.add(mainPanel);
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
		//this.pack();
		content.invalidate();
		content.validate();
	}
	
	public void setContent(JPanel contentPanel) {
		Container content = getContentPane();
		questionPanel = contentPanel;
		content.invalidate();
		content.validate();
	}
	
	public void setAnswers(JPanel answerPanel) {
		Container content = getContentPane();
		buttonPanel = answerPanel;
		content.invalidate();
		content.validate();
	}
	
	public void setContent(String content) {
		if (content == null)
			return;
		contentDescription.setText(content);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		String answer = event.getActionCommand();
		if(listener != null)
			listener.onAnswerChosen(answer);
	}
	
	public void setImage(String text, String url) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(url));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setLayout(new BorderLayout());
		setContentPane(new JLabel(new ImageIcon(img)));
		setLayout(new FlowLayout());
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		JLabel finalText = new JLabel(text);
		finalText.setBackground(new Color(255, 255 ,255));
		finalText.setOpaque(true);
		JButton exitButton = new JButton(Resources.Strings.exitButtonDesc);
		JButton logButton = new JButton(Resources.Strings.logButtonDesc);
		exitButton.setActionCommand(Resources.Strings.exitButtonDesc);
		exitButton.addActionListener(this);
		logButton.setActionCommand(Resources.Strings.logButtonDesc);
		logButton.addActionListener(this);
		add(finalText);
		add(exitButton);
		add(logButton);
		invalidate();
		validate();
	}

}
