package com.sample;

import java.util.HashMap;

import javafx.util.Pair;

public class Model {
	private String content;
	private String[] answerList;
	private HashMap<Integer, Pair<String, String>> logMap;
	
	Model(){
		logMap = new HashMap<>();
	}
	
	void setAnswers(String[] answers){
		this.answerList = answers;
	}
	
	void setContent(String content) {
		this.content = content;
	}
	
	void logAnswer(String answer) {
		Pair<String, String> questionAnswer = new Pair<>(content, answer);
		int keysetSize = logMap.keySet().size();
		logMap.put(keysetSize, questionAnswer);
	}

	public String getContent() {
		return content;
	}

	public String[] getAnswerList() {
		return answerList;
	}

	public HashMap<Integer, Pair<String, String>> getLogMap() {
		return logMap;
	}
	

}
