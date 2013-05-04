package com.sanwisdom.taobao.seo.generator;

public enum KeywordPattern {

	N_AND_N("Noun+Noun"),
	N_AND_N_AND_ADJ("Noun+Noun+Adj"),
	N_AND_ADJ("Noun+Adj"),
	N_AND_ADJ1_ADJ2("Noun+Main Adj+Sub Adj"),
	N_AND_ADJ2_ADJ2("Noun+Sub Adj+Sub Adj"),
	N_AND_ADJ1_ADJ1("Noun+Main Adj+Main Adj");
	
	private String patternName;
	
	public String getPatternName() {
		return patternName;
	}

	KeywordPattern(String patternName) {
		this.patternName = patternName;
	}
	
}
