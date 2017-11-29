package com.sample;

import java.util.HashMap;

import javafx.util.Pair;

public class ModelQuestion {
	private String content;
	private String[] answerList;
	private String chosenAnswer;
	

	public ModelQuestion(String question, String[] answerList){
		this.content = question;
		this.answerList = answerList;
	}
	
	public void setAnswers(String[] answers){
		this.answerList = answers;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	

	public String getContent() {
		return content;
	}

	public String[] getAnswerList() {
		return answerList;
	}

	public String getChosenAnswer() {
		return chosenAnswer;
	}

	public void setChosenAnswer(String chosenAnswer) {
		this.chosenAnswer = chosenAnswer;
	}

 

}
