package idv.samples;

import idv.samples.converter.SinaStockDataConverter;
import idv.samples.to.Index;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMain {

	private static final Logger log = LoggerFactory.getLogger(TestMain.class);

	public static void main(String[] args) {
		convertStockData();
	}
	
	static Index convertStockData() {
		String data = "var hq_str_s_sh000001=\"上证指数,2240.030,-1.238,-0.06,768345,5962064\";";
		SinaStockDataConverter converter = new SinaStockDataConverter();
		Index index = converter.toIndex(data);
		log.debug("{}", index);
		return index;
	}
}
