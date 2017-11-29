package com.sample;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.kie.api.runtime.KieSession;

import javafx.util.Pair;

public class Presenter {
	
	private View view;
	private Model model;
	private KieSession kSession;
	
	public Presenter(KieSession kSession) {
		this.kSession = kSession;
		view = new View(new OnViewClicked() {
			@Override
			public void onAnswerChosen(String answer) {
				switch (answer) {
				case(Resources.Strings.beginButtonDesc):
					kSession.fireAllRules();
					break;
				
				case(Resources.Strings.exitButtonDesc):
					view.dispose();
					break;
					
				case(Resources.Strings.logButtonDesc):
					try {
						saveLogToFile();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case(Resources.Strings.retryButtonDesc):
				view.dispose();
				MainActivity.launch();
				break;
					
				}
				
				

			}
		}, Resources.Strings.welcomeText, new String[] {Resources.Strings.beginButtonDesc, Resources.Strings.exitButtonDesc});
		model = new Model();
		view.setVisible(true);
	}
	
	public void setContent(String content) {
		model.setContent(content);
		model.setAnswers(null);
	}
	
	public void setQuestion(String question, String[] answerList) {
		model.setContent(question);
		model.setAnswers(answerList);
		
		view.setContent(question);
		view.addButtons(answerList);
	}
	
	public void logAnswer(String question, String[] answerList, String answer) {
		model.setContent(question);
		model.setAnswers(answerList);
		model.logAnswer(answer);
	}
	
	public String getLog(){
		HashMap<Integer, Pair<String, String>>log = model.getLogMap();
		Set<Integer> keyset = log.keySet();
		List<Integer> keyList = new ArrayList<>(keyset);
		Collections.sort(keyList);
		StringBuilder builder = new StringBuilder();
		for(Integer value : keyList) {
			Pair<String, String> questionAnswer = log.get(value);
			builder.append(questionAnswer.getKey());
			builder.append(" --> ");
			builder.append(questionAnswer.getValue());
			builder.append("\n");
		}
		return builder.toString();
	}
	
	
	public void displayFinalScreen(String finalAnswer, String finalUrl) {
		if(view != null)
			view.setImage(finalAnswer, finalUrl);
	}
	
	private void saveLogToFile() throws Exception {
		File saveDir = new File(Resources.Strings.saveFileDir);
		if(!saveDir.exists()) {
			if(!saveDir.mkdirs())
				throw new Exception("Can not create directory for file");
		}
		File saveFile = new File(saveDir, Resources.Strings.saveFileName);
		if(saveFile.exists())
			if(!saveFile.delete())
				throw new Exception("Can not delete old file");
		saveFile = new File(saveDir, Resources.Strings.saveFileName);
		saveFile.createNewFile();
		FileWriter writer = new FileWriter(saveFile);
		writer.append(getLog());
		writer.flush();
		writer.close();
		JOptionPane.showMessageDialog(null,
			    "Wynik zapisany do pliku " + Resources.Strings.saveFileDir + Resources.Strings.saveFileName);
	}

}
