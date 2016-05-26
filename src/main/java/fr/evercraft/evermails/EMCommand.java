/**
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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.LiteralText.Builder;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import fr.evercraft.everapi.EAMessage.EAMessages;
import fr.evercraft.everapi.plugin.EChat;
import fr.evercraft.everapi.plugin.ECommand;
import fr.evercraft.everapi.services.pagination.ESubCommand;

public class EMCommand extends ECommand<EverMails> {

	public EMCommand(final EverMails plugin) {
		super(plugin, "evermails");
    }
	
	@Override
	public boolean testPermission(final CommandSource source) {
		return source.hasPermission(this.plugin.getPermissions().get("EVERMAILS"));
	}
	
	@Override
	public Text description(final CommandSource source) {
		return this.plugin.getMessages().getText("DESCRIPTION");
	}

	@Override
	public Text help(final CommandSource source) {
		boolean perm_help = source.hasPermission(this.plugin.getPermissions().get("HELP"));
		boolean perm_reload = source.hasPermission(this.plugin.getPermissions().get("RELOAD"));
		boolean perm_list = source.hasPermission(this.plugin.getPermissions().get("LIST"));
		boolean perm_set = source.hasPermission(this.plugin.getPermissions().get("SET"));
		boolean perm_delete = source.hasPermission(this.plugin.getPermissions().get("DELETE"));
		boolean perm_alert = source.hasPermission(this.plugin.getPermissions().get("ALERT"));
		boolean perm_send = source.hasPermission(this.plugin.getPermissions().get("SEND"));
		
		Text help;
		if(perm_help || perm_reload || perm_list || perm_set || perm_delete || perm_alert || perm_send){
			Builder build = Text.builder("/" + this.getName() + " <");
			if(perm_help){
				build = build.append(Text.builder("help").onClick(TextActions.suggestCommand("/" + this.getName() + " help")).build());
				if(perm_reload || perm_list || perm_set || perm_delete || perm_alert || perm_send){
					build = build.append(Text.builder("|").build());
				}
			}
			if(perm_reload){
				build = build.append(Text.builder("reload").onClick(TextActions.suggestCommand("/" + this.getName() + " reload")).build());
				if(perm_list || perm_set || perm_delete || perm_alert || perm_send){
					build = build.append(Text.builder("|").build());
				}
			}
			if(perm_list){
				build = build.append(Text.builder("list").onClick(TextActions.suggestCommand("/" + this.getName() + " list")).build());
				if(perm_set || perm_delete || perm_alert || perm_send){
					build = build.append(Text.builder("|").build());
				}
			}
			if(perm_set){
				build = build.append(Text.builder("set").onClick(TextActions.suggestCommand("/" + this.getName() + " set")).build());
				if(perm_delete || perm_alert || perm_send){
					build = build.append(Text.builder("|").build());
				}
			}
			if(perm_delete){
				build = build.append(Text.builder("delete").onClick(TextActions.suggestCommand("/" + this.getName() + " delete")).build());
				if(perm_alert || perm_send){
					build = build.append(Text.builder("|").build());
				}
			}
			if(perm_alert){
				build = build.append(Text.builder("alert").onClick(TextActions.suggestCommand("/" + this.getName() + " alert")).build());
				if(perm_send){
					build = build.append(Text.builder("|").build());
				}
			}
			if(perm_send){
				build = build.append(Text.builder("send").onClick(TextActions.suggestCommand("/" + this.getName() + " send")).build());
			}

			build = build.append(Text.builder(">").build());
			help = build.color(TextColors.RED).build();
		} else {
			help = Text.builder("/" + this.getName()).onClick(TextActions.suggestCommand("/" + this.getName()))
					.color(TextColors.RED).build();
		}
		return help;
	}
	
	public Text helpReload(final CommandSource source) {
		return Text.builder("/" + this.getName() + " reload")
					.onClick(TextActions.suggestCommand("/" + this.getName() + " reload"))
					.color(TextColors.RED)
					.build();
	}
	
	public Text helpList(final CommandSource source) {
		return Text.builder("/" + this.getName() + " list")
					.onClick(TextActions.suggestCommand("/" + this.getName() + " list"))
					.color(TextColors.RED)
					.build();
	}
	
	public Text helpSet(final CommandSource source) {
		return Text.builder("/" + this.getName() + " set <" + this.plugin.getEverAPI().getMessages().getArg("player") + "> "
													  + "<" + this.plugin.getEverAPI().getMessages().getArg("mail") + ">")
					.onClick(TextActions.suggestCommand("/" + this.getName() + " list"))
					.color(TextColors.RED)
					.build();
	}
	
	public Text helpDelete(final CommandSource source) {
		return Text.builder("/" + this.getName() + " delete <" + this.plugin.getEverAPI().getMessages().getArg("player") + ">")
					.onClick(TextActions.suggestCommand("/" + this.getName() + " list"))
					.color(TextColors.RED)
					.build();
	}
	
	public Text helpAlert(final CommandSource source) {
		return Text.builder("/" + this.getName() + " alert <" + this.plugin.getEverAPI().getMessages().getArg("message") + ">")
					.onClick(TextActions.suggestCommand("/" + this.getName() + " list"))
					.color(TextColors.RED)
					.build();
	}
	
	public Text helpSend(final CommandSource source) {
		return Text.builder("/" + this.getName() + " send <" + this.plugin.getEverAPI().getMessages().getArg("player") + "> "
													   + "<" + this.plugin.getEverAPI().getMessages().getArg("message") + ">")
					.onClick(TextActions.suggestCommand("/" + this.getName() + " list"))
					.color(TextColors.RED)
					.build();
	}

	@Override
	public List<String> tabCompleter(final CommandSource source, final List<String> args) throws CommandException {
		List<String> suggests = new ArrayList<String>();
		if(args.size() == 1) {
			if(source.hasPermission(this.plugin.getPermissions().get("HELP"))) {
				suggests.add("help");
			}
			if(source.hasPermission(this.plugin.getPermissions().get("RELOAD"))) {
				suggests.add("reload");
			}
			if(source.hasPermission(this.plugin.getPermissions().get("LIST"))) {
				suggests.add("list");
			}
			if(source.hasPermission(this.plugin.getPermissions().get("SET"))) {
				suggests.add("set");
			}
			if(source.hasPermission(this.plugin.getPermissions().get("DELETE"))) {
				suggests.add("delete");
			}
			if(source.hasPermission(this.plugin.getPermissions().get("ALERT"))) {
				suggests.add("alert");
			}
			if(source.hasPermission(this.plugin.getPermissions().get("SEND"))) {
				suggests.add("send");
			}
		} else if(args.size() == 2) {
			if(args.get(0).equalsIgnoreCase("delete")) {
				if(source.hasPermission(this.plugin.getPermissions().get("DELETE"))) {
					suggests.addAll(this.plugin.getService().getMails().keySet());
				}
			} else if(args.get(0).equalsIgnoreCase("set")) {
				if(source.hasPermission(this.plugin.getPermissions().get("SET"))) {
					suggests.addAll(this.plugin.getService().getMails().keySet());
				}
			} else if(args.get(0).equalsIgnoreCase("send")) {
				if(source.hasPermission(this.plugin.getPermissions().get("SEND"))) {
					suggests.addAll(this.plugin.getService().getMails().keySet());
				}
			}
		}
		return suggests;
	}
	
	public boolean execute(final CommandSource source, final List<String> args) throws CommandException {
		boolean resultat = false;
		if(args.size() == 0 || args.get(0).equalsIgnoreCase("help")) {
			if(source.hasPermission(this.plugin.getPermissions().get("HELP"))) {
				resultat = commandHelp(source);
			} else {
				source.sendMessage(EAMessages.NO_PERMISSION.getText());
			}
		} else {
			if(args.get(0).equalsIgnoreCase("reload")) {
				if(source.hasPermission(this.plugin.getPermissions().get("RELOAD"))) {
					resultat = commandReload(source);
				} else {
					source.sendMessage(EAMessages.NO_PERMISSION.getText());
				}
			} else if(args.get(0).equalsIgnoreCase("list")) {
				if(source.hasPermission(this.plugin.getPermissions().get("LIST"))) {
					resultat = commandList(source);
				} else {
					source.sendMessage(EAMessages.NO_PERMISSION.getText());
				}
			} else if(args.get(0).equalsIgnoreCase("delete")) {
				if(source.hasPermission(this.plugin.getPermissions().get("DELETE"))) {
					resultat = commandDel(source, args.get(1));
				} else {
					source.sendMessage(EAMessages.NO_PERMISSION.getText());
				}
			} else if(args.get(0).equalsIgnoreCase("set")) {
				if(source.hasPermission(this.plugin.getPermissions().get("SET"))) {
					if(args.size() == 3) {
						resultat = commandSet(source, args.get(1), args.get(2));
					} else {
						source.sendMessage(this.helpSet(source));
					}
				} else {
					source.sendMessage(EAMessages.NO_PERMISSION.getText());
				}
			} else if(args.get(0).equalsIgnoreCase("alert")) {
				if(source.hasPermission(this.plugin.getPermissions().get("ALERT"))) {
					if(args.size() >= 2) {
						args.remove(0);
						resultat = commandAlert(source, getMessage(args));
					} else {
						source.sendMessage(this.helpAlert(source));
					}
				} else {
					source.sendMessage(EAMessages.NO_PERMISSION.getText());
				}
			} else if(args.get(0).equalsIgnoreCase("send")) {
				if(source.hasPermission(this.plugin.getPermissions().get("SEND"))) {
					if(args.size() >= 3) {
						String identifier = args.get(1);
						args.remove(1);
						args.remove(0);
						resultat = commandSend(source, identifier, getMessage(args));
					} else {
						source.sendMessage(this.helpSend(source));
					}
				} else {
					source.sendMessage(EAMessages.NO_PERMISSION.getText());
				}
			} else {
				source.sendMessage(help(source));
			}
		}
		return resultat;
	}
	
	private boolean commandHelp(CommandSource source) {
		LinkedHashMap<String, ESubCommand> commands = new LinkedHashMap<String, ESubCommand>();
		if(source.hasPermission(this.plugin.getPermissions().get("RELOAD"))) {
			commands.put(this.getName() + " reload", new ESubCommand(this.helpReload(source), this.plugin.getEverAPI().getMessages().getText("RELOAD_DESCRIPTION")));
		}
		if(source.hasPermission(this.plugin.getPermissions().get("LIST"))) {
			commands.put(this.getName() + " list", new ESubCommand(this.helpList(source), this.plugin.getMessages().getText("LIST_DESCRIPTION")));
		}
		if(source.hasPermission(this.plugin.getPermissions().get("SET"))) {
			commands.put(this.getName() + " set", new ESubCommand(this.helpSet(source), this.plugin.getMessages().getText("SET_DESCRIPTION")));
		}
		if(source.hasPermission(this.plugin.getPermissions().get("DELETE"))) {
			commands.put(this.getName() + " delete", new ESubCommand(this.helpDelete(source), this.plugin.getMessages().getText("DELETE_DESCRIPTION")));
		}
		if(source.hasPermission(this.plugin.getPermissions().get("ALERT"))) {
			commands.put(this.getName() + " alert", new ESubCommand(this.helpAlert(source), this.plugin.getMessages().getText("ALERT_DESCRIPTION")));
		}
		if(source.hasPermission(this.plugin.getPermissions().get("SEND"))) {
			commands.put(this.getName() + " send", new ESubCommand(this.helpSend(source), this.plugin.getMessages().getText("SEND_DESCRIPTION")));
		}
		this.plugin.getEverAPI().getManagerService().getEPagination().helpSubCommand(commands, source, this.plugin);
		return true;
	}

	private boolean commandReload(CommandSource player) {
		this.plugin.reload();
		player.sendMessage(EChat.of(this.plugin.getMessages().getMessage("PREFIX") + this.plugin.getEverAPI().getMessages().getMessage("RELOAD_COMMAND")));
		return true;
	}
	
	private boolean commandList(CommandSource player) {
		List<Text> list = new ArrayList<Text>();
		
		// Aucune adresse mail
		if(this.plugin.getService().getMails().isEmpty()) {
			list.add(this.plugin.getMessages().getText("LIST_EMPTY"));
		// Des adresses sont enregistré 
		} else {
			for (Entry<String, String> mail : this.plugin.getService().getMails().entrySet()) {
				list.add(EChat.of(this.plugin.getMessages().getMessage("LIST_LINE")
						.replaceAll("<player>", mail.getKey())
						.replaceAll("<address>", mail.getValue())));
			}
		}
		
		this.plugin.getEverAPI().getManagerService().getEPagination().sendTo(
				this.plugin.getMessages().getText("LIST_TITLE"), 
				list, player);
		return true;
	}
	
	private boolean commandSet(CommandSource player, String identifier, String address) {
		String address_init = this.plugin.getService().getMails().get(identifier);
		// Adresse mail différente
		if(address_init == null || !address_init.equalsIgnoreCase(address)) {
			// Adresse mail correcte
			if(this.plugin.getService().setMail(identifier, address)) {
				// Joueur identique
				if(player.getName().equalsIgnoreCase(identifier)) {
					player.sendMessage(EChat.of(this.plugin.getMessages().getMessage("PREFIX") + this.plugin.getMessages().getMessage("SET_PLAYER")
							.replaceAll("<player>", identifier)
							.replaceAll("<address>", address)));
				// Joueur différent
				} else {
					player.sendMessage(EChat.of(this.plugin.getMessages().getMessage("PREFIX") + this.plugin.getMessages().getMessage("SET_EQUALS")
							.replaceAll("<player>", identifier)
							.replaceAll("<address>", address)));
				}
			// Adresse mail incorrect
			} else {
				player.sendMessage(EChat.of(this.plugin.getMessages().getMessage("PREFIX") + this.plugin.getMessages().getMessage("SET_ERROR_PATTERN")
						.replaceAll("<player>", identifier)
						.replaceAll("<address>", address)));
			}
		// Adresse mail identique
		} else {
			player.sendMessage(EChat.of(this.plugin.getMessages().getMessage("PREFIX") + this.plugin.getMessages().getMessage("SET_ERROR_EQUALS")
					.replaceAll("<player>", identifier)
					.replaceAll("<address>", address)));
		}
		return true;
	}
	
	private boolean commandDel(CommandSource player, String identifier) {
		String address = this.plugin.getService().getMails().get(identifier);
		// Adresse mail enregistré
		if(address != null) {
			this.plugin.getService().removeMail(identifier);
			// Joueur identique
			if(player.getName().equalsIgnoreCase(identifier)) {
				player.sendMessage(EChat.of(this.plugin.getMessages().getMessage("PREFIX") + this.plugin.getMessages().getMessage("DELETE_EQUALS")
						.replaceAll("<player>", identifier)
						.replaceAll("<address>", address)));
			// Joueur différent
			} else {
				player.sendMessage(EChat.of(this.plugin.getMessages().getMessage("PREFIX") + this.plugin.getMessages().getMessage("DELETE_PLAYER")
						.replaceAll("<player>", identifier)
						.replaceAll("<address>", address)));
			}
			return true;
		// Aucune adresse mail enregistré
		} else {
			// Joueur identique
			if(player.getName().equalsIgnoreCase(identifier)) {
				player.sendMessage(EChat.of(this.plugin.getMessages().getMessage("PREFIX") + this.plugin.getMessages().getMessage("DELETE_ERROR_EQUALS")
						.replaceAll("<player>", identifier)));
			// Joueur différent
			} else {
				player.sendMessage(EChat.of(this.plugin.getMessages().getMessage("PREFIX") + this.plugin.getMessages().getMessage("DELETE_ERROR_PLAYER")
						.replaceAll("<player>", identifier)));
			}
		}
		return false;
	}
	
	private boolean commandAlert(CommandSource player, String message) {
		// Des adresses sont enregistré
		if(!this.plugin.getService().getMails().isEmpty()) {
			// Mail envoyé
			if(this.plugin.getService().alert(
					this.plugin.getMessages().getMessage("ALERT_OBJECT")
						.replaceAll("<player>", player.getName()), 
					this.plugin.getMessages().getMessage("ALERT_MESSAGE")
						.replaceAll("<player>", player.getName())
						.replaceAll("<message>", message))) {
				
				player.sendMessage(EChat.of(this.plugin.getMessages().getMessage("PREFIX") + this.plugin.getMessages().getMessage("ALERT_PLAYER")
						.replaceAll("<message>", message)));
				return true;
			// Erreur lors de l'envoie
			} else {
				player.sendMessage(EChat.of(this.plugin.getMessages().getMessage("PREFIX") + this.plugin.getEverAPI().getMessages().getMessage("COMMAND_ERROR")));
			}
		// Aucune adresse mail
		} else {
			player.sendMessage(EChat.of(this.plugin.getMessages().getMessage("PREFIX") + this.plugin.getEverAPI().getMessages().getMessage("ALERT_ERROR")));
		}
		return false;
	}
	
	private boolean commandSend(CommandSource player, String identifier, String message) {
		String address = this.plugin.getService().getMails().get(identifier);
		// Adresse mail connu
		if(address != null) {
			// Mail envoyé
			if(this.plugin.getService().send(
					address,
					this.plugin.getMessages().getMessage("SEND_OBJECT")
					.replaceAll("<player>", player.getName()), 
					this.plugin.getMessages().getMessage("SEND_MESSAGE")
						.replaceAll("<player>", player.getName())
						.replaceAll("<message>", message))) {
				// Joueur identique
				if(player.getName().equalsIgnoreCase(identifier)) {
					player.sendMessage(EChat.of(this.plugin.getMessages().getMessage("PREFIX") + this.plugin.getMessages().getMessage("SEND_EQUALS")
							.replaceAll("<player>", identifier)
							.replaceAll("<address>", address)
							.replaceAll("<message>", message)));
				// Joueur différent
				} else {
					player.sendMessage(EChat.of(this.plugin.getMessages().getMessage("PREFIX") + this.plugin.getMessages().getMessage("SEND_PLAYER")
							.replaceAll("<player>", identifier)
							.replaceAll("<address>", address)
							.replaceAll("<message>", message)));
				}
				return true;
			// Erreur lors de l'envoie
			} else {
				player.sendMessage(EChat.of(this.plugin.getMessages().getMessage("PREFIX") + this.plugin.getEverAPI().getMessages().getMessage("COMMAND_ERROR")));
			}
		// Aucune adresse mail connu
		} else {
			player.sendMessage(EChat.of(this.plugin.getMessages().getMessage("PREFIX") + this.plugin.getMessages().getMessage("SEND_ERROR")
					.replaceAll("<player>", identifier)
					.replaceAll("<message>", message)));
		}
		return false;
	}
}
