package cc.ayakurayuki.ioc.module.entity;

/**
 * cc.ayakurayuki.ioc.module.entity
 *
 * @author ayakurayuki
 * @date 2018/3/15-14:01
 */
public class Man {

    private String command;

    private String description;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "[\n" +
                "  \"command\": \"" + command + "\",\n" +
                "  \"description\": \"\n" + description + "\n\"\n" +
                "]";
    }
}
