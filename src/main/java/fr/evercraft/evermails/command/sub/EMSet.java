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
package fr.evercraft.evermails.command.sub;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import fr.evercraft.everapi.EAMessage.EAMessages;
import fr.evercraft.everapi.plugin.command.ESubCommand;
import fr.evercraft.evermails.EMCommand;
import fr.evercraft.evermails.EMMessage.EMMessages;
import fr.evercraft.evermails.EMPermissions;
import fr.evercraft.evermails.EverMails;

public class EMSet extends ESubCommand<EverMails> {
	public EMSet(final EverMails plugin, final EMCommand command) {
        super(plugin, command, "set");
    }
	
	public boolean testPermission(final CommandSource source) {
		return source.hasPermission(EMPermissions.SET.get());
	}

	public Text description(final CommandSource source) {
		return EMMessages.SET_DESCRIPTION.getText();
	}
	
	public Collection<String> subTabCompleter(final CommandSource source, final List<String> args) throws CommandException {
		List<String> suggests = new ArrayList<String>();
		if (args.size() == 1) {
			suggests.addAll(this.plugin.getService().getMails().keySet());
		} else if (args.size() == 2) {
			suggests.add("@");
		}
		return suggests;
	}

	public Text help(final CommandSource source) {
		return Text.builder("/" + this.getName() + " <" + EAMessages.ARGS_PLAYER.getString() + "> "
						  + "<" + EAMessages.ARGS_MAIL.getString() + ">")
					.onClick(TextActions.suggestCommand("/" + this.getName() + " "))
					.color(TextColors.RED)
					.build();
	}
	
	public boolean subExecute(final CommandSource source, final List<String> args) {
		if (args.size() == 2) {
			return this.commandSet(source, args.get(0), args.get(1));
		} else {
			source.sendMessage(this.help(source));
			return false;
		}
	}

	private boolean commandSet(CommandSource player, String identifier, String address) {
		String address_init = this.plugin.getService().getMails().get(identifier);
		// Adresse mail identique
		if (address_init != null && address_init.equalsIgnoreCase(address)) {
			EMMessages.SET_ERROR_EQUALS.sender()
				.replace("<player>", identifier)
				.replace("<address>", address)
				.sendTo(player);
			return false;
		}
		
		// Adresse mail incorrect
		if (!this.plugin.getService().setMail(identifier, address)) {
			EMMessages.SET_ERROR_PATTERN.sender()
				.replace("<player>", identifier)
				.replace("<address>", address)
				.sendTo(player);
			return false;
		}
		
		// Joueur identique
		if (player.getName().equalsIgnoreCase(identifier)) {
			EMMessages.SET_PLAYER.sender()
				.replace("<player>", identifier)
				.replace("<address>", address)
				.sendTo(player);
		// Joueur différent
		} else {
			EMMessages.SET_EQUALS.sender()
				.replace("<player>", identifier)
				.replace("<address>", address)
				.sendTo(player);
		}
		return true;
	}
	
}
