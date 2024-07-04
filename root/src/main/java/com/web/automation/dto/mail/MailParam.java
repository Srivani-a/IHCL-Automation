package com.web.automation.dto.mail;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import com.ihclweb.automation.consts.IHCLConstantsWeb;
import com.ihclweb.automation.util.PropertyUtil;

/**
 * 
 * @author rajeevsai.t
 *
 */
public class MailParam {

	private String subject;
	private String bodyText;
	private List<Attachment> attachments;
	
	private MailConfig mailConfig;
	
	public MailParam() {
		mailConfig = new MailConfig();
		this.subject = PropertyUtil.getString(IHCLConstantsWeb.MAIL_SUBJECT) + new SimpleDateFormat(IHCLConstantsWeb.DD_MMMM_YYYY, Locale.US).format(new Date());
		this.bodyText = PropertyUtil.getString(IHCLConstantsWeb.MAIL_BODY_TEXT);
	}
	
	public MailParam(List<Attachment> attachments) {
		this();
		this.attachments = attachments;
	}
	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public String getSubject() {
		return subject;
	}

	public String getBodyText() {
		return bodyText;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public MailConfig getMailConfig() {
		return mailConfig;
	}

	/**
	 * 
	 * @author sairam.p
	 *
	 */
	public final class MailConfig{
		
		private String username;
		private String passcode;
		private InternetAddress from;
		private InternetAddress[] to;
		private String host;
		private String port;
		private String startTLS;
		private String smtpAuth;
		
		private boolean isMailTransportable; 
		
		public MailConfig() {
			username = PropertyUtil.getString(IHCLConstantsWeb.MAIL_USER);
			passcode = PropertyUtil.getString(IHCLConstantsWeb.MAIL_PASS);
			from = initFrom();
			host = PropertyUtil.getString(IHCLConstantsWeb.MAIL_HOST);
			port = PropertyUtil.getString(IHCLConstantsWeb.MAIL_PORT);
			to = initTo();
			startTLS = PropertyUtil.getString(IHCLConstantsWeb.MAIL_SMTP_TLS_ENABLE);
			smtpAuth = PropertyUtil.getString(IHCLConstantsWeb.MAIL_SMTP_AUTH);
			isMailTransportable = initTransportPermission();
		}

		private InternetAddress initFrom() {
			try {
				return new InternetAddress(PropertyUtil.getString(IHCLConstantsWeb.MAIL_FROM));
			}catch(AddressException e) {
				throw new IllegalArgumentException("Error occured while parsing the TO receipents : "+e);

			}
		}
		
		private InternetAddress[] initTo() {
			String[] mails = PropertyUtil.getString(IHCLConstantsWeb.MAIL_TO).split(",");
			InternetAddress[] addresses = new InternetAddress[mails.length];
			for(int i=0; i<mails.length; i++) {
				try {
					addresses[i] = new InternetAddress(mails[i]);
				} catch (AddressException e) {
					throw new IllegalArgumentException("Error occured while parsing the TO receipents : "+e);

				}
			}
			return addresses;
		}
		
		private boolean initTransportPermission() {
			String permission = PropertyUtil.getString(IHCLConstantsWeb.MAIL_IS_TRANSPORTABLE, "N");
			return (("Y").equalsIgnoreCase(permission) || ("true").equalsIgnoreCase(permission) || ("yes").equalsIgnoreCase(permission)) ? Boolean.TRUE.booleanValue() : Boolean.FALSE.booleanValue();

		}
		
		public String getUsername() {
			return username;
		}

		public String getPasscode() {
			return passcode;
		}

		public InternetAddress getFrom() {
			return from;
		}

		public InternetAddress[] getTo() {
			return to;
		}

		public String getHost() {
			return host;
		}

		public String getPort() {
			return port;
		}

		public String getStartTLS() {
			return startTLS;
		}

		public String getSmtpAuth() {
			return smtpAuth;
		}

		public boolean isMailTransportable() {
			return isMailTransportable;
		}

		@Override
		public String toString() {
			return "MailConfig [username=" + username + ", passcode=" + passcode + ", from=" + from + ", to="
					+ Arrays.toString(to) + ", host=" + host + ", port=" + port + ", startTLS=" + startTLS
					+ ", smtpAuth=" + smtpAuth + ", isMailTransportable=" + isMailTransportable + "]";
		}
	}

	@Override
	public String toString() {
		return "MailParam [subject=" + subject + ", bodyText=" + bodyText + ", attachments=" + attachments
				+ ", mailConfig=" + mailConfig + "]";
	}
}
