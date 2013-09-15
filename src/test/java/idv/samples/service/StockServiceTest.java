package idv.samples.service;

import java.util.List;

import idv.samples.SpringUnitTest;
import idv.samples.service.StockService;
import idv.samples.to.Index;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class StockServiceTest extends SpringUnitTest {

	private static final Logger log = LoggerFactory.getLogger(StockServiceTest.class);
	
	@Autowired
	private StockService stockService;
	
	@Value("${index.code}")
	private String indexCode;
	
	@Test
	public void testCronJob() {
		try {
			Thread.sleep(10000);
		} catch(Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	@Test
	public void getIndex() {
		Index index = stockService.getIndex(indexCode);
		log.debug("index: {}", index);
	}
	
	@Test
	public void getIndexes() {
		String[] indexCodes = indexCode.split(",");
		List<Index> indexes = stockService.getIndexes(indexCodes);
		for (Index index: indexes) {
			log.debug("index: {}", index);
		}
	}
}
