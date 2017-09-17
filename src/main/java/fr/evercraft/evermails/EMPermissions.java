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

import fr.evercraft.everapi.plugin.EnumPermission;
import fr.evercraft.everapi.plugin.file.EnumMessage;
import fr.evercraft.evermails.EMMessage.EMMessages;

public enum EMPermissions implements EnumPermission {
	EVERMAILS("commands.execute", EMMessages.PERMISSIONS_COMMANDS_EXECUTE),
	HELP("commands.help", EMMessages.PERMISSIONS_COMMANDS_HELP),
	RELOAD("commands.reload", EMMessages.PERMISSIONS_COMMANDS_RELOAD),	
	LIST("commands.list", EMMessages.PERMISSIONS_COMMANDS_LIST),
	SET("commands.set", EMMessages.PERMISSIONS_COMMANDS_SET),
	DELETE("commands.delete", EMMessages.PERMISSIONS_COMMANDS_DELETE),
	ALERT("commands.alert", EMMessages.PERMISSIONS_COMMANDS_ALERT),
	SEND("commands.send", EMMessages.PERMISSIONS_COMMANDS_SEND);
	
	private static final String PREFIX = "evermails";
	
	private final String permission;
	private final EnumMessage message;
	private final boolean value;
    
    private EMPermissions(final String permission, final EnumMessage message) {
    	this(permission, message, false);
    }
    
    private EMPermissions(final String permission, final EnumMessage message, final boolean value) {   	    	
    	this.permission = PREFIX + "." + permission;
    	this.message = message;
    	this.value = value;
    }

    @Override
    public String get() {
    	return this.permission;
	}

	@Override
	public boolean getDefault() {
		return this.value;
	}

	@Override
	public EnumMessage getMessage() {
		return this.message;
	}
}
