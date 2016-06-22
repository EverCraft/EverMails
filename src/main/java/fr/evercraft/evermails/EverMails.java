package fr.evercraft.evermails;

import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;

import fr.evercraft.everapi.plugin.EPlugin;
import fr.evercraft.everapi.services.MailService;
import fr.evercraft.evermails.service.EMailService;

@Plugin(id = "fr.evercraft.evermails", 
		name = "EverMails", 
		version = "1.2", 
		description = "Sending mail",
		url = "http://evercraft.fr/",
		authors = {"rexbut"},
		dependencies = {
		    @Dependency(id = "fr.evercraft.everapi", version = "1.2"),
		    @Dependency(id = "fr.evercraft.everchat", optional = true)
		})
public class EverMails extends EPlugin {
	private EMConfig configs;
	private EMMessage messages;
	
	private EMailService service;
	
	@Override
	protected void onPreEnable() {
		this.configs = new EMConfig(this);
		
		this.messages = new EMMessage(this);
		
		this.service = new EMailService(this);
		this.getGame().getServiceManager().setProvider(this, MailService.class, this.service);
	}

	
	@Override
	protected void onCompleteEnable() {
		new EMCommand(this);
	}

	protected void onReload(){
		this.reloadConfigurations();
		this.service.reload();
	}
	
	protected void onDisable() {
	}

	/*
	 * Accesseurs
	 */
	
	public EMMessage getMessages(){
		return this.messages;
	}
	
	public EMConfig getConfigs() {
		return this.configs;
	}
	
	public EMailService getService() {
		return this.service;
	}
}
