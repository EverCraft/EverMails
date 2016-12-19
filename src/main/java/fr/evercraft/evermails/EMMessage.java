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

import fr.evercraft.everapi.message.EMessageFormat;
import fr.evercraft.everapi.message.format.EFormatString;
import fr.evercraft.everapi.plugin.file.EMessage;
import fr.evercraft.everapi.plugin.file.EnumMessage;

public class EMMessage extends EMessage<EverMails> {

	public EMMessage(final EverMails plugin) {
		super(plugin, EMMessages.values());
	}
	
	public enum EMMessages implements EnumMessage {
		PREFIX("prefix", "[&4Ever&6&lMails&f] "),
		DESCRIPTION("description", "Gestion des mails"),
		
		LIST_DESCRIPTION("list.description", "Affiche la liste des adresses mails", "See list of emails"),
		LIST_TITLE("list.title", "&aLa liste des adresses mails", "&aThe list of emails"),
		LIST_LINE("list.line", "    &6&l➤  <player> : &7<address>"),
		LIST_EMPTY("list.empty", "&7Aucune adresse mail", "No email"),
		
		SET_DESCRIPTION("set.description", "Défini une adresse mail", "Set an e-mail"),
		SET_PLAYER("set.player", "&7L'adresse mail de &6<player> &7a été défini en tant que &6<address>&7."),
		SET_EQUALS("set.equals", "&7Vous avez défini votre adresse mail en &6<address>&7."),
		SET_ERROR_PATTERN("set.errorPattern", "&cL'adresse mail n'a pas un format valide."),
		SET_ERROR_EQUALS("set.errorEquals", "&cLes adresses mails sont identiques."),
		
		DELETE_DESCRIPTION("delete.description", "Supprime une adresse mail", "Delete an email"),
		DELETE_PLAYER("delete.player", "&7L'adresse mail de &6<player> &7 a été supprimé"),
		DELETE_EQUALS("delete.equals", "&7Vous avez supprimé votre adresse mail."),
		DELETE_ERROR_PLAYER("delete.errorPlayer", "&6<identifier> &cn'a pas d'adresse mail."),
		DELETE_ERROR_EQUALS("delete.errorEquals", "&cVous n'avez pas d'adresse mail."),
		
		ALERT_DESCRIPTION("alert.description", "Envoie un message à tous les adresses mails", "Sends a message to all emails"),
		ALERT_OBJECT("alert.object", "Avertissement de <player>"),
		ALERT_MESSAGE("alert.message", "<message>"),
		ALERT_PLAYER("alert.player", "&7Votre message d'avertissement a bien était envoyé."),
		ALERT_ERROR("alert.error", "&cIl n'y a aucune adresse mail d'enregistré"),
		
		SEND_DESCRIPTION("send.description", "Envoie un mail à un joueur", "Send an email to a player"),
		SEND_OBJECT("send.object", "Message de <player>", "Message of <player>"),
		SEND_MESSAGE("send.message", "<message>"),
		SEND_PLAYER("send.player", "&7Votre message a bien était envoyé à &6<player>&7.", "&7Your message has well was sent to &6<player>&7."),
		SEND_EQUALS("send.equals", "&7Vous vous êtes bien envoyé un message.", "&7You have successfully sent a message."),
		SEND_ERROR("send.error", "&cIl n'y a aucune adresse mail d'enregistré au nom de &6<player>&7.", "&cIl n'y a aucune adresse mail d'enregistré au nom de &6<player>&7.");
		
		private final String path;
	    private final EMessageFormat french;
	    private final EMessageFormat english;
	    private EMessageFormat message;
	    
	    private EMMessages(final String path, final String french) {   	
	    	this(path, 
	    		EMessageFormat.builder().chat(new EFormatString(french), true).build(), 
	    		EMessageFormat.builder().chat(new EFormatString(french), true).build());
	    }
	    
	    private EMMessages(final String path, final String french, final String english) {   	
	    	this(path, 
	    		EMessageFormat.builder().chat(new EFormatString(french), true).build(), 
	    		EMessageFormat.builder().chat(new EFormatString(english), true).build());
	    }
	    
	    private EMMessages(final String path, final EMessageFormat french) {   	
	    	this(path, french, french);
	    }
	    
	    private EMMessages(final String path, final EMessageFormat french, final EMessageFormat english) {
	    	Preconditions.checkNotNull(french, "Le message '" + this.name() + "' n'est pas définit");
	    	
	    	this.path = path;	    	
	    	this.french = french;
	    	this.english = english;
	    	this.message = french;
	    }

	    public String getName() {
			return this.name();
		}
	    
		public String getPath() {
			return this.path;
		}

		public Object getFrench() {
			return this.french;
		}

		public Object getEnglish() {
			return this.english;
		}
		
		public EMessageFormat getMessage() {
			return this.message;
		}
		
		public void set(EMessageFormat message) {
			this.message = message;
		}
	}
}
