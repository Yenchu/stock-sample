package idv.samples.service;

public interface EmailService {

	public void sendMailAsync(final String subject, final String toEmails, final String fromEmail, final String templateLocation, final Object data);
	
	public void sendMail(final String subject, final String toEmails, final String fromEmail, final String templateLocation, final Object data);

}