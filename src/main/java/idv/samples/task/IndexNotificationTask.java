package idv.samples.task;

import idv.samples.service.EmailService;
import idv.samples.service.StockService;
import idv.samples.to.Index;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class IndexNotificationTask {

	private static final Logger log = LoggerFactory.getLogger(IndexNotificationTask.class);

	@Autowired
	private StockService stockService;
	
	@Autowired
	private EmailService emailService;
	
	@Value("${index.code}")
	private String indexCode;
	
	@Value("${index.change.threshold:1.0F}")
	private float indexChangeThreshold;
	
	@Value("${index.notice.subject}")
	private String emailSubject;
	
	@Value("${index.notice.toEmail}")
	private String toEmail;
	
	@Value("${index.notice.fromEmail}")
	private String fromEmail;
	
	private String emailTemplate = "velocity/indexNotice.vm";
	
	@Scheduled(cron = "${index.notification.schedule:0 0 16 * * ?}")
	public void notice() {
		log.debug("Check if change of index {} is over {}.", indexCode, indexChangeThreshold);
		Index index = stockService.getIndex(indexCode);
		if (index == null) {
			return;
		}
		
		float change = index.getChange();
		if (change > -indexChangeThreshold && change < indexChangeThreshold) {
			return;
		}

		String name = index.getName();
		log.info("Change of {} is {} is over {}", name, change, indexChangeThreshold);
		
		StringBuilder buf = new StringBuilder();
		buf.append(name).append("(").append(change).append(")");
		
		String subject = MessageFormat.format(emailSubject, indexChangeThreshold, buf.toString());
		emailService.sendMail(subject.toString(), toEmail, fromEmail, emailTemplate, index);
	}
}
