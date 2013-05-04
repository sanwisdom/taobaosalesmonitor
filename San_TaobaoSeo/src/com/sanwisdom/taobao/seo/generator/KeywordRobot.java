package com.sanwisdom.taobao.seo.generator;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.sanwisdom.taobao.seo.reader.WorkBookColumnReader;

public class KeywordRobot {

	static Logger logger = Logger.getLogger(KeywordRobot.class);
	
	private static final String MAIN_KEY_WORD = "主关键字";
	
	private static final String MAIN_ADJ = "主形容词";
	
	private static final String SUB_ADJ = "形容词";
	
	private WorkBookColumnReader columnReader = null;
	
	protected WorkBookColumnReader getColumnReader() {
		if (null == columnReader) {
			columnReader = new WorkBookColumnReader();
		}
		return columnReader;
	}
	
	public Map<String, String> shuffle(Sheet sheet) {
		Map<String, String> keys = new LinkedHashMap<String, String>();
		Set<String> keywords = this.getMainKeywords(sheet);
		Set<String> mainAdjs = this.getMainAdjectives(sheet);
		Set<Set<String>> subAdjs = this.getSubAdjectives(sheet);
		getNounNoun(keys, keywords, mainAdjs, subAdjs);
		getNounNounAdj(keys, keywords, mainAdjs, subAdjs);
		getNounAdj(keys, keywords, mainAdjs, subAdjs);
		getNounMainMain(keys, keywords, mainAdjs, subAdjs);
		getNounMainSub(keys, keywords, mainAdjs, subAdjs);
		getNounSubSub(keys, keywords, mainAdjs, subAdjs);
		return keys;
	}
	
	private void getNounSubSub(Map<String, String> keys, Set<String> keywords,
			Set<String> mainAdjs, Set<Set<String>> subAdjs) {
		int count = 0;
		for(String key : keywords) {
			int i = 0;
			for(Set<String> subs1 : subAdjs) {
				int j = i;
				for(Set<String> subs2 : subAdjs) {
					if (!subs1.equals(subs2) && j > i) {
						for(String sub1 : subs1) {
							for(String sub2 : subs2) {
								String keyword = key + " " + sub1 + " " + sub2;
								logger.debug(keyword);
								keys.put(keyword, KeywordPattern.N_AND_ADJ2_ADJ2.getPatternName());
								count++;
							}
						}
					}
					j++;
				}
				i++;
			}
		}
		logger.debug(KeywordPattern.N_AND_ADJ2_ADJ2.getPatternName() + "======" + count);
	}

	private void getNounMainSub(Map<String, String> keys, Set<String> keywords,
			Set<String> mainAdjs, Set<Set<String>> subAdjs) {
		Set<String> mixedSubs = new HashSet<String>();
		for (Set<String> subs : subAdjs) {
			mixedSubs.addAll(subs);
		}
		int count = 0;
		for(String key : keywords) {
			for(String main : mainAdjs) {
				for(String sub : mixedSubs) {
					if (!main.equals(sub)) {
						String keyword = key + " " + main + " " + sub;
						logger.debug(keyword);
						keys.put(keyword, KeywordPattern.N_AND_ADJ1_ADJ2.getPatternName());
						count++;
					}
				}
			}
		}
		logger.debug(KeywordPattern.N_AND_ADJ1_ADJ2.getPatternName() + "======" + count);
	}

	private void getNounMainMain(Map<String, String> keys,
			Set<String> keywords, Set<String> mainAdjs, Set<Set<String>> subAdjs) {
		int count = 0;
		String[] mainAdjsArray = mainAdjs.toArray(new String[0]);
		for(String key : keywords) {
			for(int i = 0; i < mainAdjsArray.length; i++) {
				for(int j = i; j < mainAdjsArray.length; j++) {
					String main1 = mainAdjsArray[i];
					String main2 = mainAdjsArray[j];
					if (!main1.equals(main2)) {
						String keyword = key + " " + main1 + " " + main2;
						logger.debug(keyword);
						keys.put(keyword, KeywordPattern.N_AND_ADJ1_ADJ1.getPatternName());
						count++;
					}
				}
			}
		}
		logger.debug(KeywordPattern.N_AND_ADJ1_ADJ1.getPatternName() + "======" + count);
	}

	private void getNounNounAdj(Map<String, String> keys, Set<String> keywords,
			Set<String> mainAdjs, Set<Set<String>> subAdjs) {
		Set<String> mixed = new HashSet<String>();
		mixed.addAll(mainAdjs);
		for (Set<String> subs : subAdjs) {
			mixed.addAll(subs);
		}
		int count = 0;
		String[] keywordsArray = keywords.toArray(new String[0]);
		for(int i = 0; i < keywordsArray.length; i++) {
			for(int j = i; j < keywordsArray.length; j++) {
				String key1 = keywordsArray[i];
				String key2 = keywordsArray[j];
				if (!key1.equals(key2)) {
					for (String adj : mixed) {
						String keyword = key1 + " " + key2 + " " + adj;
						logger.debug(keyword); 
						keys.put(keyword, KeywordPattern.N_AND_N_AND_ADJ.getPatternName());
						count++;
					}
				}
			}
		}
		logger.debug(KeywordPattern.N_AND_N_AND_ADJ.getPatternName() + "======" + count);
	}

	private void getNounNoun(Map<String, String> keys, Set<String> keywords,
			Set<String> mainAdjs, Set<Set<String>> subAdjs) {
		int count = 0;
		String[] keywordsArray = keywords.toArray(new String[0]);
		for(int i = 0; i < keywordsArray.length; i++) {
			for(int j = i; j < keywordsArray.length; j++) {
				String key1 = keywordsArray[i];
				String key2 = keywordsArray[j];
				if (!key1.equals(key2)) {
					String keyword = key1 + " " + key2;
					logger.debug(keyword);
					keys.put(keyword, KeywordPattern.N_AND_N.getPatternName());
					count++;
				}
			}
		}
		logger.debug(KeywordPattern.N_AND_N.getPatternName() + "======" + count);
	}

	private void getNounAdj(Map<String, String> keys, Set<String> keywords,
			Set<String> mainAdjs, Set<Set<String>> subAdjs) {
		Set<String> mixed = new HashSet<String>();
		mixed.addAll(mainAdjs);
		for (Set<String> subs : subAdjs) {
			mixed.addAll(subs);
		}
		int count = 0;
		for (String key : keywords) {
			for (String adj : mixed) {
				String keyword = key + " " + adj;
				logger.debug(keyword);
				keys.put(keyword, KeywordPattern.N_AND_ADJ.getPatternName());
				count++;
			}
		}
		logger.debug(KeywordPattern.N_AND_ADJ.getPatternName() + "======" + count);
	}
	
	protected Set<String> getMainAdjectives(Sheet sheet) {
		return this.getColumnReader().readColumnByHeaderValue(sheet, MAIN_ADJ);
	}
	
	protected Set<String> getMainKeywords(Sheet sheet) {
		return this.getColumnReader().readColumnByHeaderValue(sheet, MAIN_KEY_WORD);
	}
	
	protected Set<Set<String>> getSubAdjectives(Sheet sheet) {
		Row header = sheet.getRow(0);
		Set<Set<String>> subAdjs =new HashSet<Set<String>>();
		for (Cell headerCell : header) {
			String headerTitle = headerCell.getStringCellValue();
			if (!headerTitle.equals(SUB_ADJ)
					&& headerTitle.startsWith(SUB_ADJ)) {
				Set<String> adjs = this.getColumnReader().readColumnByHeaderValue(sheet, headerTitle);
				if (!adjs.isEmpty()) {					
					subAdjs.add(adjs);
				}
			} 
		}
		return subAdjs;
	}
}
