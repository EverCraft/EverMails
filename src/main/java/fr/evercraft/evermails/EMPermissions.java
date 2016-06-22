package fr.evercraft.evermails;

import org.spongepowered.api.command.CommandSource;

import com.google.common.base.Preconditions;

import fr.evercraft.everapi.plugin.EnumPermission;

public enum EMPermissions implements EnumPermission {
	EVERMAILS("command"),
	
	HELP("help"),
	RELOAD("reload"),
	
	LIST("manage.list"),
	SET("manage.set"),
	DELETE("manage.delete"),
	
	ALERT("use.alert"),
	SEND("use.send");
	
	private final static String prefix = "evermail";
	
	private final String permission;
    
    private EMPermissions(final String permission) {   	
    	Preconditions.checkNotNull(permission, "La permission '" + this.name() + "' n'est pas définit");
    	
    	this.permission = permission;
    }

    public String get() {
		return EMPermissions.prefix + "." + this.permission;
	}
    
    public boolean has(CommandSource player) {
    	return player.hasPermission(this.get());
    }
}
