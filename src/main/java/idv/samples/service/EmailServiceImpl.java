package idv.samples.service;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

@Service
public class EmailServiceImpl implements EmailService {
	
	private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Autowired
	private JavaMailSenderImpl mailSender;
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	@Async
	public void sendMailAsync(final String subject, final String toEmails, final String fromEmail, final String templateLocation, final Object data) {
		sendMail(subject, toEmails, fromEmail, templateLocation, data);
	}

	public void sendMail(final String subject, final String toEmails, final String fromEmail, final String templateLocation, final Object data) {
		log.info("Send email from {} to {} with message: {}", fromEmail, toEmails, data.toString());
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setSubject(subject);
				String[] tos = toEmails.split(";");
				for (String toEmail: tos) {
					message.addTo(toEmail);
				}
				message.setFrom(fromEmail);
				
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("data", data);
				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateLocation, "UTF-8", model);
				log.debug("Email content:\n{}", text);
				message.setText(text, true);
			}
		};
		mailSender.send(preparator);
	}
}
