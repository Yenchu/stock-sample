package idv.samples.service;

import idv.samples.converter.SinaStockDataConverter;
import idv.samples.converter.StockDataConverter;
import idv.samples.to.Index;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StockServiceImpl implements StockService {
	
	private static final Logger log = LoggerFactory.getLogger(StockServiceImpl.class);

	@Value("${stock.service.url}")
	private String stockServiceUrl;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private StockDataConverter stockDataConverter = new SinaStockDataConverter();
	
	public Index getIndex(String code) {
		log.debug("index code: {}", code);
		String data = restTemplate.getForObject(stockServiceUrl, String.class, code);
		log.debug("index data:\n{}", data);
		Index index = stockDataConverter.toIndex(data);
		return index;
	}
	
	public List<Index> getIndexes(String... codeArr) {
		String codes = StringUtils.join(codeArr, ",");
		log.debug("index codes: {}", codes);
		String data = restTemplate.getForObject(stockServiceUrl, String.class, codes);
		log.debug("index data:\n{}", data);
		List<Index> indexes = stockDataConverter.toIndexes(data);
		return indexes;
	}
}
