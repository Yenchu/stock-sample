package idv.samples.converter;

import idv.samples.to.Index;

import java.util.List;

public interface StockDataConverter {

	public Index toIndex(String data);

	public List<Index> toIndexes(String data);

}