import java.util.*;

public class CommandProcessor {
    private Map<String, Command> commands = new HashMap<>();

    public void registerCommand(String commandName, Command command) {
        commands.put(commandName, command);
    }

    public void processInput(String input) {
        Command command = commands.get(input);
        if (command != null) {
            command.execute();
        } else {
            System.out.println("Unknown command. Type 'help' for available commands.");
        }
    }

    public void displayCommands() {
        System.out.println("Available commands:");
        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            String commandName = entry.getKey();
            Command command = entry.getValue();
            String commandInfo = command.getInfo();
            System.out.println("- " + commandName + ": " + commandInfo);
        }
    }
}
