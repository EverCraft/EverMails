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

import fr.evercraft.everapi.plugin.EPermission;
import fr.evercraft.everapi.plugin.EPlugin;

public class EMPermission extends EPermission {

	public EMPermission(final EPlugin plugin) {
		super(plugin);
	}

	@Override
	protected void load() {
		add("EVERMAILS", "command");
		
		add("HELP", "help");
		add("RELOAD", "reload");
		
		add("LIST", "manage.list");
		add("SET", "manage.set");
		add("DELETE", "manage.delete");
		
		add("ALERT", "use.alert");
		add("SEND", "use.send");
	}
}
