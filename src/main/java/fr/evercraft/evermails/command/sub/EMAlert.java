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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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

public class EMAlert extends ESubCommand<EverMails> {
	public EMAlert(final EverMails plugin, final EMCommand command) {
        super(plugin, command, "alert");
    }
	
	public boolean testPermission(final CommandSource source) {
		return source.hasPermission(EMPermissions.ALERT.get());
	}

	public Text description(final CommandSource source) {
		return EMMessages.ALERT_DESCRIPTION.getText();
	}
	
	public Collection<String> tabCompleter(final CommandSource source, final List<String> args) throws CommandException {
		if (args.size() == 1) {
			return Arrays.asList("Message...");
		}
		return Arrays.asList();
	}

	public Text help(final CommandSource source) {
		return Text.builder("/" + this.getName() + " <" + EAMessages.ARGS_MESSAGE.getString() + ">")
				.onClick(TextActions.suggestCommand("/" + this.getName() + " "))
				.color(TextColors.RED)
				.build();
	}
	
	public CompletableFuture<Boolean> execute(final CommandSource source, final List<String> args) {
		if (args.size() == 1) {
			return this.commandAlert(source, args.get(1));
		} else {
			source.sendMessage(this.help(source));
			return CompletableFuture.completedFuture(false);
		}
	}

	private CompletableFuture<Boolean> commandAlert(CommandSource player, String message) {
		// Aucune adresse mail
		if (this.plugin.getService().getMails().isEmpty()) {
			EMMessages.ALERT_ERROR.sender()
				.replace("{message}", message)
				.sendTo(player);
			return CompletableFuture.completedFuture(false);
		}
		
		// Mail envoyé
		if (!this.plugin.getService().alert(
				EMMessages.ALERT_OBJECT.getFormat()
					.toString("{player}", player.getName()), 
				EMMessages.ALERT_MESSAGE.getFormat().toString(
					"{player}", player.getName(),
					"{message}", message))) {
			
			EAMessages.COMMAND_ERROR.sender()
				.prefix(EMMessages.PREFIX)
				.sendTo(player);
			return CompletableFuture.completedFuture(false);
		}
		
		EMMessages.ALERT_PLAYER.sender()
			.replace("{message}", message)
			.sendTo(player);
		return CompletableFuture.completedFuture(true);
	}
	
}
