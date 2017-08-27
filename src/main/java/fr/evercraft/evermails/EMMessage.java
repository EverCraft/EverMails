/*
 * This file is part of EverMails.
 *
 * EverMails is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EverMails is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with EverMails.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.evercraft.evermails;

import com.google.common.base.Preconditions;

import fr.evercraft.everapi.message.EMessageBuilder;
import fr.evercraft.everapi.message.EMessageFormat;
import fr.evercraft.everapi.message.format.EFormatString;
import fr.evercraft.everapi.plugin.file.EMessage;
import fr.evercraft.everapi.plugin.file.EnumMessage;

public class EMMessage extends EMessage<EverMails> {

	public EMMessage(final EverMails plugin) {
		super(plugin, EMMessages.values());
	}
	
	public enum EMMessages implements EnumMessage {
		PREFIX("PREFIX", "[&4Ever&6&lMails&f] "),
		DESCRIPTION("DESCRIPTION", "Gestion des mails"),
		
		LIST_DESCRIPTION("listDescription", "Affiche la liste des adresses mails", "See list of emails"),
		LIST_TITLE("listTitle", "&aLa liste des adresses mails", "&aThe list of emails"),
		LIST_LINE("listLine", "    &6&l➤  <player> : &7<address>"),
		LIST_EMPTY("listEmpty", "&7Aucune adresse mail", "No email"),
		
		SET_DESCRIPTION("setDescription", "Défini une adresse mail", "Set an e-mail"),
		SET_PLAYER("setPlayer", "&7L'adresse mail de &6<player> &7a été défini en tant que &6<address>&7."),
		SET_EQUALS("setEquals", "&7Vous avez défini votre adresse mail en &6<address>&7."),
		SET_ERROR_PATTERN("setErrorPattern", "&cL'adresse mail n'a pas un format valide."),
		SET_ERROR_EQUALS("setErrorEquals", "&cLes adresses mails sont identiques."),
		
		DELETE_DESCRIPTION("deleteDescription", "Supprime une adresse mail", "Delete an email"),
		DELETE_PLAYER("deletePlayer", "&7L'adresse mail de &6<player> &7 a été supprimé"),
		DELETE_EQUALS("deleteEquals", "&7Vous avez supprimé votre adresse mail."),
		DELETE_ERROR_PLAYER("deleteErrorPlayer", "&6<identifier> &cn'a pas d'adresse mail."),
		DELETE_ERROR_EQUALS("deleteErrorEquals", "&cVous n'avez pas d'adresse mail."),
		
		ALERT_DESCRIPTION("alertDescription", "Envoie un message à tous les adresses mails", "Sends a message to all emails"),
		ALERT_OBJECT("alertObject", "Avertissement de <player>"),
		ALERT_MESSAGE("alertMessage", "<message>"),
		ALERT_PLAYER("alertPlayer", "&7Votre message d'avertissement a bien était envoyé."),
		ALERT_ERROR("alertError", "&cIl n'y a aucune adresse mail d'enregistré"),
		
		SEND_DESCRIPTION("sendDescription", "Envoie un mail à un joueur", "Send an email to a player"),
		SEND_OBJECT("sendObject", "Message de <player>", "Message of <player>"),
		SEND_MESSAGE("sendMessage", "<message>"),
		SEND_PLAYER("sendPlayer", "&7Votre message a bien était envoyé à &6<player>&7.", "&7Your message has well was sent to &6<player>&7."),
		SEND_EQUALS("sendEquals", "&7Vous vous êtes bien envoyé un message.", "&7You have successfully sent a message."),
		SEND_ERROR("sendError", "&cIl n'y a aucune adresse mail d'enregistré au nom de &6<player>&7.", "&cIl n'y a aucune adresse mail d'enregistré au nom de &6<player>&7.");
		
		private final String path;
	    private final EMessageBuilder french;
	    private final EMessageBuilder english;
	    private EMessageFormat message;
	    private EMessageBuilder builder;
	    
	    private EMMessages(final String path, final String french) {   	
	    	this(path, EMessageFormat.builder().chat(new EFormatString(french), true));
	    }
	    
	    private EMMessages(final String path, final String french, final String english) {   	
	    	this(path, 
	    		EMessageFormat.builder().chat(new EFormatString(french), true), 
	    		EMessageFormat.builder().chat(new EFormatString(english), true));
	    }
	    
	    private EMMessages(final String path, final EMessageBuilder french) {   	
	    	this(path, french, french);
	    }
	    
	    private EMMessages(final String path, final EMessageBuilder french, final EMessageBuilder english) {
	    	Preconditions.checkNotNull(french, "Le message '" + this.name() + "' n'est pas définit");
	    	
	    	this.path = path;	    	
	    	this.french = french;
	    	this.english = english;
	    	this.message = french.build();
	    }

	    public String getName() {
			return this.name();
		}
	    
		public String getPath() {
			return this.path;
		}

		public EMessageBuilder getFrench() {
			return this.french;
		}

		public EMessageBuilder getEnglish() {
			return this.english;
		}
		
		public EMessageFormat getMessage() {
			return this.message;
		}
		
		public EMessageBuilder getBuilder() {
			return this.builder;
		}
		
		public void set(EMessageBuilder message) {
			this.message = message.build();
			this.builder = message;
		}
	}
}
