package fr.evercraft.evermails;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import fr.evercraft.everapi.plugin.file.EConfig;
import fr.evercraft.everapi.plugin.file.EMessage;
import fr.evercraft.everapi.services.MailService;

public class EMConfig extends EConfig {

	public EMConfig(final EverMails plugin) {
		super(plugin);
	}
	
	public void reload() {
		super.reload();
		this.plugin.getLogger().setDebug(this.isDebug());
	}
	
	@Override
	public void loadDefault() {
		addDefault("debug", false, "Displays plugin performance in the logs");
		addDefault("language", EMessage.ENGLISH, "Select language messages", "Examples : ", "  French : FR_fr", "  English : EN_en");
		
		addDefault("logger", false);
		
		addComment("smtp", 	"Examples :",
						 	"	Gmail: ",
						 	"		- auth : true",
						 	"		- starttls : true",
						 	"		- host : smtp.gmail.com",
						 	"		- port : 587",
						 	"		- username : username@gmail.com",
						 	"		- password : *********",
						 	"	No authentification :",
						 	"		- auth : false",
						 	"		- host : smtp.evercraft.fr",
						 	"		- port : 25",
						 	"		- username : username@evercraft.fr");
		addDefault("smtp.auth", false);
		addDefault("smtp.starttls", false);
		addDefault("smtp.username", "contact@evercraft.fr");
		addDefault("smtp.password", "password");
		addDefault("smtp.host", "evercraft.fr");
		addDefault("smtp.port", "587");
		
		
		if(this.get("mails").getValue() == null) {
			addComment("mails", "");
			
			addDefault("mails.rexbut", "rexbut@evercraft.fr");
			addDefault("mails.lesbleu", "lesbleu@evercraft.fr");
		}
	}
	
	public boolean getAuthentification() {
		return this.get("smtp.auth").getBoolean(false);
	}
	
	public boolean getStarttls() {
		return this.get("smtp.starttls").getBoolean(false);
	}
	
	public String getHost() {
		return this.get("smtp.host").getString("localhost");
	}
	
	public String getPort() {
		return this.get("smtp.port").getString("587");
	}
	
	public String getUserName() {
		return this.get("smtp.username").getString("contact@evercraft.fr");
	}
	
	public String getPassword() {
		return this.get("smtp.password").getString("");
	}
	
	public boolean getLogger() {
		return this.get("logger").getBoolean(false);
	}
	
	public Map<String, String> getMails() {
		Map<String, String> map = new HashMap<String, String>();
		Pattern pattern = Pattern.compile(MailService.EMAIL_PATTERN);
		for(Entry<Object, ? extends CommentedConfigurationNode> mail : this.get("mails").getChildrenMap().entrySet()) {
			if(mail.getKey() instanceof String) {
				String adress = mail.getValue().getString(null);
				if(adress != null && pattern.matcher(adress).matches()) {
					map.put((String) mail.getKey(), adress);
				}
			}
		}
		return map;
	}
	
	public void setMail(String identifier, String address) {
		this.plugin.getConfigs().getNode().getNode("mails", identifier).setValue(address);
	}

	public void removeMail(String identifier) {
		this.plugin.getConfigs().getNode().getNode("mails").removeChild(identifier);
	}

}
