# AdminChat
Bukkit plugin that allows players who have OP privileges or the correct permissions to switch to an admin only chat room in Minecraft

# Permissions
- chat.admin: 
  - default: op
  - description: allows user to talk in 'admin chat'
- chat.global:
  - default: everyone
  - description: allows user to talk in 'global chat'
- chat.broadcast:
  - default: op
  - description: allows user to broadcast a server-wide message

# Features
Admins are able to chat in a special chatroom that only they can see. Admins can switch what room they are in, admin or global, and are always able to see both no matter what chat room they are in
Admins are also able to send a server-wide message to everyone anonymously as the server.

# Commands
- /chat admin
- /chat global
- /chat status
- /broadcast <message>
