package com.mariodiana.hello;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpSegmenter;


public class HelloSegmenterTest {
	
	private final String DOCUMENT_TEXT = "The quick brown fox jumped over the lazy dog.";
	
	@Test
	public void engineShouldProcessJCas() throws UIMAException {
		JCas jcas = JCasFactory.createJCas();
		jcas.setDocumentLanguage("en");
		jcas.setDocumentText(DOCUMENT_TEXT);
		
		AnalysisEngine ae = AnalysisEngineFactory.createEngine(OpenNlpSegmenter.class);
		ae.process(jcas);
		
		String expected = DOCUMENT_TEXT.split(" ")[0];
		String result = JCasUtil.select(jcas, Token.class).iterator().next().getCoveredText();
		assertTrue(expected.equals(result));
	}
	
	@Test 
	public void engineDescriptionShouldProcessJCas() throws UIMAException, IOException {
		CollectionReaderDescription reader = CollectionReaderFactory.createReaderDescription(
				MockReader.class, 
				MockReader.DOCUMENT_TEXT, 
				DOCUMENT_TEXT);
		
		AnalysisEngineDescription aed = 
				AnalysisEngineFactory.createEngineDescription(OpenNlpSegmenter.class);
		
		JCas jcas = SimplePipeline.iteratePipeline(reader, aed).iterator().next();
		
		String expected = DOCUMENT_TEXT.split(" ")[0];
		String result = JCasUtil.select(jcas, Token.class).iterator().next().getCoveredText();
		assertTrue(expected.equals(result));
	}
}
