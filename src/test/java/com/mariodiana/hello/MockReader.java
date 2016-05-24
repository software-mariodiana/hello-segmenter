package com.mariodiana.hello;

import java.io.IOException;

import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.component.CasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.util.Progress;


public class MockReader extends CasCollectionReader_ImplBase {
    public static final String DOCUMENT_TEXT = "";
    
    @ConfigurationParameter(name = DOCUMENT_TEXT)
    private String _documentText;
    
	// We allow 1 document and then set this to false.
	private boolean _available = true;

	@Override
	public void getNext(CAS cas) throws IOException, CollectionException {
		// This should only be called once, after which hasNext returns false.
		cas.setDocumentLanguage("en");
		cas.setDocumentText(_documentText);
	}

	@Override
	public Progress[] getProgress() {
		// TODO Auto-generated method stub
		return new Progress[] { getUpdatedProgress() };
	}

	@Override
	public boolean hasNext() throws IOException, CollectionException {
		boolean available = _available;
		_available = false;
		return available;
	}
	
	@SuppressWarnings("serial")
	private Progress getUpdatedProgress() {
        return new Progress() {
            @Override
            public long getCompleted() { return 1L; }
            @Override
            public long getTotal() { return 1L; }
			@Override
            public String getUnit() { return Progress.ENTITIES; }
            @Override
            public boolean isApproximate() { return false; }
        };
    }
}
