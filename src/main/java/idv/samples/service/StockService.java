package idv.samples.service;

import java.util.List;

import idv.samples.to.Index;

public interface StockService {

	public Index getIndex(String code);

	public List<Index> getIndexes(String... codeArr);
	
}