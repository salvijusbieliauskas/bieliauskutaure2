# Bieliausku Taure 2

An in-development Minecraft [Bukkit](https://dev.bukkit.org/)/[Purpur](https://purpurmc.org/) plugin for managing minigames, teams, and player roles in a multiplayer server
environment. Intended to facilitate tournaments of around 100 players. The plugin does not have a flexible locale and is
made only with the Lithuanian audience in mind.

## Features

### Minigames System
- **Minigame Manager**: Central system for loading and managing minigames
- **Minigames**:
  - Parkour: A simple parkour minigame, acting as an example
> [!NOTE]
> Additional minigames will be added after the project planning phase is concluded

### Team Management
- Create, modify, and manage teams
- Team-based permissions and functionality
- Customizable team properties

### Role-based Player System
- Different player types with specific permissions:
  - Administrators
  - Streamers
  - Participants
  - Spectators
- Serializable player data storage

### Utility Features
- Custom logging system with adjustable debug levels
- Global event listeners for server-wide functionality
- Sound management utilities
- Configuration management

## Technical Information

- **Java Version**: Built with Java 21
- **Minecraft Version**: 1.21.1
- **Server Type**: Likely compatible with all [Bukkit](https://dev.bukkit.org/)-derived servers, built with [Purpur](https://purpurmc.org/) in mind
- **Build System**: Maven

## Installation

1. Build the plugin to create a JAR artifact

2. Place the generated JAR file in your server's `plugins` folder

3. Restart the server

## Configuration

The plugin will generate necessary configuration files on first run. These can be edited to customize the plugin's behavior.

## Development

To set up the development environment:

1. Clone the repository
2. Import as Maven project in your IDE
3. Ensure Java 21 is configured as the project SDK