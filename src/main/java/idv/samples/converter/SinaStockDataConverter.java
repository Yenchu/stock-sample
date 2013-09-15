package idv.samples.converter;

import idv.samples.to.Index;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SinaStockDataConverter implements StockDataConverter {
	
	private static final Logger log = LoggerFactory.getLogger(SinaStockDataConverter.class);

	private Pattern pattern = Pattern.compile(".*=\"(.*)\";.*", Pattern.DOTALL);
	
	public Index toIndex(String data) {
		//数据含义分别为：指数名称，当前点数，当前价格，涨跌率，成交量（手），成交额（万元）；
		//var hq_str_s_sh000001="上证指数,2240.030,-1.238,-0.06,768345,5962064";
		if (StringUtils.isBlank(data)) {
			log.debug("The index data is blank: {}", data);
			return null;
		}
		
		Index index = null;
		Matcher matcher = pattern.matcher(data);
		if (matcher.matches()) {
			String[] values = matcher.group(1).split(",");
			String name = values[0];
			float point = NumberUtils.toFloat(values[1], 0.0F);
			float price = NumberUtils.toFloat(values[2], 0.0F);
			float change = NumberUtils.toFloat(values[3], 0.0F);
			int volumn = NumberUtils.toInt(values[4], 0);
			long value = NumberUtils.toLong(values[5], 0L);
			
			index = new Index();
			index.setName(name);
			index.setPoint(point);
			index.setPrice(price);
			index.setChange(change);
			index.setVolumn(volumn);
			index.setValue(value);
		} else {
			log.error("Can not convert index data: {}", data);
		}
		return index;
	}
	
	public List<Index> toIndexes(String multilineData) {
		String[] dataArr = multilineData.split("\n");
		List<Index> indexes = new ArrayList<Index>();
		for (String data: dataArr) {
			Index index = toIndex(data);
			if (index != null) {
				indexes.add(index);
			}
		}
		return indexes;
	}
}
