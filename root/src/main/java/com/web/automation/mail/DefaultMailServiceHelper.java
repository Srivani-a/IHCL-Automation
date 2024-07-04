package com.web.automation.mail;

import java.util.List;
import java.util.Objects;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.ihclweb.automation.consts.IHCLConstantsWeb;
import com.ihclweb.automation.util.PropertyUtil;
import com.web.automation.dto.mail.Attachment;
import com.web.automation.dto.mail.MailParam;
import com.web.automation.dto.mail.MailParam.MailConfig;
import com.web.automation.dto.pdfreport.SuitesResult;

/**
 * 
 * @author rajeevsai.t
 *
 */
public class DefaultMailServiceHelper {

	public static final String TOTAL_TEST_CASES_EXECUTED = "{TOTAL_TEST_CASES_EXECUTED}";
	public static final String TEST_CASES_PASSED = "{TEST_CASES_PASSED}";
	public static final String TEST_CASES_FAILED = "{TEST_CASES_FAILED}";
	public static final String TEST_CASES_SKIPPED = "{TEST_CASES_SKIPPED}";
	public static final boolean STATS_IN_MAIL_ENABLED = PropertyUtil.getBoolean("stats.in-mail-body.enabled", true);
	public static final boolean IS_SKIPPED_ENABLED = PropertyUtil.getBoolean("stats.in-mail-body.enabled", true);
	
	private BodyPart bodyPart; 
	
	private static final Logger LOG = Logger.getLogger(DefaultMailServiceHelper.class.getName());
	
	public boolean isAttachmentsAvailable(MailParam mailParam) {
		return (Objects.nonNull(mailParam)) ? isAttachmentsAvailable(mailParam.getAttachments()) : Boolean.FALSE.booleanValue();
	}
	
	public boolean isAttachmentsAvailable(List<Attachment> attachments) {
		return (Objects.nonNull(attachments) && !attachments.isEmpty()) ? Boolean.TRUE.booleanValue() : Boolean.FALSE.booleanValue();
	}
	
	public String getBodyText(SuitesResult suitesResult) {
		if(STATS_IN_MAIL_ENABLED){
			String body = PropertyUtil.getString(IHCLConstantsWeb.MAIL_BODY_TEXT);
			if(IS_SKIPPED_ENABLED){
				return body.replace(TOTAL_TEST_CASES_EXECUTED, String.valueOf(suitesResult.totalTestsExecuted())).replace(TEST_CASES_PASSED, String.valueOf(suitesResult.totalTestsPassed())).replace(TEST_CASES_FAILED, String.valueOf(suitesResult.totalTestsFailed())).replace(TEST_CASES_SKIPPED, String.valueOf(suitesResult.totalTestsSkipped()));
			} else {
				return body.replace(TOTAL_TEST_CASES_EXECUTED, String.valueOf(suitesResult.totalTestsExecuted())).replace(TEST_CASES_PASSED, String.valueOf(suitesResult.totalTestsPassed())).replace(TEST_CASES_FAILED, String.valueOf(suitesResult.totalTestsFailed())).replace("# Test Cases Skipped: {TEST_CASES_SKIPPED}", StringUtils.EMPTY);
			}
		}
		return StringUtils.EMPTY;
	}
	
	public boolean isMandateDataAvailable(MailParam mailParam, SuitesResult suitesResult) {
		if(LOG.isDebugEnabled())
			LOG.debug("Validating "+mailParam+" and "+suitesResult);
		return (Objects.isNull(mailParam) || Objects.isNull(suitesResult) || StringUtils.isBlank(mailParam.getBodyText()) || !mailParam.getMailConfig().isMailTransportable()) ? Boolean.FALSE.booleanValue() : Boolean.TRUE.booleanValue();
	}

	public Properties getMailProperties(MailConfig config) {
		Properties props = System.getProperties();
		props.put(IHCLConstantsWeb.MAIL_SMTP_TLS_ENABLE, config.getStartTLS());
		props.put("mail.smtp.host", config.getHost());
		props.put("mail.smtp.user", config.getFrom().toString());
		props.put("mail.smtp.password", config.getPasscode());
		props.put("mail.smtp.port", config.getPort());
		props.put(IHCLConstantsWeb.MAIL_SMTP_AUTH, config.getSmtpAuth());
		return props;
	}
	
	public void attachBodyParts(Multipart multipart, List<Attachment> attachments) throws MessagingException {
		if(Objects.nonNull(multipart) && isAttachmentsAvailable(attachments)) {
			for(Attachment attachment : attachments) {
				bodyPart = new MimeBodyPart();
				bodyPart.setDataHandler(new DataHandler(new FileDataSource(attachment.getPath().toString())));
				bodyPart.setFileName(attachment.getConfigurableName().toString());
				multipart.addBodyPart(bodyPart);
			}
		}else {
			LOG.debug("No Attachment(s) available to attach."+attachments);
		}
		}
	
	public void setBodyText(Multipart multipart, SuitesResult suitesResult) throws MessagingException {
		bodyPart = new MimeBodyPart();
		bodyPart.setText(getBodyText(suitesResult));
		multipart.addBodyPart(bodyPart);
	}
}