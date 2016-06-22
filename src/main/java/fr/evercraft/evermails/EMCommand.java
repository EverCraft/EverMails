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
import fr.evercraft.everapi.plugin.command.ECommand;
import fr.evercraft.everapi.services.pagination.CommandPagination;
import fr.evercraft.evermails.EMMessage.EMMessages;

public class EMCommand extends ECommand<EverMails> {

	public EMCommand(final EverMails plugin) {
		super(plugin, "evermails");
    }
	
	@Override
	public boolean testPermission(final CommandSource source) {
		return source.hasPermission(EMPermissions.EVERMAILS.get());
	}
	
	@Override
	public Text description(final CommandSource source) {
		return EMMessages.DESCRIPTION.getText();
	}

	@Override
	public Text help(final CommandSource source) {
		boolean perm_help = source.hasPermission(EMPermissions.HELP.get());
		boolean perm_reload = source.hasPermission(EMPermissions.RELOAD.get());
		boolean perm_list = source.hasPermission(EMPermissions.LIST.get());
		boolean perm_set = source.hasPermission(EMPermissions.SET.get());
		boolean perm_delete = source.hasPermission(EMPermissions.DELETE.get());
		boolean perm_alert = source.hasPermission(EMPermissions.ALERT.get());
		boolean perm_send = source.hasPermission(EMPermissions.SEND.get());
		
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
		return Text.builder("/" + this.getName() + " set <" + EAMessages.ARGS_PLAYER.get() + "> "
													  + "<" + EAMessages.ARGS_MAIL.get() + ">")
					.onClick(TextActions.suggestCommand("/" + this.getName() + " list"))
					.color(TextColors.RED)
					.build();
	}
	
	public Text helpDelete(final CommandSource source) {
		return Text.builder("/" + this.getName() + " delete <" + EAMessages.ARGS_PLAYER.get() + ">")
					.onClick(TextActions.suggestCommand("/" + this.getName() + " list"))
					.color(TextColors.RED)
					.build();
	}
	
	public Text helpAlert(final CommandSource source) {
		return Text.builder("/" + this.getName() + " alert <" + EAMessages.ARGS_MESSAGE.get() + ">")
					.onClick(TextActions.suggestCommand("/" + this.getName() + " list"))
					.color(TextColors.RED)
					.build();
	}
	
	public Text helpSend(final CommandSource source) {
		return Text.builder("/" + this.getName() + " send <" + EAMessages.ARGS_PLAYER.get() + "> "
													   + "<" + EAMessages.ARGS_MESSAGE.get() + ">")
					.onClick(TextActions.suggestCommand("/" + this.getName() + " list"))
					.color(TextColors.RED)
					.build();
	}

	@Override
	public List<String> tabCompleter(final CommandSource source, final List<String> args) throws CommandException {
		List<String> suggests = new ArrayList<String>();
		if(args.size() == 1) {
			if(source.hasPermission(EMPermissions.HELP.get())) {
				suggests.add("help");
			}
			if(source.hasPermission(EMPermissions.RELOAD.get())) {
				suggests.add("reload");
			}
			if(source.hasPermission(EMPermissions.LIST.get())) {
				suggests.add("list");
			}
			if(source.hasPermission(EMPermissions.SET.get())) {
				suggests.add("set");
			}
			if(source.hasPermission(EMPermissions.DELETE.get())) {
				suggests.add("delete");
			}
			if(source.hasPermission(EMPermissions.ALERT.get())) {
				suggests.add("alert");
			}
			if(source.hasPermission(EMPermissions.SEND.get())) {
				suggests.add("send");
			}
		} else if(args.size() == 2) {
			if(args.get(0).equalsIgnoreCase("delete")) {
				if(source.hasPermission(EMPermissions.DELETE.get())) {
					suggests.addAll(this.plugin.getService().getMails().keySet());
				}
			} else if(args.get(0).equalsIgnoreCase("set")) {
				if(source.hasPermission(EMPermissions.SET.get())) {
					suggests.addAll(this.plugin.getService().getMails().keySet());
				}
			} else if(args.get(0).equalsIgnoreCase("send")) {
				if(source.hasPermission(EMPermissions.SEND.get())) {
					suggests.addAll(this.plugin.getService().getMails().keySet());
				}
			}
		}
		return suggests;
	}
	
	public boolean execute(final CommandSource source, final List<String> args) throws CommandException {
		boolean resultat = false;
		if(args.size() == 0 || args.get(0).equalsIgnoreCase("help")) {
			if(source.hasPermission(EMPermissions.HELP.get())) {
				resultat = commandHelp(source);
			} else {
				source.sendMessage(EAMessages.NO_PERMISSION.getText());
			}
		} else {
			if(args.get(0).equalsIgnoreCase("reload")) {
				if(source.hasPermission(EMPermissions.RELOAD.get())) {
					resultat = commandReload(source);
				} else {
					source.sendMessage(EAMessages.NO_PERMISSION.getText());
				}
			} else if(args.get(0).equalsIgnoreCase("list")) {
				if(source.hasPermission(EMPermissions.LIST.get())) {
					resultat = commandList(source);
				} else {
					source.sendMessage(EAMessages.NO_PERMISSION.getText());
				}
			} else if(args.get(0).equalsIgnoreCase("delete")) {
				if(source.hasPermission(EMPermissions.DELETE.get())) {
					resultat = commandDel(source, args.get(1));
				} else {
					source.sendMessage(EAMessages.NO_PERMISSION.getText());
				}
			} else if(args.get(0).equalsIgnoreCase("set")) {
				if(source.hasPermission(EMPermissions.SET.get())) {
					if(args.size() == 3) {
						resultat = commandSet(source, args.get(1), args.get(2));
					} else {
						source.sendMessage(this.helpSet(source));
					}
				} else {
					source.sendMessage(EAMessages.NO_PERMISSION.getText());
				}
			} else if(args.get(0).equalsIgnoreCase("alert")) {
				if(source.hasPermission(EMPermissions.ALERT.get())) {
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
				if(source.hasPermission(EMPermissions.SEND.get())) {
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
		LinkedHashMap<String, CommandPagination> commands = new LinkedHashMap<String, CommandPagination>();
		/*
		if(source.hasPermission(EMPermissions.RELOAD.get())) {
			commands.put(this.getName() + " reload", new CommandPagination(this.helpReload(source), EAMessages.RELOAD_DESCRIPTION.getText()));
		}
		if(source.hasPermission(EMPermissions.LIST.get())) {
			commands.put(this.getName() + " list", new CommandPagination(this.helpList(source), EMMessages.LIST_DESCRIPTION.getText()));
		}
		if(source.hasPermission(EMPermissions.SET.get())) {
			commands.put(this.getName() + " set", new CommandPagination(this.helpSet(source), EMMessages.SET_DESCRIPTION.getText()));
		}
		if(source.hasPermission(EMPermissions.DELETE.get())) {
			commands.put(this.getName() + " delete", new CommandPagination(this.helpDelete(source), EMMessages.DELETE_DESCRIPTION.getText()));
		}
		if(source.hasPermission(EMPermissions.ALERT.get())) {
			commands.put(this.getName() + " alert", new CommandPagination(this.helpAlert(source), EMMessages.ALERT_DESCRIPTION.getText()));
		}
		if(source.hasPermission(EMPermissions.SEND.get())) {
			commands.put(this.getName() + " send", new CommandPagination(this.helpSend(source), EMMessages.SEND_DESCRIPTION.getText()));
		}*/
		this.plugin.getEverAPI().getManagerService().getEPagination().helpSubCommand(commands, source, this.plugin);
		return true;
	}

	private boolean commandReload(CommandSource player) {
		this.plugin.reload();
		player.sendMessage(EChat.of(EMMessages.PREFIX.get() + EAMessages.RELOAD_COMMAND.get()));
		return true;
	}
	
	private boolean commandList(CommandSource player) {
		List<Text> list = new ArrayList<Text>();
		
		// Aucune adresse mail
		if(this.plugin.getService().getMails().isEmpty()) {
			list.add(EMMessages.LIST_EMPTY.getText());
		// Des adresses sont enregistré 
		} else {
			for (Entry<String, String> mail : this.plugin.getService().getMails().entrySet()) {
				list.add(EChat.of(EMMessages.LIST_LINE.get()
						.replaceAll("<player>", mail.getKey())
						.replaceAll("<address>", mail.getValue())));
			}
		}
		
		this.plugin.getEverAPI().getManagerService().getEPagination().sendTo(
				EMMessages.LIST_TITLE.getText(), 
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
					player.sendMessage(EChat.of(EMMessages.PREFIX.get() + EMMessages.SET_PLAYER.get()
							.replaceAll("<player>", identifier)
							.replaceAll("<address>", address)));
				// Joueur différent
				} else {
					player.sendMessage(EChat.of(EMMessages.PREFIX.get() + EMMessages.SET_EQUALS.get()
							.replaceAll("<player>", identifier)
							.replaceAll("<address>", address)));
				}
			// Adresse mail incorrect
			} else {
				player.sendMessage(EChat.of(EMMessages.PREFIX.get() + EMMessages.SET_ERROR_PATTERN.get()
						.replaceAll("<player>", identifier)
						.replaceAll("<address>", address)));
			}
		// Adresse mail identique
		} else {
			player.sendMessage(EChat.of(EMMessages.PREFIX.get() + EMMessages.SET_ERROR_EQUALS.get()
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
				player.sendMessage(EChat.of(EMMessages.PREFIX.get() + EMMessages.DELETE_EQUALS.get()
						.replaceAll("<player>", identifier)
						.replaceAll("<address>", address)));
			// Joueur différent
			} else {
				player.sendMessage(EChat.of(EMMessages.PREFIX.get() + EMMessages.DELETE_PLAYER.get()
						.replaceAll("<player>", identifier)
						.replaceAll("<address>", address)));
			}
			return true;
		// Aucune adresse mail enregistré
		} else {
			// Joueur identique
			if(player.getName().equalsIgnoreCase(identifier)) {
				player.sendMessage(EChat.of(EMMessages.PREFIX.get() + EMMessages.DELETE_ERROR_EQUALS.get()
						.replaceAll("<player>", identifier)));
			// Joueur différent
			} else {
				player.sendMessage(EChat.of(EMMessages.PREFIX.get() + EMMessages.DELETE_ERROR_PLAYER.get()
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
					EMMessages.ALERT_OBJECT.get()
						.replaceAll("<player>", player.getName()), 
					EMMessages.ALERT_MESSAGE.get()
						.replaceAll("<player>", player.getName())
						.replaceAll("<message>", message))) {
				
				player.sendMessage(EChat.of(EMMessages.PREFIX.get() + EMMessages.ALERT_PLAYER.get()
						.replaceAll("<message>", message)));
				return true;
			// Erreur lors de l'envoie
			} else {
				player.sendMessage(EChat.of(EMMessages.PREFIX.get() + EAMessages.COMMAND_ERROR.get()));
			}
		// Aucune adresse mail
		} else {
			player.sendMessage(EChat.of(EMMessages.PREFIX.get() + EMMessages.ALERT_ERROR.get()));
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
					EMMessages.SEND_OBJECT.get()
					.replaceAll("<player>", player.getName()), 
					EMMessages.SEND_MESSAGE.get()
						.replaceAll("<player>", player.getName())
						.replaceAll("<message>", message))) {
				// Joueur identique
				if(player.getName().equalsIgnoreCase(identifier)) {
					player.sendMessage(EChat.of(EMMessages.PREFIX.get() + EMMessages.SEND_EQUALS.get()
							.replaceAll("<player>", identifier)
							.replaceAll("<address>", address)
							.replaceAll("<message>", message)));
				// Joueur différent
				} else {
					player.sendMessage(EChat.of(EMMessages.PREFIX.get() + EMMessages.SEND_PLAYER.get()
							.replaceAll("<player>", identifier)
							.replaceAll("<address>", address)
							.replaceAll("<message>", message)));
				}
				return true;
			// Erreur lors de l'envoie
			} else {
				player.sendMessage(EChat.of(EMMessages.PREFIX.get() + EAMessages.COMMAND_ERROR.get()));
			}
		// Aucune adresse mail connu
		} else {
			player.sendMessage(EChat.of(EMMessages.PREFIX.get() + EMMessages.SEND_ERROR.get()
					.replaceAll("<player>", identifier)
					.replaceAll("<message>", message)));
		}
		return false;
	}
}
